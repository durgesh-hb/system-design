## Database Sharding

<h2>The Problem</h2>

Imagine our database has **1 billion users**.

One database server is handling everything:

```text
                    Users
                      │
                      ▼
                ┌───────────┐
                │  Database │
                │ 1 Billion │
                │   Users   │
                └───────────┘
```

Eventually:

- Storage becomes huge
- CPU becomes a bottleneck
- Queries can become slower
- Too many requests hit one machine
- Vertical scaling becomes expensive or reaches hardware limits

So we distribute the data across **multiple database servers**.

<h2>What is Sharding?</h2>

> **Database sharding is a horizontal scaling technique where data is split across multiple database servers called shards.**

For example:

```text
                 Application
                      │
                      ▼
              ┌──────────────┐
              │ Shard Router │
              └──────┬───────┘
                     │
          ┌──────────┼──────────┐
          ▼          ▼          ▼
      ┌───────┐  ┌───────┐  ┌───────┐
      │Shard 1│  │Shard 2│  │Shard 3│
      │Users  │  │Users  │  │Users  │
      │1-1M   │  │1M-2M  │  │2M-3M  │
      └───────┘  └───────┘  └───────┘
```

Each shard contains **part of the total data**.

This allows the total dataset and workload to be distributed across multiple machines.

<h2>What is a Shard Key?</h2>

The **shard key** determines which shard should store a particular record.

For example:

```text
userId
```

We can use `userId` to determine where the user should be stored.

```text
userId = 101
     ↓
Shard Selection
     ↓
Shard 1
```

Another example:

```text
userId = 205
     ↓
Shard Selection
     ↓
Shard 2
```

And:

```text
userId = 399
     ↓
Shard Selection
     ↓
Shard 3
```

Choosing a good shard key is **very important**.

A poor shard key can cause:

```text
Poor Shard Key
      ↓
Uneven Distribution
      ↓
Hot Shard
      ↓
Bottleneck
```

<h2>Hash-Based Sharding</h2>

One common sharding approach is **hash-based sharding**.

Conceptually:

```text
shard = hash(userId) % numberOfShards
```

Suppose we have 3 shards:

```text
userId = 101
     ↓
hash(101) % 3
     ↓
Shard 2
```

Another user:

```text
userId = 202
     ↓
hash(202) % 3
     ↓
Shard 1
```

Another:

```text
userId = 303
     ↓
hash(303) % 3
     ↓
Shard 3
```

The goal is to distribute records relatively evenly.

<h3>Advantage</h3>

Good distribution of data when the hash function and shard key are chosen appropriately.

<h3>Problem</h3>

Adding or removing shards can cause many records to map to different shards.

This can require **moving a large amount of data**.

Consistent hashing can reduce this movement in some architectures, but we don't need to go deep into it for HLD.

<h2>Range-Based Sharding</h2>

Instead of hashing, we can divide data into ranges.

For example:

```text
Shard 1 → userId 1 - 1,000,000

Shard 2 → userId 1,000,001 - 2,000,000

Shard 3 → userId 2,000,001 - 3,000,000
```

Conceptually:

```text
User IDs

1 ───────── 1M
     ↓
  Shard 1

1M ──────── 2M
     ↓
  Shard 2

2M ──────── 3M
     ↓
  Shard 3
```

<h3>Advantage</h3>

Range queries can be easier because we know which shard contains a particular range.

For example:

```text
Find users with IDs between 1M and 1.5M
```

The relevant data is in:

```text
Shard 2
```

<h3>Problem</h3>

Data or traffic can become unevenly distributed.

For example:

```text
Shard 1 → 10%
Shard 2 → 80% 🔥
Shard 3 → 10%
```

This can create a hot shard.

<h2>Hot Shard 🔥</h2>

A **hot shard** is a shard that receives disproportionately high traffic or contains disproportionately large amounts of data.

For example:

```text
Shard 1 → 10 million requests
Shard 2 → 1 million requests
Shard 3 → 1 million requests
```

Shard 1 becomes overloaded:

```text
          Traffic
             │
      ┌──────┼──────┐
      ▼      ▼      ▼
   Shard 1 Shard 2 Shard 3
     🔥       │       │
   Overloaded
```

This can happen because of:

- Poor shard key
- Uneven data distribution
- Uneven access patterns
- A small number of very popular records

<h2>Resharding</h2>

As the application grows, we may need more shards.

Before:

```text
Shard 1
Shard 2
Shard 3
```

Later:

```text
Shard 1
Shard 2
Shard 3
Shard 4
Shard 5
Shard 6
```

Moving existing data to the new shard layout is called **Resharding**.

Resharding can be complicated because we need to:

- Move data
- Keep the application working
- Avoid data loss
- Maintain consistency
- Minimize downtime
- Update routing information

This is why choosing a good shard key from the beginning is important.

<h2>Cross-Shard Queries</h2>

One major challenge with sharding is that a query may require data from multiple shards.

For example:

```text
             Query
               │
       ┌───────┼───────┐
       ▼       ▼       ▼
    Shard 1 Shard 2 Shard 3
```

The system may need to:

```text
Send query to multiple shards
        ↓
Collect results
        ↓
Merge results
        ↓
Return final response
```

Cross-shard queries can increase:

- Network communication
- Query latency
- Application complexity

This is one reason the shard key should align with common access patterns.

<h2>Benefits of Sharding</h2>

<h3>Horizontal Scalability</h3>

We can add more database servers as the system grows.

```text
        Database
           │
     ┌─────┼─────┐
     ▼     ▼     ▼
   Shard  Shard  Shard
     1      2      3
```

<h3>More Storage Capacity</h3>

Instead of storing the entire dataset on one machine, data is distributed across multiple machines.

```text
Single DB
    ↓
Limited Storage

Multiple Shards
    ↓
Distributed Storage
```

<h3>More Traffic Capacity</h3>

Different shards can handle requests independently.

Instead of:

```text
100% Traffic
      ↓
    DB 1
```

we can have:

```text
Traffic
   │
   ├──→ Shard 1
   ├──→ Shard 2
   ├──→ Shard 3
   └──→ Shard 4
```

<h3>Reduces Pressure on One Database</h3>

The workload is distributed instead of forcing one database server to handle the entire dataset and traffic.

<h2>Disadvantages of Sharding</h2>

Sharding provides scalability, but it also introduces significant complexity.

<h3>More Complexity</h3>

The application or routing layer needs to determine which shard contains the required data.

```text
Application
     ↓
Shard Router
     ↓
Correct Shard
```

<h3>Cross-Shard Queries</h3>

Some queries may need data from multiple shards.

```text
             Query
               │
       ┌───────┼───────┐
       ▼       ▼       ▼
    Shard 1 Shard 2 Shard 3
```

This can increase latency and complexity.

<h3>Resharding Complexity</h3>

Adding or removing shards may require moving existing data.

```text
Old Layout
    ↓
Move Data
    ↓
New Layout
```

<h3>Bad Shard Key</h3>

A poor shard key can create:

```text
Uneven Data
     ↓
Uneven Traffic
     ↓
Hot Shard
     ↓
Bottleneck
```

<h2>Sharding vs Replication vs Partitioning</h2>

This is a **very common interview question**.

| Concept | Meaning |
|---|---|
| **Replication** | Copy the same data |
| **Partitioning** | Split data into smaller logical parts |
| **Sharding** | Distribute data parts across multiple database servers |

<h3>Replication</h3>

```text
Primary
   │
   ├── Replica 1
   └── Replica 2
```

The same dataset is copied to multiple servers.

```text
Replication
     ↓
   COPY
```

<h3>Partitioning</h3>

```text
Database
   │
   ├── Partition 1
   ├── Partition 2
   └── Partition 3
```

The dataset is divided into smaller logical pieces.

```text
Partitioning
      ↓
    SPLIT
```

<h3>Sharding</h3>

```text
             Database
                │
        ┌───────┼───────┐
        ▼       ▼       ▼
      DB 1    DB 2    DB 3
     Shard 1 Shard 2 Shard 3
```

The data is split and distributed across different machines.

```text
Sharding
     ↓
SPLIT + DISTRIBUTE
```

<h2>Benefits vs Costs</h2>

| Benefits | Costs |
|---|---|
| Horizontal scalability | More system complexity |
| More storage capacity | Cross-shard queries |
| More traffic capacity | Resharding complexity |
| Reduces pressure on one DB | Poor shard key can create hot shards |
| Can support massive datasets | More difficult operations and monitoring |

<h2>When Should We Use Sharding?</h2>

Sharding is generally considered when a single database can no longer handle the required:

- Storage
- Read/write workload
- CPU
- Memory
- Overall scale

A typical evolution might look like:

```text
Single Database
       ↓
Query Optimization
       ↓
Indexes / Caching
       ↓
Vertical Scaling
       ↓
Read Replicas
       ↓
Partitioning
       ↓
Sharding
```

The exact architecture depends on the workload.

> **Don't shard just because the application is large. Shard when the database's scale requirements justify the added complexity.**

<h2>Interview Answer</h2>

If an interviewer asks:

> **What is database sharding?**

A strong HLD answer is:

> **Database sharding is a horizontal scaling technique where data is divided into smaller partitions called shards and distributed across multiple database servers. A shard key determines which shard stores each record. Sharding helps scale storage and traffic beyond the capacity of a single database server, but introduces complexity such as cross-shard queries, hot shards, and resharding.**

<h2>Memory Trick</h2>

Remember:

```text
Replication
     ↓
   COPY
```

```text
Partitioning
     ↓
   SPLIT
```

```text
Sharding
     ↓
SPLIT + DISTRIBUTE
ACROSS MACHINES
```

<h3>Shard Key</h3>

```text
Record
   ↓
Shard Key
   ↓
Shard Selection
   ↓
Correct Shard
```

<h3>Hot Shard</h3>

```text
Poor Distribution
       ↓
One Shard Gets Too Much Traffic
       ↓
🔥 Hot Shard
       ↓
Bottleneck
```

<h3>Resharding</h3>

```text
Existing Shards
      ↓
Add / Remove Shards
      ↓
Move Data
      ↓
New Shard Layout
```

<h2>Quick Revision</h2>

```text
                 Application
                      │
                      ▼
                Shard Router
                      │
          ┌───────────┼───────────┐
          ▼           ▼           ▼
       Shard 1     Shard 2     Shard 3
       Data A      Data B      Data C
```

<h3>Core Idea</h3>

```text
One Huge Database
       ↓
Split Data
       ↓
Multiple Shards
       ↓
Multiple Database Servers
```

<h3>Most Important Concepts</h3>

```text
Shard Key
    ↓
Determines where data goes

Hash Sharding
    ↓
Hash-based distribution

Range Sharding
    ↓
Range-based distribution

Hot Shard
    ↓
Uneven traffic/data

Resharding
    ↓
Redistributing data when shard layout changes
```

<h3>Final Memory Trick</h3>

```text
Replication
     ↓
Same Data
     ↓
Multiple Copies


Partitioning
     ↓
Split Data
     ↓
Logical Partitions


Sharding
     ↓
Split Data
     ↓
Different Machines
```
