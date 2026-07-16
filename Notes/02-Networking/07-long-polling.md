## Long Polling

Long Polling is a technique used to achieve **near real-time communication** using standard HTTP.

It was introduced to reduce the unnecessary requests made by **regular polling** before WebSockets became widely available.

<h2>Why Do We Need Long Polling?</h2>

First, let's recall how HTTP works.

```text
Client
   │ Request
   ▼
Server
   │ Response
   ▼
Connection Closed
```

Example:

You ask:

```text
Do I have any new messages?
```

Server replies:

```text
No.
```

Connection closes.

<h2>The Problem</h2>

Imagine you're using **WhatsApp**.

Your friend sends you a message.

How will your phone know?

One option is to ask the server every second.

```text
Phone
   │
"Any message?"
   │
Server
   │
"No"

1 second later...

"Any message?"

"No"

1 second later...

"Any message?"

"No"
```

This is called **Polling**.

<h2>What is Polling?</h2>

> **Definition : Polling means the client repeatedly asks the server for new data at fixed intervals.**

Example:

Every 2 seconds

```text
Client
   │
GET /messages
   │
Server
   │
"No new messages"
```

Even if nothing changes,

the request is still made.

<h2>Problem with Polling</h2>

Suppose you have:

- 1 Million Users
- Each user sends 1 request every second

```text
1,000,000 Requests / Second
```

Even when nothing has changed.

Huge waste of:

- CPU
- Network
- Bandwidth
- Server Resources

---

<h2>What is Long Polling?</h2>

> **Definition : Long Polling is an HTTP technique where the server keeps the request open until new data is available or a timeout occurs.**

Simply:

> **Long Polling = Request once, wait for new data.**

---

<h2>How Long Polling Works</h2>

Suppose you send:

```text
GET /messages
```

Instead of replying immediately:

```text
No Messages
```

The server waits.

```text
Client
   │
GET /messages
   │
Server

(wait...)

(wait...)

(wait...)
```

After 20 seconds,

your friend sends:

```text
Hi
```

Now the server immediately replies.

```text
Server
   │
"Hi"
   │
Connection Closed
```

---

<h2>What Happens Next?</h2>

After receiving the message,

the client immediately sends another request.

```text
Request
   │
Wait
   │
Message Received
   │
New Request
   │
Wait Again
```

This cycle continues.

---

<h2>Visual Flow</h2>

### Normal Polling

```text
Client
   │ Request
   ▼
Server
   │ No Data
   ▼
Client

2 Seconds Later...

Client
   │ Request
   ▼
Server
```

Lots of unnecessary requests.

---

### Long Polling

```text
Client
   │ Request
   ▼
Server

(wait...)

(wait...)

(wait...)

New Message
      │
      ▼
Response Sent
      │
      ▼
Client Sends New Request
```

Much fewer requests.

---

<h2>Real-Life Analogy</h2>

Imagine you're waiting outside a classroom.

### Polling

Every minute,

you open the door and ask:

```text
Has the teacher come?
```

Most of the time,

the answer is:

```text
No.
```

---

### Long Polling

You ask once:

```text
Please tell me when the teacher arrives.
```

The person waits.

The moment the teacher comes,

they tell you.

Much smarter.

---

<h2>Why is Long Polling Better than Polling?</h2>

Instead of:

```text
100 Requests
```

to receive one update,

Long Polling may only require:

```text
1 Request
     │
     ▼
1 Response
```

Less unnecessary traffic.

---

<h2>Why Not Just Use WebSockets?</h2>

Good question.

Let's compare.

### Long Polling

```text
Request
   │
Wait
   │
Response
   │
Connection Closed
   │
New Request
```

A new HTTP request is created after every response.

---

### WebSocket

```text
Client
     ⇅
Open Connection
     ⇅
Server
```

One connection.

No repeated requests.

The server can push updates anytime.

---

<h2>Polling vs Long Polling vs WebSocket</h2>

| Polling | Long Polling | WebSocket |
|----------|--------------|-----------|
| Repeated Requests | Waits Before Responding | One Persistent Connection |
| High Network Usage | Medium Network Usage | Low Network Usage |
| Poor Real-Time | Better | Best |
| Very Simple | Moderate | Best for Real-Time Applications |

---

<h2>Where is Long Polling Used?</h2>

Today it's much less common because WebSockets are widely supported.

You might still see it in:

- Legacy Applications
- Systems where WebSockets aren't available
- Older Infrastructure

---

<h2>Advantages</h2>

- ✅ Better than regular polling
- ✅ Works with standard HTTP
- ✅ Easier to support in environments where WebSockets aren't available

---

<h2>Disadvantages</h2>

- ❌ Creates a new HTTP request after every response
- ❌ More server resources than WebSockets
- ❌ Doesn't scale as efficiently as WebSockets for many real-time users

---

<h2>System Design Perspective</h2>

Imagine you're designing a chat application.

Today, you'd usually choose:

```text
✅ WebSockets
```

But if your environment doesn't support WebSockets,

```text
Long Polling
```

is a reasonable alternative.

That's why interviewers may still ask about it.

---

<h2>Interview Questions</h2>

### Q1. What is Polling?

**Answer:**

Polling is a technique where the client repeatedly sends requests to the server at fixed intervals to check for new data.

---

### Q2. What is Long Polling?

**Answer:**

Long Polling is an HTTP technique where the server keeps the request open until new data is available or a timeout occurs.

---

### Q3. Why was Long Polling invented?

**Answer:**

Because regular polling wastes resources by repeatedly asking for updates even when no new data exists.

Long Polling reduces unnecessary requests by waiting until new data is available.

---

### Q4. What is the difference between Polling and Long Polling?

| Polling | Long Polling |
|----------|--------------|
| Server responds immediately | Server waits before responding |
| Many unnecessary requests | Fewer requests |
| Higher network overhead | Lower network overhead |

---

### Q5. Long Polling vs WebSocket?

| Long Polling | WebSocket |
|--------------|-----------|
| Uses HTTP | Uses WebSocket Protocol |
| New request after every response | One persistent connection |
| Near Real-Time | True Real-Time |
| Higher overhead | Lower overhead |

---

<h2>Memory Trick</h2>

📬 **Polling = Keep Knocking**

Every few seconds you ask:

```text
Any Updates?
```

---

🚪 **Long Polling = Wait at the Door**

Knock once.

Wait.

The door opens only when there's something to tell you.

---

📞 **WebSocket = Stay on the Phone**

Connection stays open.

Both sides can talk anytime.

---

<h2>Key Takeaways</h2>

- **Polling repeatedly asks the server for updates.**
- **Long Polling waits until new data is available before responding.**
- Long Polling reduces unnecessary HTTP requests compared to Polling.
- Long Polling still creates a **new HTTP request after every response**.
- **WebSockets are more efficient** for large-scale real-time communication.
- Today, WebSockets are preferred, but Long Polling is still useful for compatibility with older systems.