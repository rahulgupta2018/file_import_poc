package com.lloyds.fileimport.common.repository;

import java.util.Optional;

/**
 * Import repository — tracks import sessions and their workflow state.
 * Dev: SQLite. Prod: Firestore.
 */
public interface ImportRepository {

    record ImportEntity(
            String importId,
            String tenantId,
            String fileId,
            String fingerprint,
            String sourceType,
            String status,          // ImportStatus enum name
            String createdAt,
            String createdBy
    ) {}

    ImportEntity save(ImportEntity entity);

    Optional<ImportEntity> findById(String importId);

    ImportEntity updateStatus(String importId, String status);
}
