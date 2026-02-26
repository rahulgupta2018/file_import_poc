# Payment File Import — Prototype Feature Guide

> **Version:** 1.2  
> **Date:** 26 February 2026  
> **Audience:** Business stakeholders, product owners, UX reviewers  
> **How to run:** Open `index.html` in any modern browser (Chrome, Edge, Safari, Firefox). No installation required.

---

## Table of Contents

1. [Overview](#1-overview)
2. [Navigation & Layout](#2-navigation--layout)
3. [Screen 1 — Dashboard](#3-screen-1--dashboard)
4. [Screen 2 — Upload](#4-screen-2--upload)
5. [Screen 3 — Import Detail](#5-screen-3--import-detail)
6. [Screen 4 — Mapping Review](#6-screen-4--mapping-review)
7. [Screen 5 — Validation Results](#7-screen-5--validation-results)
8. [Screen 6 — Publish & Approve](#8-screen-6--publish--approve)
9. [Screen 7 — Maps Registry](#9-screen-7--maps-registry)
10. [Modals & Dialogs](#10-modals--dialogs)
11. [User Journey (Happy Path)](#11-user-journey-happy-path)
12. [Status Values Reference](#12-status-values-reference)
13. [Prototype Limitations](#13-prototype-limitations)

---

## 1. Overview

This prototype demonstrates the **Payment File Import Automation** system — a tool that allows operations staff to upload payment files in various formats (ERP CSV, BACS XML, CBO CSV, Standard 18), have them automatically mapped, validated, and converted into the required BACS/CBO output format.

The prototype covers the **complete end-to-end user journey** from file upload through to output download, using realistic mock data based on actual BACS and Lloyds CBO requirements.

**Key capabilities demonstrated:**
- Automated file type detection and schema inference
- AI-powered field mapping with confidence scores
- 85-rule validation engine with clear error/warning reporting in plain language
- Automatic fixes for common issues (padding, casing, truncation, duplicate sequence numbers) — all shown in plain English
- Side-by-side "before" and "after" data previews so you can see exactly what changes
- Missing-field prompting for mandatory BACS fields
- Map publishing and reuse for repeat file formats
- Self-learning: the system remembers your corrections and improves mapping accuracy over time
- Full audit trail of every action

---

## 2. Navigation & Layout

### 2.1 Header Bar (always visible)

| Element | Description |
|---|---|
| **Logo (top-left)** | "Payment File Import v1.0" — click to return to Dashboard |
| **Dashboard tab** | Shows the import history list |
| **Upload tab** | Navigate to file upload screen |
| **Maps Registry tab** | Browse published mapping definitions |
| **Tenant indicator (top-right)** | Shows the current tenant: `lloyds-cbo-uk` |
| **User avatar (top-right)** | Shows logged-in user initials: `RG` |

The active tab is highlighted with a **green underline**. Tabs work like a single-page app — no page reload.

### 2.2 Breadcrumbs

On detail screens (Import Detail, Mapping Review, Validation, Publish), a breadcrumb trail appears at the top:

```
Dashboard › Import a3f1b2c4 › Mapping Review
```

Each segment is clickable to navigate back.

### 2.3 Keyboard Shortcuts

| Key | Action |
|---|---|
| `Escape` | Close any open modal or side panel |

---

## 3. Screen 1 — Dashboard

**Purpose:** Central hub showing all payment file imports and their current status.

### 3.1 Summary Cards (top)

Four metric cards provide an at-a-glance overview:

| Card | Value | Description |
|---|---|---|
| **Total Imports** | 47 | All imports across all statuses |
| **Completed** | 38 | Successfully published, with success rate (80.9%) |
| **In Progress** | 6 | Currently being processed; note "3 awaiting input" |
| **Failed** | 3 | Imports that encountered errors; "2 retryable" |

### 3.2 System Learning Banner (NEW)

Below the summary cards, a **purple banner** shows the system's learning status:

| Element | Description |
|---|---|
| **🧠 System Learning** | Title with brain emoji |
| **Import count** | "Learned from 38 successful imports" — how many past imports inform the AI |
| **Corrections remembered** | "5 mapping corrections" — corrections the user made that the system now applies automatically |
| **Accuracy trend** | "74% → 92%" — improvement in mapping accuracy over time |
| **Stats row** | Three metric blocks: Maps Saved (4), Corrections Remembered (5), Accuracy (92%) |

This banner addresses **Pain Point #7 (Self-Learning)**: users can see that the system is continuously improving from their feedback.

### 3.3 Filters & Search

| Control | What it does |
|---|---|
| **Status dropdown** | Filter by: All, Completed, In Progress, Awaiting Input, Failed |
| **Source Type dropdown** | Filter by: All, CBO CSV, BACS XML, Standard 18, ERP CSV |
| **Search box** | Free-text search across file names |
| **"New Import" button** | Green button → navigates to the Upload screen |

### 3.4 Import History Table

Each row represents one import and contains:

| Column | Description |
|---|---|
| **Import ID** | Short 8-character identifier (e.g., `a3f1b2c4`), monospace font |
| **File Name** | Original uploaded file name |
| **Source Type** | Format badge with icon — `ERP CSV` (blue), `BACS XML` (purple), `CBO CSV` (green), `Standard 18` (orange) |
| **Entries** | Number of payment entries detected in the file |
| **Status** | Colour-coded pill — see [Section 12](#12-status-values-reference) for all values |
| **Map Reuse** | Whether a previously published map was reused: `♻️ Reused`, `🆕 New Map`, or `—` |
| **Created** | Timestamp when the file was uploaded |
| **Arrow icon** | Visual indicator that the row is clickable |

**Interaction:** Click any row → navigates to the **Import Detail** screen for that import.

### 3.4 Pagination

Below the table: "Showing 1-7 of 47 imports" with **Previous / Next** buttons. The "Previous" button is disabled on the first page.

---

## 4. Screen 2 — Upload

**Purpose:** Upload a new payment file for automated processing.

### 4.1 Drop Zone

A large dashed-border area in the centre of the screen. Users can:
- **Drag and drop** a file onto the zone
- **Click** the zone to open a file browser

**Before file selection:**
- Cloud upload icon
- Text: "Drop your payment file here" / "or click to browse"
- Four format labels: `BACS XML`, `CBO CSV`, `Standard 18`, `ERP CSV`

**After file selection:**
- Green checkmark icon replaces the cloud
- File name displayed: `supplier_payments_feb_2026.csv`
- File size: `245.6 KB · CSV`
- "Remove" link to clear the selection

### 4.2 Reuse Recommendation

After a file is selected, a **blue information banner** appears:

> 💡 **Reusable Map Detected**  
> A previously published map "ERP-Supplier-Payments-v3" matches this file structure (92% fingerprint similarity). The system will attempt to reuse it.

This demonstrates the system's ability to recognise repeat file formats and skip redundant mapping work.

### 4.3 Upload & Process Button

- **Disabled** until a file is selected
- **Upload & Process Button** text changes to "Processing…" with status: "Detecting file type → Reading data → Identifying columns → Matching to payment fields…"
- Button text changes to "Processing…" with a spinner
- On completion → automatically navigates to the **Import Detail** screen

### 4.4 Back Link

"← Back to Dashboard" link at bottom-left.

---

## 5. Screen 3 — Import Detail

**Purpose:** Overview of a single import session — its current pipeline stage, key metrics, and available actions.

### 5.1 Header

- **File name** as page title with status pill (e.g., `Awaiting Input`)
- Full **Import ID** (UUID format)
- Upload timestamp

### 5.2 Pipeline Progress Stepper

A horizontal visual stepper showing all 7 stages of the processing pipeline:

| Step | Visual State | Meaning |
|---|---|---|
| **Upload** | ✅ Green (done) | File received and stored |
| **Read File** | ✅ Green (done) | File parsed, type auto-detected |
| **Match Fields** | ✅ Green (done) | AI-generated field mapping proposal |
| **Validate** | ✅ Green (done) | 85 validation checks run |
| **Your Input** | 🟡 Amber (current, pulsing) | Awaiting user input for missing fields |
| **Approve** | ⬜ Grey (pending) | Not yet reached |
| **Publish** | ⬜ Grey (pending) | Not yet reached |

Completed steps are connected by green lines; pending steps by grey lines. The current step pulses to draw attention.

### 5.3 Info Cards (4-column grid)

| Card | Value | Sub-text |
|---|---|---|
| **Source Type** | ERP CSV | "Auto-detected" |
| **Payment Entries** | 156 | "12 columns detected" |
| **Validation** | 3 errors · 8 warnings | "5 auto-fixable" |
| **Map Confidence** | 87% | "10/12 fields high confidence" |

### 5.4 Missing Fields Alert

An **amber warning banner** appears when the pipeline is in `AWAITING_INPUT` state:

> ⚠️ **Action Required: Missing Mandatory Fields**  
> This ERP CSV file is missing 2 mandatory BACS fields that must be provided before the import can proceed.  
> **[Provide Missing Fields →]** button

Clicking the button opens the **Missing Fields Modal** (see [Section 10.1](#101-missing-fields-modal)).

### 5.5 Action Cards (3-column grid)

| Card | Description | State |
|---|---|---|
| **Review Mapping** | \"View auto-map proposal with match quality\" | Active — click to go to Mapping Review |
| **View Validation** | \"4 errors, 8 warnings — 6 auto-fixable\" | Active — click to go to Validation Results |
| **Download Output** | "Available after publish" | Disabled/greyed out — only enabled after publish |

Each card has a hover effect (green border + shadow) and an icon that turns green on hover.

---

## 6. Screen 4 — Mapping Review

**Purpose:** Review and adjust the AI-generated field mapping proposal before proceeding.

### 6.1 Smart Mapping — Learning Banner (NEW)

A **purple banner** at the top of the mapping screen shows how past imports inform the current proposal:

| Element | Description |
|---|---|
| **🧠 Smart Mapping** | Banner title with brain emoji |
| **History depth** | "Built using patterns learned from 38 successful imports" |
| **Remembered corrections** | "Mappings you fixed before won't need fixing again" |
| **Stats** | ✅ 14 mappings confirmed · 🔄 2 corrections remembered · 📊 92% accuracy (up from 74%) |

This addresses **Pain Point #7 (Self-Learning)**: the system explicitly tells users it remembers their corrections and gets more accurate over time.

**Per-row learning indicators** also appear on specific mapping rows:
- **🧠 Learned** (purple text) — on rows where the mapping was confirmed by 14+ previous imports (e.g., Row 1 Supplier Name → Beneficiary Name)
- **🔄 Remembered** (purple text) — on rows where the user previously corrected the mapping and the system now applies that correction automatically (e.g., Row 9 Payee Email → Beneficiary Reference)

### 6.2 Confidence Summary (3-column)

| Category | Count | Description |
|---|---|---|
| **Good Match** (green) | 10 | Very likely correct |
| **Needs Review** (yellow) | 1 | Please check this one |
| **Uncertain Match** (red) | 1 | May need to change this |

### 6.3 Field Mapping Table

Each row shows one field mapping:

| Column | Description |
|---|---|
| **#** | Row number (1–12) |
| **Your File Column** | The column header from the uploaded file, shown in a blue pill |
| **→** | Arrow indicator |
| **BACS Payment Field** | The target BACS/CBO field, shown in a green pill |
| **Match Quality** | Colour-coded label: `✅ Good match` (green), `⚠️ Needs review` (yellow), `❌ No match` (red) |
| **What will change** | Plain English description of data changes, e.g., "Convert to uppercase, trim to 18 chars", "Remove hyphens, pad to 6 digits", "Convert pounds to pence (× 100)" |
| **Action** | `Edit` link (good match), `Review ⚠️` (needs review), `Assign ✏️` (no match) |

### 6.4 Notable Mappings Demonstrated

| Row | What it shows |
|---|---|
| Row 4: Amount (GBP) → Amount (Pence) | The system detected pounds vs. pence and will "Convert pounds to pence (× 100)" |
| Row 8: Currency → (validated only) | Field is not mapped to output but is checked (“Check currency is GBP”) |
| Row 9: Payee Email → Beneficiary Reference | **Needs review** — flagged with ⚠️. The AI is uncertain about this mapping |
| Row 10: Internal Cost Centre → No match | **Uncertain match** — no target field found. User must manually assign or confirm it should be skipped |
| Rows 11-12: Row Index, Supplier Code | **Skipped columns** — shown at reduced opacity with “Not needed for BACS”. These are ERP-internal fields with no BACS equivalent |

### 6.5 Checkboxes

- **Show skipped columns** (checked by default) — toggle visibility of skipped fields
- **Show what will change** — toggle the "What will change" column detail

### 6.6 Your Original Data (Source Preview)

Below the mapping table, a preview of the first 3 rows of your raw uploaded data is displayed in a monospace table, showing:

| Row | Supplier Name | Bank Sort Code | Account Number | Amount (GBP) | Payment Reference | Payment Date |
|---|---|---|---|---|---|---|
| 1 | Acme Supplies Ltd | 20-45-67 | 12345678 | 1,250.00 | INV-2026-0451 | 28/02/2026 |
| 2 | Global Services Inc | 30-12-89 | 87654321 | 3,750.50 | PO-8892 | 28/02/2026 |
| 3 | UK Office Furniture | 40-22-11 | 5678901 | 890.00 | FRN-2026-Q1 | 28/02/2026 |

This helps users verify that the mapping makes sense by seeing actual data values.

### 6.7 Target Data Preview (NEW)

Directly below the source preview, a **green-tinted table** shows what the same 3 rows will look like **after** all mappings and transforms are applied:

| Row | Beneficiary Name | Dest. Sort Code | Dest. Account No. | Amount (Pence) | Beneficiary Ref | Processing Date |
|---|---|---|---|---|---|---|
| 1 | ACME SUPPLIES LTD | 204567 | 12345678 | 125000 | INV-2026-0451 | 20260228 |
| 2 | GLOBAL SERVICES INC | 301289 | 87654321 | 375050 | PO-8892 | 20260228 |
| 3 | UK OFFICE FURNITURE | 402211 | 05678901 | 89000 | FRN-2026-Q1 | 20260228 |

**Key features:**
- Green background distinguishes it from the grey source preview above
- **Green-highlighted cells** indicate values that were transformed (e.g., uppercase, hyphens removed, pounds converted to pence, date reformatted)
- Columns not mapped (Internal ID, Payment Type, Supplier Code) are dropped from the output
- A footer note explains: "Green-highlighted values have been transformed from the source data above"

This side-by-side comparison lets users immediately see the effect of every mapping and transform.

### 6.8 Actions

- **"Accept All Mappings"** button (top-right, green) — approve the entire proposal
- **"← Back"** button — return to Import Detail

---

## 7. Screen 5 — Validation Results

**Purpose:** Show all validation findings, allow auto-fix, and support re-validation.

### 7.1 Validation Summary Bar (5-column)

| Metric | Value | Colour |
|---|---|---|
| Checks Run | 85 | Grey |
| Passed | 74 | Green |
| Errors | 4 | Red |
| Warnings | 8 | Amber |
| Can Be Fixed Automatically | 6 | Blue |

### 7.2 Filter Tabs

Four toggle buttons to filter the table:
- **All (11)** — show everything (default, green active state)
- **Errors (4)** — show only errors
- **Warnings (8)** — show only warnings
- **Can Be Fixed Automatically (6)** — show only issues that can be automatically fixed

### 7.3 Validation Results Table

Each row represents one validation finding:

| Column | Description |
|---|---|
| **Check** | Rule identifier, e.g., `VR-SC-001`, in monospace |
| **Severity** | `Error` (red pill) or `Warning` (amber pill) |
| **Area** | `Payee Details`, `File Structure`, or `Payment` — grouped by what the check relates to |
| **Where** | Where the issue was found: `Row 47, Col C` or `Trailer row` |
| **What's wrong** | Plain English explanation of what failed |
| **Current Value** | The actual data value that triggered the error, in a code pill |
| **Fix** | Either `🔧 Fix` (clickable, green) for auto-fixable issues, or `Manual` (grey) |
| **ℹ️** | Info button — opens the Explainability Panel (see [Section 10.3](#103-explainability-panel)) |

### 7.4 Validation Issues Demonstrated

| Rule ID | Severity | Issue | Auto-Fix? |
|---|---|---|---|
| `VR-SC-001` | Error | Sort code `4022-1` is only 5 digits with a hyphen (needs 6 digits) | Yes — strip hyphens, left-pad to 6 |
| `VR-AN-001` | Error | Account number `5678901` is 7 digits (needs 8) | Yes — left-pad with zero |
| `VR-AMT-003` | Error | Amount £21.5M exceeds the £20M single-transaction limit | No — manual review required |
| `VR-HT-001` | Error | Header sequence number `00047` was already used in a previous import today — duplicates cause BACS rejection | Yes — auto-generate next available unique sequence number (`00048`) |
| `VR-BEN-002` | Warning | Beneficiary names in lowercase (BACS requires uppercase) — affects 3 rows | Yes — apply UPPER_CASE |
| `VR-REF-001` | Warning | Reference exceeds 18 characters — affects 2 rows | Yes — truncate to 18 |
| `VR-DUP-001` | Warning | Possible duplicate: same beneficiary, amount, reference on consecutive rows | No |
| `VR-HT-002` | Warning | Trailer record count (155) doesn't match actual detail rows (156) | No |
| `VR-DATE-001` | Warning | Processing date falls on a Saturday (BACS doesn't process weekends) | No |
| `VR-CHAR-001` | Warning | Beneficiary name `Café Royal` contains non-BACS character `é` | No |
| `VR-TC-002` | Warning | Transaction code "01" — verify if ordinary credit or priority payment | No |

### 7.5 Inline Fix Preview

Clicking **"🔧 Fix"** on any auto-fixable row expands an inline panel showing:
- **Before value** (red, strikethrough): e.g., `4022-1`
- **Arrow** (→)
- **After value** (green): e.g., `402211`
- **Transform name**: e.g., "Remove hyphens, pad digits"
- **"Apply Fix"** button

### 7.6 Action Buttons (top-right)

| Button | Description |
|---|---|
| **← Back** | Return to Import Detail |
| **🔧 Fix 6 Issues Automatically** | Opens the Auto-Fix Preview modal (see [Section 10.4](#104-auto-fix-preview-modal)) to batch-review all fixable issues |
| **🔄 Re-check All** | Trigger a fresh validation run |

---

## 8. Screen 6 — Publish & Approve

**Purpose:** Final review gate before the map is published and the output file is generated.

### 8.1 Map Name Input

A text field where the user names the map for future reuse: `ERP-Supplier-Payments-v4`. This name appears in the Maps Registry.

### 8.2 Mapping Summary

| Field | Value |
|---|---|
| Source Format | ERP CSV |
| Target Format | CBO CSV (BACS) |
| Fields Mapped | 10 of 12 (2 ignored) |
| Transforms Applied | 8 changes to your data |
| High Confidence | 10 fields |
| User Overrides | 1 field corrected |

### 8.3 Validation Summary

Visual summary with coloured dots:
- ✅ 74 Passed
- 🔴 4 Errors → 3 Auto-Fixed, 1 Manual
- 🟡 8 Warnings → 3 Auto-Fixed, 5 Acknowledged

Green success banner: "All critical errors resolved. File is safe to publish."

### 8.4 User-Supplied Values

Table showing the values the user provided for missing fields:

| Field | Value |
|---|---|
| Debit Sort Code | `309634` |
| Debit Account Number | `12345678` |
| Processing Date | `02/03/2026 (Monday)` |

### 8.5 Audit Trail

Chronological timeline of every action taken on this import:

| Time | Event |
|---|---|
| 09:14 | File uploaded by RG |
| 09:14 | Auto-detected as ERP CSV (156 entries) |
| 09:15 | Schema inferred, 12 fields detected |
| 09:15 | Map proposal generated (87% avg confidence) |
| 09:16 | Validation run: 3 errors, 8 warnings |
| 09:17 | Auto-fix applied: 5 issues resolved |
| 09:18 | User supplied 3 missing fields |
| 09:18 | Re-validation passed (0 errors, 5 warnings acknowledged) |

### 8.6 Review Checklist (right sidebar)

A sticky checklist showing all prerequisites are met:
- ✅ All mappings reviewed
- ✅ Validation passed
- ✅ Missing fields provided
- ✅ Auto-fixes applied

### 8.7 Options

- ☑️ **Save this map for future reuse** (checked by default)
- ☐ **Remember supplemental values as defaults**

### 8.8 Action Buttons

| Button | Description |
|---|---|
| **✅ Approve & Publish** | Publishes the map and triggers output generation. Opens success modal |
| **✗ Reject** | Cancels the publish. Returns to Import Detail |

### 8.9 Output Info

Note at the bottom: "Output: CBO CSV will be generated with 156 payment entries and made available for download."

---

## 9. Screen 7 — Maps Registry

**Purpose:** Browse and manage published mapping definitions that can be reused for future imports.

### 9.1 Search

Search box to filter maps by name.

### 9.2 Map Cards

Each published map is shown as a card with:

| Field | Description |
|---|---|
| **Map Name** | Friendly name, e.g., `ERP-Supplier-Payments-v3` |
| **Direction** | Source → Target format, e.g., `ERP CSV → CBO CSV` |
| **Status badge** | `Active` (green) or `Deprecated` (grey) |
| **Fields mapped** | Number of field mappings |
| **Transforms** | Number of transforms in the map |
| **Times reused** | How many imports have used this map |
| **Last used** | Date of most recent reuse |
| **Version** | Current version number |
| **Created by / date** | Author and creation date |
| **"View Details →"** | Link to see the full map definition |

### 9.3 Maps Demonstrated

| Map | Format | Reuse Count | Notes |
|---|---|---|---|
| ERP-Supplier-Payments-v3 | ERP CSV → CBO CSV | 14 | Most recent supplier payment map |
| BACS-Payroll-Monthly | BACS XML → CBO CSV | 28 | High-reuse payroll map |
| Std18-Fixed-Payroll | Standard 18 → CBO CSV | 7 | Fixed-length format |
| CBO-Bulk-Direct-Debit | CBO CSV → CBO CSV (v2) | 3 | Deprecated map |

---

## 10. Modals & Dialogs

### 10.1 Missing Fields Modal

**Trigger:** Click "Provide Missing Fields →" on the Import Detail screen.

**Purpose:** Collect mandatory BACS fields that are absent from the uploaded source file (common with ERP CSVs).

**Fields:**

| Field | Required | Format | Description |
|---|---|---|---|
| Debit Account Sort Code | Yes | `SS-SS-SS` or `SSSSSS` | The originating bank branch |
| Debit Account Number | Yes | 8 digits | The originating account |
| Debit Account Reference | No | 6–18 chars | Optional reference |
| Processing Date | Yes | Date picker | Must be a BACS working day (Mon–Fri, excl. bank holidays) |

**Checkbox:** "Save these values as defaults for future ERP imports" — if checked, these values auto-populate next time.

**Buttons:** Cancel / Submit & Continue (navigates to Publish screen after submission)

---

### 10.2 Publish Success Modal

**Trigger:** Click "Approve & Publish" on the Publish screen.

**Shows:**
- Green checkmark icon
- "Published Successfully!"
- Map name: `ERP-Supplier-Payments-v4`
- Output file: `CBO_a3f1b2c4.csv` (156 entries)

**Download buttons:**
- ⬇️ Download CBO CSV Output
- ⬇️ Download BACS XML Output
- Back to Dashboard link

---

### 10.3 Explainability Panel

**Trigger:** Click the **ℹ️** icon on any validation row.

**Type:** Slide-in drawer from the right side of the screen.

**Sections:**

| Section | Content |
|---|---|
| **Rule** | Rule ID heading (e.g., `VR-SC-001`) |
| **Description** | Detailed technical explanation of the rule |
| **CBO Guide Reference** | Exact citation from the Lloyds CBO Guide (e.g., "Table 5 — Mandatory Fields, Row 'Sort Code'") |
| **Current Value** | The failing value displayed in a red code block |
| **Problem** | Bullet list of what's wrong (e.g., "Contains a hyphen character", "Only 5 numeric digits") |
| **Suggested Fix** | Before/after diff with plain-English description of what was done |
| **Confidence note** | "This fix is safe" |
| **Rule Configuration** | Simplified one-line technical reference (internal rule ID, name, type, severity) |

This panel demonstrates the system's **explainability** — every validation decision can be traced back to the source rule and the CBO specification. The technical YAML rule definition has been replaced with a simplified summary for better readability.

---

### 10.4 Auto-Fix Preview Modal

**Trigger:** Click "🔧 Fix 6 Issues Automatically" on the Validation Results screen.

**Purpose:** Batch-review all automatic fixes before applying them.

**Shows a list of fixes with plain-English descriptions:**

| Rule | Location | Before | → | After | What it does |
|---|---|---|---|---|---|
| VR-SC-001 | Row 47 | `4022-1` | → | `402211` | Remove hyphens, pad digits |
| VR-AN-001 | Row 112 | `5678901` | → | `05678901` | Add leading zero |
| VR-BEN-002 | 3 rows | `Acme Ltd` | → | `ACME LTD` | Convert to uppercase |
| VR-REF-001 | 2 rows | `INV-2026-0451-SUPP-A` | → | `INV-2026-0451-SUPP` | Shorten to 18 chars |
| VR-HT-001 | Header | `00047` | → | `00048` | Next unique sequence number |

**Note:** "All fixes are safe and repeatable. The system will re-check everything automatically after applying."

**Buttons:** Cancel / **Apply All 6 Fixes**

---

## 11. User Journey (Happy Path)

Follow this clickable path to experience the full workflow:

```
Step 1:  Dashboard  →  Click "New Import" button (top-right)
                       OR click the "Upload" tab in the header

Step 2:  Upload     →  Click the drop zone (simulates file selection)
                       Notice the reuse recommendation banner
                   →  Click "Upload & Process"
                       Watch the progress bar animate

Step 3:  Import Detail  →  Review the pipeline stepper (Input stage is active)
                        →  Click "Provide Missing Fields →"
                            Fill in sort code, account number, date
                        →  Click "Submit & Continue"

Step 4:  Publish    →  Review all summaries (mapping, validation, audit)
                   →  Click "Approve & Publish"
                       See the success modal with download options
                   →  Click "Back to Dashboard"

Alternative from Step 3:
         Import Detail →  Click "Review Mapping" card
         Mapping Review → Review confidence scores, transforms, source preview
                       →  Click "← Back"

         Import Detail →  Click "View Validation" card
         Validation    →  Click "🔧 Fix" on any auto-fixable row to see inline preview
                       →  Click "ℹ️" on any row to open the explainability panel
                       →  Click "🔧 Auto-Fix 6 Issues" to batch-preview all fixes
                       →  Click "← Back"

Also try:  Maps Registry  →  Browse the 4 published map cards
```

---

## 12. Status Values Reference

| Status | Colour | Meaning |
|---|---|---|
| `CREATED` | Indigo | File uploaded, queued for processing |
| `PARSING` | Violet | File being parsed and type detected |
| `PROPOSING` | Purple | AI inferring schema and generating map proposal |
| `VALIDATING` | Amber | 85 validation rules being evaluated |
| `FIXING` | Orange | Auto-fix loop active (deterministic transforms) |
| `AWAITING_INPUT` | Red/Amber | Missing mandatory fields — user must provide input |
| `APPROVED` | Emerald | User approved, publishing in progress |
| `PUBLISHED` | Green | Map saved, output file generated |
| `COMPLETED` | Dark green | Full pipeline complete |
| `FAILED` | Red | Error at any stage (may be retryable) |

---

## 13. Prototype Limitations

This is a **static HTML prototype** for stakeholder feedback. The following are intentional limitations:

| Area | Limitation |
|---|---|
| **Backend** | No live API — all data is hardcoded mock data |
| **File upload** | No actual file is uploaded; clicking the drop zone simulates a file selection |
| **Filters** | Status and source type dropdowns are visual only; they don't filter the table |
| **Search** | The search box is visual only |
| **Pagination** | "Next" button is visual only |
| **Edit mappings** | "Edit", "Review", "Assign" links in the mapping table are visual only |
| **Apply fixes** | Fix buttons show the preview but don't update the validation table |
| **Download** | Download buttons are visual only — no file is generated |
| **Real-time updates** | No WebSocket/SSE — the pipeline stepper is static |
| **Authentication** | No login screen; user is hardcoded as "RG" |
| **Responsive** | Designed for desktop (1280px+); mobile layout is limited |

### What to focus on during review:

1. **Does the workflow make sense?** Upload → Map → Validate → Fix → Publish
2. **Is the information hierarchy clear?** Can you quickly find what you need?
3. **Are the validation messages understandable?** Would a CBO operations user know what to do?
4. **Is the confidence scoring useful?** High / Medium / Low — does colour-coding help?
5. **Is the auto-fix preview sufficient?** Can you trust what the system will change?
6. **Is the explainability panel helpful?** Can you trace why something failed?
7. **Is the Maps Registry useful?** Would you use this to manage reusable mappings?
8. **Is the learning feedback visible?** Can you tell the system is improving from your corrections? (🧠 Dashboard banner, per-row "Learned" / "Remembered" labels)
9. **Is the header sequence fix clear?** Does the auto-increment for duplicate sequence numbers make sense?
10. **Missing anything?** Are there screens, fields, or actions you'd expect to see?
