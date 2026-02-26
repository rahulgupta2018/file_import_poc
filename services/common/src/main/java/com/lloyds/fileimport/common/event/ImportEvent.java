package com.lloyds.fileimport.common.event;

import java.time.Instant;
import java.util.Map;

/**
 * Internal event payload — used by both the in-process CDI event bus (dev) and Pub/Sub (prod).
 */
public record ImportEvent(
        EventType type,
        String importId,
        String tenantId,
        String correlationId,
        Instant timestamp,
        Map<String, String> attributes  // Additional key-value data (e.g., fileId, fingerprint)
) {
    public ImportEvent(EventType type, String importId, String tenantId, String correlationId) {
        this(type, importId, tenantId, correlationId, Instant.now(), Map.of());
    }
}
