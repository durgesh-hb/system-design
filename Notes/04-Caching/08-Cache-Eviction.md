## Cache Eviction

<h2>What is Cache Eviction?</h2>

Cache memory is limited.

Suppose our cache can store only:

```text
3 items
```

Currently:

```text
Cache
├── A
├── B
└── C
```

Now we want to store:

```text
D
```

There isn't enough space.

The cache must decide:

> **"Which existing item should I remove?"**

This decision is made using an **eviction policy**.

<h2>LRU — Least Recently Used</h2>

**LRU** removes the item that has **not been accessed for the longest time**.

Suppose:

```text
Cache capacity = 3

A
B
C
```

Access order:

```text
A → B → C → A
```

Now:

```text
A = Most recently used
C = Older
B = Least recently used
```

If we insert `D`:

```text
D comes in

B removed
```

Result:

```text
A
C
D
```

> **LRU = Remove what I used least recently.**

<h2>LFU — Least Frequently Used</h2>

**LFU** looks at **how many times each item has been accessed**.

Example:

```text
A → 100 accesses
B → 20 accesses
C → 2 accesses
```

If the cache needs space:

```text
C  removed
```

because C has been accessed the least number of times.

> **LFU = Remove what I use least often.**

<h3>Important Difference</h3>

LFU focuses on **frequency**, not how recently the item was accessed.

In a real LFU implementation, ties between equally frequent entries need a tie-breaking rule, often recency.

<h2>FIFO — First In, First Out</h2>

**FIFO** removes the item that entered the cache first.

Suppose:

```text
A → first
B → second
C → third
```

A new item arrives:

```text
D
```

The cache removes:

```text
A
```
because A entered first.

Result:

```text
B
C
D
```
<h3>Memory Trick</h3>

> **FIFO = First item in → first item out.**

<h2>LRU vs LFU</h2>

This distinction is important.

Suppose:

```text
A → accessed 100 times yesterday
B → accessed 5 times today
```

<h3>LFU</h3>

A may stay because:

```text
A = 100 accesses
B = 5 accesses
```

LFU asks:

> **"How frequently was this item used?"**

<h3>LRU</h3>

A may be removed if it hasn't been accessed recently.

LRU asks:

> **"When was this item last used?"**

Therefore:

```text
LRU → When was it used?

LFU → How often was it used?
```

<h2>TTL vs Eviction</h2>

These concepts are often confused, but they solve different problems.

<h3>TTL</h3>

TTL answers:

> **"How long can this item remain valid?"**

Example:

```text
TTL = 10 minutes
```

After the TTL expires:

```text
Entry → Expired
```

TTL is primarily about **expiration and freshness**.

<h3>Eviction</h3>

Eviction answers:

> **"Which item should we remove when the cache needs space?"**

Example:

```text
Cache Full
    ↓
Eviction Policy
    ↓
LRU
    ↓
Remove least recently used item
```

Eviction is primarily about **managing limited cache capacity**.

<h2>Can TTL and LRU Work Together?</h2>

Absolutely.

For example:

```text
Cache capacity = 1 GB
TTL = 30 minutes
Eviction = LRU
```

An entry can disappear because:

```text
TTL expires
       OR
Cache becomes full
       ↓
LRU chooses an entry to remove
```

Therefore:

> **TTL controls expiration; eviction controls what gets removed when cache resources are limited.**

<h2>Which Policies Should I Know for Interviews?</h2>

For HLD interviews, know these three:

```text
LRU ⭐⭐⭐
LFU ⭐⭐
FIFO ⭐
```

<h3>LRU</h3>

Most commonly discussed for general-purpose caching.

> Recently accessed data is often more likely to be accessed again.

<h3>LFU</h3>

Useful when access frequency is more important than recency.

<h3>FIFO</h3>

Simple and easy to implement, but it does not consider whether an item is frequently or recently accessed.

> **Don't claim LRU is always the best policy. The appropriate eviction policy depends on the workload.**

<h2>Eviction Example</h2>

Suppose:

```text
Cache Capacity = 3
```

Current cache:

```text
A
B
C
```

A new item arrives:

```text
D
```

The eviction policy decides what happens:

```text
LRU
→ Remove least recently used item

LFU
→ Remove least frequently used item

FIFO
→ Remove item that entered first
```

Then:

```text
A / B / C
     ↓
Eviction Policy
     ↓
Remove one
     ↓
D inserted
```

<h2>Interview Questions</h2>

<h3>What is LRU?</h3>

> **"LRU, or Least Recently Used, is a cache eviction policy that removes the item that has not been accessed for the longest time when the cache needs space."**

<h3>What is LFU?</h3>

> **"LFU, or Least Frequently Used, removes the cache entry with the lowest access frequency when the cache needs space."**

<h3>What is FIFO?</h3>

> **"FIFO, or First In, First Out, removes the cache entry that was inserted first."**

<h3>What is the difference between TTL and eviction?</h3>

> **"TTL determines how long cached data remains valid, while an eviction policy determines which entries should be removed when the cache needs to free space."**

<h2>Quick Revision </h2>

```text
Cache Full
    ↓
Eviction Policy
    │
    ├── LRU → Least Recently Used
    │
    ├── LFU → Least Frequently Used
    │
    └── FIFO → First In, First Out
```

And remember:

```text
TTL
 ↓
"How long is this entry valid?"
```

```text
Eviction
 ↓
"Which entry should I remove?"
```

<h3>Memory Trick</h3>

```text
LRU → Least Recent
LFU → Least Frequent
FIFO → First In
TTL → Time To Live
```

### Core Idea

> **TTL decides when an entry expires. Eviction decides which entry to remove when cache capacity is limited.**