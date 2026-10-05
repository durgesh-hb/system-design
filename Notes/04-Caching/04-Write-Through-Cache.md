## Write-Through Cache

<h2>What is Write-Through Cache?</h2>

The main idea is:

> **When the application writes data, the write goes through the cache, and the underlying database is updated as part of the same write flow before the operation is considered complete.**

A simple architecture:

```text
Application
     │
     ▼
   Cache
     │
     ▼
  Database
```

The cache participates in the write path and ensures that the database is updated as part of the write operation.

<h2>Example</h2>

Suppose we have:

```text
User 101
Name = Durgesh
```

We want to change it to:

```text
Durgesh → Eren
```

The application sends:

```text
UPDATE user:101
Name = Eren
```

The write flow is:

```text
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

The cache layer passes the write to the database and participates in maintaining the cached value.

Once the required database write succeeds, the application receives success.

<h2>Why is it Called Write-Through?</h2>

The name comes from the fact that the write **passes through the cache** to the database.

```text
Write
  │
  ▼
Cache
  │
  ▼
Database
```

The cache is not merely storing data for reads; it participates directly in the write path.

<h2>What Happens to the Cache?</h2>

Suppose the database successfully stores:

```text
Name = Eren
```

The cache can also contain:

```text
user:101 → Eren
```

After the successful write:

```text
Cache:
Eren

Database:
Eren
```

This means a subsequent cache read can return the updated value instead of an old cached value.

<h2>Write-Through vs Cache-Aside</h2>

The write behavior is different between the two patterns.

<h3>Cache-Aside Write</h3>

A common cache-aside approach is:

```text
Application
     │
     ▼
 Database
     │
     ▼
Invalidate / Update Cache
```

Then, on the next read:

```text
Application
     │
     ▼
 Cache → MISS
     │
     ▼
 Database
     │
     ▼
 Cache
     │
     ▼
Response
```

The **application manages the database and cache separately**.

<h3>Write-Through</h3>

```text
Application
     │
     ▼
   Cache
     │
     ▼
 Database
```

The **cache participates directly in the write flow**.

<h2>Read-Through + Write-Through</h2>

Read-through and write-through can be combined so that the cache layer handles much of the data-access behavior.

```text
                 Application
                      │
                      ▼
                    Cache
                  /       \
                READ      WRITE
                 │          │
                 ▼          ▼
             Database    Database
```

<h3>Read Flow</h3>

```text
Application
     ↓
Cache
     ↓
MISS
     ↓
Database
     ↓
Cache
     ↓
Application
```

The cache fetches the data from the database on a miss.

<h3>Write Flow</h3>

```text
Application
     ↓
Cache
     ↓
Database
     ↓
Success
```

The cache participates in updating the database before the write is considered complete.

<h2>Advantages</h2>

<h3>Cache Stays Relatively Fresh</h3>

Because writes go through the cache and the database as part of the write flow, the cache can be updated with the new value.

```text
Write
  ↓
Cache
  ↓
Database
```

This reduces the chance of immediately reading an old value from the cache.

<h3>Simpler Application Code</h3>

The application doesn't need to manually coordinate every cache update or invalidation if the caching layer handles that responsibility.

<h3>Useful for Frequently Read Data</h3>

After a successful write, the updated value can already be available in the cache for subsequent reads.

<h2>Disadvantages</h2>

<h3>Higher Write Latency</h3>

The write may need to follow:

```text
Application
     ↓
Cache
     ↓
Database
     ↓
Success
```

before the operation is considered complete.

Therefore, write-through can add latency compared with approaches where cache and database updates are decoupled.

<h2>Failure Scenario </h2>

Suppose:

```text
Application
     ↓
Cache
     ↓
Database ❌
```

The database write fails.

The system must **not simply report success**.

The caching layer needs to handle the failure correctly so that the cache does not permanently contain data that was never successfully persisted to the database.

This is one reason caching becomes more complex in distributed systems.

<h2>Write-Through vs Write-Back</h2>

Write-back caching is different because the database update happens later.

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

The database is updated as part of the write flow.

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

The database update happens later, usually asynchronously.

| Feature | Write-Through | Write-Back |
|---|---|---|
| Database update | During write flow | Later |
| Write latency | Higher | Lower |
| Cache freshness | Easier to maintain | More complex |
| Data-loss risk | Lower | Higher if cache data is lost before persistence |
| Complexity | Lower | Higher |

The simple memory rule is:

```text
Write-Through → Safer/fresher, potentially slower writes

Write-Back    → Faster writes, more risk/complexity
```

<h2>Important HLD Point</h2>

Write-through does **not** mean the cache and database are magically transactionally consistent in every implementation.

The exact consistency and failure guarantees depend on the caching layer, database, retry behavior, failure handling, and overall architecture.

For HLD, the key idea is:

> **The database update happens as part of the write-through path rather than being deferred for later.**

<h2>Interview Question</h2>

<h3>What is Write-Through Caching?</h3>

A strong answer:

> **"In a write-through cache, write operations go through the cache, and the cache synchronously or as part of the write flow updates the underlying database before the write is considered complete. This helps keep the cache and database relatively synchronized, but can increase write latency."**

<h2>Quick Revision </h2>

```text
Cache-Aside
→ Application manages Cache + Database

Read-Through
→ Cache handles Database read on miss

Write-Through
→ Cache participates in Database write

Write-Back
→ Cache accepts write first
→ Database updated later
```

### Memory Trick

```text
READ-THROUGH
Application → Cache → DB on MISS

WRITE-THROUGH
Application → Cache → DB during WRITE

WRITE-BACK
Application → Cache → SUCCESS
                    ↓
                  Later
                    ↓
                    DB
```

> **Write-Through = write through the cache to the database before completing the write.**
