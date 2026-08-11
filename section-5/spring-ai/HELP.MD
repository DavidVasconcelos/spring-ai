# Local Development Resources Guide

This document provides the necessary instructions and credentials to access the local databases and tools for this application.

## H2 Database

To view and manage your local relational database state, ensure your Spring Boot application is currently running and navigate to the following URL in your web browser:

**Console URL:** [http://localhost:8080/api/h2-console](http://localhost:8080/api/h2-console)

### Login Credentials

Use the following credentials to authenticate into the H2 console:

| Field | Value |
| :--- | :--- |
| **Username** | `madan` |
| **Password** | `12345` |

### Connection Settings

When the H2 login screen appears, you must ensure the **JDBC URL** matches the exact connection string defined in your `application.yml` or `application.properties` file (for example, `jdbc:h2:mem:testdb`).

If the JDBC URL on the login screen does not match your application's configuration, you will successfully log in, but you will be looking at a completely empty database rather than the one your application is actively using.

---

## Qdrant Vector Database

To view and manage your vector embeddings and collections, you can use the built-in Qdrant Web UI. Ensure your local Qdrant instance (likely running via Docker/Testcontainers) is active and navigate to:

**Dashboard URL:** [http://localhost:6333/dashboard](http://localhost:6333/dashboard)

> **Note:** The local Qdrant dashboard does not require a username or password by default.

---
> **Security Reminder:** Because these are local development databases, these credentials and endpoints are safe for local testing. As discussed previously, ensure you keep any physical `.db` files or local Qdrant storage volumes in your `.gitignore` so local database state is not committed to version control.
---

### Silencing Java 25 Memory Warnings in IntelliJ IDEA

If you are running the application using Java 25+, you may see console warnings regarding `sun.misc.Unsafe` or restricted native access. This is caused by high-performance libraries (like Netty and gRPC) transitioning to the new Java memory APIs.

When starting the application via the terminal using `./gradlew bootRun`, these warnings are handled automatically by the `build.gradle` configuration. However, **IntelliJ IDEA bypasses Gradle** when you click the "Play" button, meaning you must add the JVM arguments directly to the IDE.

**To fix this in IntelliJ:**

1. In the top toolbar next to the green Play/Run button, click your application name and select **Edit Configurations...**
2. In the configuration window, look for the **VM options** field.
   *(Note: If you do not see it, click the blue **Modify options** text or the ⚙️ gear icon, and check **Add VM options**).*
3. Paste the following arguments into the **VM options** box:

   ```text
   --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED
