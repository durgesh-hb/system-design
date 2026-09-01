## Database Scaling

<h2>The Problem</h2>

As an application grows, the number of requests also increases.

```text
Users
  ↓
Backend
  ↓
Database
```

A small database might handle:

```text
100 requests/sec
```

But eventually traffic may grow:

```text
100 → 1,000 → 10,000 → 100,000 requests/sec
```

The database can become the **bottleneck**.

So we need to scale the database.

<h2>Two Main Ways to Scale</h2>

```text
Database Scaling
      │
      ├── Vertical Scaling
      │
      └── Horizontal Scaling
```

<h2>Vertical Scaling</h2>

Vertical scaling means **increasing the resources of the existing database server**.

Before:

```text
8 CPU
32 GB RAM
500 GB SSD
```

After:

```text
32 CPU
128 GB RAM
2 TB SSD
```

The database is still running on the **same server**, but the server becomes more powerful.

<h3>Real-Life Analogy</h3>

Instead of opening another shop, make the existing shop bigger.

```text
Small Shop
    ↓
Bigger Shop
    ↓
More Storage
    ↓
More Capacity
```

This is **Vertical Scaling**.

<h3>Advantages</h3>

- Simple
- Easy to implement
- Usually requires fewer architectural changes
- Good starting point for smaller systems

<h3>Disadvantages</h3>

- Hardware has limits
- Very large machines can become expensive
- Scaling eventually reaches a physical/technical limit
- Does not provide unlimited scalability

```text
8 CPU
  ↓
32 CPU
  ↓
64 CPU
  ↓
128 CPU
  ↓
Hardware Limit
```

<h2>Horizontal Scaling</h2>

Horizontal scaling means **adding more database servers instead of making one server bigger**.

Instead of:

```text
       Database
          
```

we add multiple machines:

```text
       Database
       /      \
      DB1     DB2
```

Or:

```text
        Database
       /   |   \
     DB1  DB2  DB3
```

The workload can now be distributed across multiple machines.

<h3>Real-Life Analogy</h3>

Instead of building one huge supermarket, open multiple branches.

```text
Bangalore
Mumbai
Delhi
Chennai
```

Customers can be distributed between branches.

This is **Horizontal Scaling**.

<h3>Why Is Horizontal Scaling Harder?</h3>

With one database:

```text
Application
    ↓
Database
```

Everything is simple.

With multiple databases:

```text
              Application
                   │
          ┌────────┼────────┐
          ▼        ▼        ▼
         DB1      DB2      DB3
```

Now we need to answer:

> Which database contains the data I need?

We also need to handle:

- Replication
- Partitioning
- Sharding
- Consistency
- Failover

<h2>Read vs Write Traffic</h2>

This is extremely important when scaling databases.

Suppose an application receives:

```text
100,000 requests
```

And:

```text
90,000 → READ
10,000 → WRITE
```

This is a **read-heavy system**.

Examples:

```text
View post
View profile
View feed
View comments
View products
```

Most operations are reads.

If most traffic is reads, we can use **Read Replicas** instead of immediately sharding the database.

<h2>Read Replicas</h2>

A common architecture is:

```text
             Primary DB
                 │
             Replication
          ┌──────┴──────┐
          ▼             ▼
      Replica 1     Replica 2
```

The **Primary** handles writes.

The **Replicas** handle reads.

```text
             Application
              /        \
         Writes         Reads
            ↓             ↓
        Primary       Replicas
```

<h3>Example</h3>

A write operation:

```text
POST /orders
```

Changes data, so it goes to:

```text
POST
  ↓
Primary DB
```

A read operation:

```text
GET /products
```

Only reads data, so it can go to:

```text
GET
  ↓
Read Replica
```

<h3>Why Does This Help?</h3>

Suppose one database can handle:

```text
10,000 reads/sec
```

Instead of sending all reads to one database:

```text
             Primary
                ↑
          100,000 reads
```

we distribute reads:

```text
             Primary
             Writes
                │
       ┌────────┴────────┐
       ▼                 ▼
   Replica 1         Replica 2
     Reads             Reads
```

The read workload is spread across multiple machines.

This is called **Read Scaling**.

<h2>Replication</h2>

The Primary database sends changes to the replicas.

```text
Primary DB
   │
   │ Replication
   ├───────────┐
   ▼           ▼
Replica 1   Replica 2
```

For example:

```text
Primary:

Product price = ₹50,000
       │
       │ Replication
       ▼
Replicas receive update
       │
       ▼
Product price = ₹50,000
```

Replication allows multiple database servers to maintain copies of the data.

<h2>Replication Lag</h2>

A major problem with replicas is **Replication Lag**.

Suppose the Primary is updated:

```text
Primary
Price = ₹45,000
```

But the replica has not received the update yet:

```text
Replica
Price = ₹50,000
```

Now:

```text
User writes
    ↓
Primary
    ↓
Immediately reads
    ↓
Replica
```

The user may see the **old value**.

This delay is called:

> **Replication Lag**

Replication lag can create **stale reads**.

This means the data returned by a replica may be slightly older than the data in the Primary.

<h2>Vertical vs Horizontal Scaling</h2>

| Vertical Scaling | Horizontal Scaling |
|---|---|
| Make one machine bigger | Add more machines |
| Simple | More complex |
| Hardware limit | Can scale further |
| Easier initially | Better for massive scale |
| Expensive at high end | Requires distributed architecture |

<h2>Typical Database Evolution</h2>

<h3>Single Database</h3>

Start with a simple architecture:

```text
Application
    ↓
Single DB
```

<h3>Vertical Scaling</h3>

When the database needs more resources:

```text
Application
    ↓
Bigger DB
```

This is **Vertical Scaling**.

<h3>Read Scaling</h3>

When read traffic becomes the bottleneck:

```text
             Primary
            /       \
           ▼         ▼
       Replica 1  Replica 2
```

This is **Read Scaling using Replicas**.

<h3>Horizontal Data Scaling</h3>

When the data or workload becomes too large for the existing architecture:

```text
Multiple Partitions / Shards
```

This is where **Partitioning and Sharding** become important.

<h2>Don't Immediately Shard</h2>

A common beginner mistake is:

> "My application is big, so let's shard the database."

That is not always necessary.

First identify the actual bottleneck.

A possible progression is:

```text
Optimize Queries
       ↓
Add Indexes
       ↓
Caching
       ↓
Vertical Scaling
       ↓
Read Replicas
       ↓
Partitioning / Sharding
```

The exact order depends on the workload.

The important principle is:

> **Solve the bottleneck you actually have instead of adding distributed-system complexity too early.**

<h2>Key Takeaways</h2>

- **Vertical Scaling** = make one database server stronger.
- **Horizontal Scaling** = add more database servers.
- **Read Replicas** help scale read-heavy workloads.
- The **Primary** generally handles writes.
- **Replicas** generally handle reads.
- Replicas can suffer from **Replication Lag**.
- Replication lag can cause **stale reads**.
- Horizontal scaling introduces additional complexity.
- **Partitioning and Sharding** help distribute large amounts of data.
- Don't use sharding unless the actual workload requires it.
- Always identify the bottleneck before choosing a scaling strategy.

<h2>Memory Trick</h2>

<h3>Vertical Scaling</h3>

> **Make one machine stronger.**

```text
💪 DB
  ↓
💪💪💪 Bigger DB
```

<h3>Horizontal Scaling</h3>

> **Add more machines.**

```text
DB1 + DB2 + DB3
```

<h3>Read Replica</h3>

> **Copy data so multiple databases can serve reads.**

```text
             Primary
                │
        ┌───────┴───────┐
        ▼               ▼
    Replica 1       Replica 2
```
