## Kafka Basics

<h2> What is Apache Kafka?</h2>

**Apache Kafka is a distributed event-streaming platform** used to publish, store, and process events between applications and services.

Example: When a customer places an order, multiple services need to know about it.

```text
            Order Service
                  |
          OrderCreated Event
                  |
               Kafka
          ________|__________
         |        |         |
         ↓        ↓         ↓
      Inventory  Email   Analytics
       Service  Service   Service
```

Each service can consume the event independently.

<h2>Why Do We Use Kafka?</h2>

- **High throughput** → Handles large volumes of events.
- **Scalability** → Distributes data across brokers and partitions.
- **Durable storage** → Retains events according to its configuration.
- **Decoupling** → Producers and consumers work independently.
- **Replay** → Consumers can reread retained events.
- **Real-time processing** → Supports event-driven systems and streaming pipelines.

<h2> How Does Kafka Work?</h2>

| Component | Meaning |
|---|---|
| Producer | Publishes events to Kafka. |
| Topic | A named stream of events. |
| Broker | A Kafka server that stores and serves topic partitions. |
| Consumer | Reads and processes events. |

```text
Producer
   ↓
Kafka Topic
   ↓
Broker stores events
   ↓
Consumer reads events
```

**Note:** Topics are divided into partitions to support scalability and parallel processing.

<h2> Kafka vs Traditional Message Queue</h2>

| Feature | Traditional Queue (e.g., RabbitMQ) | Kafka |
|---|---|---|
| Typical model | Distributes work | Consumers read an event log |
| After processing | Message is usually acknowledged and removed | Consumer commits its offset |
| Replay | Depends on technology and configuration | Can reread retained events |
| Common use | Task queues and message routing | Event streaming and data pipelines |

These are typical patterns, not strict limitations.

<h2> Real-World Use Cases</h2>

- E-commerce order events.
- Banking transaction streams.
- Website clickstream analytics.
- Application logs and monitoring.
- Data pipelines between services.

**Q2. Why use Kafka instead of direct service calls?**

Kafka decouples services and allows consumers to process events independently.

**Q3. Does Kafka delete a message after a consumer reads it?**

No, not normally. Events remain according to the retention policy, and consumers track progress using offsets.

**Q4. What is a Kafka broker?**

A broker is a Kafka server that stores and serves topic partitions.

**Q5. What is a Kafka topic?**

A topic is a named stream of events, such as `orders` or `payments`.

<h2>Remember</h2>

```text
Kafka = Event Streaming
      + Durable Storage
      + Scalable Consumption
```