## RabbitMQ

<h2>What is RabbitMQ?</h2>

**RabbitMQ is a message broker** that allows applications to communicate asynchronously by sending messages through queues.

Example: An Order Service sends a message, and a consumer processes it separately.

```text
Producer
   ↓
RabbitMQ
   ↓
Consumer
```

<h2> How Does RabbitMQ Work?</h2>

RabbitMQ has four important components:

- **Producer** → Sends messages.
- **Exchange** → Routes messages.
- **Queue** → Stores messages until they are processed.
- **Consumer** → Receives and processes messages.

```text
Producer
   ↓
Exchange
   ↓
Queue
   ↓
Consumer
```

**Important:** Producers typically publish messages to an exchange, which routes them to queues based on routing rules.

<h2> Types of Exchanges</h2>

| Exchange | How It Routes Messages |
|---|---|
| Direct | Exact routing-key match |
| Fanout | Sends to all queues bound to it |
| Topic | Matches routing-key patterns |
| Headers | Routes based on message headers |

<h2> RabbitMQ vs Kafka</h2>

| Feature | RabbitMQ | Kafka |
|---|---|---|
| Main purpose | Message brokering and routing | Distributed event streaming |
| Data organization | Exchanges and queues | Topics and partitions |
| After processing | Acknowledged messages are usually removed | Events remain according to retention policies |
| Routing | Flexible exchange-based routing | Topic and partition-based publishing |
| Common use cases | Background jobs, task queues | Event streams, analytics, data pipelines |

Both support asynchronous communication. Neither is universally better; the choice depends on the use case.

<h2> Why Use RabbitMQ?</h2>

- **Decoupling** → Services work independently.
- **Flexible routing** → Exchanges route messages to queues.
- **Acknowledgements** → Consumers confirm successful processing.
- **Redelivery** → Failed or unacknowledged messages can be delivered again.
- **Durability** → Queues and persistent messages can be configured to survive restarts.

"RabbitMQ is a message broker used for asynchronous communication between applications. Producers publish messages to exchanges, which route them to queues. Consumers process messages and acknowledge successful processing. RabbitMQ is commonly used for task queues, background jobs, and flexible message routing."

<h2>Remember</h2>

```text
Producer → Exchange → Queue → Consumer
              ↓
        Routes Messages
```