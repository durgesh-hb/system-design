## Forward Proxy

A Forward Proxy is a server that sits between **Clients** and the **Internet** and sends requests on behalf of the clients.

<h2>What is a Forward Proxy?</h2>

> **Definition : A Forward Proxy sits between clients and the internet and sends requests on behalf of the clients.**

Architecture:

```text
Client
   ↓
Forward Proxy
   ↓
Internet
   ↓
Server
```

The server sees the **proxy's request**, rather than directly seeing the client's connection.

<h2>Real-Life Analogy</h2>

Imagine you're an employee in a company.

You want to visit a website.

But company policy says:

> "Employees cannot directly access the internet."

Instead:

```text
You
 ↓
Company Proxy
 ↓
Internet
 ↓
Website
```

The company proxy controls the internet access.

<h2>Why Use a Forward Proxy?</h2>

<h3>Security</h3>

The proxy can control what clients are allowed to access.

For example:

```text
Employee
   ↓
Proxy
   ├── YouTube ❌
   ├── Social Media ❌
   └── Company Websites ✅
```

This allows organizations to enforce internet access policies.

<h3>Privacy / IP Masking</h3>

The destination server may see the proxy's IP instead of the client's direct IP.

```text
Client
IP: A
   ↓
Proxy
IP: B
   ↓
Website
```

Website sees:

```text
IP: B
```

This can hide the client's direct network identity from the destination server.

> A Forward Proxy can hide the client's IP from the destination, but it does **not** make the user universally anonymous.

<h3>Access Control</h3>

Organizations can control:

- Which websites users can access
- Which domains are blocked
- Which traffic is allowed
- Which users can access specific resources

<h3>Caching</h3>

A Forward Proxy can cache frequently requested content.

Example:

```text
User 1
   ↓
Proxy
   ↓
Website
   ↓
Response
   ↓
Cache
```

Then:

```text
User 2
   ↓
Proxy
   ↓
Cache
   ↓
Response
```

The proxy doesn't need to contact the website again if the requested content is available and valid in the cache.

This can:

- Reduce network traffic
- Reduce bandwidth usage
- Improve response time
- Reduce repeated requests to external servers

<h2>Forward Proxy vs Reverse Proxy</h2>

This is one of the most important things to remember.

### Forward Proxy

Represents the **Client**.

```text
Client
   ↓
Forward Proxy
   ↓
Internet
   ↓
Server
```

The client uses the proxy to access external servers.

### Reverse Proxy

Represents the **Server**.

```text
Client
   ↓
Reverse Proxy
   ↓
Server
```

The reverse proxy sits in front of backend servers.

<h2>Comparison</h2>

| Forward Proxy | Reverse Proxy |
|---------------|---------------|
| Represents the client | Represents the server |
| Sits in front of clients | Sits in front of servers |
| Controls outgoing client traffic | Controls incoming server traffic |
| Can hide the client's IP from destination servers | Can hide backend servers from clients |
| Common in corporate networks | Common in web applications |
| Used for access control and filtering | Used for security, caching, and load balancing |

<h2>Simple Memory Trick</h2>

Think about the direction.

### Forward Proxy

```text
CLIENT → PROXY → INTERNET
```

The proxy is **forwarding the client's request**.

### Reverse Proxy

```text
CLIENT → PROXY → SERVER
```

The proxy is **in front of the servers**.

Simply:

> **Forward Proxy = Protects / Controls Clients**

> **Reverse Proxy = Protects / Controls Servers**

<h2>Real-World Examples</h2>

### Forward Proxy

Common in:

- Corporate Networks
- Schools
- Universities
- Government Organizations

For example, a company may route employee internet traffic through a proxy for:

- Filtering
- Access Control
- Monitoring
- Security

### Reverse Proxy

Common in:

- Web Applications
- APIs
- Microservices
- Production Infrastructure

Tools like **Nginx** can act as Reverse Proxies.

<h2>System Design Perspective</h2>

A Forward Proxy is mainly useful when the **client side** needs control over outgoing traffic.

```text
        Internal Clients
              │
              ▼
        Forward Proxy
              │
              ▼
           Internet
              │
              ▼
       External Servers
```

A Reverse Proxy is mainly useful when the **server side** needs control over incoming traffic.

```text
           Internet
              │
              ▼
        Reverse Proxy
              │
        ┌─────┼─────┐
        ▼     ▼     ▼
      App1  App2  App3
```

<h2>Interview Question</h2>

### What is a Forward Proxy?

**Answer:**

> "A Forward Proxy sits between clients and the Internet and sends requests on behalf of the clients. It can provide access control, filtering, caching, security, and can hide the client's direct IP address from destination servers."

<h2>Quick Revision</h2>

```text
Forward Proxy

Client
   ↓
Forward Proxy
   ↓
Internet
   ↓
Server
```

### Main Purposes

- Security
- Access Control
- Filtering
- IP Masking
- Caching
- Traffic Monitoring

<h2>Memory Trick</h2>

> **Forward Proxy → Client Side**

```text
Clients → Proxy → Internet
```

> **Reverse Proxy → Server Side**

```text
Clients → Proxy → Servers
```

<h2>Key Takeaways</h2>

- **Forward Proxy sits between clients and the Internet.**
- It sends requests **on behalf of clients**.
- It can provide **Security, Access Control, Filtering, Caching, and IP Masking**.
- It is commonly used in **Corporate Networks, Schools, Universities, and Organizations**.
- The destination server may see the **proxy's IP instead of the client's direct IP**.
- **Forward Proxy → Represents Clients**
- **Reverse Proxy → Represents Servers**
- **Forward Proxy = Controls outgoing client traffic**
- **Reverse Proxy = Controls incoming server traffic**