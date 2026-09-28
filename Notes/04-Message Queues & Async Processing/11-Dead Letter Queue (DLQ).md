## Dead Letter Queue (DLQ)

<h2> What is a DLQ?</h2>

A **Dead Letter Queue (DLQ)** stores messages that repeatedly fail processing so they can be investigated and handled later.

Example: An order message contains invalid data and keeps failing.

```text
Main Queue
     ↓
Consumer fails repeatedly 
     ↓
Retry limit reached
     ↓
Dead Letter Queue (DLQ)
     ↓
Investigate the message
```

The message is moved or routed to the DLQ depending on the messaging system and its configuration.

<h2>Why Do We Need a DLQ?</h2>

- Prevent endless retries.
- Keep normal message processing moving.
- Investigate invalid or problematic messages.
- Retry or replay messages after fixing the issue.

<h2> What Happens After a Message Reaches the DLQ?</h2>

1. Inspect the failed message.
2. Identify the cause.
3. Fix the underlying problem.
4. Retry or replay the message if appropriate.

       ↓
Investigate → Fix → Replay
```