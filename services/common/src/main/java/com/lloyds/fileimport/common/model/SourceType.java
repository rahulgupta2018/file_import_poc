package com.lloyds.fileimport.common.model;

/**
 * Detected source file type.
 */
public enum SourceType {
    CBO_CSV,        // Lloyds CBO H/D/C/T format
    BACS_XML,       // ISO 20022 pain.001.001.03
    STANDARD_18,    // BACS fixed-length 100-char records
    ERP_CSV         // Non-standard ERP payment-run export
}
