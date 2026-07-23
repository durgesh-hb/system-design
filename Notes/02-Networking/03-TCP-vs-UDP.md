## TCP vs UDP

TCP and UDP are two **Transport Layer Protocols** used to transfer data between a **Client** and a **Server**.

The main difference is:

- **TCP prioritizes reliability.**
- **UDP prioritizes speed.**

<h3>Why Do We Need TCP and UDP?</h3>

Imagine two situations.

### Situation 1 – Bank Transfer 

You transfer **₹10,000**.

Question:Can even **₹1** be lost?

 No.

The money must reach **exactly once** and **correctly**.

### Situation 2 – Video Call

You're on a Google Meet call.

One video frame gets lost.

Will you even notice?

Probably not.

The call continues smoothly.

These two situations have different requirements.

That's why we have **TCP** and **UDP**.


<h3>What are TCP and UDP?</h3>

> **Definition : TCP and UDP are Transport Layer Protocols that move data between a client and a server.**

They work below HTTP.

```text
Application
      │
 HTTP / HTTPS
      │
 TCP or UDP
      │
   Internet
```

Think of:

- **HTTP = What you're saying**
- **TCP / UDP = How the message is delivered**


<h2>TCP (Transmission Control Protocol)</h2>

> **Definition : TCP is a reliable, connection-oriented protocol that guarantees data reaches the destination correctly and in order.**

Simply : 

> **TCP = Reliability over Speed.**

<h3>Real-Life Analogy</h3>

Imagine sending important documents through **Speed Post**.

You get:

-  Tracking Number
-  Delivery Confirmation
-  Re-delivery if needed

It may take a little longer,

but you know the package arrived.

That's **TCP**.

<h3>TCP Characteristics</h3>

-  Reliable
-  Data arrives in order
-  Lost data is retransmitted
-  Error checking
-  Slightly slower 

<h3>Where is TCP Used?</h3>

Whenever **correctness** is more important than speed.

Examples:

- Banking
- Online Shopping
- Login Systems
- Email
- File Downloads
- REST APIs
- Database Communication


<h2>UDP (User Datagram Protocol)</h2>

> **Definition : UDP is a fast, connectionless protocol that does not guarantee delivery or order.**

Simply:

> **UDP = Speed over Reliability.**

<h3>Real-Life Analogy</h3>

Imagine making announcements over a **loudspeaker**.

If someone misses one word,

the announcement doesn't stop.

It keeps going.

That's **UDP**.


<h3>UDP Characteristics</h3>

-  Very Fast
-  Low Latency
-  No Connection Setup

 ->  but not
-  No Guarantee of Delivery
-  Packets may arrive out of order
-  No Automatic Retransmission

<h3>Where is UDP Used?</h3>

Whenever **speed** is more important than perfect delivery.

Examples:

- Video Calls (Zoom, Google Meet)
- Voice Calls (WhatsApp Calls)
- Online Gaming
- Live Streaming
- DNS Lookups

<h2>TCP vs UDP</h2>

| TCP | UDP |
|------|-----|
| Reliable | Delivery not guaranteed |
| Connection-oriented | Connectionless |
| Ordered delivery | Order not guaranteed |
| Retransmits lost data | No retransmission |
| Slower | Faster |
| Higher overhead | Lower overhead |

<h2>Real-World Examples</h2>

-> Web Browsing

You open Amazon.

Uses : TCP

**Why?**

Every HTML page, image, and product detail must arrive correctly.

-> UPI Payment

Uses : TCP

**Why?**

You cannot afford missing or duplicate transaction data.

-> Online Gaming

Uses : UDP

**Why?**

If one position update is lost,

it's better to continue than pause the game waiting for it.

-> Video Calls

Uses : UDP

**Why?**

A tiny glitch is better than freezing the entire call.

<h2>System Design Perspective</h2>

When designing a system, ask:

> **What's more important? Reliability or Speed?**

### If Reliability is Critical

Choose **TCP**

Examples:

- Banking
- E-commerce
- Login Systems
- Database Operations

### If Speed is Critical

Choose **UDP**

Examples:

- Gaming
- Live Streaming
- Video Conferencing
- Voice Calls

<h2>Do HTTP and HTTPS Use TCP or UDP?</h2>

Traditionally:

- **HTTP/1.1 → TCP**
- **HTTP/2 → TCP**

Modern:

- **HTTP/3 → QUIC → Built on UDP**

QUIC combines:

- UDP's low latency
- Reliability implemented at the application layer

> **Interview Tip:** For most entry-level interviews, it's perfectly acceptable to say that **HTTP and HTTPS commonly use TCP**. Mention **HTTP/3 over QUIC** only if you're asked about newer versions.

<h3>Why is TCP Slower?</h3>

TCP performs extra work before and during communication:

- Establishes a connection (Three-Way Handshake)
- Checks for errors
- Acknowledges received packets
- Retransmits lost packets
- Maintains packet order

All these features improve reliability but add overhead.

<h3>Why is UDP Faster?</h3>

UDP simply sends packets.

It does **not**:

- Wait for acknowledgements
- Retransmit lost packets
- Ensure packet order
- Establish a connection

Because of this,

UDP has very low latency.

<h2>Key Takeaways</h2>

- **TCP and UDP are Transport Layer Protocols.**
- **TCP = Reliable, Connection-Oriented, Ordered Delivery.**
- **UDP = Fast, Connectionless, Low Latency.**
- Use **TCP** when correctness matters.
- Use **UDP** when speed matters.
- Traditional **HTTP/HTTPS use TCP**.
- **HTTP/3 uses QUIC**, which runs on UDP while providing reliability.
- Choosing between TCP and UDP depends on your application's requirements.
