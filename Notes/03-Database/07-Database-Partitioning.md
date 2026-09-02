## Database Partitioning

<h2>What is Partitioning?</h2>

> **Database partitioning means splitting a large dataset into smaller pieces called partitions.**

Imagine we have:

```text
Users Table
10 Million Users
```

Instead of treating it as one huge table:

```text
10 Million Rows
```

we split it:

```text
             Users
               │
       ┌───────┼───────┐
       ▼       ▼       ▼
   Partition  Partition  Partition
      1          2          3
```

The data is split **logically based on some rule**.

<h2>Why Do We Need Partitioning?</h2>

Suppose we have:

```text
1 Billion Orders
```

Working with one enormous table can become difficult.

Partitioning can help with:

- Query performance
- Data management
- Scaling large datasets
- Reducing the amount of data a query needs to scan

The main idea is:

> **Instead of working with one huge dataset, work with smaller subsets of the data.**

<h2>Horizontal Partitioning</h2>

Horizontal partitioning splits **rows** across partitions.

Suppose we have:

```text
Orders

ID     User
1      A
2      B
3      C
4      D
5      E
```

We can split the rows based on a rule:

```text
Partition 1
Users 1–1000

Partition 2
Users 1001–2000

Partition 3
Users 2001–3000
```

The columns remain the same.

Only the **rows are divided**.

```text
Same Columns
Different Rows
```

<h3>Example</h3>

```text
             Orders
                │
        ┌───────┼───────┐
        ▼       ▼       ▼
    Partition  Partition  Partition
       1          2          3
    Rows 1-1000  1001-2000  2001-3000
```

So:

> **Horizontal Partitioning = Split Rows**

<h2>Vertical Partitioning</h2>

Vertical partitioning splits **columns**.

Suppose we have:

```text
Users

ID
Name
Email
Address
ProfilePhoto
Bio
```

We could separate frequently used information from large or less frequently used information.

```text
User Table
-----------
ID
Name
Email
```

And:

```text
User Profile Table
------------------
ID
Address
ProfilePhoto
Bio
```

The data is separated based on **columns**.

So:

> **Vertical Partitioning = Split Columns**

<h2>Horizontal vs Vertical Partitioning</h2>

| Horizontal Partitioning | Vertical Partitioning |
|---|---|
| Splits rows | Splits columns |
| Same columns in each partition | Different columns in each table/partition |
| Useful for very large numbers of records | Useful for separating frequently and rarely accessed data |
| Example: users 1–1M, 1M–2M | Example: basic info vs profile info |

<h2>Real-Life Analogy</h2>

Imagine a huge book.

<h3>Horizontal Partitioning</h3>

Split the book by pages:

```text
Pages 1–100
Pages 101–200
Pages 201–300
```

Each section contains the same type of information.

<h3>Vertical Partitioning</h3>

Split information by category:

```text
Book A
Names + Basic Information

Book B
Detailed Information
```

<h2>Partitioning vs Replication</h2>

This distinction is very important.

<h3>Replication</h3>

Replication **copies the same data** to multiple servers.

```text
             Primary
             /     \
            ↓       ↓
       Replica 1  Replica 2
```

Each replica contains roughly the same dataset after it catches up.

> **Replication = Copy**

<h3>Partitioning</h3>

Partitioning **splits the data** into smaller pieces.

```text
Database
   │
   ├── Partition 1 → Data A
   ├── Partition 2 → Data B
   └── Partition 3 → Data C
```

> **Partitioning = Split**

<h2>Partitioning vs Sharding </h2>

These concepts are closely related, and terminology can vary between database technologies.

For our HLD understanding:

<h3>Partitioning</h3>

Splitting data into smaller logical pieces.

```text
Database
   │
   ├── Partition 1
   ├── Partition 2
   └── Partition 3
```

<h3>Sharding</h3>

Distributing those data partitions across **different database servers**.

```text
             Application
                  │
        ┌─────────┼─────────┐
        ▼         ▼         ▼
      DB 1      DB 2      DB 3
      Shard 1   Shard 2   Shard 3
```

For our HLD learning, remember:

> **Sharding = distributing data partitions across multiple machines.**

<h2>Why Partition Instead of Just Adding an Index?</h2>

An index helps the database **find specific data faster**.

```text
Index
  ↓
Find data faster
```

Partitioning can reduce the amount of data that a query needs to consider.

```text
Partitioning
     ↓
Work with a smaller subset of data
```

They are not alternatives in every situation.

**Indexes and partitioning can often be used together.**

<h2>Example: Orders</h2>

Suppose we have:

```text
1 Billion Orders
```

And most queries ask for orders from a particular date:

```text
WHERE order_date = '2026-09-02'
```

We could partition the orders by date:

```text
Orders
  │
  ├── 2026-08
  ├── 2026-09
  ├── 2026-10
  └── ...
```

A query for September can focus on the relevant partition instead of considering unrelated partitions.

This idea is commonly called **Partition Pruning**.

<h2>Partition Key</h2>

When splitting data, we need a rule that determines where each record belongs.

This is based on a **Partition Key**.

Common examples:

```text
user_id
```

```text
order_date
```

```text
region
```

For example:

```text
user_id = 123
       ↓
Partition Calculation
       ↓
Partition 2
```

Choosing a good partition key is extremely important.

<h3>Good Partition Key Characteristics</h3>

A good partition key should ideally:

- Distribute data reasonably evenly
- Distribute traffic reasonably evenly
- Match common query patterns
- Avoid creating overloaded partitions
- Remain stable enough for the chosen partitioning strategy

<h2>Bad Partitioning</h2>

Suppose we partition users by:

```text
country
```

And we have:

```text
India → 700 Million users
Other countries → 300 Million users
```

Now:

```text
India Partition 🔥
```

contains a huge amount of data and may receive a large amount of traffic.

This creates an **imbalanced partition**.

The overloaded partition can become a bottleneck.

<h2>Hot Partition</h2>

A partition becomes a **hot partition** when it receives disproportionately high traffic or contains a disproportionately large amount of data.

For example:

```text
Partition 1 → 80% 🔥
Partition 2 → 10%
Partition 3 → 10%
```

Partition 1 becomes the bottleneck.

This is why choosing the partition key is important.

<h2>Good Partitioning</h2>

Ideally, data and workload should be reasonably distributed.

For example:

```text
Partition 1 → 33%
Partition 2 → 34%
Partition 3 → 33%
```

This is better than:

```text
Partition 1 → 80% 🔥
Partition 2 → 10%
Partition 3 → 10%
```

The goal is not always perfect equality.

The goal is to **avoid severe imbalance and hotspots**.

<h2>Partitioning and Query Performance</h2>

Partitioning can improve query performance when the query contains the partition key.

For example:

```text
Partition by order_date
```

Query:

```text
WHERE order_date = '2026-09-02'
```

The database may only need to examine the relevant partition.

Conceptually:

```text
1 Billion Rows
      ↓
Partitioning
      ↓
Relevant Partition
      ↓
Smaller Amount of Data to Examine
```

But partitioning does **not automatically make every query faster**.

Queries that don't align with the partitioning strategy may still need to examine many partitions.

<h2>Memory Trick</h2>

Remember these three:

```text
Replication
     ↓
   COPY

Partitioning
     ↓
   SPLIT

Sharding
     ↓
SPLIT + DISTRIBUTE
ACROSS MACHINES
```

And:

```text
Horizontal
     ↓
   Rows

Vertical
     ↓
 Columns
```

<h2>Interview Questions</h2>

<h3>What is database partitioning?</h3>

Database partitioning is splitting a large dataset into smaller logical pieces called partitions.

<h3>What is horizontal partitioning?</h3>

Horizontal partitioning splits **rows** across partitions.

```text
Rows
 ↓
Partition 1
Partition 2
Partition 3
```

<h3>What is vertical partitioning?</h3>

Vertical partitioning splits **columns** into separate tables or data groups.

```text
Columns
   ↓
Basic Data
+
Detailed Data
```

<h3>What is the difference between partitioning and replication?</h3>

**Partitioning splits data.**

**Replication copies data.**

```text
Partitioning → Split

Replication → Copy
```

<h3>What is a partition key?</h3>

A partition key is the field or rule used to determine which partition should contain a particular record.

<h3>What happens if the partition key is poorly chosen?</h3>

Data or traffic can become unevenly distributed.

This can create:

```text
Hot Partitions
     ↓
Uneven Load
     ↓
Bottleneck
```

<h3>What is a hot partition?</h3>

A hot partition is a partition that receives significantly more traffic or data than other partitions.

<h3>Does partitioning always improve query performance?</h3>

No.

Partitioning helps most when queries can target a small number of relevant partitions.

Poorly aligned queries may still need to scan many partitions.

<h3>Partitioning vs Sharding?</h3>

For HLD:

**Partitioning** = split data into logical pieces.

**Sharding** = distribute those pieces across multiple database machines.

<h2>Quick Revision</h2>

```text
             DATABASE
                │
        ┌───────┴────────┐
        ▼                ▼
   Horizontal        Vertical
   Partitioning      Partitioning
        │                │
      Rows             Columns
```

<h3>Replication</h3>

```text
Same Data
    ↓
Multiple Servers
```

<h3>Partitioning</h3>

```text
Large Dataset
     ↓
Smaller Logical Pieces
```

<h3>Sharding</h3>

```text
Data
  ↓
Split into Partitions
  ↓
Distribute Across Machines
```

<h3>Partition Key</h3>

```text
Record
  ↓
Partition Key
  ↓
Partition
```

<h3>Important Problem</h3>

```text
Poor Partition Key
       ↓
Uneven Distribution
       ↓
Hot Partition
       ↓
Bottleneck
```

<h3>Most Important Difference</h3>

```text
Replication
    ↓
COPY data

Partitioning
    ↓
SPLIT data

Sharding
    ↓
SPLIT + DISTRIBUTE
ACROSS MACHINES
```
