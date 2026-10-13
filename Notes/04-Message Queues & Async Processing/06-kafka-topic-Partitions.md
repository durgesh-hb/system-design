## Kafka Topics & Partitions

<h2> What is a Kafka Topic?</h2>

A **topic** is a named stream of events in Kafka. Producers publish events to topics, and consumers read them.

Examples:

- `orders` → Order events.
- `payments` → Payment events.
- `notifications` → Notification events.

```text
Order Service   ──→ orders
Payment Service ──→ payments
```

<img src=/images/kafka-componets.png alt="kafka-componets" width="80%" height="400">


<h2> What is a Partition?</h2>

A **partition** is an ordered log of events within a topic.

```text
Topic: orders

Partition 0 → Order A → Order D → Order G
Partition 1 → Order B → Order E → Order H
Partition 2 → Order C → Order F → Order I
```

Each partition maintains its own event order. Kafka does not guarantee a single global order across all partitions.

<h2> Why Does Kafka Use Partitions?</h2>

- **Scalability** → Distributes data across brokers.
- **Parallel processing** → Multiple consumers can process different partitions.
- **Higher throughput** → Reads and writes can happen in parallel.

<h2> Topic vs Partition vs Broker</h2>

- **Topic** → A named stream of events.
- **Partition** → An ordered log within a topic.
- **Broker** → A Kafka server that stores and serves partitions.

```text
Topic: orders
      |
      ├── Partition 0 → Broker 1
      ├── Partition 1 → Broker 2
      └── Partition 2 → Broker 3
```

This is a simplified example. Partition replicas may also be distributed across brokers for fault tolerance.

<img src=/images/offset.png alt="offset" width="80%" height="400">


<h2> How Does Kafka Choose a Partition?</h2>

A producer can use:

- **Key-based partitioning** → Events with the same key normally go to the same partition.
- **Partitioning without a key** → The producer uses its configured partitioning strategy.
- **Explicit partitioning** → The producer specifies the partition.

Example: Using customer ID as the key.

```text
Customer 101 events → Partition 0
Customer 202 events → Partition 1
Customer 303 events → Partition 2
```

The actual partition depends on the key and partitioning configuration.

**Why use a key?** It helps preserve the order of events with the same key within a partition.

<h2> Important Points</h2>

- A topic can contain multiple partitions.
- Each partition is an ordered log.
- Partitions improve scalability and parallel processing.
- Ordering is guaranteed within a partition, not across all partitions.
- Adding partitions can change key-to-partition mapping.
- **Partitioning** distributes data; **replication** maintains copies for fault tolerance.

<h2>Remember</h2>

```text
Topic     = Event category
Partition = Ordered log + Parallelism
Broker    = Kafka server
```
