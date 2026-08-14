## Reverse Proxy

A Reverse Proxy is an important component in System Design that sits between **Clients** and **Backend Servers**.

It can provide **Security, Load Balancing, Caching, HTTPS/TLS Termination, and Traffic Distribution**.

<h2>What is a Reverse Proxy?</h2>

> **Definition : A Reverse Proxy is a server that sits between clients and backend servers and forwards client requests to the appropriate backend.**

Instead of:

```text
Client
   ↓
Backend Server
```

we have:

```text
Client
   ↓
Reverse Proxy
   ↓
Backend Server
```

The client talks to the **Reverse Proxy**, not directly to the backend.

<h2>Why Do We Need a Reverse Proxy?</h2>

Imagine your application has multiple backend servers:

```text
             Users
                │
                ▼
        Reverse Proxy
          /     |     \
         ▼      ▼      ▼
        S1     S2      S3
```

The Reverse Proxy can decide where each request should go.

For example:

```text
Request 1 → S1
Request 2 → S2
Request 3 → S3
```

This becomes especially useful when we have many backend servers.

<h2>Security</h2>

Without a Reverse Proxy:

```text
Internet
    ↓
Backend Server
```

The backend server is directly exposed to the Internet.

With a Reverse Proxy:

```text
Internet
    ↓
Reverse Proxy
    ↓
Backend Servers
```

The backend servers can be kept behind the proxy.

The proxy becomes the **public entry point**.

Benefits:

- Hides backend servers
- Provides a single public entry point
- Can filter unwanted requests
- Can add security controls

<h2>SSL/TLS Termination</h2>

The Reverse Proxy can handle HTTPS.

```text
Client
    ↓ HTTPS
Reverse Proxy
    ↓
Backend Servers
```

The proxy decrypts the incoming HTTPS request and forwards it internally.

This means backend servers don't each have to handle the public TLS connection.

> **TLS Termination = The Reverse Proxy handles the TLS encryption/decryption instead of every backend server.**

<h2>Caching</h2>

A Reverse Proxy can cache frequently requested data.

Example:

```text
Client
   ↓
Reverse Proxy
   ↓
Cache Hit ✅
```

If the requested data is already cached, the proxy can return the response without contacting the backend.

This:

- Reduces backend load
- Improves response time
- Saves server resources
- Reduces unnecessary requests

<h2>Load Balancing</h2>

A Reverse Proxy can also distribute traffic across multiple backend servers.

```text
                Users
                  ↓
          Reverse Proxy
           /     |     \
          ↓      ↓      ↓
         S1     S2      S3
```

For example:

```text
Request 1 → S1
Request 2 → S2
Request 3 → S3
Request 4 → S1
```

When a Reverse Proxy performs this job, it is acting as a **Load Balancer**.

We'll study Load Balancers separately and in more detail.

<h2>Nginx</h2>

A very popular tool used as a Reverse Proxy is:

> **Nginx**

Nginx can perform several jobs:

- Reverse Proxy
- Load Balancing
- HTTPS/TLS Termination
- Caching
- Static File Serving

<h2>Real-World Architecture</h2>

A typical production architecture might look like:

```text
             Users
                ↓
          Reverse Proxy
                ↓
          Load Balancer
          /     |     \
         ↓      ↓      ↓
       App1    App2    App3
          \      |      /
                ↓
             Database
```

However, the Reverse Proxy and Load Balancer do **not always need to be separate components**.

In some architectures, the same component can perform both roles.

<h2>Reverse Proxy vs Forward Proxy</h2>

This is a very important System Design interview concept.

### Reverse Proxy

A Reverse Proxy represents and protects the **servers**.

```text
Client
   ↓
Reverse Proxy
   ↓
Server
```

The client doesn't directly access the backend.

### Forward Proxy

A Forward Proxy represents and protects the **clients**.

```text
Client
   ↓
Forward Proxy
   ↓
Internet
   ↓
Server
```

The server sees the proxy instead of directly seeing the client.

<h2>Comparison</h2>

| Reverse Proxy | Forward Proxy |
|---------------|---------------|
| Represents servers | Represents clients |
| Sits in front of backend servers | Sits in front of clients |
| Hides backend servers | Hides client identity |
| Handles incoming traffic | Handles outgoing client traffic |
| Can provide load balancing and caching | Can provide client-side filtering and access control |

<h2>Main Responsibilities of a Reverse Proxy</h2>

```text
                 Reverse Proxy
                       │
        ┌──────────────┼──────────────┐
        ▼              ▼              ▼
    Security        Caching       Load Balancing
        │              │              │
        ▼              ▼              ▼
  TLS Termination   Fast Response   Traffic Distribution
```

Main purposes:

- 🔐 Security
- 🔒 HTTPS/TLS Termination
- ⚡ Caching
- ⚖️ Load Balancing
- 🫥 Hide Backend Servers
- 🚦 Traffic Distribution

<h2>System Design Perspective</h2>

Suppose you have:

```text
1 Million Users
```

and only one backend server.

```text
Users
  │
  ▼
Backend Server
```

The server can become overloaded.

Instead:

```text
                 Users
                   │
                   ▼
            Reverse Proxy
                   │
          ┌────────┼────────┐
          ▼        ▼        ▼
        App1     App2     App3
```

Now the Reverse Proxy can distribute incoming requests.

This helps improve:

- Scalability
- Availability
- Performance
- Security

<h2>Interview Question</h2>

### What is a Reverse Proxy and why do we use it?

**Answer:**

> "A Reverse Proxy sits between clients and backend servers and forwards requests to the appropriate backend. It can provide security, TLS termination, caching, load balancing, and traffic distribution while hiding backend servers from direct public access."

<h2>Memory Trick</h2>

### Reverse Proxy

> **"I'm standing in front of the servers."**

```text
Users → Proxy → Servers
```

### Forward Proxy

> **"I'm standing in front of the clients."**

```text
Clients → Proxy → Internet
```

<h2>Quick Revision</h2>

```text
Reverse Proxy

Client
   ↓
Reverse Proxy
   ↓
Backend Server(s)
```

### Main Purposes

- 🔐 Security
- 🔒 HTTPS/TLS Termination
- ⚡ Caching
- ⚖️ Load Balancing
- 🫥 Hide Backend Servers
- 🚦 Traffic Distribution

### Popular Tool

**Nginx**

<h2>Key Takeaways</h2>

- **Reverse Proxy sits between clients and backend servers.**
- Clients communicate with the **Reverse Proxy** instead of directly accessing backend servers.
- It can provide **Security, TLS Termination, Caching, and Load Balancing**.
- A Reverse Proxy can hide backend servers from direct public access.
- **Nginx** is a popular Reverse Proxy.
- A Reverse Proxy and Load Balancer can sometimes be the **same component**.
- **Reverse Proxy → Represents Servers**
- **Forward Proxy → Represents Clients**