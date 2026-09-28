## Retry Mechanism

<h2> What is a Retry Mechanism?</h2>

A **retry mechanism** attempts to process a failed operation again instead of immediately giving up.

Example: A consumer receives an order message, but the database is temporarily unavailable.

```text
Consumer receives message
          ↓
Database unavailable 
          ↓
     Retry later
          ↓
Database available
          ↓
Processing succeeds 
```

<h2> Types of Retry</h2>

- **Immediate Retry** → Retry immediately after failure. Useful for short-lived issues.
- **Delayed Retry** → Wait for some time before retrying.
- **Exponential Backoff** → Increase the waiting time after each failure.

Example:

| Attempt | Wait Time |
|---|---|
| Retry 1 | 1 second |
| Retry 2 | 2 seconds |
| Retry 3 | 4 seconds |
| Retry 4 | 8 seconds |

**Jitter** adds a small random variation to the delay so many consumers don't retry simultaneously.

<h2> What If Every Retry Fails?</h2>

Don't retry forever.

After a configured number of attempts, move the message to a **Dead Letter Queue (DLQ)** for investigation or later processing.

```text
Message fails
      ↓
    Retry
      ↓
    Retry
      ↓
Retry limit reached
      ↓
      DLQ
```

- **Temporary error** → Retry may succeed.
- **Invalid input** → Retrying may not help until the data is corrected.

<h2> Important Points</h2>

- Retry temporary failures.
- Use exponential backoff and jitter.
- Set a retry limit.
- Use a DLQ for repeatedly failing messages.
- Make processing idempotent to prevent duplicate side effects.

<h2>Remember</h2>

```text
Failure
   ↓
Retry
   ↓
Success 

If retries fail
   ↓
DLQ
```