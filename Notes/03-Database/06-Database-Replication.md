Database Replication

We just learned that read replicas help scale reads. Now let's understand how the data actually gets copied from one database to another.

1. What is Database Replication?

Replication means keeping copies of the same database data on multiple database servers.

Instead of:

Application
    ↓
   DB

we have:

              Application
                   ↓
              Primary DB
              /       \
             ↓         ↓
        Replica 1   Replica 2

The replicas contain copies of the primary's data.

2. Why Do We Need Replication?

There are three major reasons:

① Scale Reads
Primary
   ↓
Writes

Replica 1 ──→ Reads
Replica 2 ──→ Reads

Instead of one database handling everything, reads can be distributed.

② High Availability

If the primary fails:

Primary ❌
   ↓
Replica 1

A replica can potentially be promoted to become the new primary.

③ Disaster Recovery

Having copies of data on separate machines can help recover from failures.

3. Primary and Replicas

You'll often hear:

Primary / Leader
Replica / Follower

Basic model:

              PRIMARY
                 │
          Replication
          /          \
         ▼            ▼
     REPLICA 1    REPLICA 2

Typically:

Write → Primary
Read  → Replica

But this isn't an absolute rule. Some databases and architectures support more complex write patterns.

4. Example

Suppose:

Users

contains:

id | name
1  | Eren
2  | Rahul

Application adds:

3 | John

The primary receives the write:

Primary
   ↓
John added

The change is then replicated:

Primary
   │
   ├──────────→ Replica 1
   │
   └──────────→ Replica 2

Eventually all copies contain:

1 | Eren
2 | Rahul
3 | John
5. Asynchronous Replication ⭐⭐⭐⭐⭐

This is very common.

The primary accepts the write without waiting for every replica to confirm it.

Application
     │
     ▼
 Primary
     │
     ├────→ Replica 1
     └────→ Replica 2

The application can receive success after the primary commits.

The replicas catch up afterward.

Advantage

⚡ Lower write latency

Problem

There can be replication lag.

6. Replication Lag

Suppose the primary has:

Balance = ₹5,000

User deposits ₹1,000.

Primary:

₹6,000

But Replica 1 hasn't received the update yet:

Replica 1
₹5,000

So:

Write
 ↓
Primary → ₹6,000
 ↓
Replica → ₹5,000

For a short period, they disagree.

That's replication lag.

7. The Read-After-Write Problem

This creates an important System Design problem.

User does:

1. Update profile
2. Immediately view profile

The update goes to:

Primary

Then the read goes to:

Replica

But the replica hasn't caught up.

User might see:

Old profile ❌

even though the write succeeded.

8. One Solution

For critical reads immediately after a write, route the read to the primary:

Write
  ↓
Primary

Immediate Read
  ↓
Primary

Later, normal reads can go to replicas.

Another approach is to use a consistency mechanism that ensures the chosen replica has caught up sufficiently.

The exact solution depends on the database and application.

9. Synchronous Replication

Now let's look at the opposite approach.

The primary waits for one or more replicas to acknowledge the write before confirming success.

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
Advantage

Stronger durability/consistency guarantees.

Disadvantage

More coordination can mean:

Higher write latency
Reduced availability if required replicas are unreachable
10. Async vs Sync
Asynchronous	Synchronous
Primary doesn't wait for replicas	Primary waits for required replica acknowledgements
Lower write latency	Higher write latency
Replication lag possible	Stronger synchronization
Good performance	Stronger guarantees
Replica may temporarily be behind	Less stale data, depending on configuration
11. What Happens if Primary Dies?

Imagine:

Primary ❌
   │
   ├── Replica 1 ✅
   └── Replica 2 ✅

We need a new primary.

This is called:

Failover

One replica can be promoted:

Before:

Primary ❌
Replica 1
Replica 2


After:

New Primary
Replica 2

Applications then need to connect to the new primary.

12. But Failover Isn't Magic

Suppose:

Primary
   │
   └── Latest transaction

The transaction hasn't reached Replica 1 yet.

Then primary crashes.

Replica 1 becomes primary.

That latest transaction may be missing.

This is one reason replication configuration and consistency guarantees matter.

13. Replication + Load Balancing

Now combine what we've learned:

                 Application
                 /          \
             Writes          Reads
                ↓              ↓
             Primary      Read Router/LB
                            /       \
                           ↓         ↓
                       Replica 1  Replica 2

The read-routing layer distributes reads across replicas.

14. Replication vs Sharding

Very important distinction.

Replication

Copy the same data to multiple servers.

DB
├── Copy
├── Copy
└── Copy
Sharding

Split the data across multiple servers.

Shard 1 → Users 1–1M
Shard 2 → Users 1M–2M
Shard 3 → Users 2M–3M

We'll study sharding later.

15. Replication Doesn't Automatically Increase Write Capacity

This is a common misconception.

Suppose:

Primary
   ↓
Replica 1
Replica 2

If all writes still go to the primary:

10,000 writes/sec
       ↓
Primary

The primary remains the write bottleneck.

Replication mainly helps with:

Read scaling
Availability
Redundancy

To scale writes across machines, we may need techniques such as sharding/partitioning or other architectures.

🧠 Memory Trick

Think of a teacher writing notes.

Teacher
   ↓
Master Copy
   ↓
Photocopies

Replication = making copies.

Sharding = splitting the book into different sections and giving each section to different machines.

🎯 Interview Questions
Q1. What is database replication?

Maintaining copies of database data on multiple servers.

Q2. Why use replication?

For read scaling, high availability, redundancy, and disaster recovery.

Q3. What is replication lag?

The delay between a change being committed on the primary and appearing on a replica.

Q4. Synchronous vs asynchronous replication?

Synchronous replication waits for required replica acknowledgements before completing the write; asynchronous replication allows the primary to proceed without waiting.

Q5. What happens when the primary fails?

A replica can potentially be promoted to a new primary through a failover process.

Q6. Does replication solve write scaling?

Not by itself. If writes still go to one primary, that primary remains the write bottleneck.

📝 Quick Revision
                PRIMARY
                   │
          ┌────────┴────────┐
          ▼                 ▼
      REPLICA 1         REPLICA 2
          │                 │
        READ              READ
Replication:
Same data
   ↓
Multiple servers
Async:
Primary → Success
    ↓
Replicas catch up
Sync:
Primary
   ↓
Required replicas ACK
   ↓
Success
Main problem:
Replication Lag
Main benefit:
Read Scaling + High Availability
