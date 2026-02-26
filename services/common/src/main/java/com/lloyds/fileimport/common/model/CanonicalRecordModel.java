package com.lloyds.fileimport.common.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Canonical Record Model (CRM) — the single normalized representation of a payment file.
 * <p>
 * Every parser produces it; every output generator consumes it.
 * See architecture.md Section 15.2 for the full specification.
 */
public record CanonicalRecordModel(
        FileEnvelope envelope,
        List<PaymentGroup> paymentGroups,
        ProcessingMetadata metadata
) {

    /**
     * Top-level file wrapper.
     * Maps to: CSV H/T records | XML {@code <GrpHdr>}.
     */
    public record FileEnvelope(
            LocalDateTime creationDate,
            String sequenceNumber,       // CSV H:C. Null for XML.
            Integer transactionCount,    // XML <NbOfTxs>. CSV: derived.
            String messageId             // Fixed: "COMMERCIAL BANKING ONLINE"
    ) {
        public static final String DEFAULT_MESSAGE_ID = "COMMERCIAL BANKING ONLINE";

        public FileEnvelope(LocalDateTime creationDate, String sequenceNumber, Integer transactionCount) {
            this(creationDate, sequenceNumber, transactionCount, DEFAULT_MESSAGE_ID);
        }
    }

    /**
     * One debit + its credits.
     * Maps to: CSV D→C* group | XML {@code <PmtInf>}.
     */
    public record PaymentGroup(
            LocalDate valueDate,
            String debitSortCode,             // 6 digits
            String debitAccountNumber,        // 8 digits
            String debitAccountReference,     // Optional, 6-18 chars
            String paymentInfoId,             // <PmtInfId>. Auto-gen if null.
            String paymentMethod,             // Fixed: "TRA"
            String localInstrument,           // Fixed: "UKBACS"
            List<CreditTransaction> credits,
            Integer sourceLine                // Raw line in source file
    ) {
        public static final String DEFAULT_PAYMENT_METHOD = "TRA";
        public static final String DEFAULT_LOCAL_INSTRUMENT = "UKBACS";

        public PaymentGroup(LocalDate valueDate, String debitSortCode, String debitAccountNumber,
                            String debitAccountReference, List<CreditTransaction> credits) {
            this(valueDate, debitSortCode, debitAccountNumber, debitAccountReference,
                    null, DEFAULT_PAYMENT_METHOD, DEFAULT_LOCAL_INSTRUMENT, credits, null);
        }
    }

    /**
     * One beneficiary payment.
     * Maps to: CSV C-record | XML {@code <CdtTrfTxInf>} | FLT record.
     */
    public record CreditTransaction(
            BigDecimal amount,                // >= 0.01, no commas, pounds with 2dp
            String currency,                  // ISO 4217 (default: GBP)
            String beneficiaryName,           // Max 18 chars
            String beneficiaryAccount,        // Exactly 8 digits
            String beneficiarySortCode,       // Exactly 6 digits, no dashes
            String beneficiaryReference,      // Optional, max 18 chars
            String endToEndId,                // XML-only. Auto-gen or null.
            Integer sourceLine                // Raw line in source file
    ) {
        public static final String DEFAULT_CURRENCY = "GBP";

        public CreditTransaction(BigDecimal amount, String beneficiaryName,
                                 String beneficiaryAccount, String beneficiarySortCode) {
            this(amount, DEFAULT_CURRENCY, beneficiaryName, beneficiaryAccount,
                    beneficiarySortCode, null, null, null);
        }
    }

    /**
     * Non-payment data for tracking and diagnostics.
     */
    public record ProcessingMetadata(
            SourceType sourceType,
            String sourceFileName,
            String sourceFileHash,            // SHA-256
            int recordCount,                  // Total credit transactions parsed
            List<Integer> rawLineRefs,
            List<String> parseWarnings,
            Map<String, String> supplementalValues  // User-supplied prompt responses
    ) {
    }
}
