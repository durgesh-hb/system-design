Read-Through Cache

The main idea:

The application talks to the cache, and the cache is responsible for fetching data from the database when there is a cache miss.

That's the key difference.

1. Cache-Aside vs Read-Through
Cache-Aside

The application manages both cache and database:

Application
    │
    ├────→ Cache
    │        │
    │       MISS
    │        │
    └────→ Database

Application says:

"Cache doesn't have it. I'll get it from DB."

Read-Through

The application only asks the cache:

Application
      │
      ▼
    Cache
      │
    MISS
      │
      ▼
  Database

The cache layer itself handles the database lookup.

2. How Read-Through Works

Suppose:

GET user:101
Step 1

Application asks cache:

Application
     │
     ▼
   Cache
Step 2 — Cache Hit

If data exists:

Application
     │
     ▼
   Cache ✅
     │
     ▼
 User 101

Done.

Step 3 — Cache Miss

If data doesn't exist:

Application
     │
     ▼
   Cache ❌
     │
     ▼
  Database

The cache automatically gets the data from the database.

Database
    │
    ▼
  Cache
    │
    ▼
Application

The application doesn't have to explicitly perform the database lookup.

3. Complete Diagram
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
4. Main Difference

This is the part you should remember:

CACHE-ASIDE

Application
    │
    ├── Cache
    │
    └── Database

Application manages the logic.

READ-THROUGH

Application
    │
    ▼
  Cache
    │
    ▼
Database

Cache manages the database fetching on a miss.

5. Real Example

Imagine an application needs:

Product 123
Cache-Aside
Application
     │
     ▼
Cache → MISS
     │
     ▼
Application → DB
     │
     ▼
Application → Cache

The application explicitly handles everything.

Read-Through
Application
     │
     ▼
Cache → MISS
     │
     ▼
Cache → DB
     │
     ▼
Cache → Application

The cache layer handles the fetching.

6. Why Use Read-Through?

It can simplify application code.

Instead of every application implementing:

check cache
if miss
query DB
store in cache
return

the cache layer provides that behavior.

This is particularly useful when many applications/services need the same caching behavior.

7. One Important Point ⚠️

Read-through caching is not something you should assume every Redis setup automatically provides.

Redis itself is primarily a data store/cache technology.

Read-through behavior generally requires an appropriate caching layer/library/framework or application architecture around it.

For HLD, remember the pattern, not that "Redis automatically does read-through."

🧠 Interview Question
Q: What's the difference between Cache-Aside and Read-Through?

A good answer:

"In Cache-Aside, the application directly manages both the cache and database. On a cache miss, the application fetches the data from the database and populates the cache. In Read-Through, the application communicates with the cache, and the caching layer handles fetching data from the database on a cache miss."

🔥 That's the important distinction.