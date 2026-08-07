## Server-Sent Events (SSE)

Server-Sent Events (SSE) is a technology that allows a **Server** to continuously send updates to a **Client** over a single long-lived HTTP connection.

Unlike WebSockets, communication is **one-way**.

<h2>Why Do We Need SSE?</h2>

Imagine you're watching a **live cricket score**.

Every time a run is scored,

the score updates automatically.

Did you press **Refresh**?

 No.

The server sent the updated score to your browser.

<h2>The Problem</h2>

With HTTP:

```text
Client
   │ Request
   ▼
Server
   │ Response
   ▼
Connection Closed
```

The server cannot send updates whenever it wants.

With Long Polling:

```text
Request
   │
Wait
   │
Response
   │
New Request
```

Better than Polling,

but after every response,

the client must create another request.

Can we keep one HTTP connection open

and let the server continuously send updates?

Yes.

That's **Server-Sent Events (SSE).**

<h2>What is SSE?</h2>

> **Definition : Server-Sent Events (SSE) is a technology where the server keeps an HTTP connection open and continuously sends updates to the client.**

Simply:

> **SSE = Server → Client communication over one long-lived HTTP connection.**

<h2>Architecture</h2>

```text
Client
   ▲
   │  Live Updates
   │
Server
```

Notice something.

Communication is **only one-way**.

- Server sends updates.
- Client receives updates.

<h2>Real-Life Analogy</h2>

Imagine you're watching a **TV News Channel**.

The TV station continuously broadcasts news.

You don't send messages back every minute.

```text
TV Station
     │
     ▼
 News Broadcast
     │
     ▼
    You
```

That's **SSE**.

<h2>Example</h2>

Suppose you're watching stock prices.

The server sends:

```text
₹250
 ↓
₹251
 ↓
₹249
 ↓
₹253
```

The browser updates automatically.

The client doesn't need to keep asking.

<h2>Where is SSE Used?</h2>

Whenever **only the server** needs to send updates.

Examples:

-  Live News
-  Live Sports Scores
-  Weather Updates
-  Stock Prices
-  System Monitoring Dashboard
-  Server Logs

<h2>Where Shouldn't You Use SSE?</h2>

Suppose you're building **WhatsApp**.

Users must:

- Send Messages
- Receive Messages

Both sides communicate.

SSE cannot do that.

Use:

✅ **WebSockets**

---

<h2>Advantages</h2>

- ✅ Simpler than WebSockets
- ✅ Uses normal HTTP
- ✅ Automatic reconnection if the connection drops (supported by browsers)
- ✅ Efficient for Server → Client updates

---

<h2>Disadvantages</h2>

- ❌ One-way communication only
- ❌ Not suitable for Chat Applications
- ❌ Not suitable for Online Gaming
- ❌ Client cannot send real-time messages over the same connection

---

<h2>SSE vs Long Polling</h2>

### Long Polling

```text
Request
   │
Wait
   │
Response
   │
New Request
```

Every response requires another request.

---

### SSE

```text
Request
   │
Connection Stays Open
   │
Update
   │
Update
   │
Update
   │
Connection Still Open
```

No repeated requests after every update.

---

<h2>SSE vs WebSocket</h2>

| SSE | WebSocket |
|-----|-----------|
| Server → Client only | Two-way communication |
| Uses HTTP | Uses WebSocket Protocol (after HTTP handshake) |
| Simpler | More Powerful |
| Great for Live Updates | Great for Chat & Gaming |

---

<h2>System Design Perspective</h2>

### Live Cricket Score

Need:

```text
Server → Users
```

Best Choice:

✅ **SSE**

---

### WhatsApp

Need:

```text
Users ↔ Server
```

Best Choice:

✅ **WebSocket**

---

### Gmail

Need:

```text
Server → Browser
```

Notify users when a new email arrives.

Best Choice:

✅ **SSE**

---

<h2>Which Should You Choose?</h2>

| Requirement | Best Choice |
|-------------|-------------|
| Live Dashboard | SSE |
| Stock Market | SSE |
| Live Sports Score | SSE |
| Chat Application | WebSocket |
| Video Call | WebSocket |
| Online Game | WebSocket |

---

<h2>SSE vs Long Polling vs WebSocket</h2>

| Feature | Long Polling | SSE | WebSocket |
|---------|--------------|-----|-----------|
| Communication | Client → Server | Server → Client | Two-Way |
| Connection | Recreated after every response | One Long-Lived HTTP Connection | One Persistent Connection |
| Uses HTTP | ✅ Yes | ✅ Yes | Initial HTTP Handshake, then WebSocket Protocol |
| Real-Time | Better | Good | Best |
| Network Overhead | Medium | Low | Lowest |
| Best For | Legacy Systems | Live Updates | Chat & Gaming |

---

<h2>Interview Questions</h2>

### Q1. What is SSE?

**Answer:**

SSE (Server-Sent Events) is a technology where the server continuously sends updates to the client over a long-lived HTTP connection.

---

### Q2. What is the biggest limitation of SSE?

**Answer:**

The client cannot send real-time messages back over the same connection.

Communication is only **Server → Client**.

---

### Q3. When would you use SSE?

**Answer:**

For applications where only the server needs to push updates.

Examples:

- Live Dashboards
- Notifications
- Stock Prices
- Sports Scores
- Weather Updates

---

### Q4. Difference between SSE and WebSocket?

| SSE | WebSocket |
|-----|-----------|
| One-Way Communication | Two-Way Communication |
| Server → Client | Client ↔ Server |
| Simpler | More Powerful |
| Uses HTTP | Uses WebSocket Protocol |

---

### Q5. Difference between SSE and Long Polling?

**Answer:**

Long Polling creates a **new HTTP request after every response**.

SSE keeps **one HTTP connection open** and continuously sends updates.

---

<h2>Memory Trick</h2>

📺 **SSE = TV News Channel**

The TV station continuously broadcasts news.

You only watch.

---

📞 **WebSocket = Phone Call**

Both people can talk anytime.

---

🚪 **Long Polling = Wait Outside the Door**

Knock once.

Wait.

When there's news,

the door opens.

---

<h2>Key Takeaways</h2>

- **SSE enables one-way Server → Client communication.**
- SSE keeps a **single HTTP connection open** for continuous updates.
- SSE is simpler than WebSockets.
- SSE is ideal for **Live Dashboards, Notifications, Sports Scores, and Stock Prices.**
- WebSockets are better when **both Client and Server need to communicate**.
- Long Polling is an older alternative that creates a new request after every response.
- Choose the technology based on the communication pattern your application needs.
