## CAP Theorem ⭐⭐⭐⭐⭐

CAP stands for:

```text
C → Consistency
A → Availability
P → Partition Tolerance
```

The core idea is:

> **When a network partition occurs in a distributed system, you must trade off between Consistency and Availability.**

The most important part of CAP is **what happens when a network partition occurs**.

<h2>What is a Distributed System?</h2>

A distributed system has multiple machines or nodes working together.

For example:

```text
             Application
                  │
          ┌───────┴───────┐
          ▼               ▼
       Node 1           Node 2
```

Normally, the nodes can communicate:

```text
Node 1 ←──── Network ────→ Node 2
```

But what if the network connection breaks?

```text
Node 1       ❌       Node 2

        Network Failure
```

Both nodes may still be running.

The problem is:

> **They can no longer communicate with each other.**

This is called a **Network Partition**.

<h2>C — Consistency</h2>

In CAP, consistency means that every read receives the **most recent valid value according to the system's consistency guarantee**.

For example:

```text
Initial Balance
₹1000
```

We update it:

```text
₹1000
   ↓
₹500
```

After a successful write, under a strong consistency guarantee:

```text
Read Node 1 → ₹500
Read Node 2 → ₹500
```

We don't want one node returning:

```text
₹500
```

while another returns:

```text
₹1000
```

when the system promises strong consistency.

<h3>Simple Idea</h3>

> **Consistency = Don't return conflicting or stale data when the system promises the latest value.**

<h2>A — Availability</h2>

Availability means that the system continues responding to requests even when some nodes are unavailable.

Suppose:

```text
Node 1 ❌
Node 2 ✅
Node 3 ✅
```

The system should still be able to respond using healthy nodes when its availability guarantee permits it.

```text
Client
   │
   ▼
Node 2
   │
   ▼
Response ✅
```

<h3>Simple Idea</h3>

> **Availability = The system keeps responding to requests.**

<h2>P — Partition Tolerance</h2>

Partition tolerance means the system can continue operating despite a network communication failure between nodes.

For example:

```text
Node 1       ❌       Node 2

        Network Partition
```

The nodes are still running, but they cannot communicate.

A partition-tolerant distributed system is designed to handle this situation.

<h3>Simple Idea</h3>

> **Partition Tolerance = Continue operating despite network communication failures.**

<h2>The Core CAP Problem 🔥</h2>

Suppose we have:

```text
             Network Partition
                    ❌
        ┌───────────┴───────────┐
        ▼                       ▼
      Node A                  Node B
```

A client sends:

```text
WRITE balance = ₹500
```

to Node A.

Node A accepts the write:

```text
Node A
Balance = ₹500
```

But Node B cannot communicate with Node A.

Now another client asks Node B:

```text
READ balance
```

Node B has an old value:

```text
Balance = ₹1000
```

What should Node B do?

<h2>Option 1 — Prefer Consistency</h2>

Node B says:

> "I cannot safely determine whether my data is up to date."

So it refuses or delays the request.

```text
Node B
   │
   └── ❌ Cannot safely return data
```

Result:

```text
Consistency ✅
Availability ❌
```

This is a **CP-style choice**.

The system prefers returning correct/consistent data even if some requests cannot be served during the partition.

<h2>Option 2 — Prefer Availability</h2>

Node B says:

> "I'll return the data I currently have."

It responds:

```text
Balance = ₹1000
```

even though the latest value may be:

```text
Balance = ₹500
```

Result:

```text
Availability ✅
Consistency ❌
```

This is an **AP-style choice**.

The system continues responding even though some responses may temporarily contain stale data.

<h2>Why Can't We Have C + A + P?</h2>

This is the core idea of CAP.

During a network partition:

```text
       Network Partition
              ↓
       Nodes can't communicate
              ↓
      ┌───────┴───────┐
      ▼               ▼
   Node A           Node B
```

Suppose Node B receives a read.

If Node B responds immediately:

```text
Availability ✅
```

But it might return stale or conflicting data:

```text
Consistency ❌
```

If Node B refuses to respond until it can safely determine the correct value:

```text
Consistency ✅
```

But:

```text
Availability ❌
```

Therefore:

> **During a network partition, a distributed system must trade off between Consistency and Availability.**

<h2>CP — Consistency + Partition Tolerance</h2>

A CP system prefers consistency when a partition occurs.

```text
Network Partition
       ↓
Prefer correct/consistent data
       ↓
Some requests may fail or wait
```

The system may sacrifice availability for consistency.

<h3>When Is CP Useful?</h3>

Use stronger consistency when incorrect or conflicting data can cause serious problems.

Examples may include:

- Certain financial systems
- Distributed coordination
- Systems managing critical shared state
- Unique resource allocation

The exact choice depends on the application's requirements.

<h2>AP — Availability + Partition Tolerance</h2>

An AP system prefers availability when a partition occurs.

```text
Network Partition
       ↓
Keep responding
       ↓
Temporary inconsistency may occur
       ↓
Reconcile / converge later
```

The system sacrifices immediate consistency to remain available.

<h3>When Is AP Useful?</h3>

AP-style behavior can be useful when temporary stale or conflicting data is acceptable.

Examples may include:

- Some social feeds
- Some counters
- Some distributed content systems
- Systems where availability is more important than immediate consistency

<h2>What About CA?</h2>

You may see:

```text
CA
CP
AP
```

But there is an important catch.

A truly distributed system cannot simply assume that network partitions will never happen.

If a network partition occurs, you must deal with it.

So:

```text
CA
 ↓
Assumes no partition
```

This can be meaningful for systems where partition tolerance is not being considered, such as a single-node system.

But for real distributed systems:

```text
Network Failures
       ↓
Can Happen
       ↓
Partition Tolerance Matters
```

Therefore, the practical CAP discussion is usually:

```text
CP vs AP
```

<h2>Real-World Analogy: ATM</h2>

Imagine two ATM machines:

```text
ATM A                 ATM B
₹10,000               ₹10,000
```

The network between them breaks:

```text
ATM A       ❌       ATM B
```

Someone withdraws ₹8,000 from ATM A.

ATM A now knows:

```text
₹2,000 remaining
```

But ATM B still thinks:

```text
₹10,000 remaining
```

Now another person tries to withdraw ₹8,000 from ATM B.

If ATM B allows it:

```text
First withdrawal  → ₹8,000
Second withdrawal → ₹8,000

Total → ₹16,000
```

This could violate the actual account balance.

A CP-oriented approach might reject or delay the second operation until the system can safely coordinate.

An AP-oriented system might continue accepting operations and reconcile later **if the business model allows that behavior**.

<h2>CAP Is Not "Choose Any 2 of 3"</h2>

This is a very common interview mistake.

Don't simply say:

> "CAP means you can only choose 2 out of 3."

That is an oversimplification.

The better explanation is:

> **CAP says that when a network partition occurs, a distributed system must trade off between consistency and availability. Partition tolerance is generally unavoidable in a distributed system because network failures can happen.**

<h2>CAP vs Normal Consistency</h2>

CAP consistency should not be confused with the **Consistency** in ACID.

<h3>ACID Consistency</h3>

ACID consistency means a transaction preserves the database's defined rules and constraints.

```text
Transaction
    ↓
Database Rules
    ↓
Valid Database State
```

<h3>CAP Consistency</h3>

CAP consistency is about what happens when data is distributed across multiple nodes.

```text
Multiple Nodes
      ↓
Network Partition
      ↓
What value should a read return?
```

So:

```text
ACID Consistency
→ Preserve database invariants

CAP Consistency
→ Consistent view of distributed data
```

They are related concepts, but they are **not the same thing**.

<h2>CAP in System Design</h2>

When designing a distributed system, ask:

```text
Can the system tolerate
temporary stale data?
          │
     ┌────┴────┐
     │         │
    YES        NO
     │         │
     ▼         ▼
    AP        CP
```

This is a simplified decision guide.

In a real system, the exact consistency and availability guarantees can be more nuanced than simply labeling the entire database "CP" or "AP."

<h2>CP vs AP</h2>

| CP | AP |
|---|---|
| Consistency + Partition Tolerance | Availability + Partition Tolerance |
| Prefers consistent data during partition | Prefers continued responses during partition |
| Some requests may fail or wait | Some responses may be stale |
| Useful when correctness is critical | Useful when availability is critical |
| Sacrifices some availability during partition | Sacrifices immediate consistency during partition |

<h2>Simple CAP Example</h2>

Suppose:

```text
             Network Partition
                    ❌
              ┌─────┴─────┐
              ▼           ▼
            Node A       Node B
```

Latest value:

```text
Node A → ₹500
Node B → ₹1000
```

<h3>CP Approach</h3>

```text
Read Node B
     ↓
Cannot confirm latest value
     ↓
Reject / Delay
     ↓
Consistency ✅
Availability ❌
```

<h3>AP Approach</h3>

```text
Read Node B
     ↓
Return current value
     ↓
₹1000
     ↓
Availability ✅
Consistency ❌
```

<h2>Interview Answer</h2>

If the interviewer asks:

> **What is CAP Theorem?**

A strong HLD answer is:

> "CAP Theorem states that when a network partition occurs in a distributed system, the system has to trade off between consistency and availability. Partition tolerance is generally required because network failures can happen. A CP system prefers consistency during a partition, potentially rejecting or delaying some requests, while an AP system prefers availability and may temporarily return stale or conflicting data."

<h3>What Does C Mean?</h3>

> **Consistency means reads observe the latest valid value according to the system's consistency guarantee.**

<h3>What Does A Mean?</h3>

> **Availability means the system continues responding to requests according to its availability guarantee.**

<h3>What Does P Mean?</h3>

> **Partition tolerance means the system can handle network communication failures between distributed nodes.**

<h3>Why Can't We Have C + A During a Partition?</h3>

> "During a network partition, nodes cannot communicate. If we always respond, we may return stale or
