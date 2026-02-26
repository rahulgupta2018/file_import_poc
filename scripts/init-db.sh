#!/usr/bin/env bash
# ============================================================
# init-db.sh — Initialise the SQLite development database
# Usage: ./scripts/init-db.sh [--reset]
# ============================================================
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
DB_PATH="$PROJECT_DIR/data/import.db"
SQL_FILE="$SCRIPT_DIR/init-db.sql"

if [[ "${1:-}" == "--reset" ]] && [[ -f "$DB_PATH" ]]; then
  echo "🗑  Removing existing database: $DB_PATH"
  rm "$DB_PATH"
fi

mkdir -p "$(dirname "$DB_PATH")"

echo "📦 Initialising SQLite database: $DB_PATH"
sqlite3 "$DB_PATH" < "$SQL_FILE"
echo "✅ Database ready."
