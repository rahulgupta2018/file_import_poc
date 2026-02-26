package com.lloyds.fileimport.common.model;

/**
 * Import workflow states — matches the orchestration state machine (Section 5.8).
 */
public enum ImportStatus {
    CREATED,
    PARSING,
    PROPOSING,
    VALIDATING,
    FIXING,
    AWAITING_INPUT,
    APPROVED,
    PUBLISHED,
    COMPLETED,
    FAILED
}
