## DNS (Domain Name System)

DNS is one of the most important building blocks of the Internet.

Without DNS, we would have to remember the **IP address** of every website instead of easy-to-read names like **google.com** or **amazon.in**.

<h3>Why Do We Need DNS?</h3>

Imagine you open your browser and type:

```text
www.google.com
```

Question:

> How does your computer know where Google's server is?

Does the Internet understand the word:

```text
google.com
```
 No.

Computers communicate using **IP addresses**, not names.

Example:

```text
142.250.183.110
```

Imagine remembering IP addresses for every website:

```text
142.250.183.110
157.240.22.35
13.224.189.15
```

Impossible.

So we use **Domain Names**.

DNS converts them into IP addresses.

<h2>What is DNS?</h2>

> **Definition : DNS (Domain Name System) translates human-readable domain names into IP addresses.**

Simply:

> **DNS = The Internet's Phonebook.**

<h2>Real-Life Analogy</h2>

Imagine your phone.

You don't remember your friend's phone number.

You save:

```text
Rahul
```

When you click **Rahul**,

your phone actually dials:

```text
+91XXXXXXXXXX
```

DNS works exactly the same way.

```text
google.com
      │
      ▼
142.xxx.xxx.xxx
```

<h2>Why Do We Need DNS?</h2>

Without DNS,

instead of typing:

```text
www.amazon.com
```

you would have to type:

```text
54.239.xxx.xxx
```

for every website.

DNS makes the Internet **human-friendly**.

<h2>DNS Lookup Process</h2>

Let's say you type:

```text
www.netflix.com
```

The flow is:

```text
Browser
    │
    ▼
DNS Resolver
    │
    ▼
Find IP Address
    │
    ▼
Return IP Address
    │
    ▼
Browser Connects to Netflix Server
```

Once the browser gets the IP address,

it can send the **HTTP/HTTPS request**.

<h2>Where Does DNS Look First?</h2>

DNS doesn't always go to the Internet.

It checks in this order:

```text
1. Browser Cache
        │
        ▼
2. Operating System Cache
        │
        ▼
3. DNS Resolver (ISP / Public DNS)
        │
        ▼
4. Internet DNS Servers
```

### Why?

Because searching the Internet every time would be slow.

Caching makes repeated lookups much faster.

<h2>DNS Cache</h2>

Imagine you visited:

```text
www.youtube.com
```

5 minutes ago.

The browser already knows the IP address.

So next time:

```text
youtube.com
       │
       ▼
Found in Cache
       │
       ▼
No DNS Lookup Needed
```

Result : Faster page loading.

<h2>What is TTL?</h2>

> **TTL (Time To Live) tells devices how long a DNS record should remain in cache before it expires.**

Example:

```text
TTL = 300 seconds (5 minutes)
```

```text
Browser
    │
Stores Google's IP
    │
Uses it for 5 Minutes
    │
TTL Expires
    │
Asks DNS Again
```

<h2>Why Not Cache Forever?</h2>

Imagine Amazon changes its server IP.

Old:

```text
54.239.10.1
```

New:

```text
54.239.20.8
```

If your computer kept the old IP forever,

it would connect to the wrong server.

TTL ensures cached entries eventually expire and refresh.

<h2>Public DNS Providers</h2>

Some popular DNS services are:

| Provider | DNS Address |
|----------|-------------|
| Google DNS | 8.8.8.8 |
| Cloudflare DNS | 1.1.1.1 |
| Quad9 | 9.9.9.9 |

These services resolve domain names for millions of users.

<h2>DNS in System Design</h2>

Suppose Netflix has servers in:

- India
- USA
- Japan

When you type:

```text
www.netflix.com
```

DNS may return the IP address of the **nearest or most appropriate server**.

```text
User (India)
      │
      ▼
     DNS
      │
      ▼
Netflix India Server
```

This reduces:

- Latency
- Network Traffic

and improves performance.

<h2>Real-World Examples</h2>

### Google

```text
google.com
      │
      ▼
DNS Returns Google's IP
      │
      ▼
Browser Connects
      │
      ▼
Search Page Opens
```

<h2>How DNS Helps Scalability</h2>

DNS does more than convert names to IP addresses.

It can also help distribute traffic.

Example:

```text
            Users
               │
               ▼
              DNS
       ┌───────┼────────┐
       ▼       ▼        ▼
 India Server USA Server Japan Server
```

Users are directed to the nearest or healthiest server.

Benefits:

- Lower Latency
- Better User Experience
- Reduced Server Load
- Improved Availability

<h2>Key Takeaways</h2>

- **DNS = Domain Name System.**
- DNS converts **Domain Names → IP Addresses**.
- Computers communicate using **IP addresses**, not names.
- DNS first checks **Cache** before querying Internet DNS servers.
- **TTL** controls how long DNS records stay in cache.
- Public DNS providers include **Google (8.8.8.8)** and **Cloudflare (1.1.1.1)**.
- DNS improves **Performance, Scalability, Availability, and User Experience** by routing users to appropriate servers.
