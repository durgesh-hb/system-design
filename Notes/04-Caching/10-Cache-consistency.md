## Cache Consistency

<h2>What is it?</h2>

**Cache consistency means keeping cache data reasonably in sync with the database.**

Example:

```text
Database → ₹500
Cache    → ₹400 wrong!!
```

Cache has **stale data**.

<h2>How to Maintain It?</h2>

- **Update cache** when DB changes.
- **Delete cache** when DB changes.
- **Use TTL** to expire old data.

```text
DB changes
    ↓
Update / Delete Cache
    ↓
Fresh data
```

<h2>Key Point</h2>

> **Cache consistency = preventing the cache from serving outdated data beyond what the system allows.**

<h2>Remember</h2>

```text
DB changes
    ↓
Cache should eventually reflect the change
```