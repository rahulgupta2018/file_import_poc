package com.lloyds.fileimport.common.event;

/**
 * Event types matching the Pub/Sub topics defined in the architecture (Section 8).
 */
public enum EventType {
    FILE_UPLOADED,
    MAP_PROPOSED,
    VALIDATION_DONE,
    FIX_APPLIED,
    MAP_PUBLISHED,
    PROMPT_REQUIRED,
    IMPORT_COMPLETED
}
