<h2>Availability</h2>

Availability is one of the most important **Non-Functional Requirements** in System Design.

It tells us whether a system is **operational and accessible whenever users need it.**

<h2>Why Do We Need Availability?</h2>

Imagine you want to watch a movie on Netflix.

You open the app.

Instead of showing movies, it displays:


 ** 503 Service Unavailable **


You try again.

Still doesn't work.

After **30 minutes...**

Still down.

Would you continue using Netflix?

Probably not.

Users expect services to be available whenever they need them.

That's why **Availability** is so important.

<h2>Definition</h2>

> **Definition : Availability is the ability of a system to remain operational and accessible to users whenever they need it.**

Simply:

> **Availability = Is the system up and working?**

-> Example
 Gmail

If Gmail is available:

-  Users can send emails.
-  Users can receive emails.
-  Everything works.

If Gmail is unavailable:

-  Cannot login.
-  Cannot send emails.
-  Cannot receive emails.

The service is down.


<h2>Uptime vs Downtime</h2>

Availability is measured using two terms.

### Uptime

The system is working.

Example:

<pre>
Website
   ↓
Running
</pre>


### Downtime

The system is not working.

Example:

<pre>
  Website
   ↓
Server Crash
   ↓
Users Cannot Access
</pre>



<h2>Availability Formula</h2>

Availability is usually expressed as a percentage.

```text
Availability = (Uptime / Total Time) × 100
```
 Example

Suppose a website runs for : 99 hours

and is down for : 1 hour

Total time : 100 hours


Availability : 99 / 100 × 100 = 99%

<h2>Why Do We Talk About "Nines"?</h2>

In industry, you'll often hear:

- 99%
- 99.9%
- 99.99%
- 99.999%

These are called **"Nines of Availability."**

The more **9s**, the more reliable the service is expected to be.


<h2>Downtime Comparison</h2>

| Availability | Approximate Downtime per Year |
|--------------|-------------------------------|
| 99% | ~3.65 days |
| 99.9% | ~8.8 hours |
| 99.99% | ~53 minutes |
| 99.999% | ~5 minutes |

> Notice how adding one more **9** dramatically reduces the allowed downtime.


<h2>Which Applications Need High Availability?</h2>

-> Banking

Imagine if your bank app is down on salary day.

Millions of users can't access their money.

**High availability is essential.**

-> WhatsApp

If WhatsApp is unavailable for an hour:

Millions of messages won't be sent.

-> Hospital Systems

If patient records become unavailable during surgery...

That can become dangerous.


<h2>Which Applications Can Tolerate Lower Availability?</h2>

Not every application needs five nines.

Examples:

- College project
- Personal portfolio website
- Internal testing tools
- Demo applications

If these are unavailable for a while, the impact is much smaller.

<h2>How Do Companies Increase Availability?</h2>

Imagine you have only one server.

```text
Users
   │
Server
```

If it crashes:

Everything stops.

Instead, companies use multiple servers.

```text
             Users
               │
         Load Balancer
        ┌──────┼──────┐
        │      │      │
     Server A Server B Server C
```

If **Server B** fails,

Servers **A** and **C** continue serving users.

The service stays available.

<h2>Other Ways to Improve Availability</h2>

### 1. Redundancy

Have backup components.

Examples:

- Multiple servers
- Multiple databases
- Backup power supplies

### 2. Failover

If one server fails...

Traffic automatically goes to another healthy server.


### 3. Replication

Store copies of data on multiple machines.

If one database fails,

another can continue serving requests.


### 4. Monitoring

Continuously monitor:

- CPU
- Memory
- Errors
- Disk
- Network

If something goes wrong,

the operations team is alerted immediately.

<h2>Availability vs Reliability</h2>

Many beginners confuse these.

| Availability | Reliability |
|--------------|-------------|
| Can I access the service right now? | Does the system always produce the correct result? |
| Focuses on uptime | Focuses on correctness |
| Example: Netflix opens | Example: Payment is processed correctly |
 Example

You transfer **₹1,000**.

If the system deducts **₹1,000 twice**,

it is **available**,

but **not reliable**.

<h2>Ways to Improve Availability</h2>

- Redundancy
- Multiple Servers
- Replication
- Load Balancers
- Monitoring
- Failover


<h2>Key Takeaways</h2>

- **Availability = System is operational and accessible.**
- **Availability is measured using Uptime and Downtime.**
- **Availability = (Uptime / Total Time) × 100**
- More **9s** means less downtime.
- High availability is essential for Banking, WhatsApp, Netflix, Google, and Hospital Systems.
- Companies improve availability using **Redundancy, Failover, Replication, Monitoring, Multiple Servers, and Load Balancers.**
- A system can be **available but not reliable.**
