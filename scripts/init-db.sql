-- ============================================================
-- File Import Automation — SQLite Schema (Dev Environment)
-- Source: architecture.md Section 6.4
-- Usage: sqlite3 ./data/import.db < scripts/init-db.sql
-- ============================================================

CREATE TABLE IF NOT EXISTS maps (
    map_id           TEXT NOT NULL,
    import_id        TEXT NOT NULL,
    tenant_id        TEXT NOT NULL DEFAULT 'local',
    map_name         TEXT,
    version          INTEGER NOT NULL DEFAULT 1,
    status           TEXT NOT NULL DEFAULT 'DRAFT',  -- DRAFT | PUBLISHED | ARCHIVED
    source_type      TEXT NOT NULL,                   -- ERP_CSV | CBO_CSV | FIXED_LENGTH | XML
    target_type      TEXT NOT NULL,                   -- BACS_CSV | BACS_XML
    definition       TEXT NOT NULL,                   -- JSON string
    supplemental_values TEXT,                         -- JSON string
    fingerprint      TEXT,
    validation_summary TEXT,                          -- JSON string
    created_at       TEXT NOT NULL DEFAULT (datetime('now')),
    updated_at       TEXT NOT NULL DEFAULT (datetime('now')),
    created_by       TEXT NOT NULL DEFAULT 'local-dev',
    PRIMARY KEY (tenant_id, map_id)
);

CREATE INDEX IF NOT EXISTS idx_maps_fingerprint
    ON maps (tenant_id, fingerprint, status);

CREATE INDEX IF NOT EXISTS idx_maps_status
    ON maps (tenant_id, status, updated_at DESC);

CREATE TABLE IF NOT EXISTS mapping_history (
    history_id         TEXT NOT NULL,
    map_id             TEXT NOT NULL,
    tenant_id          TEXT NOT NULL DEFAULT 'local',
    version            INTEGER NOT NULL,
    change_type        TEXT NOT NULL,
    definition_diff    TEXT,           -- JSON string
    rules_applied      TEXT,           -- JSON array string
    transforms_applied TEXT,           -- JSON array string
    validation_summary TEXT,           -- JSON string
    created_at         TEXT NOT NULL DEFAULT (datetime('now')),
    created_by         TEXT NOT NULL DEFAULT 'local-dev',
    PRIMARY KEY (tenant_id, map_id, history_id),
    FOREIGN KEY (tenant_id, map_id) REFERENCES maps (tenant_id, map_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS file_fingerprints (
    fingerprint   TEXT NOT NULL,
    tenant_id     TEXT NOT NULL DEFAULT 'local',
    file_name     TEXT,
    source_type   TEXT,
    first_seen    TEXT NOT NULL DEFAULT (datetime('now')),
    last_seen     TEXT NOT NULL DEFAULT (datetime('now')),
    import_count  INTEGER NOT NULL DEFAULT 0,
    best_map_id   TEXT,
    PRIMARY KEY (tenant_id, fingerprint)
);

CREATE TABLE IF NOT EXISTS rule_scores (
    rule_id       TEXT NOT NULL,
    tenant_id     TEXT NOT NULL DEFAULT 'local',
    attempt_count INTEGER NOT NULL DEFAULT 0,
    success_count INTEGER NOT NULL DEFAULT 0,
    failure_count INTEGER NOT NULL DEFAULT 0,
    last_outcome  TEXT,
    last_updated  TEXT NOT NULL DEFAULT (datetime('now')),
    PRIMARY KEY (tenant_id, rule_id)
);

CREATE TABLE IF NOT EXISTS transforms_log (
    log_id        TEXT NOT NULL PRIMARY KEY,
    import_id     TEXT NOT NULL,
    transform_id  TEXT NOT NULL,
    rule_id       TEXT,
    location      TEXT,            -- JSON string
    before_value  TEXT,
    after_value   TEXT,
    outcome       TEXT,
    created_at    TEXT NOT NULL DEFAULT (datetime('now'))
);

CREATE TABLE IF NOT EXISTS sequence_numbers (
    tenant_id   TEXT NOT NULL DEFAULT 'local',
    seq         TEXT NOT NULL,
    file_id     TEXT,
    imported_at TEXT NOT NULL DEFAULT (datetime('now')),
    PRIMARY KEY (tenant_id, seq)
);

CREATE TABLE IF NOT EXISTS imports (
    import_id    TEXT NOT NULL PRIMARY KEY,
    tenant_id    TEXT NOT NULL DEFAULT 'local',
    file_id      TEXT,
    fingerprint  TEXT,
    source_type  TEXT,
    status       TEXT NOT NULL DEFAULT 'CREATED',
    created_at   TEXT NOT NULL DEFAULT (datetime('now')),
    created_by   TEXT NOT NULL DEFAULT 'local-dev'
);

-- Updated-at trigger for maps table
CREATE TRIGGER IF NOT EXISTS trg_maps_updated_at
    AFTER UPDATE ON maps
    FOR EACH ROW
BEGIN
    UPDATE maps SET updated_at = datetime('now')
    WHERE tenant_id = OLD.tenant_id AND map_id = OLD.map_id;
END;
