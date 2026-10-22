## Backpressure

<h2> What is Backpressure?</h2>

**Backpressure controls incoming work when a system cannot process it fast enough.**

Example: The producer sends 1,000 messages/sec, but the consumer processes only 400 messages/sec.

```text
Producer: 1,000 messages/sec
              ↓
        Message Queue
              ↓
Consumer:   400 messages/sec

Backlog grows by 600 messages/sec
```

<h2> Why Is Backpressure Needed?</h2>

Without proper control:

- Queue backlog grows excessively.
- Memory or disk usage increases.
- Processing latency rises.
- Consumers and downstream databases may become overloaded.
 
<h2> How Can We Handle Backpressure?</h2>

- **Limit producer rate** → Reduce incoming messages.
- **Control consumer intake** → Limit in-flight messages or use prefetch limits where supported.
- **Add consumers** → Increase processing capacity when possible.
- **Load shedding** → Reject or drop lower-priority work when safe.
- **Optimize bottlenecks** → Improve slow queries or downstream services.



"Backpressure controls incoming work when consumers cannot keep up with producers. It protects downstream services and prevents unbounded backlog growth. Common techniques include rate limiting, bounded queues, controlling in-flight messages, and scaling consumers when possible."

<h2>Remember</h2>

```text
Producer Faster Than Consumer
            ↓
       Queue Grows
            ↓
     Apply Backpressure
            ↓
      Control the Load
```
