## Latency vs Throughput

Latency and Throughput are important non-functional requirements in System Design.
They measure two different aspects of system performance.

<h2>1. Latency</h2>

> Definition : Latency is the **time taken to complete one request**.

Simply:

> **Latency = How fast one request finishes.**

 Example : 
Suppose a Google Search returns results in:
```text
150 milliseconds
```

The **150 ms** is the latency.

<h3>Examples of Latency</h3> 

- API response time
- Login time
- Search response time
- Message delivery time
- Database query response time

### Units

Latency is usually measured in:

- Milliseconds (ms)
- Microseconds (µs)
- Seconds

### Goal

-> Latency should be as low as practical.

<h2>2. Throughput</h2>

> Definition : Throughput is the **number of requests processed in a given amount of time**.

Simply:

> **Throughput = How much work the system can do.**

Example : 
Suppose a server handles:

```text
5,000 requests every second
```

The throughput is:

5,000 RPS


**RPS = Requests Per Second**

<h3>Examples of Throughput</h3>

- Requests/sec
- Transactions/sec
- Queries/sec
- Messages/sec

 Goal

-> Throughput should be as high as needed for the expected workload.



<h2>Software Example</h2>

Suppose an API responds in : 100 ms

Latency : 100 milliseconds

Now suppose the server handles : 5,000 requests every second

Throughput : 5,000 RPS



<h2>Low Latency but Low Throughput</h2>

Yes, a system can have low latency but low throughput.

Imagine one very fast chef.

Each burger takes : 20 seconds

Each customer gets their burger quickly.

Therefore : Low Latency

But because there is only one chef, only a limited number of burgers can be produced.

Therefore : Low Throughput


<h2>High Throughput but High Latency</h2>

A system can also have high throughput but high latency.

Imagine 100 cooks.

Each burger takes : 10 minutes

Each customer waits a long time.

Therefore : High Latency

But 100 cooks can produce many burgers together.

Therefore : High Throughput


<h2>Real-World Examples</h2>

-> Netflix

Netflix needs both **low latency** and **high throughput**.

Low latency is important for:

- Starting video playback quickly
- Searching for movies
- User interactions

High throughput is required to:

- Serve millions of users simultaneously
- Deliver large amounts of video data

-> WhatsApp

When you send a message : Hi

You expect it to arrive almost instantly.

This requires : Low Latency

WhatsApp also handles billions of messages.

This requires : High Throughput

<h2>Which One Is More Important?</h2> 

It depends on the application.

| Application | Priority |
|-------------|----------|
| Video Call | Very Low Latency |
| Online Gaming | Very Low Latency |
| Search Engine | Low Latency + High Throughput |
| Messaging App | Low Latency + High Throughput |
| E-commerce | Low Latency + High Throughput |
| Email | Latency is less critical |
| Analytics System | High Throughput |

<h2>How to Reduce Latency</h2> 

We can reduce latency using:

- Faster algorithms
- Caching
- CDN
- Better databases
- Faster networks
- Efficient database queries

<h2>How to Increase Throughput</h2>

We can increase throughput using:

- Horizontal scaling
- Load balancers
- More servers
- Better database design
- Asynchronous processing
- Message queues

<h2>Latency vs Throughput Comparison</h2>

| Latency | Throughput |
|---------|------------|
| Time for one request | Number of requests handled |
| Measures response speed | Measures processing capacity |
| Measured in ms, µs, seconds | Measured in requests/sec, transactions/sec, etc. |
| Lower is better | Higher is better |
| Focuses on speed | Focuses on capacity |

---

<h2>Memory Trick</h2>

Imagine a water pipe.

> **Latency** = How long it takes for the first drop of water to reach the other end after turning on the tap.

> **Throughput** = How much water flows through the pipe every second.


<h2>Key Takeaways</h2>

- **Latency = Time taken to complete one request**
- **Throughput = Number of requests processed per unit time**
- Lower latency is generally better.
- Higher throughput is generally better.
- Low latency does not automatically mean high throughput.
- High throughput does not automatically mean low latency.
- Adding more servers can increase throughput without necessarily reducing latency.
- Large-scale systems usually need to optimize both latency and throughput.
