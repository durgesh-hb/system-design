## WebSockets

WebSockets enable **real-time, two-way communication** between a client and a server.

Unlike HTTP, where the connection closes after every request, WebSockets keep the connection open, allowing both the client and the server to send data at any time.

<h2>Why Do We Need WebSockets?</h2>

Think about **WhatsApp**.

Suppose your friend sends you:

```text
"Hi Bro!"
```

How does your phone receive that message instantly?

Did your phone keep asking the server every second?

```text
Phone:
"Any new message?"

Server:
"No"

Phone:
"Any new message?"

Server:
"No"

Phone:
"Any new message?"

Server:
"Yes"
```
 That would waste huge amounts of network resources.

There must be a better way.

<h2>Why HTTP Isn't Enough?</h2>

Remember HTTP?

```text
Client
   │ Request
   ▼
Server
   │ Response
   ▼
Connection Closed
```

After the response,

the connection is closed.

If the server later wants to send something,

it can't, because the connection no longer exists.

> **HTTP is Client-Driven.**

The server **cannot send data whenever it wants.**

<h2>What is WebSocket?</h2>

> **Definition : WebSocket is a protocol that creates a persistent, two-way communication channel between a client and a server.**

Simply:

> **WebSocket keeps the connection open, allowing both the client and the server to send data at any time.**

<h2>Real-Life Analogy</h2>

### HTTP

Like making a phone call,

asking one question,

then hanging up.

Need another answer?

Call again.

Every request starts a new conversation.

### WebSocket 

Like staying on a phone call.

The connection remains open.

Both people can talk whenever they want.

<h2>HTTP vs WebSocket Communication</h2>

### HTTP

```text
Client
   │ Request
   ▼
Server
   │ Response
   ▼
Connection Closed
```

Need more data?

Start another request.

### WebSocket

```text
Client
      ⇅
Persistent Connection
      ⇅
Server
```

Connection stays open.

Both sides can send messages anytime.

<h2>Example: WhatsApp</h2>

You send:

```text
Hello
```

Flow:

```text
You
 │
 ▼
WhatsApp Server
 │
 ▼
Friend
```

The server immediately pushes the message to your friend's phone.

No repeated HTTP requests.

<h2>Example: Stock Market</h2>

Stock prices change every second.

Imagine using HTTP.

```text
Every Second

GET /stock-price
```

Millions of users.

Millions of unnecessary requests.

Instead, WebSocket works like:

```text
Stock Price Changed
        │
        ▼
Server Pushes Update
        │
        ▼
User Sees New Price
```

Much more efficient.

<h2>Where Are WebSockets Used?</h2>

Whenever **real-time communication** is needed.

Examples:

-  WhatsApp
-  Messenger
-  Online Games
-  Live Stock Prices
-  Live Sports Scores
-  Live Location Tracking
-  Collaborative Editors (Google Docs)

<h2>System Design Perspective</h2>

Imagine a chat application.

### Without WebSockets

```text
Phone
   │
GET /messages
   │
Server
```

Every few seconds,

the phone asks:

> "Any new messages?"

This is called **Polling**.

It wastes bandwidth and increases server load.

### With WebSockets

```text
Phone
      ⇅
WebSocket Connection
      ⇅
Server
```

A new message arrives.

```text
New Message
      │
      ▼
Server Pushes Message
      │
      ▼
Phone Displays Instantly
```

No polling required.

<h2>Advantages</h2>

-  Real-time communication
-  Lower latency
-  Less network overhead (No repeated polling)
-  Efficient for frequent updates
-  Better user experience for live applications

<h2>Disadvantages</h2>

-  Connection stays open, so the server must manage many active connections.
-  More complex than a simple REST API.
-  Consumes more server memory than stateless HTTP requests.

For normal applications like login or product search,

REST is usually the better choice.

<h2>REST API vs WebSocket</h2>

| REST API | WebSocket |
|-----------|-----------|
| Request → Response | Two-way Communication |
| Connection closes after response | Connection stays open |
| Client starts communication | Both Client and Server can send data |
| Stateless | Persistent Connection |
| Best for CRUD operations | Best for Real-Time Updates |

<h2>Can We Replace REST with WebSockets?</h2>

 No.

Think about Amazon.

Do you need a permanent connection just to:

- View Products
- Register
- Login
- Place an Order

No.

REST is simpler and more appropriate.

WebSockets are used **only where real-time communication is important.**

<h2>REST and WebSockets Together</h2>

In many real-world systems,

both are used together.

### Amazon

```text
REST
 │
 ├── Login
 ├── Products
 ├── Orders

WebSocket
 │
 └── Live Order Status (Optional)
```

### WhatsApp

```text
REST
 │
 ├── Login
 ├── Profile
 ├── Contacts

WebSocket
 │
 ├── Chat Messages
 ├── Typing Indicator
 └── Online Status
```
<h2>REST vs WebSocket: When to Use?</h2>

### Use REST API When

- User Login
- Registration
- Product Search
- Orders
- Payments
- CRUD Operations

### Use WebSocket When

- Chat Applications
- Live Notifications
- Online Gaming
- Live Scores
- Live Stock Prices
- Collaborative Editing
- Live Location Tracking

| HTTP | WebSocket |
|------|-----------|
| Request–Response | Full-Duplex Communication |
| Connection closes | Connection remains open |
| Client initiates communication | Client and Server can both send data |
| Best for CRUD | Best for Real-Time Applications |

<h2>Key Takeaways</h2>

- **WebSocket provides persistent, two-way communication.**
- HTTP is **Request–Response**, while WebSocket is **Full-Duplex** communication.
- WebSockets eliminate repeated polling and reduce network overhead.
- REST is ideal for **CRUD operations**.
- WebSockets are ideal for **real-time applications** like chat, gaming, live updates, and collaborative editing.
- Most modern applications use **REST and WebSockets together**, each for the tasks they are best suited for.
