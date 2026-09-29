# Local Development Resources Guide

This document provides the necessary instructions and credentials to access the local databases and tools for this application.

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


# Using the MCP Inspector

The Model Context Protocol (MCP) Inspector is a web-based diagnostic tool used to test, debug, and interact with MCP servers. It supports multiple transport layers, including standard input/output (Stdio) and HTTP Server-Sent Events (SSE).

Below is the documentation on how to configure and connect the Inspector to two different server environments.

## Prerequisites

* **Node.js** installed (to run `npx @modelcontextprotocol/inspector`)
* **Docker** installed and running (for the GitHub MCP server)
* A valid **GitHub Personal Access Token** (for the GitHub MCP server)

---

## 1. Stdio Connection: Dockerized GitHub MCP Server

This configuration connects the Inspector to an official GitHub MCP server running inside a Docker container. Because it uses the `stdio` transport, you can pass the entire Docker execution command directly to the Inspector CLI.

### The Command

```bash
export GITHUB_PERSONAL_ACCESS_TOKEN="your_actual_token_here"

npx @modelcontextprotocol/inspector 

```
### How to use it

1. Run the command above in your terminal.
2. The terminal will output a local URL (typically `http://localhost:5173`).
3. Open that URL in your browser.
4. The Inspector will automatically default to the **Command** tab with your Docker configuration pre-loaded. Click **Connect**.

### Breakdown

* `npx @modelcontextprotocol/inspector`: Launches the Inspector proxy and web UI.
* `transport:` stdio
* `comannd:` docker
* `arguments`:
  ```
  run
  -i
  --rm
  -e
  GITHUB_PERSONAL_ACCESS_TOKEN
  ghcr.io/github/github-mcp-server
  ```
* `docker run -i`: Runs the container in interactive mode (crucial for `stdio` communication to work).
* `--rm`: Automatically cleans up and removes the container when you close the Inspector.
* `-e GITHUB_PERSONAL_ACCESS_TOKEN`: Passes your local environment variable into the container so the server can authenticate with the GitHub API.


---

## 2. SSE / Streamable HTTP: Local Spring Boot Server

This configuration connects the Inspector to your local Spring Boot 4 MCP server over HTTP using Server-Sent Events (SSE). Unlike `stdio`, HTTP connections are configured directly in the Inspector's web interface rather than via the CLI.

### The Configuration Target

* **URL:** `http://localhost:8090/mcp`
* **Transport:** streamable-http

### How to use it

1. Ensure your Spring Boot application is running and accessible at port `8090`.
2. Launch the Inspector without any command-line arguments:
```bash
npx @modelcontextprotocol/inspector

```


3. Open the provided local URL (e.g., `http://localhost:5173`) in your browser.
4. In the Inspector UI, select the **SSE** tab (instead of Command).
5. In the **URL** input field, paste: `http://localhost:8090/mcp`
6. Click **Connect**.

> **Note on CORS:** If the Inspector fails to connect, ensure your Spring Boot server is configured to allow Cross-Origin Resource Sharing (CORS) for `http://localhost:5173`, as the Inspector UI runs on a different port than your backend.