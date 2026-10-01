# H2 Database Access Guide

This document provides the necessary instructions and credentials to access the local H2 Database console for this application.

## Accessing the Console

To view and manage your local database state, ensure your Spring Boot application is currently running and navigate to the following URL in your web browser:

**Console URL:** [http://localhost:8080/api/h2-console](http://localhost:8080/api/h2-console)

## Login Credentials

Use the following credentials to authenticate into the H2 console:

| Field | Value |
| :--- | :--- |
| **Username** | `madan` |
| **Password** | `12345` |

## Connection Settings

When the H2 login screen appears, you must ensure the **JDBC URL** matches the exact connection string defined in your `application.yml` or `application.properties` file (for example, `jdbc:h2:mem:testdb`). 

If the JDBC URL on the login screen does not match your application's configuration, you will successfully log in, but you will be looking at a completely empty database rather than the one your application is actively using.

---
> **Security Reminder:** Because this is an in-memory or local development database, these credentials are safe for local testing. As discussed previously, ensure you keep any physical `.db` files in your `.gitignore` so local database state is not committed to version control.
