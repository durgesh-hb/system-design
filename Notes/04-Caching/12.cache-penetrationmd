## Cache Penetration

<h2>What is it?</h2>

**Cache Penetration happens when requests for non-existent data repeatedly reach the database.**

```text
User
 ↓
Cache MISS
 ↓
Database → No data 
 ↓
Every request repeats
```

<h2>Example</h2>

```text
Request: userId = 99999

Cache → MISS
DB → User doesn't exist
```

If many users request `99999`, the DB gets unnecessary load.

<h2>How to Prevent It?</h2>

- **Cache null results** → temporarily cache "not found".
- **Bloom Filter** → check whether data probably exists before querying DB.

<h2>Key Point</h2>

> **Cache Penetration = requests for non-existent data repeatedly reaching the database.**

<h2>Remember</h2>

```text
Non-existent data
       ↓
Cache MISS
       ↓
Database repeatedly
       ↓
Unnecessary DB load
```