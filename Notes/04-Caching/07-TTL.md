## TTL (Time To Live)

<h2>What is TTL?</h2>

**TTL = Time To Live**

TTL tells the cache:

> **"Keep this data for this amount of time, then expire it."**

Example:

```text
product:123 → ₹1000
TTL = 5 minutes
```

The cache keeps the entry valid for 5 minutes.

After the TTL expires:

```text
product:123 → EXPIRED 
```

The entry is no longer considered valid.

<h2>Why Do We Need TTL?</h2>

Suppose we cache:

```text
Product price = ₹1000
```

Later, the database changes:

```text
Database = ₹1200
Cache    = ₹1000 
```

Without expiration, the old value could remain in the cache for a long time.

With TTL:

```text
Cache
₹1000
  │
  │ 5 minutes
  ▼
Expired 
```

The next request can fetch fresh data:

```text
Cache MISS
   ↓
Database
   ↓
₹1200
   ↓
Cache
```

Therefore:

> **TTL helps prevent cached data from remaining stale indefinitely.**

<h2>TTL vs Manual Invalidation</h2>

<h3>Manual Invalidation</h3>

The application explicitly removes the cache entry when the underlying data changes.

```text
DB updated
   ↓
DELETE cache
```

The stale entry is removed immediately.

<h3>TTL</h3>

The cache entry automatically becomes invalid after the configured time.

```text
Store data
   ↓
TTL starts
   ↓
Time expires
   ↓
Cache entry expires
```

<h3>Using Both</h3>

A system can use both approaches.

For example:

```text
DB update
   ↓
Delete cache immediately
```

And additionally:

```text
If invalidation somehow misses the entry
   ↓
TTL eventually expires it
```

This can provide an additional safety mechanism, depending on the architecture.

<h2>Choosing a TTL</h2>

TTL depends mainly on:

- How frequently the data changes
- How fresh the data needs to be
- How much database load the system can handle
- How much stale data the system can tolerate

<h3>Frequently Changing Data</h3>

Use a shorter TTL when freshness is important:

```text
Stock price → seconds
Live information → seconds/minutes
```

<h3>Slowly Changing Data</h3>

A longer TTL may be appropriate:

```text
Product information → minutes/hours
Configuration → hours
```

<h3>Almost Static Data</h3>

Very long TTLs or explicit invalidation may work:

```text
Country list
Static configuration
```

There is **no universal TTL value**.

<h2>Short TTL vs Long TTL</h2>

<h3>Short TTL</h3>

Example:

```text
TTL = 10 seconds
```

Advantages:

- Fresher data
- Lower chance of stale values

Disadvantages:

- More cache misses
- More database requests
- Higher database load

```text
Freshness ↑
DB load ↑
```

<h3>Long TTL</h3>

Example:

```text
TTL = 1 hour
```

Advantages:

- More cache hits
- Lower database load
- Lower repeated database access

Disadvantages:

- Data can remain stale for longer

```text
DB load ↓
Staleness risk ↑
```

This is an important HLD trade-off.

<h2>TTL and Cache Hit Rate</h2>

Suppose there are:

```text
1000 requests
```

With a suitable TTL:

```text
900 → Cache HIT
100 → Cache MISS
```

Cache hit rate:

```text
900 / 1000 = 90%
```

If the TTL is too short:

```text
600 → Cache HIT
400 → Cache MISS
```

The database receives more requests.

Therefore, TTL can affect:

- Data freshness
- Cache hit rate
- Database load
- Response latency

<h2>TTL Does Not Mean Exact Physical Deletion</h2>

At HLD level, think of TTL as an **expiration policy**.

Depending on the caching system, an expired entry may be:

- Removed immediately
- Removed lazily when accessed
- Cleaned up by a background process

Therefore, don't assume:

> "At exactly 60.000 seconds, a background process must physically delete the entry."

The important concept is:

```text
TTL expires
    ↓
Entry is no longer valid
```
<h2>TTL and Stale Data</h2>

TTL reduces the maximum time stale data can normally remain valid, but it does **not** guarantee immediate freshness.

For example:

```text
TTL = 1 hour
```

Database changes after 5 minutes:

```text
Database → ₹1200
Cache    → ₹1000
```

The cache may still serve:

```text
₹1000
```

until the entry expires.

Therefore:

> **TTL is an expiration mechanism, not an immediate cache invalidation mechanism.**

If immediate freshness is required, explicit invalidation or update mechanisms may also be needed.

<h2>Interview Question</h2>

<h3>Why do we use TTL in caching?</h3>

A strong answer:

> **"TTL automatically expires cached data after a configured period. It helps prevent stale data from remaining in the cache indefinitely and provides a balance between data freshness, cache hit rate, and database load."**

<h2>Quick Revision </h2>

```text
TTL
 ↓
Time To Live
 ↓
How long a cache entry remains valid
```

<h3>Short TTL</h3>

```text
Short TTL
    ↓
Fresher data
    ↓
More cache misses
    ↓
More DB load
```

<h3>Long TTL</h3>

```text
Long TTL
    ↓
More cache hits
    ↓
Less DB load
    ↓
Greater staleness risk
```

### Core Idea

> **TTL controls how long cached data remains valid.**

### HLD Trade-off

```text
Short TTL
→ Freshness ↑
→ Cache Hit Rate ↓
→ DB Load ↑

Long TTL
→ Freshness ↓
→ Cache Hit Rate ↑
→ DB Load ↓
```