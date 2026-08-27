## Database Indexing

Database Indexing is a very important System Design concept.

The main question we're answering is:

> **How does a database find data quickly when a table has millions or billions of rows?**

<h2>The Problem</h2>

Imagine we have a `users` table with **10 million users**.

```text
users

id     name       email
1      Rahul      ...
2      Eren       ...
3      John       ...
...
10,000,000
```

Now we run:

```sql
SELECT * FROM users
WHERE email = 'eren@gmail.com';
```

How does the database find that user?

<h2>Without an Index</h2>

Without an index, the database may need to check rows one by one:

```text
Row 1     No
Row 2     No
Row 3     No
Row 4     No
...
Row 8,472,391   Yes
```

This is called a **full table scan**.

For a huge table, this can be expensive.

Conceptually:

```text
10 million rows
      ↓
Check many rows
      ↓
Find matching row
```

<h2>What is an Index?</h2>

> **Definition : An index is an additional data structure that helps the database find rows faster without scanning the entire table.**

Think of a **book**.

Suppose you want to find "Database Indexing" in a 1,000-page book.

Without an index:

```text
Page 1
Page 2
Page 3
...
Page 1000
```

You might have to search page by page.

With the book's index:

```text
Database Indexing → Page 527
```

You can jump directly near the relevant page.

A database index works on the same basic idea.

<h2>Simple Database Example</h2>

Without index:

```text
Users Table
    ↓
Scan rows
    ↓
Find email
```

With index:

```text
Email Index
     ↓
Find email
     ↓
Locate row
     ↓
Users Table
```

So:

```text
Query
  ↓
Index
  ↓
Row Location
  ↓
Actual Data
```

<h2>Example</h2>

Suppose we create:

```sql
CREATE INDEX idx_users_email
ON users(email);
```

Now:

```sql
SELECT *
FROM users
WHERE email = 'eren@gmail.com';
```

The database can use the index to locate the matching row much more efficiently.

<h2>What Does the Index Actually Store?</h2>

At a high level, think:

```text
Index

Email                 → Row Location
-------------------------------------
a@gmail.com           → Row 10
b@gmail.com           → Row 25
eren@gmail.com        → Row 847
john@gmail.com        → Row 1200
```

The database uses this structure to locate the relevant data.

The exact implementation depends on the database and index type.

<h2>B-Tree / B+ Tree</h2>

You will often hear:

> **"Database indexes use B-Trees or B+ Trees."**

Don't worry about the implementation yet.

The important idea is:

> **They keep indexed values organized so the database can search efficiently instead of checking every row.**

Conceptually:

```text
              Root
             /    \
           /        \
         /            \
      Values        Values
      /   \          /   \
    ...   ...      ...   ...
```

This allows the database to narrow down where the desired value is.

<h2>Why Not Create an Index on Every Column?</h2>

Very important.

You might think:

> "If indexes make queries faster, I'll index everything!"

Bad idea.

Indexes have costs.

<h2>Indexes Consume Storage</h2>

The database needs to store the index separately.

```text
Database
│
├── Table Data
│
└── Indexes
```

More indexes = more storage.

<h2>Indexes Slow Down Writes</h2>

Suppose you insert a new user:

```sql
INSERT INTO users ...
```

The database needs to:

- Add the row to the table
- Update every relevant index

So:

```text
INSERT
  ↓
Table Update
  ↓
Index Update
```

More indexes mean more work during:

- INSERT
- UPDATE
- DELETE

So there is a trade-off:

> **Indexes improve reads but add storage and write overhead.**

<h2>The Main Trade-Off</h2>

```text
             Index
            /     \
           /       \
       Faster      More
        Reads      Write Cost
                   Storage
```

This is a very important System Design concept.

<h2>When Should We Create an Index?</h2>

Usually when a column is frequently used for:

- Searching
- Filtering
- Joining
- Sorting

For example:

```sql
WHERE email = ?
```

If your application frequently searches users by email, an index on `email` can be useful.

<h2>Example: E-Commerce</h2>

Suppose you frequently query:

```sql
SELECT *
FROM orders
WHERE user_id = 123;
```

An index on:

```text
user_id
```

can make those lookups much faster.

<h2>Composite Index</h2>

Sometimes queries use multiple columns.

Example:

```sql
SELECT *
FROM users
WHERE city = 'Mysore'
AND age = 22;
```

You could potentially use a **composite index** involving:

```text
(city, age)
```

This is called a **composite index**.

<h2>Index Order Matters</h2>

For a composite index:

```text
(city, age)
```

the order of columns matters.

It can efficiently support queries that use the leading column(s), depending on the database's query planner and index rules.

You don't need to memorize all the edge cases right now.

Just remember:

> **Composite index = index containing multiple columns, and column order matters.**

<h2>Index vs Primary Key</h2>

Most relational databases automatically create an index associated with a primary key.

Example:

```sql
PRIMARY KEY (id)
```

The database can efficiently locate rows by `id`.

But **not every indexed column is a primary key**.

You can have:

```text
Primary Key:
id

Other Indexes:
email
username
created_at
```

<h2>System Design Example</h2>

Imagine Instagram has:

```text
1 Billion users
```

And you frequently search:

```text
username = "eren"
```

Without an index:

```text
1 Billion rows
       ↓
Potentially huge scan
```

With an index:

```text
username index
       ↓
Find "eren"
       ↓
User row
```

This is one of the basic techniques that makes large databases practical.

<h2>Important Warning: Index Doesn't Guarantee Fast Queries</h2>

This is an important nuance.

Creating an index doesn't automatically mean the database will use it.

The database's **query planner/optimizer** decides whether an index is beneficial.

For example, if a query returns a very large percentage of the table, scanning the table may sometimes be cheaper than using the index.

So:

> **Index = tool for improving query performance, not a guarantee.**

<h2>Memory Trick</h2>

Think about a textbook.

<h3>No Index</h3>

```text
Search every page
```

<h3>Index</h3>

```text
Topic
  ↓
Page number
  ↓
Go directly there
```

That's essentially what a database index does.


<h2>Quick Revision</h2>

```text
Query
  │
  ▼
Index
  │
  ▼
Find Row
  │
  ▼
Return Data
```

<h3>Without Index</h3>

```text
Query
  ↓
Scan many/all rows
  ↓
Find Data
```

<h3>With Index</h3>

```text
Query
  ↓
Index
  ↓
Locate Data
```

<h3>Trade-off</h3>

```text
Indexes
  │
  ├── Faster Reads
  ├── More Storage
  └── Slower Writes
```

<h2>What You Need to Remember</h2>

If an interviewer asks:

> **"How would you improve a slow database query?"**

A strong answer:

> "First I'd inspect the query and execution plan. If the query frequently filters, joins, or sorts on certain columns, an appropriate index may improve performance. But I'd also consider the storage and write overhead of adding that index."

That's much stronger than simply saying **"add an index."**
