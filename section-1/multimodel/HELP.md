### Quick Start Guide

#### 1. Start the Services
Navigate to the folder containing your `docker-compose.yml` file and run this command to start Ollama and Open WebUI in the background:

```bash
docker compose up -d
```

#### 2. Download the Llama 3.2 Model
Once the containers are successfully running, run this command to download the lightweight Llama 3.2 1B model (optimized for your GPU). *Note: This only needs to be run once.*

```bash
docker exec -it ollama ollama pull llama3.2:1b
```

#### 3. Access the Interface
Open your web browser on your Machine and navigate to:

**`http://localhost:3000`**

Create a quick local account, select **`llama3.2:1b`** from the model dropdown at the top of the page, and start chatting!