## Kafka Consumer Groups

<h2>What is a Consumer Group?</h2>

A consumer group is a set of consumers that share the work of processing a topic. Kafka assigns partitions to consumers in the group, so each partition is read by only one consumer in that group at a time. That enables parallel processing and scaling.

Kafka assigns partitions among consumers in the group to enable parallel processing.

Example: 3 partitions and 3 consumers.

```text
Topic: orders

P0 ──→ Consumer 1
P1 ──→ Consumer 2
P2 ──→ Consumer 3
```

Each consumer processes its assigned partition.

<h2> Why Do We Need Consumer Groups?</h2>

- **Parallel processing** → Multiple consumers process partitions simultaneously.
- **Scalability** → Add consumers to increase processing capacity, up to the available partition parallelism.
- **Fault tolerance** → If a consumer fails, Kafka can reassign its partitions to other consumers.

<h2> What If We Have More Consumers Than Partitions?</h2>

Suppose a topic has 3 partitions and the group has 5 consumers.

```text
3 Partitions → 3 Active Consumers
                         +
                   2 Idle Consumers
```

Within a consumer group, each partition is assigned to at most one consumer at a time.

**Key point:** The number of consumers actively processing distinct partitions is limited by the number of partitions.

<h2> Can Multiple Consumer Groups Read the Same Events?</h2>

**Yes!** Each consumer group maintains its own offsets and can independently consume the same topic.

```text
             orders Topic
                  |
          ┌───────┴───────┐
          ↓               ↓
     Email Group     Analytics Group
          ↓               ↓
    Send Email       Analyze Sales
```

The email group sends confirmations while the analytics group analyzes sales data.

<h2> What Happens If a Consumer Fails?</h2>

Kafka can rebalance the group and assign the failed consumer's partitions to other consumers.

```text
Before:
P0 → Consumer 1
P1 → Consumer 2 ❌
P2 → Consumer 3

After rebalance:
P0 → Consumer 1
P1 → Consumer 3
P2 → Consumer 3
```

This is an illustrative assignment; the actual result depends on the partition assignment strategy.

The replacement consumer resumes from the group's committed offset. Some events may be processed again if processing completed but the corresponding offset was not committed.


<h2>Remember</h2>

```text
Same Group     → Share the workload
Different Groups → Consume independently
More Consumers than Partitions → Some may be idle
```