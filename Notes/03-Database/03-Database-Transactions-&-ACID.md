## Database Transactions & ACID

<h2>What is a Transaction?</h2>

> **Definition : A transaction is a group of database operations treated as one logical unit of work.**

<h3>Example : Bank Transfer</h3>

You transfer ₹1,000 from Account A to Account B.

There are actually two operations:

1. Remove ₹1,000 from A
2. Add ₹1,000 to B

We want both to happen.

```text
A: ₹10,000 → ₹9,000
B: ₹5,000  → ₹6,000
```

But imagine the server crashes after step 1:

```text
A: ₹10,000 → ₹9,000

Server crashes

B: ₹5,000 → ₹5,000
```

₹1,000 disappeared.

A transaction prevents this kind of partial update.

<h2>Transaction</h2>

We treat both operations as one unit:

```text
        Transaction
             │
       ┌─────┴─────┐
       ▼           ▼
   Debit A      Credit B
       │           │
       └─────┬─────┘
             ▼
          COMMIT
```

If everything succeeds:

```text
COMMIT
```

If something fails:

```text
ROLLBACK
```

<h2>ACID</h2>

Transactions are commonly described using **ACID properties**.

```text
A → Atomicity
C → Consistency
I → Isolation
D → Durability
```

Let's understand each one.

<h2>Atomicity</h2>

> **Definition : Atomicity means a transaction is treated as one indivisible unit: either all required operations succeed, or none of them take effect.**

Think:

> **All or Nothing.**

Bank transfer:

```text
Debit A     Successful
Credit B    Successful
```

Everything succeeds → commit.

But:

```text
Debit A     Successful
Credit B    Failed
```

The transaction rolls back the debit.

```text
Debit A
   ↩
Rollback
```

Final result:

```text
A → Original balance
B → Original balance
```

No partial transaction.

<h3>Memory Trick</h3>

> **A = All or Nothing**

<h2>Consistency</h2>

> **Definition : Consistency means a successful transaction moves the database from one valid state to another valid state while preserving defined rules and constraints.**

Example:

Suppose a database rule says:

```text
Account balance >= 0
```

If an operation would violate that rule, the database should reject it rather than leave the database in an invalid state.

Before:

```text
A = ₹10,000
B = ₹5,000
```

After valid transfer:

```text
A = ₹9,000
B = ₹6,000
```

The database remains valid.

<h3>Memory Trick</h3>

> **C = Correct / Valid State**

<h2>Isolation</h2>

Now imagine two transactions happen at the same time.

```text
Transaction 1
Transfer ₹1000

Transaction 2
Transfer ₹500
```

Both are accessing the same account.

Without proper isolation, they could interfere with each other and produce incorrect results.

> **Definition : Isolation means concurrent transactions should not improperly interfere with each other's intermediate states.**

The database controls how much one transaction can see of another transaction's work.

<h3>Simple Example of Isolation</h3>

Suppose:

```text
Balance = ₹10,000
```

Transaction A is updating it.

Transaction B tries to read the balance at the same time.

We don't want B to accidentally see an inappropriate intermediate state.

Conceptually:

```text
Transaction A
   │
   │ Updating
   ▼
Database
   ▲
   │
Transaction B
```

The database's **isolation level** determines exactly what B is allowed to see.

We'll study isolation levels separately.

<h3>Memory Trick</h3>

> **I = Independent**

Transactions should not improperly interfere with each other.

<h2>Durability</h2>

> **Definition : Durability means that once a transaction is successfully committed, its changes should survive failures such as a database or server crash.**

Example:

```text
Transfer ₹1,000
      ↓
COMMIT
      ↓
Server crashes
```

After the database comes back:

```text
₹1,000 transfer still exists
```

The committed data shouldn't simply disappear.

<h3>Memory Trick</h3>

> **D = Data stays**

<h2>ACID Together</h2>

Let's use the bank transfer again.

```text
Transfer ₹1,000
       │
       ▼
   Transaction
       │
       ├── Atomicity
       │    All or nothing
       │
       ├── Consistency
       │    Valid database state
       │
       ├── Isolation
       │    Concurrent transactions don't improperly interfere
       │
       └── Durability
            Committed data survives failures
```

<h2>What is COMMIT?</h2>

When the transaction completes successfully:

```text
COMMIT
```

means:

> **Make the transaction's changes permanent.**

Example:

```text
Debit A
   ↓
Credit B
   ↓
COMMIT
```

<h2>What is ROLLBACK?</h2>

If something goes wrong:

```text
ROLLBACK
```

means:

> **Undo the transaction's changes.**

Example:

```text
Debit A
   ↓
Credit B
   ↓
Failed
   ↓
ROLLBACK
```

The database undoes the transaction's changes and returns to the previous valid state.

<h2>Why ACID Matters in System Design</h2>

Consider:

```text
Banking
Account
   ↓
Transaction
   ↓
Payment
```

You don't want money to disappear or appear incorrectly.

So strong transaction guarantees are extremely important.

Other examples:

- Payments
- Order Processing
- Inventory Updates
- Financial Systems

<h2>Does Every Operation Need ACID?</h2>

No.

This is important.

Suppose you're recording:

```text
User clicked video
User watched 10 seconds
User scrolled
```

For billions of analytics events, strict transactional guarantees for every individual event may not be worth the cost.

You might choose a system optimized for:

- High throughput
- Availability
- Eventual consistency

So System Design is about **requirements and trade-offs**.

<h2>ACID vs Performance</h2>

Strong transactional guarantees can introduce coordination and overhead.

Conceptually:

```text
More guarantees
      ↓
More coordination
      ↓
Potentially more latency / lower throughput
```

This doesn't mean ACID databases are "slow."

Modern databases can be extremely fast.

The point is:

> **Use the consistency and transaction guarantees your application actually needs.**

<h2>ACID vs BASE</h2>

You'll eventually encounter **BASE** when studying distributed and NoSQL systems.

Very roughly:

```text
ACID
↓
Strong transaction guarantees
```

```text
BASE
↓
More availability/flexibility
↓
Often eventual consistency
```

We'll study BASE properly when we reach distributed systems.

Don't worry about it now.

<h2>Memory Trick</h2>

Think of a bank transfer:

```text
A → Atomicity
```

All or nothing.

```text
C → Consistency
```

Database remains valid.

```text
I → Isolation
```

Transactions don't improperly interfere.

```text
D → Durability
```

Committed data stays.

> **ACID = Safe Transaction**

<h2>Interview Questions</h2>

### Q1. What is a transaction?

> A group of database operations treated as one logical unit.

### Q2. What is Atomicity?

> All required operations succeed or none take effect.

### Q3. What is Consistency?

> A successful transaction preserves the database's defined rules and constraints.

### Q4. What is Isolation?

> Concurrent transactions are controlled so they don't improperly interfere with each other's intermediate work.

### Q5. What is Durability?

> Committed changes survive failures.

### Q6. What is the difference between COMMIT and ROLLBACK?

> **COMMIT** makes successful transaction changes permanent.

> **ROLLBACK** undoes the transaction's changes after failure or cancellation.

<h2>Quick Revision</h2>

```text
              ACID
               │
     ┌─────────┼─────────┐
     ▼         ▼         ▼
 Atomicity Consistency Isolation
                         │
                         ▼
                     Durability
```

<h2>Key Takeaways</h2>

- **Transaction = Group of database operations treated as one logical unit.**
- **Atomicity = All or Nothing.**
- **Consistency = Database moves between valid states.**
- **Isolation = Concurrent transactions don't improperly interfere.**
- **Durability = Committed data survives failures.**
- **COMMIT = Make changes permanent.**
- **ROLLBACK = Undo transaction changes.**
- ACID is especially important for systems such as **banking, payments, orders, and inventory**.
- Not every operation requires strong ACID guarantees.
- System Design requires choosing the right **consistency, performance, and availability trade-offs**.