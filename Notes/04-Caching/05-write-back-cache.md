## Cache Invalidation

<h2>What is Cache Invalidation?</h2>

**Cache invalidation** is the process of removing or updating stale data from the cache when the underlying data changes.

The core problem is:

> **What happens when the data in the database changes but the cache still contains the old data?**

Example:

```text
Database:
Product price = ₹1000

Cache:
Product price = ₹1000
```

The database is updated:

```text
Database:
Product price = ₹1200
```

But the cache still contains:

```text
Cache:
Product price = ₹1000 
```

The cache now contains **stale data**.

We need a strategy to remove or update that stale value.

<h2>Why is Cache Invalidation Needed?</h2>

Without invalidation:

```text
Database → New Data
Cache    → Old Data
```

Users may continue seeing outdated information.

Example:

```text
Database → ₹1200
Cache    → ₹1000

User sees → ₹1000 
```

Therefore, caching systems need a strategy to keep cached data reasonably fresh.

<h2>Delete the Cache Entry</h2>

One of the most common approaches is to delete the cached value when the underlying data changes.

Suppose:

```text
Cache:
product:123 → ₹1000
```

The database is updated:

```text
Database:
product:123 → ₹1200
```

Then delete the cache entry:

```text
DELETE product:123 from Cache
```

Now:

```text
Cache:
product:123 
```

The next request becomes a cache miss:

```text
Application
     ↓
Cache → MISS
     ↓
Database → ₹1200
     ↓
Cache → ₹1200
     ↓
Response
```

This approach is commonly used with **Cache-Aside**

<h2>Update the Cache Entry</h2>

Instead of deleting the cached value, we can directly update it.

Before:

```text
Database → ₹1000
Cache    → ₹1000
```

After the update:

```text
Database → ₹1200
Cache    → ₹1200
```

This avoids a cache miss on the next read.

However, coordinating the database and cache can become difficult.

For example:

```text
Database update → Success 
Cache update    → Failed 
```

Now:

```text
Database → ₹1200
Cache    → ₹1000 
```

The cache has become stale again.

<h2>TTL — Time To Live</h2>

**TTL (Time To Live)** specifies how long an item can remain in the cache.

For example:

```text
product:123
Price = ₹1000
TTL = 5 minutes
```

After the TTL expires:

```text
Cache entry → EXPIRED
```

The next request can fetch fresh data:

```text
Cache MISS
    ↓
Database
    ↓
Fresh data
    ↓
Cache
```

TTL is useful when occasional stale data is acceptable.

<h2>TTL Does Not Guarantee Freshness</h2>

This is an important interview point.

Suppose:

```text
TTL = 1 hour
```

The database changes after 5 minutes:

```text
Database → ₹1200
Cache    → ₹1000
```

The cache may continue returning:

```text
₹1000
```

for the remaining 55 minutes.

Therefore:

> **TTL limits how long data can remain cached; it does not guarantee immediate consistency.**

<h2>Event-Based Invalidation</h2>

In larger distributed systems, cache invalidation can be triggered by events.

Example:

```text
User updates profile
        │
        ▼
    Database
        │
        ▼
   Event / Queue
        │
        ▼
 Cache Invalidation
        │
        ▼
 Delete user:101
```

For example, a:

```text
UserUpdated
```

event can trigger:

```text
DELETE user:101 from Cache
```

This can be useful when multiple services depend on the same underlying data.

<h2>Famous Cache Invalidation Quote</h2>

A famous saying in computer science is:

> **"There are only two hard things in Computer Science: cache invalidation and naming things."**

The reason is that keeping cached data synchronized with changing source data can become surprisingly difficult, especially in distributed systems.

<h2>Common Cache-Aside Invalidation Pattern</h2>

A common approach is to update the database first and then invalidate the cache:

<h3>Write Flow</h3>

```text
             WRITE
               │
               ▼
           Database
               │
               ▼
        Delete Cache
```

<h3>Read Flow</h3>

```text
             READ
               │
               ▼
             Cache
            /     \
         HIT       MISS
          │          │
          ▼          ▼
       Return       Database
                       │
                       ▼
                     Cache
                       │
                       ▼
                    Return
```

The database remains the source of truth, while the cache is rebuilt when needed.

<h2>Important Race Condition </h2>

Cache invalidation can become difficult when multiple requests operate concurrently.

Suppose two requests happen almost simultaneously:

```text
Request A → Update DB
Request B → Read data
```

Consider this sequence:

```text
A: Update DB → ₹1200
B: Read old DB value → ₹1000
A: Delete cache
B: Put ₹1000 into cache 
```

Now we have:

```text
Database = ₹1200
Cache    = ₹1000
```

The cache contains stale data again.

This is why distributed systems may require careful handling using techniques such as:

- Correct operation ordering
- Versioning
- Locks where appropriate
- Event-driven approaches
- Conditional updates
- Carefully designed cache-write logic

The exact solution depends on the system's consistency requirements.

For HLD, remember:

> **Cache invalidation is simple conceptually but can become difficult under concurrent and distributed writes.**

<h2>Cache Invalidation Strategies</h2>

```text
Cache Invalidation
       │
       ├── Delete cache entry
       │
       ├── Update cache entry
       │
       ├── TTL expiration
       │
       └── Event-based invalidation
```

Each strategy has different consistency, complexity, and performance characteristics.

<h2>Delete vs Update vs TTL</h2>

| Strategy | Main Idea | Advantage | Challenge |
|---|---|---|---|
| Delete | Remove stale entry | Simple | Causes cache miss |
| Update | Replace cached value | Avoids immediate miss | Coordination can be difficult |
| TTL | Expire after a period | Simple and automatic | Data can remain stale until expiry |
| Event-based | Invalidate from an event | Good for distributed systems | More infrastructure and complexity |

<h2>Interview Question</h2>

<h3>How do you handle stale cache data?</h3>

A strong answer:

> **"We can invalidate the cache when the underlying data changes, update the cached value, or use TTL-based expiration. In distributed systems, event-driven invalidation can also be used. The right approach depends on the required consistency and freshness."**

<h2>Quick Revision</h2>

```text
DB changes
    ↓
Cache may become stale
    ↓
Invalidate / Update Cache
```

The main strategies:

```text
Delete
  ↓
Remove stale entry

Update
  ↓
Replace stale value

TTL
  ↓
Expire automatically

Event
  ↓
Invalidate when data changes
```

### Core Idea

> **Cache invalidation keeps cached data from remaining stale after the underlying data changes.**

### Most Important Interview Point

> **There is no single best invalidation strategy. Choose based on freshness requirements, consistency requirements, workload, and system complexity.**
