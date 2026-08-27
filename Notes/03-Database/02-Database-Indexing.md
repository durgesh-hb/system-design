## SQL vs NoSQL

Before learning **indexing, replication, sharding, etc.**, we need to understand **why different types of databases exist**.

<h2>What is a Database?</h2>

> **Definition : A database is a system used to store, organize, and retrieve data.**

Example: An e-commerce application needs to store:

```text
Users
Products
Orders
Payments
Reviews
```

A database stores this information so the application can retrieve it when needed.

<h2>Two Major Categories</h2>

At a high level:

```text
                    Databases
                       │
             ┌─────────┴─────────┐
             ▼                   ▼
           SQL                 NoSQL
       Relational          Non-Relational
```

<h2>SQL Database</h2>

> **Definition : SQL databases are relational databases that organize data into tables and relationships.**

Example:

<h3>Users</h3>

| ID | Name | Email |
|----|------|-------|
| 1 | Eren | eren@gmail.com |
| 2 | Rahul | rahul@gmail.com |

<h3>Orders</h3>

| ID | User_ID | Product |
|----|---------|---------|
| 101 | 1 | Laptop |
| 102 | 2 | Phone |

The tables can be related.

```text
Users
  │
  │ user_id
  ▼
Orders
```

<h2>Examples of SQL Databases</h2>

Common examples:

- MySQL
- PostgreSQL
- Oracle Database
- Microsoft SQL Server

You've already worked with **PostgreSQL**, so this should feel familiar.

<h2>NoSQL Database</h2>

> **Definition : NoSQL databases are non-relational databases that use different data models depending on the database.**

Different NoSQL databases use different data models.

For example, a document database might store:

```json
{
  "id": 1,
  "name": "Eren",
  "email": "eren@gmail.com",
  "orders": [
    {
      "product": "Laptop"
    }
  ]
}
```

The structure is more flexible.

<h2>Examples of NoSQL</h2>

<h3>Document</h3>

**MongoDB**

```text
JSON-like documents
```

<h3>Key-Value</h3>

**Redis**

```text
key → value
```

<h3>Wide-Column</h3>

**Cassandra**

```text
Large-scale distributed data
```

<h3>Graph</h3>

**Neo4j**

```text
Nodes + Relationships
```

Don't worry about these yet. We'll study them later.

<h2>SQL vs NoSQL</h2>

| SQL | NoSQL |
|-----|-------|
| Relational | Non-relational |
| Tables | Various data models |
| Fixed/structured schema traditionally | More flexible schema |
| Strong relationships | Often optimized for specific access patterns |
| SQL queries | Database-specific APIs/query models |
| Strong transactional support | Varies by database |
| Great for structured data | Useful for flexible or massive-scale workloads |

<h2>When Should You Use SQL?</h2>

Suppose you're building a **banking system**.

You have:

```text
Account
Transaction
Customer
Payment
```

These entities have important relationships.

You need strong transactional guarantees.

SQL is usually a strong choice.

```text
Customer
   │
   ▼
Account
   │
   ▼
Transaction
```

<h2>When Should You Use NoSQL?</h2>

Imagine you're building a system that stores **billions of user activity events**.

For example:

```text
User viewed video
User liked video
User searched
User clicked recommendation
```

You may need:

- Huge scale
- High write throughput
- Flexible data
- Horizontal scaling

Depending on the access pattern, a NoSQL database may be a better fit.

<h2>Important: NoSQL Does NOT Mean "No SQL"</h2>

This is a common beginner mistake.

NoSQL does not mean:

> "There is no SQL anywhere."

It generally means:

> **Not Only SQL**

It refers to database systems that don't primarily follow the traditional relational model.

<h2>Can a System Use Both?</h2>

Absolutely.

A large system doesn't have to choose only one.

For example:

```text
                    Application
                         │
              ┌──────────┴──────────┐
              ▼                     ▼
         PostgreSQL              MongoDB
         Users/Orders          Flexible Data
```

And you might also use:

```text
Redis
  ↓
Caching
```

Different databases can solve different problems.

<h2>Real System Example</h2>

Imagine an e-commerce system.

<h3>PostgreSQL</h3>

Store:

```text
Users
Orders
Payments
Products
```

Because relationships and transactions matter.

<h3>Redis</h3>

Store:

```text
Sessions
Popular Products
Frequently accessed data
```

Because fast access matters.

<h3>Search Engine</h3>

Store/search:

```text
Product Search Index
```

Because search is its specialty.

So a real system might look like:

```text
                Application
                     │
        ┌────────────┼────────────┐
        ▼            ▼            ▼
   PostgreSQL      Redis    Search Engine
        │
        ▼
      Orders
```

This is called **polyglot persistence** — using different data stores for different needs.

<h2>The Real Question in System Design</h2>

Don't think:

> "SQL is better."

or:

> "NoSQL is better."

That's the wrong mindset.

Instead ask:

> **What are the requirements and access patterns?**

<h3>Need Complex Relationships + Transactions?</h3>

➡️ SQL is often a good choice.

<h3>Need Flexible Documents?</h3>

➡️ Document NoSQL may fit.

<h3>Need Extremely Fast Key-Value Access?</h3>

➡️ Redis or another key-value store may fit.

<h3>Need Massive Distributed Writes?</h3>

➡️ A distributed NoSQL database may be appropriate.

<h2>Memory Trick</h2>

<h3>SQL</h3>

```text
Structured
Tables
Relationships
Transactions
```

<h3>NoSQL</h3>

```text
Flexible
Different data models
Horizontal scale
Specific access patterns
```

But remember: **modern databases overlap**, so these are tendencies, not absolute rules.

<h2>Interview Question</h2>

### "How would you choose between SQL and NoSQL?"

A strong answer:

> "I'd look at the data model, relationship complexity, transaction requirements, consistency needs, scale, read/write patterns, and operational requirements. I wouldn't choose SQL or NoSQL simply based on which is more popular."

That's exactly the kind of thinking System Design interviews want.

<h2>Quick Revision</h2>

```text
Database
   │
   ├── SQL
   │    ├── Relational
   │    ├── Tables
   │    ├── Relationships
   │    └── Transactions
   │
   └── NoSQL
        ├── Document
        ├── Key-Value
        ├── Wide-Column
        └── Graph
```

<h2>Key Takeaways</h2>

- **SQL → Relational databases**
- **NoSQL → Non-relational databases**
- SQL organizes data primarily using **tables and relationships**.
- NoSQL databases use different models such as **Document, Key-Value, Wide-Column, and Graph**.
- SQL is often a strong choice when **relationships and transactions** are important.
- NoSQL can be useful for **flexible data models and large-scale distributed workloads**.
- **NoSQL does not mean "No SQL"; it generally means "Not Only SQL".**
- A system can use **both SQL and NoSQL databases**.
- Using different databases for different requirements is called **Polyglot Persistence**.
- The correct question is not **"SQL or NoSQL?"**
- The correct question is **"Which database fits the system's requirements and access patterns?"**
