Databases
Lesson 1: SQL vs NoSQL

Before learning indexing, replication, sharding, etc., we need to understand why different types of databases exist.

1. What is a Database?

A database is a system used to store, organize, and retrieve data.

Example: An e-commerce application needs to store:

Users
Products
Orders
Payments
Reviews

A database stores this information so the application can retrieve it when needed.

2. Two Major Categories

At a high level:

                    Databases
                       │
             ┌─────────┴─────────┐
             ▼                   ▼
           SQL                 NoSQL
       Relational          Non-Relational
3. SQL Database

SQL databases are relational databases.

They organize data into tables.

Example:

Users
ID	Name	Email
1	Eren	eren@gmail.com
2	Rahul	rahul@gmail.com
Orders
ID	User_ID	Product
101	1	Laptop
102	2	Phone

The tables can be related.

Users
  │
  │ user_id
  ▼
Orders
4. Examples of SQL Databases

Common examples:

MySQL
PostgreSQL
Oracle Database
Microsoft SQL Server

You've already worked with PostgreSQL, so this should feel familiar.

5. NoSQL Database

NoSQL databases don't primarily use the traditional table/relationship model.

Different NoSQL databases use different data models.

For example, a document database might store:

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

The structure is more flexible.

6. Examples of NoSQL

Some common types:

Document

MongoDB

JSON-like documents
Key-Value

Redis

key → value
Wide-Column

Cassandra

Large-scale distributed data
Graph

Neo4j

Nodes + Relationships

Don't worry about these yet. We'll study them later.

7. SQL vs NoSQL
SQL	NoSQL
Relational	Non-relational
Tables	Various models
Fixed/structured schema traditionally	More flexible schema
Strong relationships	Often optimized for specific access patterns
SQL queries	Database-specific APIs/query models
Strong transactional support	Varies by database
Great for structured data	Useful for flexible or massive-scale workloads
8. When Should You Use SQL?

Suppose you're building a banking system.

You have:

Account
Transaction
Customer
Payment

These entities have important relationships.

You need strong transactional guarantees.

SQL is usually a strong choice.

Customer
   │
   ▼
Account
   │
   ▼
Transaction
9. When Should You Use NoSQL?

Imagine you're building a system that stores billions of user activity events.

For example:

User viewed video
User liked video
User searched
User clicked recommendation

You may need:

Huge scale
High write throughput
Flexible data
Horizontal scaling

Depending on the access pattern, a NoSQL database may be a better fit.

10. Important: NoSQL Does NOT Mean "No SQL"

This is a common beginner mistake.

 NoSQL does not mean:

"There is no SQL anywhere."

It generally means:

Not Only SQL

It refers to database systems that don't primarily follow the traditional relational model.

11. Can a System Use Both?

Absolutely. 

A large system doesn't have to choose only one.

For example:

                    Application
                         │
              ┌──────────┴──────────┐
              ▼                     ▼
         PostgreSQL              MongoDB
         Users/Orders          Flexible Data

And you might also use:

Redis
  ↓
Caching

Different databases can solve different problems.

12. Real System Example

Imagine an e-commerce system.

PostgreSQL

Store:

Users
Orders
Payments
Products

Because relationships and transactions matter.

Redis

Store:

Sessions
Popular Products
Frequently accessed data

Because fast access matters.

Elasticsearch

Store/search:

Product Search Index

Because search is its specialty.

So a real system might look like:

                Application
                     │
        ┌────────────┼────────────┐
        ▼            ▼            ▼
   PostgreSQL      Redis    Search Engine
        │
        ▼
      Orders

This is called polyglot persistence—using different data stores for different needs.

13. The Real Question in System Design

Don't think:

"SQL is better."

or:

"NoSQL is better."

That's the wrong mindset.

Instead ask:

What are the requirements and access patterns?

For example:

Need complex relationships + transactions?

SQL is often a good choice.

Need flexible documents?

 Document NoSQL may fit.

Need extremely fast key-value access?

 Redis or another key-value store may fit.

Need massive distributed writes?

 A distributed NoSQL database may be appropriate.

 Memory Trick

Think:

SQL
Structured
Tables
Relationships
Transactions
NoSQL
Flexible
Different data models
Horizontal scale
Specific access patterns

But remember: modern databases overlap, so these are tendencies, not absolute rules.

 Interview Question
"How would you choose between SQL and NoSQL?"

A strong answer:

"I'd look at the data model, relationship complexity, transaction requirements, consistency needs, scale, read/write patterns, and operational requirements. I wouldn't choose SQL or NoSQL simply based on which is more popular."

That's exactly the kind of thinking System Design interviews want.

 Quick Revision
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
Remember:

SQL vs NoSQL is not "which is better?"

It's "which fits the system's requirements better?"