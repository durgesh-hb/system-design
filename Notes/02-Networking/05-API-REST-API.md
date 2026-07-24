## REST API

REST API is one of the most common ways for a **Client** and **Server** to communicate in modern applications.

Before understanding REST API, first understand what an **API** is.

<h3>1. What is an API?</h3>

Imagine you're using a food delivery app.

You click:

```text
Show Restaurants
```
Your mobile app needs restaurant information from the backend.

How does it ask the backend?

Through an **API**.

> **Definition : API (Application Programming Interface) is a defined way for software systems to communicate with each other.**

Simply:

> **API = Communication contract between software systems.**

Example:

```text
Mobile App
    │
    │ "Give me restaurants"
    ▼
   API
    │
    ▼
Backend Server
    │
    ▼
Database
```

<h3>2. What is REST?</h3>

REST stands for:

> **Representational State Transfer**

What's important:

> **REST is an architectural style for designing APIs around resources, usually using HTTP.**

An API designed according to REST principles is commonly called a **REST API**.

Simply:

> **REST API = API designed using REST principles.**

<h2>3. What is a Resource?</h2>

> **Resource = Something your system manages.**

### Amazon

Resources can be:

- Users
- Products
- Orders
- Payments
- Reviews

### Instagram

Resources can be:

- Users
- Posts
- Comments
- Followers
- Messages

REST APIs expose these resources through URLs called **Endpoints**.

<h3>4. What is an Endpoint?</h3>

> **Definition : An endpoint is a specific API address used to access a resource or operation.**

Example:

```text
/api/products
```

Means:

```text
Products Resource
```

Specific product:

```text
/api/products/123
```

Means:

```text
Product with ID 123
```
<h3>5. REST + HTTP Methods</h3>

HTTP methods tell the server what you want to do with a resource.

### Get Products

```text
GET /products
```

Means:

> Give me products.

---

### Get Specific Product

```text
GET /products/123
```

Means:

> Give me product 123.

---

### Create Product

```text
POST /products
```

Means:

> Create a new product.

---

### Update Product

```text
PATCH /products/123
```

Means:

> Modify product 123.

### Delete Product

```text
DELETE /products/123
```

Means:

> Delete product 123.

<h3>6. REST and CRUD</h3>

CRUD means:

- Create
- Read
- Update
- Delete

| CRUD | HTTP Method | Purpose |
|------|-------------|---------|
| Create | POST | Create Data |
| Read | GET | Retrieve Data |
| Update | PUT / PATCH | Update Data |
| Delete | DELETE | Remove Data |

Simply:

```text
REST API
   │
   ├── GET        → Read
   │
   ├── POST       → Create
   │
   ├── PUT/PATCH  → Update
   │
   └── DELETE     → Remove
```

<h3>7. Example: Instagram</h3>

### Load Feed

```text
GET /posts
```

Server returns posts.


### Upload Post

```text
POST /posts
```

Server creates the post.

---

### Edit Caption

```text
PATCH /posts/123
```

Server updates post 123.

### Delete Post

```text
DELETE /posts/123
```

Server deletes post 123.

That's REST at a basic level.

<h3>8. REST APIs Are Usually Stateless</h3>

Remember HTTP Statelessness?

The same idea is important for REST.

```text
Request 1
    │
    ▼
Server 1

Request 2
    │
    ▼
Server 3

Request 3
    │
    ▼
Server 2
```

Each request should contain enough information for the server to understand and process it.

This makes **Horizontal Scaling** much easier.

<h3>9. Why REST is Popular</h3>

REST APIs are:

- Simple
- Easy to understand
- Built around HTTP
- Supported almost everywhere
- Good for Web and Mobile applications
- Easy to scale when designed statelessly

That's why you'll see REST everywhere in backend development.

<h3>10. REST in System Design</h3>

Imagine:

```text
Mobile App
     │
     │ REST API
     ▼
Load Balancer
     │
     ▼
Backend Servers
     │
     ▼
Database
```

REST defines how the client communicates with your backend.

When designing systems like Instagram or Amazon, we may define APIs such as:

```text
POST /posts

GET /feed

POST /orders

GET /products
```

---

<h2>API vs REST API</h2>

Many beginners confuse **API** and **REST API**.

> **API is a general concept for communication between software systems.**

> **REST API is a type of API that follows REST principles, usually using HTTP and resources.**

### Example

Think of:

```text
API
 │
 ├── REST API
 ├── GraphQL API
 ├── gRPC API
 └── Other APIs
```

So:

> **Every REST API is an API, but every API is not a REST API.**

---

<h2>API vs REST API Comparison</h3>

| API | REST API |
|-----|----------|
| General way for software systems to communicate | A specific style of API |
| Can use different protocols and architectures | Usually uses HTTP |
| Does not have to follow REST principles | Follows REST principles |
| Can be REST, GraphQL, gRPC, etc. | Uses resources and endpoints |
| Broad concept | Type of API |

### Simple Example

**API**

```text
Software A
    │
    ▼
   API
    │
    ▼
Software B
```

**REST API**

```text
Client
   │
GET /products
   │
   ▼
Server
   │
JSON Response
   ▼
Client
```

---

<h3>REST Isn't the Only Option</h3>

Important:

> **API ≠ REST**

REST is just one way to design APIs.

Other approaches include:

- GraphQL
- gRPC
- WebSockets for persistent real-time communication
  
<h2>Memory Trick</h2>

Think:

> **API = Communication Contract**

and:

> **REST = A popular way of designing that contract using Resources + HTTP.**

Example:

Resource:

```text
Product
```

Operations:

```text
GET    /products

POST   /products

PATCH  /products/123

DELETE /products/123
```

<h2>Key Takeaways</h2>

- **API = Communication contract between software systems.**
- **REST = Architectural style for designing APIs.**
- **REST API = API that follows REST principles.**
- **Resource = Something the system manages.**
- **Endpoint = Address used to access a resource or operation.**
- REST commonly uses **GET, POST, PUT, PATCH, and DELETE**.
- REST maps naturally to **CRUD operations**.
- REST APIs are **Stateless**.
- Statelessness makes **Horizontal Scaling easier**.
- **Every REST API is an API, but every API is not a REST API.**
- REST is not the only option; alternatives include **GraphQL and gRPC**.
