


```text
100,000 requests
       ↓
   Database 
```

The database can become overloaded.

<h2>Adding a Cache</h2>

For example:

```text
GET /product/123
```

<h3>First Request</h3>

```text
Application
    ↓
Cache ❌
    ↓
Database
    ↓
Product 123
    ↓
Cache
    ↓
User
```
The application retrieves the data from the database and stores it in the cache.

<h3>Next Request</h3>

```text
Application
    ↓
Cache ✅
    ↓
User
```

The database doesn't need to be contacted for that request.

<h2>Why is Cache Faster?</h2>

A cache is typically designed for **very fast access** and often keeps frequently accessed data in memory.

```text
             Speed

Database      slow
Cache         fast
```

For example:

```text
Without Cache
100 requests → 100 DB queries

With Cache
100 requests → 1 DB query
               + 99 cache hits
```

This can significantly reduce:

- Database load
- Response latency
- Repeated database queries

<h2>Cache Hit vs Cache Miss</h2>

These are two important caching terms.

<h3>Cache Hit </h3>

A **cache hit** occurs when the requested data already exists in the cache.

```text
Request
   ↓
Cache
   ↓
Found 
   ↓
Response
```

The application can return the cached data directly.

<h3>Cache Miss </h3>

A **cache miss** occurs when the requested data is not available in the cache.

```text
Request
   ↓
Cache
   ↓
Not Found 
   ↓
Database
   ↓
Store in Cache
   ↓
Response
```

The next request can potentially be served from the cache.

<h2>Simple Real-World Example</h2>

Imagine an online store with a popular product:

```text
iPhone
Price: ₹70,000
Name: iPhone XYZ
```

Thousands of users request the same product.

<h3>Without Cache</h3>

```text
100,000 users
      ↓
Application
      ↓
Database 🔥
```

The database receives a large number of repeated requests.

<h3>With Cache</h3>

```text
100,000 users
      ↓
Application
      ↓
   Cache ⚡
      ↓
Product data
```

Only when the cache doesn't contain the required data does the application need to query the database.

<h2>Where Does Cache Sit?</h2>

A typical architecture looks like:

```text
Client
  ↓
Load Balancer
  ↓
Application Server
  ↓
Cache
  ↓
Database
```

A commonly used caching technology is **Redis**.

> **Redis is a technology used for caching; caching itself is an architectural concept.**

Other caching technologies exist as well. The important HLD concept is understanding **why and where caching is used**, not just memorizing Redis.

<h2>Why Not Store Everything in Cache?</h2>

Caching everything is usually not practical.

A cache typically has:

- Limited memory
- Additional cost
- Temporary data storage
- Expiration policies
- Eviction policies

Most importantly, cached data can become **stale**.

<h3>Example of Stale Data</h3>

Suppose:

```text
Database:
Price = ₹70,000

Cache:
Price = ₹65,000
```

If the price changes in the database but the cache still contains the old value, users may see outdated information.

This leads to one of the biggest challenges in caching:

> **Cache invalidation**

Cache invalidation determines **when cached data should be removed or updated**.

<h2>What Should We Cache?</h2>

Good candidates for caching are usually data that is frequently accessed, expensive to retrieve or calculate, and relatively stable.

<h3>Frequently Read Data</h3>

Examples:

```text
Popular products
User profiles
News articles
```

<h3>Expensive to Calculate</h3>

Examples:

```text
Complex queries
Recommendations
Aggregated results
```

<h3>Relatively Stable Data</h3>

Data that doesn't change frequently is often easier to cache.

If data changes extremely frequently, caching it can become more complicated because the system must deal with stale values and invalidation.

<h2>Core Caching Flow</h2>

The fundamental caching pattern is:

```text
             REQUEST
                │
                ▼
             CACHE
            /      \
          HIT      MISS
          │          │
          ▼          ▼
       Response    DATABASE
                      │
                      ▼
                    CACHE
                      │
                      ▼
                   Response
```

The basic idea is:

> **Check the cache first. If the data exists, return it. If not, fetch it from the database, store it in the cache, and return it.**


<h2>Key Takeaways</h2>

- **Cache** = fast temporary storage for frequently accessed data.
- Cache reduces repeated database access.
- **Cache hit** = data found in cache.
- **Cache miss** = data not found in cache.
- Caching can reduce database load and improve latency.
- **Redis** is a popular caching technology.
- Don't cache everything because memory is limited and cached data can become stale.
- Good cache candidates are frequently accessed, expensive, and relatively stable data.
- **Cache invalidation** is one of the major challenges in caching.
- Caching is an **architectural concept**, while Redis is one technology used to implement it.
