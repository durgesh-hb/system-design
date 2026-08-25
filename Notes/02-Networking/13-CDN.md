## CDN

CDN is an important component in System Design that helps deliver content to users from locations that are geographically closer to them.

<h2>What is a CDN?</h2>

> **Definition : CDN (Content Delivery Network) is a distributed network of servers that delivers content to users from a location geographically closer to them.**

Simply:

> **CDN = Store content closer to users so it loads faster.**

<h2>What's the Problem Without a CDN?</h2>

Imagine your main server is in the USA.

```text
User India
    │
    │ Long distance
    ▼
Server USA
```

A user in India has to travel all the way to the USA to get:

- Images
- Videos
- CSS
- JavaScript
- Files

This increases **latency**.

<h2>CDN Solution</h2>

A CDN has servers distributed around the world.

```text
                 Main Server
                     │
          ┌──────────┼──────────┐
          ▼          ▼          ▼
       India       Europe      USA
       Edge         Edge       Edge
       Server       Server     Server
```

Now:

```text
Indian User
     │
     ▼
India CDN Edge
```

Instead of:

```text
Indian User
     │
     ▼
USA Server
```

The content is delivered from a location that is closer to the user.

<h2>What is an Edge Server?</h2>

> **Definition : An Edge Server is a CDN server located closer to the end user that can cache and deliver content.**

For example:

```text
User in Bangalore
        │
        ▼
CDN Edge Server in India
        │
        ▼
Content
```

The user doesn't need to contact the origin server every time.

<h2>CDN Caching</h2>

This is the most important concept.

Suppose 10,000 users request the same image.

<h3>Without CDN</h3>

```text
10,000 Users
     │
     ▼
Backend Server
     │
     ▼
Image
```

The backend has to serve the image repeatedly.

<h3>With CDN</h3>

First request:

```text
User
 │
 ▼
CDN
 │
 │ Cache Miss
 ▼
Origin Server
 │
 ▼
Image
 │
 ▼
CDN stores image
```

Next users:

```text
User
 │
 ▼
CDN
 │
 │ Cache Hit
 ▼
Image
```

The origin server doesn't need to handle every request.

<h2>Cache Hit vs Cache Miss</h2>

<h3>Cache Hit</h3>

The CDN already has the content.

```text
User
 ↓
CDN
 ↓
Content found
```

The content can be returned directly from the CDN.

This is faster because the request doesn't need to reach the origin server.

<h3>Cache Miss</h3>

The CDN doesn't have the content.

```text
User
 ↓
CDN
 ↓
Not found
 ↓
Origin Server
 ↓
Content
 ↓
CDN caches it
 ↓
User
```

The next request can be served from the CDN.

<h2>What Does a CDN Usually Store?</h2>

CDNs are especially useful for **static or cacheable content**:

- Images
- Videos
- CSS
- JavaScript
- Fonts
- PDFs
- Software Downloads
- Static Website Files

For dynamic or private data, the request often still needs to reach the backend.

<h2>CDN and Latency</h2>

Suppose:

```text
Origin → USA
User → India
```

Without CDN:

```text
India
  ↓
USA
  ↓
India
```

With CDN:

```text
India
  ↓
India CDN Edge
  ↓
Response
```

Shorter network distance generally means lower latency.

<h2>CDN and Scalability</h2>

Suppose your website has:

```text
10 Million Users
```

Many users request the same:

```text
logo.png
```

Without CDN:

```text
10M requests
      ↓
Backend
```

With CDN:

```text
10M requests
      ↓
CDN
      ↓
Most requests served from cache
```

This reduces the load on your:

- Backend Servers
- Database
- Origin Infrastructure

<h2>CDN + Load Balancer</h2>

These two solve **different problems**.

<h3>Load Balancer</h3>

Distributes traffic between your backend servers.

```text
Users
  ↓
Load Balancer
  ↓
S1 S2 S3
```

<h3>CDN</h3>

Serves cached content from locations closer to users.

```text
Users
  ↓
CDN
  ↓
Cached Content
```

They can work together:

```text
                   Users
                     │
                     ▼
                    CDN
                 /       \
          Cache Hit      Cache Miss
             │              │
             ▼              ▼
          Response      Load Balancer
                            │
                       ┌────┼────┐
                       ▼    ▼    ▼
                      S1   S2   S3
```

<h2>CDN vs Cache</h2>

Don't confuse them.

<h3>Cache</h3>

> **Definition : A Cache is a general technique of storing frequently accessed data temporarily for faster access.**

A cache can exist in:

- Browser
- Server
- Redis
- CDN

<h3>CDN</h3>

> **Definition : A CDN is a distributed network of servers specifically designed to deliver content closer to users.**

So:

> **CDN uses caching, but not every cache is a CDN.**

<h2>Real-World Example: Netflix</h2>

Imagine Netflix has a popular movie.

Millions of users want to watch it.

It would be inefficient for every user to download every video segment from one central server.

Instead, content can be distributed across CDN infrastructure closer to users.

```text
                 Netflix Origin
                       │
          ┌────────────┼────────────┐
          ▼            ▼            ▼
       India CDN    Europe CDN    USA CDN
          │            │            │
          ▼            ▼            ▼
        Users        Users        Users
```

This helps Netflix deliver large amounts of video efficiently.

<h2>CDN Doesn't Make Everything Faster</h2>

Important assumption to avoid:

> **A CDN is not automatically useful for every request.**

If every request requires fresh, personalized database data:

```text
GET /my-account
```

Caching that response at a shared CDN may be inappropriate because the data is user-specific and can become stale or leak between users if configured incorrectly.

CDNs are most valuable for **cacheable content**.

<h2>Memory Trick</h2>

Think of a restaurant chain.

Instead of having one restaurant in Bangalore serving all of India:

```text
One Restaurant
      ↓
Entire India
```

you open branches everywhere:

```text
Bangalore
Delhi
Mumbai
Chennai
```

Customers go to the nearest branch.

That's basically the idea of a CDN.

Remember:

> **CDN → Move content closer to users**

> **Load Balancer → Distribute requests across servers**

<h2>Key Takeaways</h2>

- **CDN = Content Delivery Network**
- Uses geographically distributed **Edge Servers**
- Caches frequently requested content
- **Cache Hit → Serve content from CDN**
- **Cache Miss → Fetch content from Origin**
- Reduces latency
- Reduces origin server load
- Improves scalability and performance
- Especially useful for static content and large files/videos
- **CDN and Load Balancer solve different problems**
- **CDN → Brings content closer to users**
- **Load Balancer → Distributes requests across backend servers**
