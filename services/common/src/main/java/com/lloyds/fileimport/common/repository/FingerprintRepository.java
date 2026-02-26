package com.lloyds.fileimport.common.repository;

import java.util.Optional;

/**
 * File fingerprint repository — supports reuse/recommendation engine (Section 16.1).
 */
public interface FingerprintRepository {

    record FingerprintEntity(
            String fingerprint,
            String tenantId,
            String fileName,
            String sourceType,
            String firstSeen,
            String lastSeen,
            int importCount,
            String bestMapId
    ) {}

    FingerprintEntity save(FingerprintEntity entity);

    Optional<FingerprintEntity> findByFingerprint(String tenantId, String fingerprint);

    void incrementCount(String tenantId, String fingerprint);
}
