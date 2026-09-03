## NoSQL Databases 

NoSQL databases are designed for data models and workloads that don't necessarily fit the traditional relational table structure.

The four major NoSQL types you should know for HLD are:

```text
NoSQL
 │
 ├── Document
 ├── Key-Value
 ├── Wide-Column
 └── Graph
```

<h2>Document Database </h2>

A Document Database stores data as **documents**, usually in a JSON-like format.

Example:

```json
{
  "userId": 101,
  "name": "Durgesh",
  "email": "durgesh@example.com",
  "skills": ["Java", "AWS", "React"]
}
```

Instead of storing data like a traditional relational table:

```text
Users Table
------------------------
id | name | email
```

we store a complete document.

<h3>Example</h3>

**MongoDB** is a popular document database.

A document can contain nested objects, arrays, and different fields.

For example, an e-commerce application may have products with different attributes:

```text
Phone
 ├── RAM
 ├── Storage
 └── Camera
```

while shoes may have:

```text
Shoes
 ├── Size
 ├── Material
 └── Brand
```

A document database can handle these flexible and evolving structures naturally.

<h3>Good For</h3>

- User profiles
- Product catalogs
- Content management systems
- Applications with flexible or evolving schemas
- Data that is naturally represented as documents

<h2>Key-Value Database </h2>

A Key-Value database stores data as:

```text
Key → Value
```

Example:

```text
KEY        VALUE
-------------------------
user:101   Durgesh
user:102   Rahul
user:103   John
```

Think of it like a giant dictionary.

```text
key → value
```

<h3>Example</h3>

**Redis** is a popular key-value data store.

For example:

```text
session:user101 → abc123xyz
```

When the user makes a request:

```text
Application
     │
     ▼
   Redis
     │
     ▼
session:user101
```

The application can quickly retrieve the value using the key.

<h3>Good For</h3>

- Caching
- User sessions
- Shopping carts
- Counters
- Fast key-based lookups
- Temporary or frequently accessed data

<h2>Wide-Column Database </h2>

Wide-column databases organize data around rows and columns, but they are designed for **large-scale distributed workloads**.

Examples include:

- Apache Cassandra
- Apache HBase

A simplified representation might look like:

```text
UserId       Name       Country       LoginTime
------------------------------------------------
101          A          India         10:00
102          B          USA           10:05
103          C          India         10:10
```

The important point is not simply that the data looks like a table.

Wide-column databases are designed to distribute very large amounts of data across many machines.

<h2>Example</h2>

Imagine we're storing user activity.

```text
User 101

login_time → 10:00
location   → Mysore
device     → Mobile
```

Another user might have:

```text
User 102

login_time → 11:00
location   → Bangalore
```

And another:

```text
User 103

login_time → 12:00
device     → Laptop
browser    → Chrome
```

> The rows don't have to behave like a rigid traditional relational table in the same way.

<h3>Good For</h3>

- Massive-scale applications
- High-throughput workloads
- Large-scale event data
- Time-series data
- High write workloads
- Systems requiring high availability across many nodes

<h2>Graph Database </h2>

Graph databases are designed for **relationships between entities**.

The basic model is:

```text
Person → FRIEND → Person

Person → FOLLOWS → Person

Person → WORKS_AT → Company
```

A graph can look like:

```text
       Durgesh
       /     \
   FRIEND    FOLLOWS
     /         \
   Rahul      John
```

Graph databases represent entities as **nodes** and relationships as **edges**.

<h3>Example</h3>

**Neo4j** is a popular graph database.

<h3>Good For</h3>

- Social networks
- Recommendation systems
- Fraud detection
- Network analysis
- Relationship-heavy applications
- Knowledge graphs

For example:

> "Find friends of friends who like the same movies."

This type of relationship traversal is naturally represented using a graph.

<h2>Quick Comparison</h2>

| Type | Data Model | Good For |
|---|---|---|
| **Document** | JSON-like documents | Profiles, catalogs, content |
| **Key-Value** | Key → Value | Cache, sessions, carts |
| **Wide-Column** | Distributed rows/columns | Massive-scale workloads |
| **Graph** | Nodes + relationships | Social networks, recommendations |

<h2>How to Choose a NoSQL Database?</h2>

Don't memorize:

> "MongoDB is always better."

Instead, start with the **data model and access patterns**.

Ask:

<h3>Flexible Document Data?</h3>

```text
Flexible Documents
       ↓
Document Database
```

Example:

```text
MongoDB
```

<h3>Simple Key → Value Lookup?</h3>

```text
Key → Value
     ↓
Key-Value Database
```

Example:

```text
Redis
```

<h3>Massive Distributed Workload?</h3>

```text
Massive Data
     +
High Throughput
     +
Distributed Workload
       ↓
Wide-Column Database
```

Examples:

```text
Cassandra
HBase
```

<h3>Relationships Are the Main Thing?</h3>

```text
Relationships
      ↓
Graph Database
```

Example:

```text
Neo4j
```

<h2>SQL vs NoSQL</h2>

The choice should not be:

```text
SQL = Old
NoSQL = New
```

or:

```text
NoSQL = Faster
```

Instead, choose based on:

```text
Data Model
    +
Access Patterns
    +
Scale
    +
Consistency Requirements
    +
Availability Requirements
```

SQL databases are often a good choice when you need:

- Strong relational modeling
- Complex joins
- Structured schemas
- Transactions
- Strong consistency requirements

NoSQL databases can be a good choice when you need:

- Flexible data models
- Very large horizontal scale
- High throughput
- Specific access patterns
- Specialized data models such as key-value or graph

<h2>Polyglot Persistence</h2>

A large system does not necessarily have to use only one database.

Different parts of the system can use different storage technologies based on their requirements.

For example:

```text
                    Application
                        │
        ┌───────────────┼────────────────┐
        ▼               ▼                ▼
   User Profiles      Cache         Relationships
        │               │                │
   Document DB       Redis           Graph DB
```

Another part of the system might use a relational database for transactional data.

This approach is called:

> **Polyglot Persistence**

**Polyglot Persistence = using different databases for different workloads within the same system.**

<h2>Real System Example</h2>

Imagine we are designing a social media application.

We might choose:

```text
User Profiles
      ↓
Document Database
```

```text
Sessions / Cache
      ↓
Key-Value Database
```

```text
Large-Scale Events
      ↓
Wide-Column Database
```

```text
Friend / Follow Relationships
      ↓
Graph Database
```

The goal is not to use NoSQL everywhere.

The goal is to choose the storage technology that best fits each workload.

<h2>Interview Question</h2>

<h3>Why would you choose NoSQL instead of SQL?</h3>

A strong HLD answer:

> "I'd choose NoSQL when the workload needs a flexible data model, very large horizontal scale, high throughput, or a specialized data model such as key-value or graph relationships. The decision should be based on access patterns, scalability, availability, and consistency requirements rather than simply assuming NoSQL is faster."

<h3>Is NoSQL Always Better Than SQL?</h3>

No.

Both have different strengths.

```text
SQL
 ↓
Structured + Relational + Transactions

NoSQL
 ↓
Flexible + Distributed + Specialized Models
```

The correct choice depends on the system requirements.

<h2>Memory Trick</h2>

Remember the four major NoSQL types:

```text
Document
    ↓
JSON-like Data
```

```text
Key-Value
    ↓
Key → Value
```

```text
Wide-Column
    ↓
Massive Distributed Data
```

```text
Graph
    ↓
Relationships
```

<h3>Simple Selection Rule</h3>

```text
Flexible Documents
       ↓
   Document DB


Key → Value
       ↓
   Key-Value DB


Massive Distributed Workload
       ↓
   Wide-Column DB


Relationships
       ↓
    Graph DB
```

<h2>Quick Revision</h2>

```text
                 NoSQL
                   │
       ┌───────────┼───────────┐
       │           │           │
       ▼           ▼           ▼
   Document    Key-Value   Wide-Column
       │           │           │
   Documents     Cache      Massive Scale
   Profiles      Sessions   High Throughput
   Catalogs      Carts      Distributed Data
       │
       └─────────────────────────────┐
                                     ▼
                                   Graph
                                     │
                               Relationships
```

<h3>Core Concepts</h3>

```text
Document
→ JSON-like documents

Key-Value
→ Key → Value

Wide-Column
→ Large distributed workloads

Graph
→ Nodes + Relationships
```

<h3>Final Memory Trick</h3>

```text
Document     → JSON-like data
Key-Value    → key → value
Wide-Column  → massive distributed data
Graph        → relationships
```

And:

```text
SQL vs NoSQL
      ↓
Choose based on
      ↓
Data Model
Access Pattern
Scale
Consistency
Availability
```
