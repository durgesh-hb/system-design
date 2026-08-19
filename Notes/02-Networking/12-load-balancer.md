## Load Balancer

A Load Balancer is an important component in System Design that distributes incoming traffic across multiple backend servers.

It helps improve:

- Scalability
- Availability
- Performance
- Fault tolerance

<h2>Why Do We Need a Load Balancer?</h2>

Imagine your application has only one server:

```text
             10,000 Users
                  │
                  ▼
             +---------+
             | Server  |
             +---------+
```

Initially, it's fine.

But your application grows:

```text
            1 Million Users
                  │
                  ▼
             +---------+
             | Server  |
             |   🔥    |
             +---------+
```

The server may become:

- CPU overloaded
- Memory overloaded
- Network overloaded
- Slow
- Eventually unavailable

So we add more servers:

```text
          1 Million Users
                 │
       ┌─────────┼─────────┐
       ▼         ▼         ▼
    Server 1  Server 2  Server 3
```

But now we have a new problem:

> **Who decides which server receives each request?**

That's the job of the **Load Balancer**.

<h2>What is a Load Balancer?</h2>

> **Definition : A Load Balancer distributes incoming traffic across multiple backend servers.**

Simply:

> **Load Balancer = Traffic Distributor**

```text
                  Users
                    │
                    ▼
             +-------------+
             |Load Balancer|
             +-------------+
              /     |     \
             ▼      ▼      ▼
           S1       S2      S3
```

Instead of sending everything to one server, traffic gets distributed.

<h2>Real-Life Analogy</h2>

Imagine a toll plaza.

There are three toll booths:

```text
Cars
  │
  ▼
Traffic Controller
  ├── Booth 1
  ├── Booth 2
  └── Booth 3
```

The controller decides:

> "Go to Booth 2."

The Load Balancer does the same thing with servers.

<h2>Why Not Just Use DNS?</h2>

You might wonder:

> "DNS already gives us an IP. Why do we need a Load Balancer?"

DNS can help direct users to different servers or locations, but it isn't designed to continuously monitor individual application servers and intelligently distribute every request.

A Load Balancer can:

- Check server health
- Distribute individual requests
- Remove unhealthy servers
- Apply routing rules

That's why both can exist in the same architecture.

<h2>Basic Architecture</h2>

```text
                 Users
                   │
                   ▼
                  DNS
                   │
                   ▼
            Load Balancer
             /     |     \
            ▼      ▼      ▼
          S1       S2      S3
            \      |      /
                   ▼
                Database
```

Now suppose Server 2 crashes.

```text
         Load Balancer
          /         \
         ▼           ▼
       S1 ✅        S2 ❌
                     S3 ✅
```

The Load Balancer stops sending traffic to Server 2.

Users can continue using the application.

This is one reason Load Balancers improve **availability**.

<h2>Load Balancing Algorithms</h2>

The method used by a Load Balancer to decide which server receives a request is called a **Load Balancing Algorithm**.

Common algorithms include:

- Round Robin
- Weighted Round Robin
- Least Connections
- IP Hash

<h2>Round Robin</h2>

The simplest approach.

Suppose we have:

```text
S1
S2
S3
```

Requests are distributed:

```text
Request 1 → S1
Request 2 → S2
Request 3 → S3
Request 4 → S1
Request 5 → S2
Request 6 → S3
```

Like taking turns.

<h3>Advantage</h3>

Very simple.

<h3>Problem</h3>

It doesn't know whether one server is already busy.

Example:

```text
S1 → 90% busy
S2 → 10% busy
S3 → 10% busy
```

Round Robin may still send the next request to S1.

<h2>Weighted Round Robin</h2>

What if servers have different capacities?

```text
S1 → Powerful
S2 → Medium
S3 → Small
```

Give them weights:

```text
S1 → Weight 3
S2 → Weight 2
S3 → Weight 1
```

More traffic goes to the more powerful server.

Useful when servers aren't identical.

<h2>Least Connections</h2>

Instead of simply taking turns, the Load Balancer checks:

> **Which server currently has the fewest active connections?**

Example:

```text
S1 → 100 connections
S2 → 20 connections
S3 → 50 connections
```

Next request:

```text
              ↓
         Load Balancer
              │
              ▼
             S2
```

Because S2 has the fewest active connections.

This can work better when requests have different durations.

<h2>IP Hash</h2>

The Load Balancer uses the client's IP address to determine the server.

Conceptually:

```text
Client IP
   ↓
Hash Function
   ↓
Server Selection
```

Example:

```text
User A → S1
User B → S2
User C → S3
```

The same client tends to go to the same server.

This can be useful for **session affinity / sticky-session-like behavior**, although modern systems often prefer storing session state outside individual servers.

<h2>Health Checks</h2>

This is extremely important.

Imagine:

```text
S1 ✅
S2 ❌
S3 ✅
```

How does the Load Balancer know S2 is broken?

It performs **health checks**.

For example:

```text
GET /health
```

Server responds:

```text
200 OK
```

Server is healthy.

If it doesn't respond correctly:

```text
Timeout ❌
```

The Load Balancer marks it unhealthy.

```text
Traffic
  │
  ▼
Load Balancer
  │
  ├── S1 ✅
  ├── S2 ❌ ← Remove
  └── S3 ✅
```

This is called **automatic failover**.

<h2>Sticky Sessions</h2>

Suppose a user logs in.

You have:

```text
S1
S2
S3
```

Request 1:

```text
User → S1
```

Request 2:

```text
User → S2
```

If the session exists only in S1's memory, the user may appear logged out on S2.

One solution is **Sticky Sessions**.

```text
User A → S1
User A → S1
User A → S1
```

The Load Balancer tries to keep that user connected to the same server.

<h2>Is Sticky Session Always Good?</h2>

Not really.

It can create problems.

Suppose:

```text
S1 → 10,000 users
S2 → 2,000 users
S3 → 2,000 users
```

S1 becomes overloaded.

Also, if S1 dies, those users may lose their in-memory session.

A more scalable design is often:

```text
Servers
   │
   ▼
Shared Session Store / Database / Cache
```

For example, sessions can be stored in Redis or another shared store.

Then any server can handle the request.

This fits nicely with the **stateless architecture** we learned earlier.

<h2>Layer 4 vs Layer 7 Load Balancer</h2>

This is an important interview topic.

<h3>Layer 4 Load Balancer</h3>

A Layer 4 Load Balancer works at the **Transport Layer**.

It mainly looks at things like:

- IP Address
- Port
- TCP/UDP connection information

It doesn't need to understand the HTTP request itself.

Conceptually:

```text
Client
   │
   ▼
L4 Load Balancer
   │
   ├── Server 1
   ├── Server 2
   └── Server 3
```

<h3>Advantage</h3>

Very fast and efficient.

<h3>Layer 7 Load Balancer</h3>

A Layer 7 Load Balancer works at the **Application Layer**.

It can understand HTTP information such as:

- URL
- HTTP Method
- Headers
- Host

So it can make smarter routing decisions.

Example:

```text
/api/users/*
       ↓
User Service

/api/orders/*
       ↓
Order Service

/images/*
       ↓
Image Service
```

Architecture:

```text
                 Client
                   │
                   ▼
             L7 Load Balancer
              /      |       \
             ▼       ▼        ▼
        User Svc  Order Svc  Image Svc
```

This is very useful in modern microservice architectures.

<h2>L4 vs L7 Comparison</h2>

| L4 | L7 |
|----|----|
| Transport Layer | Application Layer |
| Uses IP + Port | Uses HTTP details |
| Faster / Simpler | Smarter routing |
| Less request awareness | Understands requests |
| TCP/UDP level | HTTP/HTTPS level |

<h2>Load Balancer + Reverse Proxy</h2>

Remember our previous lesson?

A Reverse Proxy can also perform load balancing.

So the architecture can look like:

```text
Users
  │
  ▼
Nginx
  │
  ├── Server 1
  ├── Server 2
  └── Server 3
```

Here Nginx is acting as both:

> **Reverse Proxy + Load Balancer**

These concepts overlap in real infrastructure.

<h2>Benefits of Load Balancing</h2>

<h3>Scalability</h3>

```text
2 Servers
   ↓
5 Servers
   ↓
20 Servers
```

You can add capacity as traffic grows.

<h3>Availability</h3>

If one server fails:

```text
S1 ❌
S2 ✅
S3 ✅
```

Traffic continues through the healthy servers.

<h3>Performance</h3>

Traffic is distributed instead of overwhelming one server.

<h2>Real Production Architecture</h2>

Now combine what we've learned:

```text
                       Users
                         │
                         ▼
                        DNS
                         │
                         ▼
                  Load Balancer
                         │
              ┌──────────┼──────────┐
              ▼          ▼          ▼
           Server 1   Server 2   Server 3
              │          │          │
              └──────────┼──────────┘
                         ▼
                       Cache
                         │
                         ▼
                      Database
```

This is the beginning of a real scalable architecture.

<h2>Memory Trick</h2>

Think of a **traffic police officer**.

The officer:

- Receives traffic
- Sends cars to different roads
- Avoids blocked roads
- Distributes traffic
- Keeps the system moving

That's essentially what a Load Balancer does for servers.

<h2>Interview Questions</h2>

### Q1. Why do we need a Load Balancer?

> To distribute traffic across multiple servers, improving scalability, availability, and performance.

### Q2. What happens if a server fails?

> Health checks detect the failure and the Load Balancer stops sending traffic to that server.

### Q3. What is Round Robin?

> Requests are distributed sequentially across servers.

### Q4. What is Least Connections?

> The request is sent to the server with the fewest active connections.

### Q5. What is Sticky Session?

> It attempts to keep a client's requests routed to the same server.

### Q6. What is the difference between L4 and L7 Load Balancers?

> L4 works using transport-level information such as IP and port, while L7 understands application-level information such as HTTP paths, methods, headers, and hosts.

<h2>Quick Revision</h2>

```text
LOAD BALANCER
      │
      ├── Distributes Traffic
      │
      ├── Health Checks
      │
      ├── Failover
      │
      ├── Scaling
      │
      └── High Availability
```

Algorithms:

```text
→ Round Robin
→ Weighted Round Robin
→ Least Connections
→ IP Hash
```

Types:

```text
→ L4
→ L7
```

<h2>Key Takeaways</h2>

- **Load Balancer distributes traffic across multiple servers.**
- **Health checks** remove unhealthy servers from traffic.
- **Round Robin = Take turns.**
- **Weighted Round Robin = More traffic to more powerful servers.**
- **Least Connections = Send traffic to the least-busy server.**
- **IP Hash = Route based on client IP.**
- **Sticky Sessions** keep a client connected to the same server.
- **L4 = Transport-level load balancing.**
- **L7 = Application-level / HTTP-aware load balancing.**
- A **Reverse Proxy can also act as a Load Balancer**.
- Load Balancers improve **Scalability, Availability, and Performance**.