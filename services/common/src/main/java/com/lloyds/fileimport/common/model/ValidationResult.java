package com.lloyds.fileimport.common.model;

import java.time.Instant;

/**
 * Validation rule result produced by the Rules Engine.
 */
public record ValidationResult(
        String ruleId,          // e.g., VR-DATE-001
        Severity severity,
        boolean passed,
        String location,        // e.g., "line 4, column B" or "<PmtInf>[0]/<ReqdExctnDt>"
        String message,         // Human-readable explanation
        String suggestion,      // Suggested fix text (nullable)
        boolean autoFixable
) {

    public enum Severity {
        ERROR,
        WARN
    }
}
