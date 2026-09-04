## Database Consistency 

Suppose we have a Primary database and multiple replicas:

```text
        Primary DB
           │
     ┌─────┴─────┐
     ▼           ▼
 Replica 1     Replica 2
```

A user changes:

```text
Balance = ₹1000
       ↓
Balance = ₹500
```

The Primary gets the update immediately.

But Replica 1 might still contain:

```text
Balance = ₹1000
```

for a short period.

If the user reads from Replica 1, they may see **old data**.

This is a distributed-system **consistency problem**.

<h2>Strong Consistency</h2>

With **strong consistency**, after a successful write, subsequent reads return the latest committed value according to the system's consistency guarantees.

Example:

```text
Initial:
Balance = ₹1000

       ↓
     WRITE

Balance = ₹500
```

Immediately:

```text
READ → ₹500 
```

The system should not return the old value after the write has successfully completed, assuming the read is made under the same consistency guarantee.

<h3>Simple Idea</h3>

> **Read = latest committed value**

Strong consistency is useful when reading stale data could cause an important business problem.

<h2>Eventual Consistency</h2>

With **eventual consistency**, replicas may temporarily contain different values, but if no new updates occur, they will eventually converge.

Example:

```text
             Primary
                │
         Balance = ₹500
                │
       ┌────────┴────────┐
       ▼                 ▼
 Replica 1           Replica 2
  ₹500                 ₹1000
```

A little later:

```text
Replica 1 → ₹500
Replica 2 → ₹500
```

Eventually, the replicas converge to the same value.

<h3>Simple Idea</h3>

> **Temporary inconsistency is acceptable, as long as replicas eventually converge.**

<h2>Real-World Examples</h2>

<h3>Social Media Profile</h3>

Suppose you change your profile name:

```text
Old Name → Durgesh
New Name → Eren
```

Another user might temporarily see:

```text
Durgesh
```

before eventually seeing:

```text
Eren
```

For many social applications, this temporary delay may be acceptable.

```text
Eventual Consistency
        ↓
Temporary stale data
        ↓
Eventually updated
```

<h3>Bank Account</h3>

Suppose you transfer:

```text
₹10,000
```

We don't want different parts of the system to incorrectly show conflicting balances during a critical financial operation.

For critical financial data, stronger consistency guarantees are often required.

<h3>Social Media Likes</h3>

Suppose a post has:

```text
10,000 likes
```

One user may temporarily see:

```text
9,998 likes
```

while another sees:

```text
10,000 likes
```

If the count becomes correct shortly afterward, this may be acceptable.

Eventual consistency can be a reasonable choice for this type of data.

<h3>Product Catalog</h3>

For product descriptions or recommendations, slightly stale data may often be acceptable.

However, **inventory or stock availability** can require stronger consistency depending on the business requirements.

<h3>Analytics</h3>

Analytics systems often tolerate some delay.

For example:

```text
Event Generated
      ↓
Processed
      ↓
Analytics Updated
```

The dashboard doesn't necessarily need to reflect every event immediately.

Eventual consistency can therefore be suitable for many analytics workloads.

<h2>Read-After-Write Consistency</h2>

This is particularly important in distributed systems.

Suppose:

```text
User
  ↓
Update Profile
```

The write goes to the Primary:

```text
Primary
name = Eren
```

Immediately afterward, the user requests:

```text
GET /profile
```

But the read goes to a lagging replica:

```text
Replica
name = Durgesh
```

The user sees:

```text
Durgesh - wrong
```

even though they just changed the profile.

This is a **Read-After-Write Consistency Problem**.

<h3>Simple Example</h3>

```text
WRITE
  ↓
Primary
name = Eren
  ↓
READ
  ↓
Lagging Replica
name = Durgesh
```

The user expects:

```text
Eren
```

but receives:

```text
Durgesh
```

<h2>How Can We Handle Read-After-Write?</h2>

One simple approach is to route the immediate read to the Primary.

```text
WRITE
  ↓
Primary

Immediately After Write
  ↓
READ → Primary
```

After some time, normal reads can be served by replicas.

Another approach is to use a database or distributed-system consistency mechanism that guarantees the selected replica has caught up sufficiently.

The exact solution depends on the database and architecture.

<h2>Consistency vs Performance</h2>

This is an important HLD trade-off.

<h3>Strong Consistency</h3>

```text
Need latest data
       ↓
More coordination
       ↓
Potentially higher latency
```

Depending on the architecture, stronger consistency can reduce availability or increase coordination costs.

<h3>Eventual Consistency</h3>

```text
Allow temporary stale data
       ↓
Less coordination
       ↓
Can improve scalability
and availability
```

The important point is:

> **Strong consistency is not always better, and eventual consistency is not always worse.**

The correct choice depends on the business requirements.

<h2>Consistency as a System Design Trade-Off</h2>

When designing a distributed system, ask:

```text
Can users tolerate stale data?
          │
     ┌────┴────┐
     │         │
    YES        NO
     │         │
     ▼         ▼
Eventual     Stronger
Consistency  Consistency
```

For example:

```text
Likes Count
    ↓
Temporary Stale Data
    ↓
Usually Acceptable
```

But:

```text
Bank Balance
    ↓
Incorrect/Stale Value
    ↓
Potentially Dangerous
```

Therefore, consistency should be chosen based on the **business impact of stale or conflicting data**.

<h2>Important Distinction: Distributed Consistency vs ACID Consistency</h2>

Don't confuse these two meanings of **consistency**.

<h3>ACID Consistency</h3>

In ACID transactions:

```text
ACID
  ↓
Consistency
```

Consistency means that a transaction preserves the database's defined rules and constraints.

For example:

```text
Account Balance
       ↓
Must not violate defined business rules
```

<h3>Distributed-System Consistency</h3>

In distributed systems, consistency usually refers to:

> **What value does a read observe across replicas or nodes, and how quickly do different copies reflect updates?**

For example:

```text
Primary
₹500

Replica
₹1000
```

The question is:

> Which value should a read return?

These are **different contexts using the same word**.

<h2>Strong vs Eventual Consistency</h2>

| Strong Consistency | Eventual Consistency |
|---|---|
| Reads observe the latest value according to the consistency guarantee | Reads may temporarily observe stale data |
| Stronger consistency guarantees | Weaker consistency guarantees |
| More coordination may be required | Less coordination may be required |
| Can increase latency | Can reduce latency |
| Useful for critical data | Useful when temporary staleness is acceptable |
| Can have availability trade-offs depending on architecture | Often useful for highly distributed systems |

<h2>When to Prefer Stronger Consistency</h2>

Consider stronger consistency when stale or conflicting data could cause serious problems.

Examples:

```text
Financial Transactions
Inventory / Stock
Critical Account State
Unique Resource Allocation
```

The exact consistency requirement should still be determined by the application's business rules.

<h2>When Eventual Consistency Can Be Enough</h2>

Eventual consistency can be useful when temporary stale data is acceptable.

Examples:

```text
Social Media Likes
Recommendations
Analytics
Counters
Search Indexes
Some Profile Data
```

The key question is:

> **What happens if the user temporarily sees old data?**

If the answer is "nothing serious," eventual consistency may be a reasonable choice.

<h2>Interview Answer</h2>

If the interviewer asks:

> **What is eventual consistency?**

A strong HLD answer is:

> "Eventual consistency means that after a write, different replicas may temporarily return different values, but if no new updates occur, the replicas will eventually converge to the same value. It can improve scalability and availability when temporary stale reads are acceptable."

<h3>Strong Consistency Interview Answer</h3>

> "Strong consistency means that after a successful write, subsequent reads under the same consistency guarantee observe the latest committed value. It provides stronger guarantees but may require additional coordination and can introduce latency or availability trade-offs depending on the architecture."

<h3>Read-After-Write Interview Answer</h3>

> "A read-after-write problem occurs when a user successfully writes to the Primary but immediately reads from a lagging replica and sees stale data. One solution is to route the immediate read to the Primary or use a consistency mechanism that ensures the read is served from a sufficiently up-to-date replica."

<h2>Memory Trick</h2>

<h3>Strong Consistency</h3>

```text
WRITE
  ↓
Latest Value
  ↓
READ
  ↓
Latest Value
```

> **"I wrote it, I expect to read it."**

<h3>Eventual Consistency</h3>

```text
WRITE
  ↓
Primary Updated
  ↓
Replicas Catch Up
  ↓
Eventually Same Data
```

> **"It may be stale now, but it will eventually become correct."**

<h3>Read-After-Write</h3>

```text
WRITE
  ↓
Primary
  ↓
Immediate READ
  ↓
Lagging Replica
  ↓
Old Data ❌
```

<h2>Quick Revision</h2>

```text
                 Database Consistency
                         │
              ┌──────────┴──────────┐
              ▼                     ▼
       Strong Consistency    Eventual Consistency
              │                     │
              ▼                     ▼
       Latest value           Temporary stale data
       after write                    │
              │                       ▼
              │                Replicas converge
              │                       │
              └──────────┬────────────┘
                         ▼
                 Depends on
               Business Requirements
```

<h3>Strong Consistency</h3>

```text
Read
 ↓
Latest Committed Value
```

<h3>Eventual Consistency</h3>

```text
Write
 ↓
Replicas May Lag
 ↓
Eventually Converge
```

<h3>Read-After-Write</h3>

```text
Write → Primary
           ↓
     Immediate Read
           ↓
    Lagging Replica
           ↓
       Stale Data
```

<h3>Core Principle</h3>

```text
Consistency Requirement
          ↓
Business Requirement
          ↓
Choose Appropriate
Consistency Model
```

<h3>Final Memory Trick</h3>

```text
Strong
→ Latest data

Eventual
→ Eventually same data

Read-After-Write
→ "I just wrote it,
   why am I seeing old data?"
```
