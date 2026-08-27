1. What is a Transaction?

A transaction is a group of database operations treated as one logical unit of work.

Example: Bank Transfer

You transfer ₹1,000 from Account A to Account B.

There are actually two operations:

1. Remove ₹1,000 from A
2. Add ₹1,000 to B

We want both to happen.

A: ₹10,000 → ₹9,000
B: ₹5,000  → ₹6,000

But imagine the server crashes after step 1:

A: ₹10,000 → ₹9,000 ✅

Server crashes ❌

B: ₹5,000 → ₹5,000

₹1,000 disappeared! 😨

A transaction prevents this kind of partial update.

2. Transaction

We treat both operations as one unit:

        Transaction
             │
       ┌─────┴─────┐
       ▼           ▼
   Debit A      Credit B
       │           │
       └─────┬─────┘
             ▼
          COMMIT

If everything succeeds:

COMMIT ✅

If something fails:

ROLLBACK ❌
3. ACID

Transactions are commonly described using ACID properties.

A → Atomicity
C → Consistency
I → Isolation
D → Durability

Let's understand each one.

4. A — Atomicity ⭐

Atomicity means a transaction is treated as one indivisible unit: either all required operations succeed, or none of them take effect.

Think:

All or Nothing.

Bank transfer:

Debit A     ✅
Credit B    ✅

Everything succeeds → commit.

But:

Debit A     ✅
Credit B    ❌

The transaction rolls back the debit.

Debit A     ↩️ Rollback

Final result:

A → Original balance
B → Original balance

No partial transaction.

Memory Trick

A = All or Nothing

5. C — Consistency ⭐

Consistency means a successful transaction moves the database from one valid state to another valid state while preserving defined rules/constraints.

Example:

Suppose a database rule says:

Account balance >= 0

If an operation would violate that rule, the database should reject it rather than leave the database in an invalid state.

Before:

A = ₹10,000
B = ₹5,000

After valid transfer:

A = ₹9,000
B = ₹6,000

The database remains valid.

Memory Trick

C = Correct / Valid State

6. I — Isolation ⭐

Now imagine two transactions happen at the same time.

Transaction 1
Transfer ₹1000

Transaction 2
Transfer ₹500

Both are accessing the same account.

Without proper isolation, they could interfere with each other and produce incorrect results.

Isolation means:

Concurrent transactions should not improperly interfere with each other's intermediate states.

The database controls how much one transaction can see of another transaction's work.

7. Simple Example of Isolation

Suppose:

Balance = ₹10,000

Transaction A is updating it.

Transaction B tries to read the balance at the same time.

We don't want B to accidentally see an inappropriate intermediate state.

Conceptually:

Transaction A
   │
   │ Updating
   ▼
Database
   ▲
   │
Transaction B

The database's isolation level determines exactly what B is allowed to see.

We'll study isolation levels separately.

Memory Trick

I = Independent

Transactions should not improperly interfere with each other.

8. D — Durability ⭐

Durability means that once a transaction is successfully committed, its changes should survive failures such as a database/server crash.

Example:

Transfer ₹1,000
      ↓
COMMIT ✅
      ↓
Server crashes 💥

After the database comes back:

₹1,000 transfer still exists ✅

The committed data shouldn't simply disappear.

Memory Trick

D = Data stays

9. ACID Together

Let's use the bank transfer again.

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
10. What is COMMIT?

When the transaction completes successfully:

COMMIT

means:

Make the transaction's changes permanent.

Example:

Debit A ✅
Credit B ✅

       ↓

    COMMIT
11. What is ROLLBACK?

If something goes wrong:

ROLLBACK

means:

Undo the transaction's changes.

Example:

Debit A ✅
Credit B ❌

       ↓

   ROLLBACK

       ↓

Undo Debit A

The database returns to the previous valid state.

12. Why ACID Matters in System Design

Consider:

Banking
Account
   ↓
Transaction
   ↓
Payment

You don't want money to disappear or appear incorrectly.

So strong transaction guarantees are extremely important.

Other examples:

Payments
Order processing
Inventory updates
Financial systems
13. Does Every Operation Need ACID?

No.

This is important.

Suppose you're recording:

User clicked video
User watched 10 seconds
User scrolled

For billions of analytics events, strict transactional guarantees for every individual event may not be worth the cost.

You might choose a system optimized for:

High throughput
Availability
Eventual consistency

So system design is about requirements and trade-offs.

14. ACID vs Performance

Strong transactional guarantees can introduce coordination and overhead.

Conceptually:

More guarantees
      ↓
More coordination
      ↓
Potentially more latency / lower throughput

This doesn't mean ACID databases are "slow." Modern databases can be extremely fast.

The point is:

Use the consistency and transaction guarantees your application actually needs.

15. ACID vs BASE

You'll eventually encounter BASE when studying distributed/NoSQL systems.

Very roughly:

ACID
↓
Strong transaction guarantees

BASE
↓
More availability/flexibility
↓
Often eventual consistency

We'll study BASE properly when we reach distributed systems.

Don't worry about it now.

🧠 Memory Trick

Think of a bank transfer:

A → Atomicity

All or nothing.

C → Consistency

Database remains valid.

I → Isolation

Transactions don't improperly interfere.

D → Durability

Committed data stays.

ACID = Safe Transaction
🎯 Interview Questions
Q1. What is a transaction?

A group of database operations treated as one logical unit.

Q2. What is Atomicity?

All required operations succeed or none take effect.

Q3. What is Consistency?

A successful transaction preserves the database's defined rules and constraints.

Q4. What is Isolation?

Concurrent transactions are controlled so they don't improperly interfere with each other's intermediate work.

Q5. What is Durability?

Committed changes survive failures.

Q6. What is the difference between COMMIT and ROLLBACK?

COMMIT makes successful transaction changes permanent.

ROLLBACK undoes the transaction's changes after failure or cancellation.

📝 Quick Revision
              ACID
               │
     ┌─────────┼─────────┐
     ▼         ▼         ▼
 Atomicity Consistency Isolation
                         │
                         ▼
                     Durability