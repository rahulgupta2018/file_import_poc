# File Import Automation

Automated mapping, validation, auto-fix, and publishing of payment file imports for ISO 20022 `pain.001.001.03` BACS payments.

## Architecture

- **Backend**: Java 21 + Quarkus 3.17.5 (Maven multi-module monorepo)
- **Frontend**: React 18 + TypeScript + Tailwind CSS 3 + Vite
- **Dev DB**: SQLite (`./data/import.db`)
- **Dev LLM**: Ollama (Mistral / Llama3)
- **Prod**: GCP (Cloud Run, Spanner, GCS, Pub/Sub, Vertex AI)

See [docs/architecture.md](docs/architecture.md) for full architecture documentation.

## Project Structure

```
file_import_poc/
├── pom.xml                         # Parent POM (Maven reactor)
├── config/
│   ├── dev.yaml                    # Local dev environment config
│   └── prod.yaml                   # GCP production config
├── data/
│   ├── import.db                   # SQLite database (auto-created)
│   └── storage/                    # Local file storage
├── docs/
│   ├── architecture.md             # Architecture document (v1.3)
│   └── detailed_requirements.md    # Requirements (v1.1)
├── frontend/                       # React SPA
│   ├── package.json
│   ├── vite.config.ts
│   ├── tailwind.config.js
│   └── src/
│       ├── App.tsx
│       ├── components/
│       ├── pages/
│       ├── hooks/
│       ├── services/
│       └── types/
├── scripts/
│   ├── init-db.sh                  # Initialise SQLite schema
│   ├── init-db.sql                 # SQLite DDL
│   └── start-dev.sh                # Start all services
├── services/
│   ├── common/                     # Shared models, repos, events
│   ├── file-upload/                # Port 8081
│   ├── import-interface/           # Port 8082
│   ├── rules-engine/               # Port 8083
│   ├── validation/                 # Port 8084
│   ├── auto-fix/                   # Port 8085
│   ├── auto-mapping-agent/         # Port 8086
│   ├── map-publish/                # Port 8087
│   └── llm-integration/           # Port 8088
└── logs/                           # Service log files
```

## Prerequisites

| Tool | Version | Install |
|------|---------|---------|
| JDK | 21+ | `brew install openjdk@21` |
| Maven | 3.9+ | `brew install maven` |
| Node.js | 20+ | `brew install node` |
| SQLite | 3.x | Pre-installed on macOS |
| Ollama | latest | `brew install ollama` |

## Quick Start

### 1. Clone & Build

```bash
git clone <repo-url> file_import_poc
cd file_import_poc
mvn clean install -DskipTests
```

### 2. Set Up Ollama (LLM)

```bash
ollama pull mistral
ollama serve          # runs on http://localhost:11434
```

### 3. Initialise Database

```bash
./scripts/init-db.sh
# Reset: ./scripts/init-db.sh --reset
```

### 4. Start Backend Services

```bash
./scripts/start-dev.sh
```

This starts all 8 Quarkus services in dev mode. Logs go to `./logs/<service>.log`.

### 5. Start Frontend

```bash
cd frontend
npm install
npm run dev           # runs on http://localhost:5173
```

## Service Ports

| Service | Port | Health Check |
|---------|------|-------------|
| file-upload | 8081 | `GET /api/upload/health` |
| import-interface | 8082 | `GET /api/import/health` |
| rules-engine | 8083 | `GET /api/rules/health` |
| validation | 8084 | `GET /api/validation/health` |
| auto-fix | 8085 | (CDI bean — no HTTP) |
| auto-mapping-agent | 8086 | `GET /api/agent/health` |
| map-publish | 8087 | `GET /api/publish/health` |
| llm-integration | 8088 | (internal service) |
| **Frontend** | 5173 | Vite dev server |

## Environment Profiles

Configuration is driven by Quarkus profiles:

- **Dev** (`-Dquarkus.profile=dev`): SQLite + Ollama + local filesystem + mock auth
- **Prod** (`-Dquarkus.profile=prod`): Spanner + Vertex AI + GCS + Pub/Sub + OAuth2

See [config/dev.yaml](config/dev.yaml) and [config/prod.yaml](config/prod.yaml).

## Supported File Formats

| Format | Extension | Description |
|--------|-----------|-------------|
| BACS XML | `.xml` | ISO 20022 `pain.001.001.03` |
| Standard 18 | `.txt` | BACS fixed-length (80 chars/line) |
| CBO CSV | `.csv` | Lloyds CBO portal export (H/D/C/T rows) |
| ERP CSV | `.csv` | ERP payment run export |

## Documentation

- [Detailed Requirements](docs/detailed_requirements.md) — v1.1, 18 sections + 5 appendices
- [Architecture](docs/architecture.md) — v1.3, 20+ sections, Mermaid diagrams
