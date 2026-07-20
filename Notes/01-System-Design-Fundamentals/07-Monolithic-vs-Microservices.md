## Monolithic vs Microservices

Monolithic and Microservices are two common software architecture styles used to organize an application's code and services.

<h2>Imagine You're Building an E-Commerce Website</h2>

Your website has these features:

- User Login
- Product Catalog
- Cart
- Payment
- Order Management
- Notifications

Now the question is:

> **How should we organize the code?**

There are two common approaches:

- Monolithic Architecture
- Microservices Architecture

(../../images/Monilithic-&-Microservice.png)

<h2> -> 1. Monolithic Architecture</h2>

> **Definition : A Monolithic Application is an application where all features are part of one single application.**

Everything is packaged and deployed together.

### Example

Imagine Amazon.

Instead of separating features,

everything is inside one project.

```text
Amazon Application

├── Login
├── Products
├── Cart
├── Orders
├── Payments
├── Notifications
└── Database
```

Everything runs together.

<h3>Architecture</h3>

```text
                Users
                   │
                   ▼

        +----------------------+
        |  Amazon Application  |
        |----------------------|
        | Login                |
        | Products             |
        | Cart                 |
        | Orders               |
        | Payment              |
        | Notifications        |
        +----------------------+
                   │
                   ▼
             +------------+
             | Database   |
             +------------+
```

One application.

One deployment.

<h3>Advantages of Monolith</h3>

1. Easy to Develop

- Only one project.
- Everything is in one place.

 2. Easy to Test

- Start one application.
- Test everything.

 3. Easy to Deploy

- Build once.
- Deploy once.

 4. Best for Small Projects

Examples:

- College Projects
- Startups
- Internal Tools
- Admin Panels

<h3>Disadvantages of Monolith</h3>

1. Very Large Codebase

Imagine after five years.

Amazon Application

500,000 lines of code

Finding bugs becomes harder.

2. One Bug Can Affect Everything

Suppose Payment crashes.

Sometimes the entire application may fail depending on how it's designed.

 3. Scaling Problems

Suppose only Product Search is heavily used.

Traffic:

```text
Products  → 90%
Payments  → 5%
Orders    → 5%
```

With a monolith,

you often need to scale the **entire application**, even though only one module needs extra capacity.

This wastes resources.
4. Slower Deployments

Changing one small feature often means rebuilding and redeploying the entire application.

<h2>-> 2. Microservices Architecture</h2>

> **Definition : Instead of one huge application, split it into many small applications.**

Each application performs one business function.
 Example

- Login Service
- Product Service
- Cart Service
- Payment Service
- Order Service
- Notification Service

Each service is independent.

<h3>Architecture</h3>

```text
                            Users
                              │
                              ▼
                         API Gateway
                              │
         ┌─────────┬──────────┼─────────┬────────────┐
         ▼         ▼          ▼         ▼            ▼
    +--------+ +--------+ +--------+ +--------+ +-------------+
    | Login  | |Product | | Cart   | |Payment | | Order       |
    |Service | |Service | |Service | |Service | | Service     |
    +--------+ +--------+ +--------+ +--------+ +-------------+
                            │
                            ▼
                      Notification
                         Service
```

Each service can have its own database or share data depending on the architecture.

<h3>Advantages of Microservices</h3>

 1. Independent Deployment

Update only the Payment Service.

No need to redeploy Login or Product Service.

 2. Independent Scaling

Suppose Product Search receives huge traffic.

Only scale Product Service.

```text
Product Service
      ↓
   10 Servers

Payment Service
      ↓
    2 Servers
```

Much more efficient.

3. Fault Isolation

If Notification Service crashes,

Login and Payment can continue working.

A failure is less likely to bring down the entire system.

 4. Easier for Large Teams

Example:

- Team A → Login
- Team B → Payment
- Team C → Orders
- Team D → Products

Each team owns its service.

<h3>Disadvantages of Microservices</h3>
 1. More Complex

Now you have:

- Many services
- Many deployments
- Many APIs

Managing everything becomes harder.

 2. Network Communication

Services call each other over the network.

Network failures become part of your system.

3. Harder Debugging

One user request may pass through multiple services.

Tracing problems is more difficult.

 4. Data Consistency

Suppose:

- Payment succeeds.
- Order creation fails.

How do you recover?

This becomes a distributed systems problem.

We'll learn solutions like the **Saga Pattern** later.


<h2>Monolith vs Microservices</h2>

| Monolith | Microservices |
|-----------|---------------|
| One application | Many small services |
| One deployment | Independent deployments |
| Requires less planning at the start, but gets increasingly complex to understand and maintain | Requires more planning and infrastructure at the start, but gets easier to manage and maintain over time |
| Easier to build | More complex |
| Easier to debug | Harder to debug |
| Scale entire application | Scale individual services |
| Best for small projects | Best for large systems |



<h2>Which One Do Companies Use?</h2>
-> Startup

Usually starts with : Monolith

Reason:

- Simple
- Fast development
- Lower operational complexity

-> Amazon

Uses : Microservices

Examples:

- Product
- Orders
- Payments
- Recommendations
- Search

Each is a separate service.


<h2>Should You Always Use Microservices?</h2>

❌ No.

This is a common beginner mistake.

If you're building:

- Portfolio
- College Project
- Small Business Website
- Personal Blog

A **Monolith** is often the better choice.

Microservices add operational complexity that may not be justified.
<hr>

-> Q1. Why doesn't every company use Microservices?

**Answer:**

Because microservices introduce additional complexity, such as:

- Network communication
- Deployment management
- Distributed data
- Monitoring
- Debugging

For smaller applications, a **Monolith** is often simpler, faster to build, and easier to maintain.

<h2>Key Takeaways</h2>

- **Monolith = One application containing all features.**
- **Microservices = Application split into multiple independent services.**
- Monolith is easier to develop, test, and deploy.
- Microservices provide independent deployment, scaling, and better fault isolation.
- Monolith is ideal for **small projects**.
- Microservices are ideal for **large-scale applications**.
- Microservices introduce higher operational complexity.
- Choose the architecture based on your application's size and requirements, not because one is more popular.
