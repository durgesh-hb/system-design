## Producer & Consumer

<h2> What is a Producer?</h2>

A **Producer** is an application or service that creates and sends messages to a message queue.

Example: The Order Service sends a message when a customer places an order.

<h2> What is a Consumer?</h2>

A **Consumer** is an application or service that receives and processes messages.

Example: The Email Service reads the order message and sends a confirmation email.

<h2> How Do They Work Together?</h2>

```text
┌──────────────────────┐
│ Producer             │
│ Order Service        │
│ Creates OrderCreated │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│ Message Queue        │
│ Stores the message   │
└──────────┬───────────┘
           ↓
┌──────────────────────┐
│ Consumer             │
│ Email Service        │
│ Sends confirmation   │
└──────────────────────┘
```

The producer and consumer can work independently. The producer doesn't need to know the consumer's internal implementation.

<h2> Can We Have Multiple Producers and Consumers?</h2>

**Yes!**

- Multiple producers can send messages to the same queue.
- Multiple consumers can share the workload.
- Adding consumers can increase processing capacity, depending on the queue system.

Example:

```text
Order Service ──┐
Payment Service ├──→ Queue ──→ Consumer 1
User Service ───┘           ├→ Consumer 2
                            └→ Consumer 3
```

In a **competing-consumer pattern**, consumers share the workload. In **Pub/Sub**, multiple subscribers can receive copies of an event.

<h2> Important Interview Points</h2>

- **Producer** → Sends messages.
- **Queue** → Stores messages temporarily.
- **Consumer** → Processes messages.
- **ACK and retries** → Help handle processing failures.
- **Multiple consumers** → Can improve throughput, but ordering and duplicate processing must be considered.

<h2>Interview Answer</h2>

"A producer creates and sends messages to a message broker, while a consumer receives and processes those messages. The queue decouples producers from consumers, allowing them to operate and scale independently."

<h2>Remember</h2>

```text
Producer → Queue → Consumer
  Sends     Stores   Processes
```