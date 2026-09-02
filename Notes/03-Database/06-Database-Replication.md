## Database Replication

<h2>What is Database Replication?</h2>

Database replication means **keeping copies of the same database data on multiple database servers**.

Instead of:

```text
Application
    ↓
   DB
```

we have:

```text
              Application
                   ↓
              Primary DB
              /       \
             ↓         ↓
        Replica 1   Replica 2
```

The replicas contain copies of the Primary's data.

<h2>Why Do We Need Replication?</h2>

There are three major reasons:

<h3>Scale Reads</h3>

The Primary handles writes while replicas handle reads.

```text
             Primary
                ↓
              Writes

Replica 1 ─────→ Reads
Replica 2 ─────→ Reads
```

Instead of one database handling everything, read traffic can be distributed across replicas.

<h3>High Availability</h3>

If the Primary fails:

```text
Primary -- fails
   ↓
Replica 1 -- working
```

A replica can potentially be promoted to become the new Primary.

This helps keep the application available.

<h3>Disaster Recovery</h3>

Having copies of data on separate database servers can help recover from failures.

Replication provides **redundancy**, because the data exists in multiple places.

<h2>Primary and Replicas</h2>

You'll often hear these terms:

```text
Primary / Leader
Replica / Follower
```

Basic model:

```text
              PRIMARY
                 │
          Replication
          /          \
         ▼            ▼
     REPLICA 1    REPLICA 2
```

Typically:

```text
Write → Primary
Read  → Replica
```

But this is not an absolute rule.

Some databases and architectures support more complex write patterns.

<h2>Example</h2>

Suppose we have:

```text
Users

id | name
---------
1  | Eren
2  | Rahul
```

The application adds:

```text
3 | John
```

The Primary receives the write:

```text
Primary
   ↓
John added
```

The change is then replicated:

```text
Primary
   │
   ├──────────→ Replica 1
   │
   └──────────→ Replica 2
```

Eventually all copies contain:

```text
1 | Eren
2 | Rahul
3 | John
```

<h2>Asynchronous Replication</h2>

Asynchronous replication is very common.

The Primary accepts the write **without waiting for every replica to confirm it**.

```text
Application
     │
     ▼
 Primary
     │
     ├────→ Replica 1
     └────→ Replica 2
```

The application can receive success after the Primary commits the write.

The replicas catch up afterward.

<h3>Advantage</h3>

⚡ Lower write latency

The Primary doesn't need to wait for replicas before responding.

<h3>Problem</h3>

There can be **replication lag**.

The replicas may temporarily contain older data.

<h2>Replication Lag</h2>

Suppose the Primary has:

```text
Balance = ₹5,000
```

The user deposits ₹1,000.

Primary becomes:

```text
₹6,000
```

But Replica 1 hasn't received the update yet:

```text
Replica 1
₹5,000
```

So temporarily:

```text
Write
  ↓
Primary → ₹6,000
  ↓
Replica → ₹5,000
```

For a short period, the databases disagree.

This is called:

> **Replication Lag**

<h2>Read-After-Write Problem</h2>

Replication lag creates an important System Design problem.

Suppose a user:

```text
1. Updates profile
2. Immediately views profile
```

The update goes to:

```text
Primary
```

Then the read goes to:

```text
Replica
```

But the replica hasn't caught up yet.

The user might see:

```text
Old Profile 
```

even though the write succeeded.

This is called a **Read-After-Write Consistency Problem**.

<h2>Handling Read-After-Write</h2>

For critical reads immediately after a write, we can route the read to the Primary.

```text
Write
  ↓
Primary

Immediate Read
  ↓
Primary
```

Later, normal reads can go to replicas.

Another approach is to use a consistency mechanism that ensures the selected replica has caught up sufficiently.

The exact solution depends on the database and application requirements.

<h2>Synchronous Replication</h2>

Synchronous replication takes the opposite approach.

The Primary waits for one or more required replicas to acknowledge the write before confirming success.

```text
Application
     ↓
 Primary
    /   \
   ↓     ↓
 R1      R2
   \     /
    ACK
     ↓
Primary
     ↓
Success
```

<h3>Advantage</h3>

Provides **stronger durability and consistency guarantees**.

<h3>Disadvantages</h3>

More coordination can result in:

- Higher write latency
- Reduced availability if required replicas are unreachable
- More coordination between database servers

<h2>Asynchronous vs Synchronous Replication</h2>

| Asynchronous | Synchronous |
|---|---|
| Primary doesn't wait for replicas | Primary waits for required replica acknowledgements |
| Lower write latency | Higher write latency |
| Replication lag is possible | Stronger synchronization |
| Better performance | Stronger guarantees |
| Replica may temporarily be behind | Less stale data, depending on configuration |

<h2>What Happens if the Primary Dies?</h2>

Imagine:

```text
Primary ❌
   │
   ├── Replica 1 ✅
   └── Replica 2 ✅
```

We need a new Primary.

This process is called:

> **Failover**

One of the replicas can potentially be promoted:

Before:

```text
Primary ❌
Replica 1
Replica 2
```

After:

```text
New Primary
    │
    └── Replica 2
```

Applications then need to connect to the new Primary.

<h2>Failover Isn't Magic</h2>

Suppose:

```text
Primary
   │
   └── Latest transaction
```

The latest transaction hasn't reached Replica 1 yet.

Then the Primary crashes.

If Replica 1 becomes the new Primary:

```text
Primary ❌
   ↓
Replica 1 → New Primary
```

That latest transaction may be missing.

This is one reason replication configuration and consistency guarantees matter.

Failover can involve trade-offs between:

- Availability
- Consistency
- Data loss
- Recovery time

<h2>Replication + Load Balancing</h2>

We can combine replication with a read-routing layer:

```text
                 Application
                 /          \
             Writes          Reads
                ↓              ↓
             Primary      Read Router / LB
                            /       \
                           ↓         ↓
                       Replica 1  Replica 2
```

The read-routing layer distributes read requests across replicas.

<h2>Replication vs Sharding</h2>

This distinction is extremely important.

<h3>Replication</h3>

Replication **copies the same data** to multiple servers.

```text
        Database
        /   |   \
       ↓    ↓    ↓
     Copy  Copy  Copy
```

Each replica contains the same dataset, assuming it has caught up.

<h3>Sharding</h3>

Sharding **splits the data** across multiple servers.

```text
Shard 1 → Users 1–1M
Shard 2 → Users 1M–2M
Shard 3 → Users 2M–3M
```

Each shard contains only a portion of the total data.

<h3>Simple Difference</h3>

```text
Replication
    ↓
Copy the same data

Sharding
    ↓
Split the data
```

<h2>Replication Doesn't Automatically Increase Write Capacity</h2>

This is a common misconception.

Suppose:

```text
        Primary
        /     \
       ↓       ↓
   Replica 1  Replica 2
```

If all writes still go to the Primary:

```text
10,000 writes/sec
        ↓
     Primary
```

The Primary remains the **write bottleneck**.

Replication mainly helps with:

- Read scaling
- High availability
- Redundancy
- Disaster recovery

To scale writes across machines, we may need techniques such as:

- Sharding
- Partitioning
- Other distributed database architectures


<h2>Interview Questions</h2>

<h3>What is database replication?</h3>

Maintaining copies of database data on multiple servers.

<h3>Why use replication?</h3>

For:

- Read scaling
- High availability
- Redundancy
- Disaster recovery

<h3>What is replication lag?</h3>

The delay between a change being committed on the Primary and appearing on a replica.

<h3>What is synchronous replication?</h3>

Synchronous replication waits for required replica acknowledgements before completing the write.

<h3>What is asynchronous replication?</h3>

Asynchronous replication allows the Primary to complete the write without waiting for replicas to acknowledge it.

<h3>What happens when the Primary fails?</h3>

A replica can potentially be promoted to become the new Primary through a **failover** process.

<h3>Does replication solve write scaling?</h3>

Not by itself.

If writes still go to one Primary, that Primary remains the write bottleneck.

<h3>Replication vs Sharding?</h3>

**Replication** copies the same data across multiple servers.

**Sharding** splits the data across multiple servers.

<h2>Quick Revision</h2>

```text
                 PRIMARY
                    │
           ┌────────┴────────┐
           ▼                 ▼
       REPLICA 1         REPLICA 2
           │                 │
         READ              READ
```

<h3>Replication</h3>

```text
Same Data
    ↓
Multiple Servers
```

<h3>Async Replication</h3>

```text
Primary
   ↓
Success
   ↓
Replicas Catch Up
```

<h3>Sync Replication</h3>

```text
Primary
   ↓
Required Replicas ACK
   ↓
Success
```

<h3>Main Problem</h3>

```text
Replication Lag
       ↓
Stale Reads
```

<h3>Main Benefits</h3>

```text
Read Scaling
     +
High Availability
     +
Redundancy
     +
Disaster Recovery
```

<h3>Most Important Difference</h3>

```text
Replication
    ↓
COPY data

Sharding
    ↓
SPLIT data
```
