## gRPC

gRPC is another way for software services to communicate with each other.

It is especially useful for **microservice-to-microservice communication**.

<h2>1. Why Do We Need gRPC?</h2>

Imagine an e-commerce system with many microservices:

```text
        API Gateway
             │
     ┌───────┼────────┐
     ▼       ▼        ▼
   User    Order    Payment
 Service  Service   Service
```

The services need to communicate with each other.

For example:

```text
Order Service
      │
      │ "Give me user details"
      ▼
User Service
```

You could use REST:

```text
GET /users/123
```

But when you have **thousands or millions of internal service-to-service calls**, you may want something more efficient and strongly defined.

That's where **gRPC** comes in.

<h2>2. What is gRPC?</h2>

> **Definition : gRPC is a high-performance RPC framework that allows one service to directly call a function on another service over a network.**

RPC means:

> **Remote Procedure Call**

The idea is simple:

Instead of thinking:

> "I'm sending an HTTP request to another server."

You can think:

> "I'm calling a function that happens to run on another server."

<h2>3. Simple Example</h2>

Suppose User Service has:

```text
getUser(userId)
```

Order Service can call:

```text
getUser(123)
```

Even though the function actually runs on the **User Service's server**.

Conceptually:

```text
Order Service

getUser(123)
      │
      │ Network
      ▼
User Service

getUser(123)
      │
      ▼
User Data
```

That's the core idea behind RPC.

<h2>4. REST vs gRPC</h2>

With REST:

```text
GET /users/123
```

The client thinks in terms of:

> **HTTP + URL + Resource**

With gRPC:

```text
GetUser(123)
```

The client thinks in terms of:

> **Calling a Remote Function**

<h2>5. Protocol Buffers</h2>

This is an important gRPC concept.

gRPC commonly uses:

> **Protocol Buffers (Protobuf)**

Protobuf defines the structure of the messages and services.

Example conceptually:

```text
User
 ├── id
 ├── name
 └── email
```

You define the contract once.

Then tools can generate client/server code for different programming languages.

For example:

* Java
* Go
* Python
* C++
* Node.js

This is extremely useful when different microservices use different languages.

<h2>6. Why is gRPC Fast?</h2>

One major reason is that gRPC commonly uses:

* **HTTP/2**
* **Binary serialization**
* **Efficient connection handling**
* **Multiplexing**

REST APIs commonly send JSON:

```json
{
  "id": 123,
  "name": "Durgesh"
}
```

JSON is human-readable but has more textual overhead.

gRPC commonly sends **binary Protobuf data**, which is more compact and efficient for machine-to-machine communication.

<h2>7. HTTP/2 and Multiplexing</h2>

This is a useful System Design concept.

Suppose one service needs to make multiple requests.

HTTP/2 allows multiple streams to share a single connection.

Conceptually:

```text
One Connection
      │
 ┌────┼────┬────┐
 ▼    ▼    ▼    ▼
Req1 Req2 Req3 Req4
```

This improves efficiency.

You don't need to memorize the protocol details yet.

<h2>8. Where is gRPC Used?</h2>

gRPC is particularly useful for:

### Microservices

```text
Order Service
      │
      │ gRPC
      ▼
Payment Service
```

### Internal APIs

Service-to-service communication inside a company.

### High-Performance Systems

Where there are huge numbers of internal requests and low latency matters.

<h2>9. Where Would You Prefer REST?</h2>

For a public API used by:

* Web Browsers
* Mobile Apps
* Third-Party Developers

REST is often simpler.

Example:

```text
Mobile App
     │
     │ REST / HTTPS
     ▼
API Gateway
```

It's easy to inspect and works naturally with HTTP clients.

<h2>10. REST vs gRPC</h2>

| REST                         | gRPC                                 |
| ---------------------------- | ------------------------------------ |
| Resource-oriented            | Function / RPC-oriented              |
| Usually JSON                 | Usually Protobuf                     |
| Easy for humans to inspect   | More optimized for machines          |
| Very common for public APIs  | Common for internal services         |
| Simple and widely understood | Strong contracts and code generation |
| HTTP-based                   | Commonly uses HTTP/2                 |

<h2>11. Important System Design Example</h2>

Imagine YouTube has:

```text
API Gateway
      │
      ├── User Service
      ├── Video Service
      ├── Recommendation Service
      ├── Comment Service
      └── Notification Service
```

A user request might enter through REST:

```text
Mobile App
     │
     │ REST
     ▼
API Gateway
```

But internally:

```text
Recommendation Service
        │
        │ gRPC
        ▼
User Service
```

So you can have **REST and gRPC in the same system**.

> You don't have to choose only REST or only gRPC for an entire application.

<h2>12. gRPC Streaming</h2>

gRPC also supports different communication patterns.

At a high level:

### Unary

One request → One response

```text
Client → Server
          ↓
       Response
```

### Streaming

Multiple messages can flow through a connection.

This makes gRPC useful for certain high-performance and streaming scenarios.

You don't need to memorize all four RPC patterns yet.

<h2>When Should You Choose gRPC?</h2>

Think:

```text
Internal Service Communication?
        │
        ├── Yes → gRPC is a strong option
        │
        └── No
             ↓
         Public API?
             ↓
      REST is often simpler
```

This isn't an absolute rule.

REST can absolutely be used internally, and gRPC can be exposed externally when appropriate.

<h2>API vs REST API vs gRPC</h2>

| API                              | REST API                     | gRPC                             |
| -------------------------------- | ---------------------------- | -------------------------------- |
| General communication concept    | API architectural style      | RPC framework                    |
| Can use different approaches     | Usually uses HTTP            | Commonly uses HTTP/2             |
| Broad concept                    | Resource-oriented            | Function-oriented                |
| Can be REST, gRPC, GraphQL, etc. | Commonly uses JSON           | Commonly uses Protobuf           |
| Defines communication            | Uses resources and endpoints | Uses services and remote methods |

> **Every REST API is an API, and gRPC is also a way to build APIs.**

<h2>Memory Trick</h2>

### REST

> **"Give me this resource."**

```text
GET /users/123
```

### gRPC

> **"Call this function."**

```text
GetUser(123)
```

RPC stands for **Remote Procedure Call**.

### Why would you use gRPC instead of REST?

For internal service-to-service communication where performance, efficient serialization, strong contracts, and code generation are valuable, gRPC can be preferable.

REST is often simpler and more convenient for public APIs.



### What is Protocol Buffers?

**Answer:**

Protocol Buffers (Protobuf) is a serialization format used by gRPC to define message structures and service contracts.

<h2>Quick Revision</h2>

```text
gRPC
 │
 ├── RPC = Remote Procedure Call
 │
 ├── Service-to-Service Communication
 │
 ├── Usually Protobuf
 │
 ├── Commonly HTTP/2
 │
 ├── Binary & Efficient
 │
 └── Strong Contracts + Code Generation
```

<h2>Key Takeaways</h2>

* **gRPC = Remote Procedure Call framework.**
* It is commonly used for **microservice-to-microservice communication**.
* gRPC commonly uses **Protocol Buffers**.
* gRPC commonly uses **HTTP/2**.
* Binary serialization makes communication compact and efficient.
* gRPC provides **strong contracts and code generation**.
* REST is often simpler for **public APIs**.
* gRPC is often a strong choice for **internal service communication**.
* REST and gRPC can be used **together in the same system**.
* **REST → Resource-based communication**
* **gRPC → Function-based communication**
