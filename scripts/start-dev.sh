#!/usr/bin/env bash
# ============================================================
# start-dev.sh — Start all Quarkus services in dev mode
# Usage: ./scripts/start-dev.sh
# ============================================================
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

SERVICES=(
  "file-upload"
  "import-interface"
  "rules-engine"
  "validation"
  "auto-fix"
  "auto-mapping-agent"
  "map-publish"
  "llm-integration"
)

# Ensure database exists
"$SCRIPT_DIR/init-db.sh"

echo ""
echo "🚀 Starting Quarkus services in dev mode..."
echo "   Press Ctrl+C to stop all services."
echo ""

PIDS=()

cleanup() {
  echo ""
  echo "🛑 Shutting down all services..."
  for pid in "${PIDS[@]}"; do
    kill "$pid" 2>/dev/null || true
  done
  wait 2>/dev/null
  echo "✅ All services stopped."
}

trap cleanup EXIT INT TERM

for svc in "${SERVICES[@]}"; do
  echo "  ▸ Starting $svc ..."
  cd "$PROJECT_DIR/services/$svc"
  mvn quarkus:dev -Dquarkus.profile=dev -Dquarkus.http.host=0.0.0.0 \
      > "$PROJECT_DIR/logs/${svc}.log" 2>&1 &
  PIDS+=($!)
  cd "$PROJECT_DIR"
done

echo ""
echo "📋 Service logs: ./logs/<service-name>.log"
echo "📋 Service ports:"
echo "     file-upload          → http://localhost:8081"
echo "     import-interface     → http://localhost:8082"
echo "     rules-engine         → http://localhost:8083"
echo "     validation           → http://localhost:8084"
echo "     auto-fix             → http://localhost:8085"
echo "     auto-mapping-agent   → http://localhost:8086"
echo "     map-publish          → http://localhost:8087"
echo "     llm-integration      → http://localhost:8088"
echo ""
echo "🌐 Frontend: cd frontend && npm run dev  →  http://localhost:5173"
echo ""

# Wait for all background processes
wait
