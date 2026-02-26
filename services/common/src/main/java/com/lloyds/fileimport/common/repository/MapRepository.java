package com.lloyds.fileimport.common.repository;

import java.util.List;
import java.util.Optional;

/**
 * Map repository — stores mapping definitions, versions, and lineage.
 * Dev: SQLite. Prod: Spanner.
 */
public interface MapRepository {

    record MapEntity(
            String mapId,
            String importId,
            String tenantId,
            String mapName,
            int version,
            String status,          // DRAFT | PUBLISHED | ARCHIVED
            String sourceType,
            String targetType,
            String definition,      // JSON string
            String supplementalValues, // JSON string (nullable)
            String fingerprint,
            String validationSummary, // JSON string (nullable)
            String createdAt,
            String updatedAt,
            String createdBy
    ) {}

    MapEntity save(MapEntity entity);

    Optional<MapEntity> findById(String tenantId, String mapId);

    List<MapEntity> findByTenantId(String tenantId);

    List<MapEntity> findByFingerprint(String tenantId, String fingerprint);

    List<MapEntity> findByStatus(String tenantId, String status);

    void delete(String tenantId, String mapId);
}
