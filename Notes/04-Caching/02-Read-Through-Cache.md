## Read-Through Cache

<h2>What is Read-Through Cache?</h2>

The main idea is:

> **The application talks to the cache, and the cache is responsible for fetching data from the database when there is a cache miss.**

The key difference is **who handles the database lookup on a cache miss**.

- **Cache-Aside** → Application handles the database lookup.
- **Read-Through** → Cache layer handles the database lookup.
- 
<h2>Cache-Aside vs Read-Through</h2>

<h3>Cache-Aside</h3>

The **application manages both the cache and database**.

```text
Application
    │
    ├────→ Cache
    │        │
    │       MISS
    │        │
    └────→ Database
```

The application says:

> "Cache doesn't have the data, so I'll get it from the database."

The application is responsible for:

```text
Check Cache
     ↓
Cache Miss
     ↓
Query Database
     ↓
Store in Cache
     ↓
Return Data
```

<h3>Read-Through</h3>

The application only communicates with the cache:

```text
Application
      │
      ▼
    Cache
      │
    MISS
      │
      ▼
  Database
```

The **cache layer itself** handles the database lookup when the requested data is missing.

The application doesn't explicitly query the database for that read.

<h2>How Read-Through Works</h2>

Suppose the application needs:

```text
GET user:101
```

<h3>Cache Hit</h3>

First, the application asks the cache:

```text
Application
     │
     ▼
   Cache
```

If the data exists:

```text
Application
     │
     ▼
   Cache ✅
     │
     ▼
 User 101
```

The cache returns the data.

No database request is required.

<h3>Cache Miss</h3>

If the data doesn't exist:

```text
Application
     │
     ▼
   Cache ❌
     │
     ▼
  Database
```

The **cache layer fetches the data from the database**.

```text
Database
    │
    ▼
  Cache
    │
    ▼
Application
```

The cache can then store the retrieved data for future requests.

<h2>Complete Read-Through Flow</h2>

```text
                  Application
                       │
                       ▼
                  Read-Through
                     Cache
                   /       \
                HIT         MISS
                 │            │
                 │            ▼
                 │         Database
                 │            │
                 │            ▼
                 │          Cache
                 │            │
                 └────────────┘
                       │
                       ▼
                    Response
```

The important flow is:

```text
Application
     ↓
Cache
     ↓
Cache Hit → Return data
     ↓
Cache Miss
     ↓
Database
     ↓
Store in Cache
     ↓
Return data
```

<h2>Main Difference</h2>

This is the most important thing to remember.

<h3>Cache-Aside</h3>

```text
Application
    │
    ├── Cache
    │
    └── Database
```

The **application manages the caching logic**.

On a miss:

```text
Application
     ↓
Cache → MISS
     ↓
Application → Database
     ↓
Application → Cache
```

<h3>Read-Through</h3>

```text
Application
    │
    ▼
  Cache
    │
    ▼
Database
```

The **cache layer manages the database fetching**.

On a miss:

```text
Application
     ↓
Cache → MISS
     ↓
Cache → Database
     ↓
Cache → Application
```

<h2>Real Example</h2>

Suppose an application needs:

```text
Product 123
```

<h3>Cache-Aside</h3>

```text
Application
     │
     ▼
Cache → MISS
     │
     ▼
Application → Database
     │
     ▼
Application → Cache
     │
     ▼
Response
```

The application explicitly handles the entire process.

<h3>Read-Through</h3>

```text
Application
     │
     ▼
Cache → MISS
     │
     ▼
Cache → Database
     │
     ▼
Cache → Application
     │
     ▼
Response
```

The cache layer handles the database lookup and cache population.

<h2>Why Use Read-Through?</h2>

Read-through caching can simplify application code.

Without a read-through layer, applications may repeatedly implement:

```text
Check Cache
     ↓
If Miss
     ↓
Query Database
     ↓
Store in Cache
     ↓
Return Data
```

With read-through:

```text
Application
     ↓
Cache
     ↓
Data
```

The caching layer handles the miss behavior.

This can be useful when multiple applications or services need consistent caching behavior.

<h2>Advantages</h2>

- Simplifies application-side read logic.
- Centralizes cache-miss handling.
- Reduces repeated cache/database access logic across services.
- Provides a consistent caching pattern for applications using the same caching layer.

<h2>Important Point About Redis ⚠️</h2>

Do **not** assume that Redis automatically provides read-through caching.

Redis is primarily a **data store/cache technology**.

Read-through behavior generally requires an appropriate:

- Caching library
- Framework
- Cache abstraction
- Custom caching layer
- Application architecture

For HLD interviews, remember the **pattern**, rather than saying:

> "Redis automatically does read-through caching."

<h2>Cache-Aside vs Read-Through</h2>

| Feature | Cache-Aside | Read-Through |
|---|---|---|
| Application talks to cache | Yes | Yes |
| Application directly talks to DB for cache miss | Yes | No |
| Cache handles DB lookup | No | Yes |
| Cache population on miss | Application | Cache layer |
| Application complexity | Higher | Lower |
| Main idea | Application manages cache | Cache manages cache miss |

<h2>Interview Question</h2>

<h3>What's the difference between Cache-Aside and Read-Through?</h3>

A strong answer:

> **"In Cache-Aside, the application directly manages both the cache and database. On a cache miss, the application fetches the data from the database and populates the cache. In Read-Through, the application communicates with the cache, and the caching layer handles fetching data from the database on a cache miss."**

<h2>Quick Revision </h2>

```text
CACHE-ASIDE

Application
   │
   ├──→ Cache
   │      │
   │     MISS
   │      │
   └──→ Database
```

**Application handles the miss.**

```text
READ-THROUGH

Application
      │
      ▼
    Cache
      │
     MISS
      │
      ▼
  Database
```

**Cache layer handles the miss.**

### Memory Trick

> **Cache-Aside → Application goes aside to the DB.**

> **Read-Through → Cache reads through to the DB.**
