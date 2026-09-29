# Using Mailpit for Local Email Testing

Mailpit is a lightweight, fake SMTP server designed specifically for local development and testing. It catches all outgoing emails sent by your application so they don't accidentally reach real customers, and provides both a Web UI and a REST API to inspect them.

Based on your `docker-compose.yml`, here is how to run and interact with your Mailpit instance.

## 1. Starting the Service

To start Mailpit, run the following command in the directory containing your `docker-compose.yml` file:

```bash
docker compose up -d mailpit

```

## 2. Ports & Connectivity

Your Docker configuration exposes two distinct ports for different ways of interacting with Mailpit:

* **`1025` (SMTP Server):** This is the port your Spring Boot application (or any local email client) uses to *send* emails. Mailpit will accept and store any email sent to this port.
* **`8025` (Web UI & REST API):** This is the port used to *read* and monitor the inbox. You can open it in a browser to view emails visually, or connect to it via HTTP to fetch emails programmatically (like your Spring Boot `MailpitClient` does).

## 3. How to Interact with Mailpit

### A. Browsing Emails Visually (Web UI)

Open your browser and navigate to **`http://localhost:8025`**.
You will see a standard webmail interface where you can view all caught emails, inspect their HTML/Plain text rendering, check headers, and download attachments.

### B. Sending/Seeding Test Emails (SMTP)

Configure your application's mail sender (e.g., Spring Mail) to use the following properties:

* **Host:** `localhost`
* **Port:** `1025`
* **Authentication:** None required (or use any dummy username/password).

Because your configuration includes `MP_SMTP_AUTH_ACCEPT_ANY: "1"` and `MP_SMTP_AUTH_ALLOW_INSECURE: "1"`, Mailpit will accept emails regardless of the credentials you provide, making it a frictionless sandbox.

### C. Reading Emails Programmatically (REST API)

As you set up in your `MailpitApi` HTTP Interface, your AI agent can monitor and read emails by making HTTP requests to port 8025.

* **Base URL:** `http://localhost:8025/api/v1`
* **Example - Search Unread:** `GET http://localhost:8025/api/v1/search?query=is:unread`
* **Example - Get Message:** `GET http://localhost:8025/api/v1/message/{id}`

## 4. Data Persistence & Limits

Your configuration is designed to be persistent across restarts:

* **Storage:** Emails are saved to an SQLite database located at `/data/mailpit.db` inside the container.
* **Host Mapping:** Because of the `volumes: - ./data:/data` mapping, this database is physically stored in a `data` folder right next to your `docker-compose.yml` file. If you restart or destroy the Docker container, your test emails will remain intact.
* **Capacity:** Mailpit is configured to hold a maximum of 5,000 messages (`MP_MAX_MESSAGES: 5000`). Once this limit is reached, it will automatically delete the oldest emails to make room for new ones.