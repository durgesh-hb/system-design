# End-to-End Request Flow

This is the **most important lesson of Module 2** because it connects everything we've learned in Networking and System Design.

Imagine you open:

```text
https://example.com/products/123
```

and want to see product `123`.

<h2>The Complete Journey</h2>

At a high level:

```text
User
 │













## End-to-End Request Flow

This is the **most important lesson of Module 2** because it connects everything we've learned in Networking and System Design.

Imagine you open:

```text
https://example.com/products/123
```

and want to see product `123`.

<h2>The Complete Journey</h2>

At a high level:

```text
User
 │
 ▼
DNS
 │
 ▼
CDN
 │
 ▼
Load Balancer
 │
 ▼
Reverse Proxy
 │
 ▼
Backend Server
 │
 ▼
Cache
 │
 ▼
Database
 │
 ▼
Backend
 │
 ▼
User
```

But there are some important details.

Let's go step by step.

<h2>Client</h2>

You open your browser:

```text
https://example.com/products/123
```

Your browser needs to find the server responsible for `example.com`.

So it needs the server's IP address.

It asks DNS.

<h2>DNS</h2>

```text
Browser
   │
   │ "Where is example.com?"
   ▼
DNS
```

DNS resolves:

```text
example.com
      ↓
IP Address
```

For example:

```text
example.com
      ↓
203.xxx.xxx.xxx
```

Now the browser knows where to send the request.

<h2>HTTPS Connection</h2>

The browser establishes a secure HTTPS connection with the destination infrastructure.

```text
Browser
   │
   │ HTTPS
   ▼
Server Infrastructure
```

TLS protects the communication from being read or modified by attackers in transit.

<h2>CDN</h2>

The request may first reach a CDN.

```text
Browser
   ↓
CDN Edge Server
```

The CDN checks:

> "Do I already have this content cached?"

<h3>Cache Hit</h3>

If the requested content is cacheable and already stored:

```text
Browser
   ↓
CDN
   ↓
Response
```

Done.

The request doesn't need to reach your backend.

<h3>Cache Miss</h3>

If the CDN doesn't have the content:

```text
Browser
   ↓
CDN
   ↓
Origin Infrastructure
```

The request continues.

<h2>Load Balancer</h2>

Now the request reaches the application's Load Balancer.

```text
                 Load Balancer
                /      |      \
               ▼       ▼       ▼
             S1       S2       S3
```

The Load Balancer decides:

> "Which backend server should handle this request?"

It might use:

- Round Robin
- Least Connections
- IP Hash
- Other routing logic

It also avoids servers that fail health checks.

<h2>Reverse Proxy</h2>

The request may then pass through a Reverse Proxy.

```text
Client
  ↓
Load Balancer
  ↓
Reverse Proxy
  ↓
Backend
```

The Reverse Proxy can:

- Route requests
- Handle TLS
- Cache responses
- Hide backend servers
- Apply security rules

> In real systems, the **Load Balancer and Reverse Proxy may be the same component**, so don't assume there must always be two separate machines.

<h2>Backend Server</h2>

Now the request reaches the application.

```text
GET /products/123
```

The backend understands:

> "The user wants product 123."

It needs to retrieve the product data.

Where might it look first?

**Cache.**

<h2>Cache</h2>

The backend checks something like Redis:

```text
Backend
   │
   │ "Do we have product 123?"
   ▼
Cache
```

<h3>Cache Hit</h3>

```text
Cache
  │
  │ Product found
  ▼
Backend
```

The backend doesn't need to query the database.

This is faster.

<h3>Cache Miss</h3>

```text
Cache
  │
  │ Product not found
  ▼
Database
```

<h2>Database</h2>

The backend asks the database:

```sql
SELECT * FROM products
WHERE id = 123;
```

The database returns:

```text
Product 123
Name: Laptop
Price: ₹50,000
```

The backend may then store this result in the cache for future requests.

```text
Database
   ↓
Backend
   ↓
Cache
```

<h2>Response</h2>

The backend creates the response:

```json
{
  "id": 123,
  "name": "Laptop",
  "price": 50000
}
```

Then it travels back:

```text
Database
   ↓
Backend
   ↓
Reverse Proxy
   ↓
Load Balancer
   ↓
CDN (if applicable)
   ↓
Browser
```

The browser displays the product.

<h2>Complete Flow</h2>

Now put everything together:

```text
                         User
                          │
                          ▼
                         DNS
                          │
                          ▼
                        HTTPS
                          │
                          ▼
                         CDN
                          │
                    Cache Miss
                          │
                          ▼
                    Load Balancer
                          │
                          ▼
                    Reverse Proxy
                          │
                          ▼
                    Backend Server
                          │
                    ┌─────┴─────┐
                    ▼           ▼
                  Cache      Cache Miss
                    │           │
                    │           ▼
                    │        Database
                    │           │
                    └─────┬─────┘
                          ▼
                       Backend
                          │
                          ▼
                       Response
                          │
                          ▼
                         User
```

<h2>Where Does TCP Fit?</h2>

Good question.

Remember TCP?

The network stack underneath HTTP/HTTPS handles the actual transport.

For traditional HTTP/1.1 and HTTP/2:

```text
Application
    │
 HTTP/HTTPS
    │
   TCP
    │
   IP
    │
 Internet
```

HTTP/3 uses **QUIC over UDP** instead.

For System Design, you mainly need to understand that **HTTP/HTTPS communication relies on underlying transport and network protocols**.

<h2>Where Do WebSockets Fit?</h2>

Suppose instead of requesting a product, you're building WhatsApp.

You might have:

```text
Client
   │
   │ WebSocket
   ▼
Load Balancer
   │
   ▼
Chat Servers
```

The connection stays open so the server can push messages to the client.

So different communication requirements use different technologies.

<h2>Where Does gRPC Fit?</h2>

Suppose your backend has multiple microservices:

```text
Order Service
      │
      │ gRPC
      ▼
Payment Service
```

gRPC is often useful for **internal service-to-service communication**.

<h2>What About Message Queues?</h2>

We haven't studied them yet, but you'll soon see them.

Suppose the user places an order:

```text
Client
   ↓
Order Service
   ↓
Message Queue
   ↓
Email Worker
```

The Order Service doesn't need to wait for the email to be sent before completing the order.

This is **asynchronous processing**.

We'll learn this in a later module.

<h2>Where Can a Request Become Slow?</h2>

When designing a system, ask:

```text
DNS?
CDN?
Network?
Load Balancer?
Backend?
Cache?
Database?
```

Each component can introduce latency.

<h2>Where Can a Request Fail?</h2>

Possible failure points include:

```text
Server Failure
Database Failure
Network Failure
Cache Failure
Load Balancer Failure
CDN Failure
```

A good system design considers how the system should behave when these components fail.

<h2>Where Can We Scale?</h2>

We can scale different parts of the system.

For example:

```text
Multiple Backend Servers
        ↓
Load Balancer
```

Or:

```text
Cache
   ↓
Distributed Cache
```

Or:

```text
Database
   ↓
Replication / Sharding
```

This is the beginning of **System Design thinking**.

<h2>One Important Correction</h2>

Don't memorize this as a **fixed mandatory pipeline**:

```text
DNS → CDN → Load Balancer → Reverse Proxy → Backend → Cache → Database
```

Real systems differ.

For example:

- CDN might serve the request directly.
- CDN might not be used.
- Load Balancer and Reverse Proxy might be the same component.
- Cache might exist at multiple layers.
- Some requests don't need a database.
- A request might go through an API Gateway.
- A microservice might call another service using gRPC.

The important skill is understanding **what each component solves** and deciding when you need it.

<h2>Final Mental Model</h2>

Think of a large application as a city:

```text
DNS
↓
"Where is the building?"

CDN
↓
"Can I give you the content from the nearest branch?"

Load Balancer
↓
"Which server should handle you?"

Reverse Proxy
↓
"Let me safely route your request."

Backend
↓
"Let me process your request."

Cache
↓
"I already have this data."

Database
↓
"I am the source of persistent data."
```

<h2>Module 2 Key Takeaways</h2>

You should now understand:

| Component | Purpose |
|-----------|---------|
| **DNS** | Finds the destination |
| **HTTPS** | Secures communication |
| **TCP/UDP** | Transports data |
| **REST** | Normal API communication |
| **WebSockets** | Real-time two-way communication |
| **Long Polling** | Waits for updates using HTTP |
| **SSE** | Server-to-client streaming updates |
| **gRPC** | Efficient service-to-service communication |
| **Reverse Proxy** | Sits in front of servers |
| **Forward Proxy** | Sits in front of clients |
| **Load Balancer** | Distributes traffic |
| **CDN** | Delivers cacheable content closer to users |

<h2>Final Takeaways</h2>

- **DNS → Finds where the service is.**
- **HTTPS → Secures communication.**
- **TCP/UDP → Handles transport of data.**
- **CDN → Brings cacheable content closer to users.**
- **Load Balancer → Distributes traffic across servers.**
- **Reverse Proxy → Sits in front of backend servers.**
- **Cache → Reduces repeated database or backend work.**
- **Database → Stores persistent data.**
- **WebSockets → Enable real-time two-way communication.**
- **SSE → Enables server-to-client real-time updates.**
- **gRPC → Enables efficient service-to-service communication.**
- **Long Polling → Provides a way to wait for updates using HTTP.**
- Real systems don't always use every component.
- The goal of System Design is to understand **what problem each component solves and when to use it**.