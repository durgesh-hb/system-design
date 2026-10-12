## Message Ordering in Kafka

<h2> What is Message Ordering?</h2>

**Message ordering means processing messages in the correct sequence.**

Example: Banking application.

```text
Initial Balance = ₹1,000

Deposit  ₹500
Withdraw ₹200
        ↓
Final Balance = ₹1,300
```

Processing operations in the wrong order can cause problems, depending on how they are implemented.

<h2> How Does Kafka Maintain Message Order?</h2>

Kafka guarantees record order **within a single partition**.

```text
Partition 0

1. Payment Created
          ↓
2. Payment Confirmed
          ↓
3. Payment Completed
```

Consumers read records according to their offsets. However, concurrent processing can cause tasks to finish out of order.

<h2> What Happens With Multiple Partitions?</h2>

```text
Partition 0          Partition 1

Message A            Message B
    ↓                    ↓
Message C            Message D
```

Kafka preserves order within each partition but does not guarantee a global order between partitions.

<h2> How to Preserve Order for Related Messages?</h2>

Use a consistent **message key**, such as `orderId`.

Example:

```text
Order 101 — Created
        ↓
Order 101 — Paid
        ↓
Order 101 — Shipped
        ↓
Same Partition
```

Events with the same key generally go to the same partition when using the default key-based partitioning strategy.

**Important:** The consumer must also process related events in order if completion order matters.
<br>

---><b>IMP</b><br>
The same key does not directly make them orderly by default, it routes all events to that one partition.

-> What happens if you process them all in parallel threads? in side the one partition
If you process in parallel threads, completion order can get scrambled, even though the partition order is preserved. If strict order matters, stick to sequential processing per key.

<h2> Points</h2>

- Kafka preserves order within a partition.
- There is no guaranteed global order across multiple partitions.
- Message keys help route related events to the same partition.
- Multiple partitions improve parallelism but complicate global ordering.
- Concurrent processing can cause completion order to differ from record order.

"Kafka guarantees ordering within a partition, but not across multiple partitions. To preserve the order of related events, we can use a consistent key, such as order ID, to route them to the same partition. Consumers must also process those events in order when processing sequence matters."

<h2>Remember</h2>

```text
Same Partition → Ordered Records
Multiple Partitions → No Global Order
Same Key → Generally Same Partition
```