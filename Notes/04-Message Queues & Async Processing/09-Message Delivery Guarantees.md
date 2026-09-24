## Message Delivery Guarantees

<h2>1. What Are Message Delivery Guarantees?</h2>

They define whether messages can be lost, delivered multiple times, or processed with exactly-once effects.

There are three main types:

- At-most-once
- At-least-once
- Exactly-once

<h2> At-Most-Once Delivery</h2>

**Message may be lost.**

Example: Commit before processing.

```text
Order message received
        ↓
Offset committed
        ↓
Consumer crashes before processing
        ↓
Consumer restarts
        ↓
Message is skipped ❌
```

The order might never be processed. Kafka doesn't redeliver it just because processing failed.

- **Advantage:** Avoids redelivery and duplicate processing caused by retries.
- **Disadvantage:** Messages can be lost from the application's perspective.

<h2> At-Least-Once Delivery</h2>

**Duplicates are possible.**

Example: Process before committing.

```text
Order message received
        ↓
Order processed successfully
        ↓
Consumer crashes before committing offset
        ↓
Consumer restarts
        ↓
Message is read again
        ↓
Order may be processed twice ⚠️
```

The message is processed again because Kafka still has the previously committed offset.

- **Advantage:** Reduces the chance of losing messages.
- **Disadvantage:** Duplicate processing is possible.

<h2> Exactly-Once Processing</h2>

**One effect within a defined scope.**

Example: Avoid charging twice for one order.

```text
Payment message received
        ↓
Check unique transaction ID
        ↓
Process payment
        ↓
Record transaction as completed
        ↓
Retry arrives
        ↓
Detect duplicate
        ↓
Don't charge again ✅
```

The same message may be delivered more than once, but the application prevents the payment from taking effect twice.

- **Advantage:** Prevents duplicate effects within the defined processing scope.
- **Disadvantage:** Requires additional design, such as idempotency or transactions.

**Important:** Kafka transactions alone don't guarantee exactly-once effects in an external payment service.

<h2> Kafka Offset Example</h2>

- **Commit before processing** → A crash can cause a message to be skipped.
- **Commit after processing** → A crash before committing can cause the message to be processed again.

```text
Commit Before Processing
          ↓
     At-Most-Once

Commit After Processing
          ↓
     At-Least-Once
```

<h2>Remember</h2>

```text
At-Most-Once  → Message may be lost
At-Least-Once → Duplicate processing possible
Exactly-Once  → One effect within defined scope
```