# Docker Model Runner

---

## 1. Install the Plugin
Since standard Docker CE is already installed, add the Model Runner plugin using your package manager:

```bash
sudo dnf install docker-model-plugin
```

## 2. Configure the Firewall
To allow external devices on your network to reach the API, open port `12434`. (Opening the port is safer than disabling the firewall entirely).

```bash
sudo firewall-cmd --add-port=12434/tcp --permanent
sudo firewall-cmd --reload
```

## 3. Start the Background Server
By default, the runner only listens to `localhost`. Start the daemon and force it to listen on all network interfaces (`0.0.0.0`) so it accepts outside connections:

```bash
docker model start-runner --host 0.0.0.0
```

## 4. Download and Run the Model
Pull and run Gemma 3 in detached mode (`-d`). This keeps your terminal free while exposing the OpenAI-compatible API on port `12434`:

```bash
docker model run -d ai/gemma3
```

*(Note: The runner is highly efficient. If it receives no prompts for 5 minutes, it will automatically unload the model from memory to free up system resources, and reload it instantly on the next request).*

---



