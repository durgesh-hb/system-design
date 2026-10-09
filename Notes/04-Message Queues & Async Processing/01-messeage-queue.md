## Message Queue

<h2>What is a Message Queue?</h2>

A **message queue** allows services to communicate asynchronously by storing messages until consumers process them.

```text
Producer
    ↓
Message Queue
    ↓
Consumer
```

- **Producer** → Sends messages.
- **Queue / Broker** → Holds and delivers messages.
- **Consumer** → Processes messages.

<h2>Why Do We Need It?</h2>

- **Asynchronous processing** → The producer doesn't need to wait for the consumer to finish.
- **Decoupling** → Services communicate without directly depending on each other.
- **Handle traffic spikes** → Stores messages when requests arrive faster than consumers can process them.
- **Independent scaling** → Add more consumers when processing demand increases.
- **Failure handling** → Messages can be processed after a service recovers, depending on queue configuration.

<h2>Example: Online Shopping</h2>

When a customer places an order, the system needs to send a confirmation email.

```text
Customer places order
         ↓
    Order Service
         ↓
    Save Order
         ↓
   Message Queue
         ↓
    Email Service
         ↓
   Send Email
```

The order service doesn't need to wait for the email to be sent before responding, provided the email is not required for the order response.

<h2>How Does It Work?</h2>

1. Producer creates a message.
2. Producer sends it to the broker.
3. The broker stores the message according to its configuration.
4. Consumer receives and processes it.
5. Consumer acknowledges successful processing.

If processing fails, the message may be delivered again.

<h2>Popular Message Technologies</h2>

- **RabbitMQ** → Message broker commonly used for task queues.
- **Apache Kafka** → Event-streaming platform used for high-throughput event processing.
- **Amazon SQS** → Managed message queuing service on AWS.

<h2>What If the Consumer Is Slow?</h2>

Suppose the producer sends 500 messages/sec, but the consumer processes only 300 messages/sec.

```text
Producer   → 500 messages/sec
Consumer   → 300 messages/sec
                     ↓
       Backlog grows by 200 messages/sec
```

**Solution:** Add consumers, optimize processing, or limit incoming traffic when appropriate.

<h2>Limitations</h2>

- Messages may be delayed.
- Duplicate delivery can occur.
- Message ordering may be challenging.
- Backlogs can grow if consumers are slow.
- Monitoring and failure handling add complexity.

## How Is a Message Removed from the Queue?

Imagine the queue contains an order message.

```text
┌──────────────────────────────────────────┐
│                  QUEUE                   │
│  Message: Send order confirmation email  │
└──────────────────────────────────────────┘
                     |
                     v
┌──────────────────────────────────────────┐
│                CONSUMER                  │
│     Receives and processes the message   │
└──────────────────────────────────────────┘
                     |
                     v
┌──────────────────────────────────────────┐
│          ACK (Acknowledgement)           │
│    Consumer confirms successful work     │
└──────────────────────────────────────────┘
                     |
                     v
┌──────────────────────────────────────────┐
│             MESSAGE REMOVED              │
│          Or marked as processed          │
└──────────────────────────────────────────┘
```

**Remember:** Process message → Send ACK → Message removed or marked as processed.

<h2>Interview Questions</h2>

**1. Does a message queue guarantee exactly-once processing?**

Not automatically. Duplicate delivery is possible, so consumers often need idempotency and suitable delivery guarantees.

**2. What happens if a consumer crashes?**

Unprocessed messages may be delivered again after recovery, depending on acknowledgements, durability, and broker configuration.

**3. Does a message queue make processing faster?**

Not necessarily. It improves responsiveness and absorbs traffic spikes, but actual processing speed depends on consumer capacity.

<h2>Key Point</h2>

> **A message queue enables asynchronous communication, decouples services, and helps handle traffic spikes.**

<h2>Remember</h2>

```text
Producer → Queue → Consumer
             ↓
    Async Processing
    Service Decoupling
    Traffic Spike Handling
```