## Cache Stampede / Thundering Herd

<h2>What is it?</h2>

When a popular cache entry **expires**, many requests may arrive at the same time and all try to fetch the data from the database.

```text
Cache expires
     ↓
1000 requests
     ↓
All go to Database 
     ↓
Database overloaded
```

This is called a **Cache Stampede** or **Thundering Herd**.

<h2>How to Prevent It?</h2>

- **Locking** → Only one request fetches from the database.
- **TTL Jitter** → Give entries slightly different expiration times.
- **Refresh Before Expiry** → Refresh popular data before it expires.

<h2>Key Point</h2>

> **Cache expires + many requests arrive together = Cache Stampede.**

<h2>Remember</h2>

```text
Cache expires
     ↓
Many requests
     ↓
Database overload
```