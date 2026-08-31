## Transaction Isolation Levels

We just learned **Isolation** in ACID.

Now the question is:

> **How much isolation should the database provide when multiple transactions run at the same time?**

This is where **Isolation Levels** come in.

<h2>Why Do We Need Isolation Levels?</h2>

Imagine two users are accessing the same account simultaneously.

```text
Transaction A
      │
      ▼
   Database
      ▲
      │
Transaction B
```

If both transactions can freely see each other's intermediate changes, we can get incorrect results.

But if we make transactions completely isolated, the system may become slower because there's more coordination.

So databases provide **different levels of isolation**.

> **Higher isolation → stronger consistency between concurrent transactions, but potentially more blocking/coordination and lower concurrency.**

<h2>The 4 Standard Isolation Levels</h2>

From weakest to strongest:

```text
READ UNCOMMITTED
       ↓
READ COMMITTED
       ↓
REPEATABLE READ
       ↓
SERIALIZABLE
```

Think:

```text
More Isolation
      ↑
      │
Serializable       High
Repeatable Read    Medium-High
Read Committed     Medium
Read Uncommitted   Low
```

<h2>Read Uncommitted</h2>

This is the **lowest isolation level**.

A transaction may be able to read changes made by another transaction **before those changes are committed**.

This can cause a:

> **Dirty Read**

<h3>Example</h3>

Transaction A:

```text
Balance = ₹10,000
      ↓
Changes balance to ₹5,000
```

But A hasn't committed yet.

Transaction B reads:

```text
₹5,000
```

Then A fails and rolls back.

```text
Actual balance = ₹10,000
```

But B temporarily saw ₹5,000.

That's a **dirty read**.

<h2>Read Committed</h2>

Now we don't allow reading **uncommitted** changes.

Transaction A:

```text
Balance = ₹10,000
      ↓
Change → ₹5,000
      ↓
Not committed yet
```

Transaction B:

```text
Read balance
```

It sees the last **committed** value:

```text
₹10,000
```

Good.

But there is another problem.

<h2>Non-Repeatable Read</h2>

Suppose Transaction B reads:

```text
Balance = ₹10,000
```

Then Transaction A changes and commits:

```text
₹10,000 → ₹8,000
```

Transaction B reads again:

```text
Balance = ₹8,000
```

Same transaction, same query, different result.

That's called:

> **Non-repeatable read**

The Problem: Transaction A gets two different answers for the same query inside a single task. This can cause calculation errors in checkout or billing systems

<h2>Repeatable Read</h2>

This level provides stronger guarantees.

If Transaction B reads a row:

```text
Balance = ₹10,000
```

Repeatable read is a transaction isolation level. It guarantees that any row read during a transaction will stay exactly the same every time it is read again until the transaction ends.

So conceptually:

```text
First Read
₹10,000

      ↓

Other Transaction Updates

      ↓

Second Read
₹10,000
```

This prevents **non-repeatable reads**.

But there can still be another problem:

> **Phantom Read**

<h2>Phantom Read</h2>

Imagine Transaction B runs:

```sql
SELECT * FROM users
WHERE age >= 18;
```

It gets:

```text
10 users
```

While B's transaction is still running, Transaction A inserts another matching user and commits.

Now B runs the same query again.

It may get:

```text
11 users
```

The new row is like a **phantom** appearing in the result.

That's a phantom read.

<h2>Serializable</h2>

This is the strongest standard isolation level.

The idea is:

> **Concurrent transactions behave as if they were executed one after another.**

Instead of:

```text
A + B simultaneously
```

the database ensures behavior equivalent to:

```text
A → B
```

or:

```text
B → A
```

This gives very strong isolation.

But it can reduce concurrency and increase waiting/coordination.

<h2>Comparison</h2>

| Isolation Level | Dirty Read | Non-Repeatable Read | Phantom Read |
|----------------|------------|---------------------|--------------|
| Read Uncommitted | Possible | Possible | Possible |
| Read Committed | Prevented | Possible | Possible |
| Repeatable Read | Prevented | Prevented | Depends on DB/implementation |
| Serializable | Prevented | Prevented | Prevented |

> **Important : The exact behavior of `REPEATABLE READ` can vary by database implementation. For example, PostgreSQL's MVCC behavior differs from MySQL's InnoDB behavior in some details.**

<h2>Why Not Always Use Serializable?</h2>

You might think:

> "Serializable is safest. Why not use it everywhere?"

Because stronger isolation can mean:

```text
More coordination
      ↓
More waiting / contention
      ↓
Lower concurrency
      ↓
Potentially higher latency
```

For a high-traffic application, that can become expensive.

So we choose the isolation level based on requirements.

<h2>Real-World Example</h2>

<h3>Banking</h3>

You generally want strong correctness.

```text
Transfer Money
      ↓
Strong transaction guarantees
```

Higher isolation may be appropriate for critical operations, depending on the database and transaction design.

<h3>Social Media Likes</h3>

Suppose:

```text
1,000 users
```

like a post at nearly the same time.

You may not need every operation to behave as if the entire world is serialized.

You might optimize for:

- High throughput
- Concurrency
- Availability

The exact solution depends on the design.

<h2>Easy Way to Remember the Problems</h2>

<h3>Dirty Read</h3>

> **Reading data that another transaction hasn't committed yet.**

```text
Uncommitted data
      ↓
Other transaction reads it
```

<h3>Non-Repeatable Read</h3>

> **Reading the same row twice and getting different values because another transaction changed it.**

```text
Read → ₹10,000

Other transaction updates

Read → ₹8,000
```

<h3>Phantom Read</h3>

> **Running the same query twice and getting a different set of rows because another transaction inserted/deleted matching rows.**

```text
First query → 10 rows

Other transaction inserts

Second query → 11 rows
```

<h2>Memory Trick</h2>

Think about reading a classroom attendance sheet.

<h3>Dirty Read</h3>

Someone writes a name but hasn't officially submitted it.

You read it anyway.

<h3>Non-Repeatable Read</h3>

You check Rahul's status:

```text
Present
```

Later you check again:

```text
Absent
```

Someone changed his record.

<h3>Phantom Read</h3>

You count:

```text
20 students
```

Later you count again:

```text
21 students
```

A new student appeared.

<h2>Interview Question</h2>

### "What are database isolation levels?"

A strong answer:

> "Isolation levels define how much one concurrent transaction is isolated from changes made by other transactions. The standard levels are Read Uncommitted, Read Committed, Repeatable Read, and Serializable. Stronger isolation provides stronger guarantees but can reduce concurrency."

That's enough for most System Design interviews.

<h2>Quick Revision</h2>

```text
READ UNCOMMITTED
↓
May see uncommitted data
↓
Dirty Reads possible

READ COMMITTED
↓
Only committed data
↓
Non-repeatable reads possible

REPEATABLE READ
↓
Stable reads of existing rows
↓
Phantom behavior depends on DB/implementation

SERIALIZABLE
↓
Strongest standard isolation
↓
Transactions behave as if executed sequentially
```

<h2>Most Important</h2>

Don't memorize the table blindly.

Understand the progression:

```text
Less Isolation
      ↓
More Concurrency
      ↓
Potentially weaker guarantees
```

```text
More Isolation
      ↓
More Coordination
      ↓
Potentially less Concurrency
```

That's the **System Design insight**.
