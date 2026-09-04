# Database Consistency

Suppose we have:

```
```

```
        Primary DB
           │
     ┌─────┴─────┐
     ▼           ▼
 Replica 1     Replica 2
```

User changes:

```
```

```
Balance = ₹1000
       ↓
Balance = ₹500
```

The primary gets the update immediately.

But Replica 1 might still have:

```
```

```
Balance = ₹1000
```

for a short time.

So if the user reads from Replica 1, they may see **old data**.

That's the consistency problem.

---

# 1. Strong Consistency

With **strong consistency**, after a successful write, subsequent reads return the latest value.

Example:

```
```

```
Initial:
Balance = ₹1000

       ↓ WRITE

Balance = ₹500
```

Immediately:

```
```

```
READ → ₹500 ✅
```

You don't want the system to return ₹1000 after the write has successfully completed.

### Simple idea:

> **Read = latest committed data**

---

# 2. Eventual Consistency

With **eventual consistency**, replicas may temporarily have different values, but they will eventually converge.

Example:

```
```

```
             Primary
                │
         Balance = ₹500
                │
       ┌────────┴────────┐
       ▼                 ▼
 Replica 1           Replica 2
 ₹500                ₹1000
```

A little later:

```
```

```
Replica 1 → ₹500
Replica 2 → ₹500
```

Eventually everyone gets the updated value.

So:

> **Temporary inconsistency is acceptable.**

---

# 3. Real-world Example

Imagine a social media app.

You change your profile name:

```
```

```
Old name → Durgesh
New name → Eren
```

If another user sees:

```
```

```
Durgesh
```

for a few seconds before seeing:

```
```

```
Eren
```

that may be acceptable.

We can use **eventual consistency**.

---

But imagine:

### Bank account

You transfer:

```
```

```
₹10,000
```

You don't want:

```
```

```
Account A → ₹10,000 deducted
Account B → still doesn't show ₹10,000
```

depending on where the user reads.

Financial operations generally need stronger consistency guarantees around critical data.

---

# 4. Read-After-Write Consistency

This is particularly important.

Suppose:

```
```

```
User → Update profile
```

Write goes to Primary:

```
```

```
Primary:
name = Eren
```

Immediately user requests:

```
```

```
GET /profile
```

But the read goes to a lagging replica:

```
```

```
Replica:
name = Durgesh
```

The user sees:

```
```

```
Durgesh ❌
```

even though they just changed it.

This is called a **read-after-write consistency problem**.

---

# How can we handle it?

One simple approach:

```
```

```
WRITE
  ↓
Primary

Immediately after write
  ↓
READ → Primary
```

Instead of sending the immediate read to a replica.

Another approach is to use a system/database feature that provides the required consistency guarantee.

The exact solution depends on the database and architecture.

---

# 5. Consistency vs Performance

Here's the important HLD trade-off.

### Strong consistency

```
```

```
Need latest data
      ↓
More coordination
      ↓
Potentially higher latency
```

### Eventual consistency

```
```

```
Allow temporary stale data
      ↓
Less coordination
      ↓
Can improve scalability/availability/latency
```

It's not simply:

> Strong = good
>
> Eventual = bad

❌

It's:

> **Choose the consistency level according to the business requirement.**

---

# 6. Examples

### Banking 💰

```
```

```
Strong consistency
```

because correctness is critical.

### Social media likes ❤️

```
```

```
Eventual consistency
```

Usually acceptable if the displayed count is briefly stale.

### Product catalog 🛒

Often eventual consistency can be acceptable for things like descriptions or recommendations, while **inventory/stock** may require stronger guarantees.

### Analytics 📊

Usually eventual consistency is acceptable.

---

# 🧠 Important distinction

Don't confuse:

### Consistency

with

### Database Consistency from ACID

Earlier we learned:

```
```

```
ACID
 ↓
Consistency
```

That means transactions preserve defined database rules/invariants.

Here, when we discuss **distributed-system consistency**, we're talking about:

> **What value does a read observe across replicas/nodes, and how quickly do copies converge?**

Same word, but **different context**.

---

# 🎯 Interview Answer

If interviewer asks:

**"What is eventual consistency?"**

Say:

> "Eventual consistency means that after a write, different replicas may temporarily return different values, but if no new updates occur, the replicas will eventually converge to the same value. It can improve scalability and availability when temporary stale reads are acceptable."

Perfect HLD-level answer. 🔥

---

## Quick Revision

```
```

```
Database Consistency
        │
        ├── Strong Consistency
        │      ↓
        │   Reads see latest value
        │
        ├── Eventual Consistency
        │      ↓
        │   Temporary stale data allowed
        │      ↓
        │   Eventually replicas converge
        │
        └── Read-after-write
               ↓
          "I just updated it,
           why am I seeing old data?"
```
