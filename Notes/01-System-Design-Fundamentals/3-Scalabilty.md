
## What is Scalability?

Scalability is the ability of a system to handle an increasing number of users, requests, or data by adding resources without significantly affecting performance.

> Definition: A scalable system can continue to perform well even as the workload grows.
 
Types of Scalability

There are two main types:

I. Vertical Scaling (Scale Up)

II. Horizontal Scaling (Scale Out)

<img width="700" height="450" alt="image" src="https://github.com/user-attachments/assets/af8604e7-4e5c-4743-acba-d2c1da1dc2d8" />


<h2> -> Vertical Scaling (Scale Up)</h2> 

Vertical scaling means increasing the resources of an existing server instead of adding more servers.

Resources include:

1. RAM
2. CPU
3. Storage
4. Processing Power

Example
<pre>
Before                    After

Server             ->      Server
RAM = 4 GB         ->      RAM = 64 GB
</pre>

The same server becomes more powerful.

<h2> -> Horizontal Scaling (Scale Out)</h2>

Horizontal scaling means adding more servers instead of making one server more powerful.

A Load Balancer distributes incoming requests among multiple servers.

Example
<pre>
 Before                                  After
  
  user                                    User             
   |                                       │
 server                                Load Balancer
                                    ┌──────┼──────┐
                                    │      │      │
                                 Server1 Server2 Server3
                                           │
                                        Database
  </pre>
<h2> -> Horizontal Scaling vs Vertical Scaling</h2>

| Horizontal | Vertical |
|------------|----------|
| Load balancer required | Not required |
| Resilient (Better fault tolerance) | Single point of failure |
| Network call for communication (Slow) | Inter process communication (Fast) |
| Data inconsistency | Consistent |
| Scale very well | Hardware limit |
| More complex | Easy to implement |
| More difficult to implement | Easier to implement |
| Need to changes architecture code | No architecture changes |

<h2> -> Signs Your System Needs Scalability</h2>

A system should be scaled when you observe:

* Slow response time
* CPU usage constantly close to 100%
* Memory (RAM) is full
* Frequent crashes
* Increasing number of users
* Higher request volume

<h2> -> Scalability != Performance</h2>

Performance = Performance refers to how fast is.

Scalability = Scalability refers to whether the system can maintain good performance as the workload increases.

> Performance = Speed <br>
> Scalability = Ability to handle growth

<h2>-> Bottleneck</h2>

A bottleneck is the component that limits the overall performance of a system.

Examples:

* Slow database
* Overloaded CPU
* Limited network bandwidth
* Slow disk I/O

Removing or improving the bottleneck increases the overall system performance.
