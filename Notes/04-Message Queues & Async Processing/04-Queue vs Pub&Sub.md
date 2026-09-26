## Queue vs Pub/Sub

<h2> What is a Message Queue?</h2>

A **message queue** distributes work among competing consumers. Typically, each message is processed by one consumer in the group.

Example: Three workers share 100 orders.

```text
       Order Queue
       100 Messages
            |
     ┌──────┼──────┐
     ↓      ↓      ↓
 Worker 1 Worker 2 Worker 3
```

**Use case:** Order processing, invoice generation, background jobs.

<h2> What is Pub/Sub?</h2>

**Pub/Sub (Publish/Subscribe)** allows multiple subscribers to receive the same event independently.

Example: A customer places an order.

```text
             Order Service
                  |
             OrderCreated
                  |
                Event
       ┌──────────┼─────────┐
       ↓          ↓         ↓
     Email      Stock    Analytics
    Service    Service    Service
```

Each subscriber receives its own copy of the event and processes it independently.

**Use case:** Notifications, inventory updates, analytics.

<h2> Key Differences</h2>

| Feature | Queue | Pub/Sub |
|---|---|---|
| Main purpose | Share work | Broadcast events |
| Delivery | One consumer per message in a competing group | Each subscriber gets a copy |
| Scaling | Add workers to share workload | Add independent subscribers |
| Example | Process orders | Notify email, inventory, analytics |

**Note:** These are messaging patterns, not necessarily separate products. Kafka and RabbitMQ can support different patterns depending on configuration.

<h2>When Should We Use Each?</h2>

- **Queue** → When a task should be handled by one worker in a group.
- **Pub/Sub** → When multiple services need to react to the same event.

<h2>Remember</h2>

```text
Queue  → Share the work
Pub/Sub → Share the event
```