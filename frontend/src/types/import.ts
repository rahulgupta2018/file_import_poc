/** Source file types supported by the system */
export type SourceType = 'CBO_CSV' | 'BACS_XML' | 'STANDARD_18' | 'ERP_CSV';

/** Pipeline status states */
export type ImportStatus =
  | 'CREATED'
  | 'PARSING'
  | 'PROPOSING'
  | 'VALIDATING'
  | 'FIXING'
  | 'AWAITING_INPUT'
  | 'APPROVED'
  | 'PUBLISHED'
  | 'COMPLETED'
  | 'FAILED';

/** List-view summary returned by GET /api/import */
export interface ImportSummary {
  importId: string;
  fileName: string;
  sourceType: SourceType;
  status: ImportStatus;
  createdAt: string;
}

/** Detail view returned by GET /api/import/:id */
export interface ImportDetail extends ImportSummary {
  tenantId: string;
  entryCount: number;
  validationSummary?: ValidationSummary;
}

/** Validation summary for an import */
export interface ValidationSummary {
  totalRules: number;
  passed: number;
  failed: number;
  warnings: number;
  autoFixable: number;
}

/** Single validation result row */
export interface ValidationResult {
  ruleId: string;
  severity: 'ERROR' | 'WARNING' | 'INFO';
  passed: boolean;
  location: string;
  message: string;
  suggestion?: string;
  autoFixable: boolean;
}

/** Upload response */
export interface UploadResponse {
  importId: string;
  fileName: string;
  sourceType: SourceType;
}
