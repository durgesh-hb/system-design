## Cache Breakdown

<h2>What is it?</h2>

**Cache Breakdown happens when a very popular cache entry expires and many requests hit the database at the same time.**

```text
Popular data expires
        ↓
Many requests
        ↓
Cache MISS
        ↓
Database gets heavy load 
```

<h2>Example</h2>

A celebrity profile is requested by thousands of users.

```text
Cache expires
     ↓
Thousands of requests
     ↓
All go to DB 
```

<h2>Key Point</h2>

> **Cache Breakdown = popular data expires → many requests hit the DB at once.**

<h2>Remember</h2>

```text
Popular Cache Expires
        ↓
Many Requests
        ↓
Cache MISS
        ↓
DB Overload
```


### Cache Penetration

**Data does NOT exist.**

```text
Request user 999
      ↓
Cache MISS
      ↓
DB → User doesn't exist 
```

Repeated invalid requests keep hitting the DB.

### Cache Breakdown

 **Data DOES exist, but its popular cache entry expires.**

```text
Popular user data
      ↓
Cache expires
      ↓
Many users request it
      ↓
DB gets many requests 
```

### Easy Difference 

| | Cache Penetration | Cache Breakdown |
|---|---|---|
| Data exists? |  No |  Yes |
| Main problem | Non-existent data | Popular data expires |
| DB requests | Repeated invalid requests | Many requests at once |

**Remember:**

> **Penetration → Data doesn't exist.**  
> **Breakdown → Data exists, but popular cache expired.**