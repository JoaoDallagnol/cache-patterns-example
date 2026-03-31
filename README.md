# Cache Patterns Example

A hands-on Spring Boot application demonstrating the four main cache patterns: **Cache-Aside**, **Read-Through**, **Write-Through**, and **Write-Back/Write-Behind**.

This project provides practical examples of each pattern with dedicated API endpoints, allowing you to compare their behavior, trade-offs, and use cases side-by-side.

## 📋 Table of Contents

- [Overview](#overview)
- [Cache Patterns](#cache-patterns)
  - [Cache-Aside](#1-cache-aside)
  - [Read-Through](#2-read-through)
  - [Write-Through](#3-write-through)
  - [Write-Back/Write-Behind](#4-write-backwrite-behind)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Pattern Comparison](#pattern-comparison)

## Overview

Caching is a critical technique for improving application performance by storing frequently accessed data in a fast-access layer (Redis) instead of repeatedly querying a slower data source (database).

This project implements a Product API where each endpoint demonstrates a specific cache pattern. Each pattern has different characteristics regarding:
- **Consistency**: How quickly cache and database sync
- **Latency**: Read/write operation speed
- **Complexity**: Implementation difficulty
- **Reliability**: Risk of data loss

## 📚 Related Articles

This project was created as a companion to the following articles:

- 🇺🇸 English: [A Practical Guide to Cache Patterns](https://medium.com/@jvdallagnol2001/a-practical-guide-to-cache-patterns-use-cases-and-trade-offs-87eebdc2a6a7)
- 🇧🇷 Portuguese (PT-BR): [Padrões de Cache: Casos de Uso e Trade-offs](https://medium.com/@jvdallagnol2001/padrões-de-cache-casos-de-uso-e-trade-offs-e535263cfbd9)

## Cache Patterns

### 1. Cache-Aside

**Also known as:** Lazy Loading

**How it works:**
1. Application checks the cache first
2. **Cache HIT**: Return cached data immediately
3. **Cache MISS**: Query the database, store result in cache, then return

**Characteristics:**
- Application explicitly manages cache reads and writes
- Database has no awareness of the cache
- Cache is populated on-demand (lazy)
- Most common and straightforward pattern

**Trade-offs:**
- ✅ Simple to implement and understand
- ✅ Cache only contains requested data (efficient memory usage)
- ✅ Cache failures don't affect database operations
- ❌ First request always hits the database (cold start)
- ❌ Potential for stale data if cache TTL is too long
- ❌ Cache and DB can become inconsistent on writes

**Use cases:**
- Read-heavy workloads
- Data that doesn't change frequently
- When cache failures should not block operations

### 2. Read-Through

**How it works:**
1. Application always queries the cache
2. **Cache HIT**: Cache returns data directly
3. **Cache MISS**: Cache automatically loads from database and stores result

**Characteristics:**
- Cache sits as a proxy between application and database
- Application code contains no explicit cache logic
- Implemented via Spring's `@Cacheable` annotation
- Cache layer handles all read operations transparently

**Trade-offs:**
- ✅ Cleaner application code (no cache logic)
- ✅ Consistent caching behavior across the application
- ✅ Cache automatically populated on misses
- ❌ First request still hits the database
- ❌ Tight coupling between cache and data source
- ❌ Cache failures can impact application

**Use cases:**
- When you want to abstract cache logic from business code
- Read-heavy applications with predictable access patterns
- When using a caching framework/library

**Difference from Cache-Aside:**
- **Cache-Aside**: You write `redis.get()` and `redis.set()` explicitly
- **Read-Through**: Framework handles it via `@Cacheable` annotation

### 3. Write-Through

**How it works:**
1. Application writes to database
2. Immediately writes to cache in the same operation
3. Both operations complete before returning to client

**Characteristics:**
- Synchronous writes to both database and cache
- Cache is always consistent with database after writes
- Higher write latency due to dual writes
- No risk of stale reads

**Trade-offs:**
- ✅ Strong consistency between cache and database
- ✅ No stale data after writes
- ✅ Subsequent reads are fast (data already cached)
- ❌ Higher write latency (waits for both operations)
- ❌ Writes data that might never be read
- ❌ More complex error handling (what if one fails?)

**Use cases:**
- Applications requiring strong consistency
- When read performance after writes is critical
- Financial systems, inventory management
- When stale data is unacceptable

### 4. Write-Back/Write-Behind

**Also known as:** Write-Behind, Lazy Write

**How it works:**
1. Application writes to cache immediately
2. Returns success to client
3. Database write happens asynchronously in background (after ~2 seconds)

**Characteristics:**
- Lowest write latency (doesn't wait for database)
- Returns HTTP 202 Accepted (not 200 OK)
- Database eventually becomes consistent
- Risk of data loss if system crashes before flush

**Trade-offs:**
- ✅ Fastest write performance
- ✅ Reduces database load (can batch writes)
- ✅ Better user experience (quick responses)
- ❌ Risk of data loss on system failure
- ❌ Eventual consistency (temporary inconsistency)
- ❌ More complex implementation
- ❌ Difficult error handling (async failures)

**Use cases:**
- High-throughput write scenarios
- When write latency is critical
- Analytics, logging, metrics collection
- Social media likes/views counters
- When temporary data loss is acceptable

**Variants in this project:**
- **Simple Async**: Uses `@Async` for background writes
- **Queue-based**: Uses a queue for better reliability and ordering

## Technology Stack

- **Java 25**
- **Spring Boot 4.0.3**
- **Spring Data JPA** - Database access
- **Spring Data Redis** - Cache layer
- **H2 Database** - In-memory database
- **Redis 7.4** - Cache store
- **Lombok** - Boilerplate reduction
- **MapStruct** - Object mapping
- **Maven** - Build tool

## Getting Started

### Prerequisites

- Java 25 or higher
- Maven 3.6+
- Docker and Docker Compose (for Redis)

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd cache-patterns
```

2. **Start Redis**
```bash
docker-compose up -d
```

3. **Build the project**
```bash
./mvnw clean install
```

4. **Run the application**
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### Verify Setup

Check Redis is running:
```bash
docker ps
```

Access the API documentation:
```
http://localhost:8080/swagger-ui.html
```

## API Endpoints

### Cache-Aside Pattern

**Get Product**
```http
GET /products/{id}
```
- Checks Redis first (`product:{id}`)
- On miss: queries H2, caches result with 3-minute TTL
- Explicit Redis operations in service code

### Read-Through Pattern

**Get Product (Read-Through)**
```http
GET /products/{id}/read-through
```
- Uses Spring's `@Cacheable` annotation
- Functionally identical to Cache-Aside
- No explicit Redis code in service layer

### Write-Through Pattern

**Create Product**
```http
POST /products
Content-Type: application/json

{
  "name": "Mechanical Keyboard",
  "price": 249.99,
  "category": "Electronics",
  "stock": 42
}
```
- Writes to H2 and Redis synchronously
- Returns 201 Created when both complete

**Update Product**
```http
PUT /products/{id}
Content-Type: application/json

{
  "name": "Updated Product",
  "price": 299.99,
  "category": "Electronics",
  "stock": 30
}
```
- Updates H2 and Redis synchronously
- Cache is immediately consistent

### Write-Back/Write-Behind Pattern

**Update Product (Async)**
```http
PUT /products/{id}/write-back
Content-Type: application/json

{
  "name": "Updated Product",
  "price": 299.99
}
```
- Updates Redis immediately
- Returns 202 Accepted
- DB write happens ~2 seconds later

**Update Product (Queue-based)**
```http
PUT /products/{id}/write-back-queue
Content-Type: application/json

{
  "name": "Updated Product",
  "price": 299.99
}
```
- Updates Redis immediately
- Enqueues DB write for background processing
- Returns 202 Accepted
- Better reliability and ordering guarantees

### Example Request/Response

**Request:**
```bash
curl -X GET http://localhost:8080/products/1
```

**Response:**
```json
{
  "id": 1,
  "name": "Mechanical Keyboard",
  "price": 249.99,
  "category": "Electronics",
  "stock": 42,
  "createdAt": "2025-03-12T10:00:00Z",
  "updatedAt": "2025-03-12T11:30:00Z"
}
```

## Pattern Comparison

| Pattern | Read Latency | Write Latency | Consistency | Complexity | Data Loss Risk |
|---------|--------------|---------------|-------------|------------|----------------|
| **Cache-Aside** | Low (on hit) | Low | Eventual | Low | None |
| **Read-Through** | Low (on hit) | Low | Eventual | Low | None |
| **Write-Through** | Low | High | Strong | Medium | None |
| **Write-Back** | Low | Very Low | Eventual | High | Yes |

### When to Use Each Pattern

**Cache-Aside / Read-Through:**
- General-purpose caching
- Read-heavy workloads
- When you control cache invalidation

**Write-Through:**
- Strong consistency requirements
- Financial transactions
- Inventory systems
- When stale reads are unacceptable

**Write-Back/Write-Behind:**
- High write throughput needed
- Write latency is critical
- Analytics and metrics
- When eventual consistency is acceptable
- When some data loss is tolerable

## Project Structure

```
cache-patterns/
├── src/main/java/com/patterns/cache/
│   ├── controller/     # REST endpoints
│   ├── service/        # Business logic & cache patterns
│   ├── repository/     # JPA repositories
│   ├── model/          # Domain entities
│   └── dto/            # Request/Response objects
├── api-docs.yaml       # OpenAPI specification
├── docker-compose.yml  # Redis setup
└── pom.xml            # Maven dependencies
```
