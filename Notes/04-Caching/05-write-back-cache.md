## Write-Back Cache

<h3>What is Write-Back Cache?</h3>

The main idea is:

> **The application writes to the cache first, and the cache updates the database later, asynchronously.**

This is the key difference from **Write-Through Cache**.

```text
Application
     ↓
   Cache
     ↓
Success ⚡

   ...later...

Cache
  ↓
Database
```

The application does **not wait for the database write** before receiving success.

<h2>Write-Through vs Write-Back</h2>

<h3>Write-Through</h3>

```text
Application
     ↓
   Cache
     ↓
 Database
     ↓
Success
```

The database is updated as part of the write flow before the operation is considered complete.

<h3>Write-Back</h3>

```text
Application
     ↓
   Cache
     ↓
Success ⚡

   ...later...

Cache
  ↓
Database
```

The database update happens later, asynchronously.

<h2>Example</h2>

Suppose we have a video view counter:

```text
Video views = 1,000
```

A user watches the video:

```text
1000 → 1001
```

With Write-Back:

```text
Application
     ↓
Cache
     ↓
1001
     ↓
Success 
```

At this moment, the database might still contain:

```text
Database = 1000
```

Later, the cache persists the updated value:

```text
Cache
  ↓
Database
  ↓
1001
```

The database eventually catches up with the cache.


<h2>Why is Write-Back Faster? ⚡</h2>

The application does not have to wait for the database.

<h3>Write-Through</h3>

```text
App
 ↓
Cache
 ↓
DB
 ↓
Response
```

The response waits for the database write to complete.

<h3>Write-Back</h3>

```text
App
 ↓
Cache
 ↓
Response ⚡

DB update happens later
```

Therefore, Write-Back can provide **very low write latency** and can reduce the number of immediate database writes.

<h2>Where is Write-Back Useful?</h2>

Write-Back can be useful when:

- There are very frequent writes.
- Very low write latency is important.
- Temporary delay before database persistence is acceptable.
- The system can tolerate additional consistency and durability complexity.

<h3>View Counters</h3>

```text
Video views
```

Millions of users may generate frequent updates.

<h3>Metrics</h3>

```text
Page views
Clicks
Counters
Events
```

These workloads may generate large numbers of updates that can sometimes be aggregated before being persisted.

<h3>Gaming</h3>

Some rapidly changing game-state data can potentially use asynchronous persistence, depending on the durability and consistency requirements.

<h2>The Biggest Risk </h2>

The biggest disadvantage is the possibility of losing updates before they reach durable storage.

Suppose:

```text
Application
    ↓
Cache
    ↓
Success 
```

The cache contains:

```text
1001
```

But the database still contains:

```text
1000
```

Before the cache persists the update:

```text
Cache  crashes !!!
```

The database may remain:

```text
1000
```

The update to `1001` could be lost.

Therefore:

> **Write-Back improves write performance but introduces higher durability and consistency risk.**

<h2>Cache Failure and Recovery</h2>

Consider a large number of pending writes:

```text
1000 writes
    ↓
Cache
    ↓
Database hasn't received them yet
```

If the cache fails before those writes are safely persisted:

```text
1000 updates 
```

Therefore, a production Write-Back architecture may need mechanisms such as:

- Durable queues
- Write-ahead logs or other persistence mechanisms
- Retry mechanisms
- Failure recovery
- Background workers
- Monitoring and alerting

The exact mechanism depends on the system's durability requirements.

<h2>Write-Back Flow with Asynchronous Persistence</h2>

A more realistic HLD design can look like:

```text
                 Application
                      │
                      ▼
                    Cache
                      │
                      ├────────→ Immediate Response ⚡
                      │
                      ▼
                Pending Writes
                      │
                      ▼
              Queue / Worker
                      │
                      ▼
                  Database
```

The important idea is that the application doesn't wait for the final database persistence.

<h2>Write-Back vs Write-Through</h2>

| Feature | Write-Through | Write-Back |
|---|---|---|
| Write goes to cache | Yes | Yes |
| DB updated during write flow | Yes | No |
| DB updated later | No | Yes |
| Write latency | Higher | Lower |
| Database write load | Higher | Potentially lower |
| Data-loss risk | Lower | Higher |
| Consistency complexity | Lower | Higher |
| Failure handling | Simpler | More complex |

<h2>Memory Trick </h2>

```text
Write-Through
→ Cache → DB → Success
```

```text
Write-Back
→ Cache → Success ⚡
          ↓
        DB later
```

Think:

> **Write-Through = write through to DB before success.**

> **Write-Back = write to cache now, write to DB later.**

<h2>Interview Question</h2>

<h3>What is Write-Back Caching?</h3>

A strong HLD answer:

> **"In Write-Back caching, writes are first stored in the cache and the database is updated asynchronously later. This reduces write latency and can reduce immediate database write load, but introduces additional complexity and a risk of losing updates if the cache fails before the data is safely persisted."**

<h2>Quick Revision 🚀</h2>

```text
Write-Through
      │
      ▼
Application
      │
      ▼
    Cache
      │
      ▼
  Database
      │
      ▼
   Success
```

```text
Write-Back
      │
      ▼
Application
      │
      ▼
    Cache
      │
      ▼
   Success ⚡
      │
      │  Later / Async
      ▼
  Database
```

### Core Idea

> **Write-Back = fast writes now, database persistence later.**

> **Main benefit → Lower write latency**

> **Main risk → Higher durability and consistency complexity**