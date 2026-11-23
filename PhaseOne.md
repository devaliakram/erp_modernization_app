As part of our **ERP Inventory Spring Boot application**, we planned to complete security in three phases:

- **Phase 1:** Basic Authentication + Role-Based Authorization
- **Phase 2:** JWT Authentication
- **Phase 3:** OAuth2 / OpenID Connect

Right now, we are working on **Phase 1**.

## **Phase 1 — Progress tracker**

Based on what we've implemented and discussed so far:

**Phase 1: Basic Authentication**

In progress

About halfway through the planned implementation. Some items still need testing and confirmation.

| **#** | **Task**                                | **Status** |
| ----- | --------------------------------------- | ---------- |
| 1     | Add Spring Security dependency          | Completed  |
| 2     | Understand default Basic Authentication | Completed  |
| 3     | Create SecurityConfig.java              | Added      |
| 4     | Configure environment variables         | Completed  |
| 5     | Configure BCrypt password encoder       | To verify  |
| 6     | Configure ADMIN and USER roles          | To verify  |
| 7     | Test GET, POST, PUT, PATCH, DELETE      | Pending    |
| 8     | Write security unit/integration tests   | Pending    |

## **1. What we've done so far**

**Step 1 — Added Spring Security**

You added this dependency in `pom.xml`:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

You observed that Spring Security automatically protected your APIs and generated a password in the IntelliJ console.

**Step 2 — Understood CSRF**

We discussed:

```
.csrf(csrf -> csrf.disable())
```

This temporarily disables CSRF protection for your local Postman testing.

**Step 3 — Created SecurityConfig.java**

You added the configuration class, and we planned three important beans:

```
@Bean
public SecurityFilterChain securityFilterChain(...)

@Bean
public UserDetailsService userDetailsService(...)

@Bean
public PasswordEncoder passwordEncoder()
```

**Step 4 — Externalized credentials**

We fixed the startup error caused by the missing `APP_ADMIN_PASSWORD` environment variable.

Your IntelliJ Run Configuration now needs these variables:

```
DB_USERNAME
DB_PASSWORD
APP_ADMIN_PASSWORD
APP_USER_PASSWORD
```

We also learned that this approach is called **Externalized Configuration using Environment Variables**.

## **2. What you need to do next**

**Your next tasks**

0/6 completed

Verify Basic Authentication

Use admin credentials in Postman and test GET /api/products

Verify ADMIN and USER roles

ADMIN should modify products; USER should only read products

Test all CRUD endpoints

Verify POST 201, GET 200, PUT 200, PATCH 200, DELETE 204

Test unauthorized access

Missing or wrong credentials → 401; insufficient role → 403

Add Spring Security tests

Use spring-security-test, MockMvc and httpBasic()

Run Maven build

Execute mvn clean install and resolve any failing tests

## **3. Expected final security behavior**

| Request                  | Authentication | Expected result  |
| ------------------------ | -------------- | ---------------- |
| GET `/api/products`      | USER           | 200 OK           |
| GET `/api/products`      | ADMIN          | 200 OK           |
| POST `/api/products`     | ADMIN          | 201 Created      |
| POST `/api/products`     | USER           | 403 Forbidden    |
| DELETE `/api/products/1` | ADMIN          | 204 No Content   |
| GET `/api/products`      | No credentials | 401 Unauthorized |
| GET `/api/products`      | Wrong password | 401 Unauthorized |

These assume valid request data, correct endpoint mappings, and an existing product for deletion.

## **4. What we'll do after Phase 1**

**Phase 2 — JWT Authentication**

Replace Basic Auth with a login endpoint, MySQL-backed users, JWT generation, token validation, expiration, and refresh tokens.

**Phase 3 — OAuth2 / OpenID Connect**

Implement external login using an identity provider, authorization code flow, and PKCE.

**Our immediate next step is to verify your&#x20;**`SecurityConfig.java`**&#x20;and test both ADMIN and USER credentials in Postman.** Once we confirm that authentication and role-based authorization work, we'll write automated security tests and complete Phase 1.