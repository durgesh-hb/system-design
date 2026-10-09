## Synchronous vs Asynchronous Processing

<h2>1. Synchronous Processing</h2>

**Synchronous means the caller waits for the operation to finish before continuing.**

Example: User Login

```text
User
  ↓
Application
  ↓
Authentication Service
  ↓
Login Success / Failure
  ↓
Application responds to User
```

The application needs the authentication result before completing the login flow.

<h2>2. Asynchronous Processing</h2>

**Asynchronous means the caller can continue without waiting for the entire task to finish.**

Example: Order Confirmation Email

```text
User places order
       ↓
Order Service
       ↓
Save Order
       ↓
Message Queue
       ↓
Application responds to User

Email Consumer → Sends Email Later
```

The user receives the order response without waiting for the email to be sent.

<h2>3. Key Differences</h2>

| Synchronous | Asynchronous |
|---|---|
| Caller waits for the result | Caller can continue without waiting |
| Operation completes before the caller continues | Task can finish later |
| Useful when an immediate result is required | Useful for background tasks |
| Example: Login verification | Example: Sending an email |

<h2>4. Important Interview Point</h2>

- Asynchronous processing does **not always require a message queue**.
- It can also use background jobs, callbacks, futures, or event loops.
- Asynchronous processing improves responsiveness, but doesn't automatically make the task itself faster.

<h2>Interview Answer</h2>

"Synchronous processing requires the caller to wait for the operation's result. Asynchronous processing allows the caller to continue while the task runs separately. Message queues are commonly used for asynchronous tasks such as email notifications and background processing."

<h2>Remember</h2>

```text
Synchronous   → Wait for result
Asynchronous  → Continue without waiting
```