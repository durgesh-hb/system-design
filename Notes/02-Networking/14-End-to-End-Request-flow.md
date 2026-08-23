Final Lesson: End-to-End Request Flow

This is the most important lesson of Module 2 because we're going to connect everything we've learned.

Let's imagine you open:

https://example.com/products/123

and want to see product 123.

1. The Complete Journey

At a high level:

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

But there are some important details.

Let's go step by step.

2. Step 1 — Client

You open your browser:

https://example.com/products/123

Your browser needs to find the server responsible for example.com.

So it needs the server's IP address.

It asks DNS.

3. Step 2 — DNS
Browser
   │
   │ "Where is example.com?"
   ▼
DNS

DNS resolves:

example.com
      ↓
IP Address

For example:

example.com
      ↓
203.xxx.xxx.xxx

Now the browser knows where to send the request.

4. Step 3 — HTTPS Connection

The browser establishes a secure HTTPS connection with the destination infrastructure.

Browser
   │
   │ HTTPS
   ▼
Server Infrastructure

TLS protects the communication from being read or modified by attackers in transit.

5. Step 4 — CDN

The request may first reach a CDN.

Browser
   ↓
CDN Edge Server

The CDN checks:

"Do I already have this content cached?"

Cache Hit

If the requested content is cacheable and already stored:

Browser
   ↓
CDN
   ↓
Response

Done.

The request doesn't need to reach your backend.

Cache Miss

If the CDN doesn't have the content:

Browser
   ↓
CDN
   ↓
Origin Infrastructure

The request continues.

6. Step 5 — Load Balancer

Now the request reaches the application's load balancer.

                 Load Balancer
                /      |      \
               ▼       ▼       ▼
             S1       S2       S3

The Load Balancer decides:

"Which backend server should handle this request?"

It might use:

Round Robin
Least Connections
IP Hash
Other routing logic

It also avoids servers that fail health checks.

7. Step 6 — Reverse Proxy

The request may then pass through a Reverse Proxy.

Client
  ↓
Load Balancer
  ↓
Reverse Proxy
  ↓
Backend

The Reverse Proxy can:

Route requests
Handle TLS
Cache responses
Hide backend servers
Apply security rules

In real systems, the Load Balancer and Reverse Proxy may be the same component, so don't assume there must always be two separate machines.

8. Step 7 — Backend Server

Now the request reaches the application.

GET /products/123

The backend understands:

"The user wants product 123."

It needs to retrieve the product data.

Where might it look first?

Cache.

9. Step 8 — Cache

The backend checks something like Redis:

Backend
   │
   │ "Do we have product 123?"
   ▼
Cache
Cache Hit
Cache
  │
  │ Product found
  ▼
Backend

The backend doesn't need to query the database.

Fast.

Cache Miss
Cache
  │
  │ Product not found
  ▼
Database
10. Step 9 — Database

The backend asks the database:

SELECT * FROM products
WHERE id = 123;

The database returns:

Product 123
Name: Laptop
Price: ₹50,000

The backend may then store this result in the cache for future requests.

Database
   ↓
Backend
   ↓
Cache
11. Step 10 — Response

The backend creates the response:

{
  "id": 123,
  "name": "Laptop",
  "price": 50000
}

Then it travels back:

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

The browser displays the product.

12. Complete Flow

Now put everything together:

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
13. Where Does TCP Fit?

Good question.

Remember TCP?

The network stack underneath HTTP/HTTPS handles the actual transport.

For traditional HTTP/1.1 and HTTP/2:

Application
    │
 HTTP/HTTPS
    │
   TCP
    │
   IP
    │
 Internet

HTTP/3 uses QUIC over UDP instead.

For System Design, you mainly need to understand that HTTP/HTTPS communication relies on underlying transport/network protocols.

14. Where Do WebSockets Fit?

Suppose instead of requesting a product, you're building WhatsApp.

You might have:

Client
   │
   │ WebSocket
   ▼
Load Balancer
   │
   ▼
Chat Servers

The connection stays open so the server can push messages to the client.

So different communication requirements use different technologies.

15. Where Does gRPC Fit?

Suppose your backend has multiple microservices:

Order Service
      │
      │ gRPC
      ▼
Payment Service

gRPC is often useful for internal service-to-service communication.

16. What About Message Queues?

We haven't studied them yet, but you'll soon see them.

Suppose the user places an order:

Client
   ↓
Order Service
   ↓
Message Queue
   ↓
Email Worker

The Order Service doesn't need to wait for the email to be sent before completing the order.

This is asynchronous processing.

We'll learn this in a later module.

17. Why This Flow Matters

When designing a system, you can now look at a request and ask:

Where can it become slow?
DNS?
CDN?
Network?
Load Balancer?
Backend?
Cache?
Database?
Where can it fail?
Server failure
Database failure
Network failure
Cache failure
Where can we scale?
Multiple backend servers
        ↓
Load Balancer

Cache
        ↓
Distributed Cache

Database
        ↓
Replication / Sharding

This is the beginning of System Design thinking.

18. One Important Correction

Don't memorize this as a fixed mandatory pipeline:

DNS → CDN → LB → Reverse Proxy → Backend → Cache → DB

Real systems differ.

For example:

CDN might serve the request directly.
CDN might not be used.
Load Balancer and Reverse Proxy might be the same component.
Cache might exist at multiple layers.
Some requests don't need a database.
A request might go through an API Gateway.
A microservice might call another service using gRPC.

The important skill is understanding what each component solves and deciding when you need it.

🧠 Final Mental Model

Think of a large application as a city:

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
🎯 Module 2 — Key Takeaways

You should now understand:

DNS → finds the destination
HTTPS → secures communication
TCP/UDP → transports data
REST → normal API communication
WebSockets → real-time two-way communication
Long Polling → wait for updates using HTTP
SSE → server-to-client streaming updates
gRPC → efficient service-to-service communication
Reverse Proxy → sits in front of servers
Forward Proxy → sits in front of clients
Load Balancer → distributes traffic
CDN → delivers cacheable content closer to users