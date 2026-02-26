package com.lloyds.fileimport.common.repository;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SQLite implementation of {@link MapRepository} — active only in the {@code dev} profile.
 */
@ApplicationScoped
@IfBuildProfile("dev")
public class SqliteMapRepository implements MapRepository {

    @Inject
    DataSource dataSource;

    @PostConstruct
    void initSchema() {
        try (var conn = dataSource.getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS maps (
                    map_id           TEXT NOT NULL,
                    import_id        TEXT NOT NULL,
                    tenant_id        TEXT NOT NULL DEFAULT 'local',
                    map_name         TEXT,
                    version          INTEGER NOT NULL DEFAULT 1,
                    status           TEXT NOT NULL DEFAULT 'DRAFT',
                    source_type      TEXT NOT NULL,
                    target_type      TEXT NOT NULL,
                    definition       TEXT NOT NULL,
                    supplemental_values TEXT,
                    fingerprint      TEXT,
                    validation_summary TEXT,
                    created_at       TEXT NOT NULL DEFAULT (datetime('now')),
                    updated_at       TEXT NOT NULL DEFAULT (datetime('now')),
                    created_by       TEXT NOT NULL DEFAULT 'local-dev',
                    PRIMARY KEY (tenant_id, map_id)
                )
                """);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialise SQLite maps table", e);
        }
    }

    @Override
    public MapEntity save(MapEntity entity) {
        var sql = """
            INSERT OR REPLACE INTO maps
                (map_id, import_id, tenant_id, map_name, version, status, source_type, target_type,
                 definition, supplemental_values, fingerprint, validation_summary, created_at, updated_at, created_by)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, datetime('now'), datetime('now'), ?)
            """;
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, entity.mapId());
            ps.setString(2, entity.importId());
            ps.setString(3, entity.tenantId());
            ps.setString(4, entity.mapName());
            ps.setInt(5, entity.version());
            ps.setString(6, entity.status());
            ps.setString(7, entity.sourceType());
            ps.setString(8, entity.targetType());
            ps.setString(9, entity.definition());
            ps.setString(10, entity.supplementalValues());
            ps.setString(11, entity.fingerprint());
            ps.setString(12, entity.validationSummary());
            ps.setString(13, entity.createdBy());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save map", e);
        }
    }

    @Override
    public Optional<MapEntity> findById(String tenantId, String mapId) {
        var sql = "SELECT * FROM maps WHERE tenant_id = ? AND map_id = ?";
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenantId);
            ps.setString(2, mapId);
            var rs = ps.executeQuery();
            return rs.next() ? Optional.of(mapFromRow(rs)) : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find map", e);
        }
    }

    @Override
    public List<MapEntity> findByTenantId(String tenantId) {
        return queryList("SELECT * FROM maps WHERE tenant_id = ? ORDER BY updated_at DESC", tenantId);
    }

    @Override
    public List<MapEntity> findByFingerprint(String tenantId, String fingerprint) {
        var sql = "SELECT * FROM maps WHERE tenant_id = ? AND fingerprint = ? AND status = 'PUBLISHED'";
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenantId);
            ps.setString(2, fingerprint);
            var rs = ps.executeQuery();
            var list = new ArrayList<MapEntity>();
            while (rs.next()) list.add(mapFromRow(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find maps by fingerprint", e);
        }
    }

    @Override
    public List<MapEntity> findByStatus(String tenantId, String status) {
        return queryList("SELECT * FROM maps WHERE tenant_id = ? AND status = ? ORDER BY updated_at DESC", tenantId, status);
    }

    @Override
    public void delete(String tenantId, String mapId) {
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement("DELETE FROM maps WHERE tenant_id = ? AND map_id = ?")) {
            ps.setString(1, tenantId);
            ps.setString(2, mapId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete map", e);
        }
    }

    // --- Helpers ---

    private List<MapEntity> queryList(String sql, String... params) {
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setString(i + 1, params[i]);
            var rs = ps.executeQuery();
            var list = new ArrayList<MapEntity>();
            while (rs.next()) list.add(mapFromRow(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query maps", e);
        }
    }

    private MapEntity mapFromRow(ResultSet rs) throws SQLException {
        return new MapEntity(
                rs.getString("map_id"),
                rs.getString("import_id"),
                rs.getString("tenant_id"),
                rs.getString("map_name"),
                rs.getInt("version"),
                rs.getString("status"),
                rs.getString("source_type"),
                rs.getString("target_type"),
                rs.getString("definition"),
                rs.getString("supplemental_values"),
                rs.getString("fingerprint"),
                rs.getString("validation_summary"),
                rs.getString("created_at"),
                rs.getString("updated_at"),
                rs.getString("created_by")
        );
    }
}
