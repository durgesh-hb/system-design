## Distributed Caching 

<h2>What is Distributed Caching?</h2>

Instead of using **one cache server**, we use **multiple cache servers/nodes** to store cached data.

```text
              Application
                   │
          Distributed Cache
          /       │       \
         ▼        ▼        ▼
     Cache 1   Cache 2   Cache 3
```

The cached data is distributed across multiple cache nodes.

<h2>Why Do We Need Distributed Caching?</h2>

A single cache server may eventually become a bottleneck.

Distributed caching helps because:

- One cache server may not have enough memory.
- Multiple servers can handle more traffic.
- Cache capacity can be increased by adding more nodes.
- With proper replication and failover, availability can be improved.
- The cache layer can scale horizontally.

<h2>Example</h2>

Suppose we have user data:

```text
User Data

Cache 1 → Users A–D
Cache 2 → Users E–H
Cache 3 → Users I–L
```

Instead of storing everything on one server:

```text
Cache
 ├── Users A–L
```

the data is distributed:

```text
             User Data
                 │
        ┌────────┼────────┐
        ▼        ▼        ▼
     Cache 1  Cache 2  Cache 3
      A–D       E–H       I–L
```

A request for a particular user is routed to the cache node containing that data.

<h2>How Data is Distributed</h2>

A cache key is typically mapped to a particular cache node.

For example:

```text
user:101 → Cache 1
user:102 → Cache 3
user:103 → Cache 2
```

Hashing or **consistent hashing** can be used to distribute keys across nodes.

```text
Cache Key
    ↓
Hash / Consistent Hashing
    ↓
Cache Node
```

<h2>Advantages</h2>

<h3>More Memory</h3>

Multiple cache nodes provide greater total cache capacity.

```text
Cache 1 → Memory
Cache 2 → Memory
Cache 3 → Memory

       ↓

Larger total cache capacity
```

<h3>Higher Throughput</h3>

Requests can be distributed across multiple nodes:

```text
Requests
    │
 ┌──┼──┐
 ▼  ▼  ▼
C1 C2 C3
```

This allows the cache layer to handle more traffic.

<h3>Horizontal Scaling</h3>

Instead of making one server increasingly powerful:

```text
One Cache
   ↓
Bigger Cache
   ↓
Even Bigger Cache
```

we can add more cache nodes:

```text
Cache 1
Cache 2
Cache 3
Cache 4
...
```

<h3>Better Availability</h3>

With proper replication and failover, if one cache node fails, other nodes can continue serving requests.

<h2>Important Point</h2>

Distributed caching does **not** automatically mean that every cache node contains every piece of data.

Often, data is distributed across nodes:

```text
Cache 1 → Some keys
Cache 2 → Some keys
Cache 3 → Some keys
```

Replication can additionally be used when we want copies of data on multiple nodes.

<h2>Key Point</h2>

> **Distributed caching = cached data spread across multiple cache servers/nodes for scalability, performance, and potentially better availability.**

<h2>Single Cache vs Distributed Cache</h2>

```text
Single Cache
     │
     ▼
One Server
     │
     ├── Limited Memory
     ├── Limited Throughput
     └── Potential Single Point of Failure
```

```text
Distributed Cache
        │
   ┌────┼────┐
   ▼    ▼    ▼
  C1   C2   C3
   │    │    │
   └────┼────┘
        │
   More Scale
```

<h2>Interview Question</h2>

<h3>What is Distributed Caching?</h3>

A strong HLD answer:

> **"Distributed caching means storing cached data across multiple cache nodes instead of a single server. This allows the cache layer to scale horizontally, handle more memory and traffic, and potentially improve availability. The data can be distributed across nodes using techniques such as hashing or consistent hashing."**

<h2>Quick Revision </h2>

```text
One Cache
    ↓
Limited

Multiple Cache Nodes
    ↓
Distributed Cache
    ↓
More Memory
    ↓
More Traffic Handling
    ↓
Horizontal Scaling
    ↓
More Complexity
```

### Remember

> **One cache → limited.**

> **Multiple cache nodes → distributed caching.**