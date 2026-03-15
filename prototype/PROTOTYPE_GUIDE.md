# Payment File Import — Prototype Feature Guide

> **Version:** 1.3  
> **Date:** 15 March 2026  
> **Audience:** Business stakeholders, product owners, UX reviewers  
> **How to run:** Open `index.html` in any modern browser (Chrome, Edge, Safari, Firefox). No installation required.

---

## Table of Contents

1. [Overview](#1-overview)
2. [Navigation & Layout](#2-navigation--layout)
3. [Screen 0 — Landing Page (NEW)](#3-screen-0--landing-page-new)
4. [Screen 0a — Manual Import Holding Page (NEW)](#4-screen-0a--manual-import-holding-page-new)
5. [Screen 0b — AI Import Warning Page (NEW)](#5-screen-0b--ai-import-warning-page-new)
6. [Screen 1 — Dashboard](#6-screen-1--dashboard)
7. [Screen 2 — Upload](#7-screen-2--upload)
8. [Screen 3 — Import Detail](#8-screen-3--import-detail)
9. [Screen 4 — Mapping Review](#9-screen-4--mapping-review)
10. [Screen 5 — Validation Results](#10-screen-5--validation-results)
11. [Screen 6 — Publish & Approve](#11-screen-6--publish--approve)
12. [Screen 7 — Maps Registry](#12-screen-7--maps-registry)
13. [Modals & Dialogs](#13-modals--dialogs)
14. [User Journey (Happy Path)](#14-user-journey-happy-path)
15. [Status Values Reference](#15-status-values-reference)
16. [Prototype Limitations](#16-prototype-limitations)

---

## 1. Overview

This prototype demonstrates the **Payment File Import Automation** system — a tool that allows clients to upload payment files in various formats (ERP CSV, BACS XML, CBO CSV, Standard 18), have them automatically mapped, validated, and converted into the required Lloyds CBO required output format.

The prototype covers the **complete end-to-end user journey** from file upload through to output download, using realistic mock data based on actual BACS and Lloyds CBO requirements.

**Key capabilities demonstrated:**
- **Landing page** with choice between Manual and AI Assisted import modes
- **AI import onboarding** — a warning/explainer page so users understand what AI will do before proceeding
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
| **Logo (top-left)** | "Payment File Import v1.0" — click to return to the **Landing Page** |
| **Dashboard tab** | Shows the import history list |
| **Upload tab** | Navigate to file upload screen |
| **Maps Registry tab** | Browse published mapping definitions |
| **Tenant indicator (top-right)** | Shows the current tenant: `lloyds-cbo-uk` |
| **User avatar (top-right)** | Shows logged-in user initials: `RG` |

The active tab is highlighted with a **green underline**. Tabs work like a single-page app — no page reload.

> **Note (v1.3):** The logo now navigates to the Landing Page instead of the Dashboard, reflecting the new entry-point flow.

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

## 3. Screen 0 — Landing Page (NEW)

> Added in v1.3

**Purpose:** Entry point for the application. Lets the user choose between two import modes before proceeding.

### 3.1 Page Layout

- Centred layout with a document icon, page title "Payment File Import", and subtitle "Choose how you'd like to import your payment file"
- Two side-by-side cards presented as large buttons

### 3.2 Import Mode Cards

| Card | Style | Icon | Description | Action |
|---|---|---|---|---|
| **Manual File Import** | Grey border, grey icon | Document icon | "Use the traditional file import process with manual field mapping, validation, and review steps." | Navigates to the **Manual Import Holding Page** |
| **AI Assisted File Import** | Green border, "AI Powered" badge (top-right), Lloyds green icon | Lightning bolt / AI sparkle icon (`icons/ai-import-icon-2.svg`) | "Let AI automatically detect file types, map fields, validate data, and fix common issues — with your review and approval." | Navigates to the **AI Import Warning Page** |

The AI card is visually distinguished with a green border, an "AI Powered" pill badge, and the custom sparkle icon.

### 3.3 Footer Note

"Both methods support BACS XML, CBO CSV, Standard 18, and ERP CSV file formats."

---

## 4. Screen 0a — Manual Import Holding Page (NEW)

> Added in v1.3

**Purpose:** Placeholder page for the traditional manual import workflow (not yet implemented in this prototype).

### 4.1 Breadcrumb

```
File Import › Manual Import
```

### 4.2 Content

- Large document/archive icon (grey)
- Title: "Traditional Manual File Import"
- Description explaining that manual import requires step-by-step field mapping, validation, and fixes
- A numbered list of the 5 manual import steps:
  1. Upload & select file format manually
  2. Map each column to the target field
  3. Review validation errors one by one
  4. Fix issues manually & re-validate
  5. Approve & publish output file

### 4.3 Buttons

| Button | Description |
|---|---|
| **← Back** | Returns to the Landing Page |
| **Coming Soon** | Disabled grey button — indicates manual import screens are not yet available |

A note below states: "Manual import screens are not yet available in this prototype. Please use AI Assisted Import to experience the automated workflow."

---

## 5. Screen 0b — AI Import Warning Page (NEW)

> Added in v1.3

**Purpose:** Explains what the AI-assisted import process will do, so users understand the new workflow before proceeding. Acts as an onboarding/consent gate.

### 5.1 Breadcrumb

```
File Import › AI Assisted Import
```

### 5.2 Header Banner

A green gradient banner (Lloyds green → accent green) with:
- Lightning bolt icon
- Title: "AI Assisted File Import"
- Subtitle: "Understand what happens when you use AI to process your payment file"

### 5.3 Process Steps

A numbered list of 5 steps explaining what AI will do:

| Step | Title | Description |
|---|---|---|
| **1** | Detect File Type Automatically | System reads the file and identifies the format — no manual selection needed |
| **2** | Map Fields Using AI & Past Imports | AI matches columns to BACS fields using patterns from previous imports and user corrections |
| **3** | Validate Against 85 BACS Rules | Every entry checked against sort code, account, amount, date, and format rules — explained in plain English |
| **4** | Auto-Fix Common Issues | Safe, deterministic fixes suggested (padding, uppercase, date reformatting) — user reviews and approves each one |
| **5** | You Stay In Control | Nothing published without explicit approval; mappings can be overridden; changes can be rejected at any step |

### 5.4 Important Notice

An **amber warning box** with the following bullet points:
- AI-generated mappings should always be reviewed before approval
- The system learns from your corrections to improve accuracy over time
- All actions are logged in a full audit trail for compliance
- No data leaves your organisation — processing is on-premises

### 5.5 Action Buttons

| Button | Description |
|---|---|
| **Cancel** | Returns to the Landing Page |
| **Proceed with AI Import** | Triggers the AI import flow: navigates to the Upload screen, auto-selects a file, runs the upload progress bar, then navigates to the Dashboard |

### 5.6 AI Import Flow (after clicking Proceed)

When the user clicks "Proceed with AI Import":
1. The Upload screen is shown
2. A file is auto-selected (simulated) after a brief delay
3. The upload progress bar animates (file processing simulation)
4. On completion, the user is taken to the **Dashboard** where they can see import history and click into any import

This provides a seamless demo experience without requiring the user to manually interact with the upload screen.

---

## 6. Screen 1 — Dashboard

**Purpose:** Central hub showing all payment file imports and their current status.

### 6.1 Summary Cards (top)

Four metric cards provide an at-a-glance overview:

| Card | Value | Description |
|---|---|---|
| **Total Imports** | 47 | All imports across all statuses |
| **Completed** | 38 | Successfully published, with success rate (80.9%) |
| **In Progress** | 6 | Currently being processed; note "3 awaiting input" |
| **Failed** | 3 | Imports that encountered errors; "2 retryable" |

### 6.2 System Learning Banner

Below the summary cards, a **purple banner** shows the system's learning status:

| Element | Description |
|---|---|
| **🧠 System Learning** | Title with brain emoji |
| **Import count** | "Learned from 38 successful imports" — how many past imports inform the AI |
| **Corrections remembered** | "5 mapping corrections" — corrections the user made that the system now applies automatically |
| **Accuracy trend** | "74% → 92%" — improvement in mapping accuracy over time |
| **Stats row** | Three metric blocks: Maps Saved (4), Corrections Remembered (5), Accuracy (92%) |

This banner addresses **Pain Point #7 (Self-Learning)**: users can see that the system is continuously improving from their feedback.

### 6.3 Filters & Search

| Control | What it does |
|---|---|
| **Status dropdown** | Filter by: All, Completed, In Progress, Awaiting Input, Failed |
| **Source Type dropdown** | Filter by: All, CBO CSV, BACS XML, Standard 18, ERP CSV |
| **Search box** | Free-text search across file names |
| **"New Import" button** | Green button → navigates to the Upload screen |

### 6.4 Import History Table

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

### 6.5 Pagination

Below the table: "Showing 1-7 of 47 imports" with **Previous / Next** buttons. The "Previous" button is disabled on the first page.

---

## 7. Screen 2 — Upload

**Purpose:** Upload a new payment file for automated processing.

### 7.1 Drop Zone

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

### 7.2 Reuse Recommendation

After a file is selected, a **blue information banner** appears:

> 💡 **Reusable Map Detected**  
> A previously published map "ERP-Supplier-Payments-v3" matches this file structure (92% fingerprint similarity). The system will attempt to reuse it.

This demonstrates the system's ability to recognise repeat file formats and skip redundant mapping work.

### 7.3 Upload & Process Button

- **Disabled** until a file is selected
- **Upload & Process Button** text changes to "Processing…" with status: "Detecting file type → Reading data → Identifying columns → Matching to payment fields…"
- Button text changes to "Processing…" with a spinner
- On completion → automatically navigates to the **Import Detail** screen

### 7.4 Back Link

"← Back to Dashboard" link at bottom-left.

---

## 8. Screen 3 — Import Detail

**Purpose:** Overview of a single import session — its current pipeline stage, key metrics, and available actions.

### 8.1 Header

- **File name** as page title with status pill (e.g., `Awaiting Input`)
- Full **Import ID** (UUID format)
- Upload timestamp

### 8.2 Pipeline Progress Stepper

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

### 8.3 Info Cards (4-column grid)

| Card | Value | Sub-text |
|---|---|---|
| **Source Type** | ERP CSV | "Auto-detected" |
| **Payment Entries** | 156 | "12 columns detected" |
| **Validation** | 3 errors · 8 warnings | "5 auto-fixable" |
| **Map Confidence** | 87% | "10/12 fields high confidence" |

### 8.4 Missing Fields Alert

An **amber warning banner** appears when the pipeline is in `AWAITING_INPUT` state:

> ⚠️ **Action Required: Missing Mandatory Fields**  
> This ERP CSV file is missing 2 mandatory BACS fields that must be provided before the import can proceed.  
> **[Provide Missing Fields →]** button

Clicking the button opens the **Missing Fields Modal** (see [Section 13.1](#131-missing-fields-modal)).

### 8.5 Action Cards (3-column grid)

| Card | Description | State |
|---|---|---|
| **Review Mapping** | \"View auto-map proposal with match quality\" | Active — click to go to Mapping Review |
| **View Validation** | \"4 errors, 8 warnings — 6 auto-fixable\" | Active — click to go to Validation Results |
| **Download Output** | "Available after publish" | Disabled/greyed out — only enabled after publish |

Each card has a hover effect (green border + shadow) and an icon that turns green on hover.

---

## 9. Screen 4 — Mapping Review

**Purpose:** Review and adjust the AI-generated field mapping proposal before proceeding.

### 9.1 Smart Mapping — Learning Banner (NEW)

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

### 9.2 Confidence Summary (3-column)

| Category | Count | Description |
|---|---|---|
| **Good Match** (green) | 10 | Very likely correct |
| **Needs Review** (yellow) | 1 | Please check this one |
| **Uncertain Match** (red) | 1 | May need to change this |

### 9.3 Field Mapping Table

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

### 9.4 Notable Mappings Demonstrated

| Row | What it shows |
|---|---|
| Row 4: Amount (GBP) → Amount (Pence) | The system detected pounds vs. pence and will "Convert pounds to pence (× 100)" |
| Row 8: Currency → (validated only) | Field is not mapped to output but is checked (“Check currency is GBP”) |
| Row 9: Payee Email → Beneficiary Reference | **Needs review** — flagged with ⚠️. The AI is uncertain about this mapping |
| Row 10: Internal Cost Centre → No match | **Uncertain match** — no target field found. User must manually assign or confirm it should be skipped |
| Rows 11-12: Row Index, Supplier Code | **Skipped columns** — shown at reduced opacity with “Not needed for BACS”. These are ERP-internal fields with no BACS equivalent |

### 9.5 Checkboxes

- **Show skipped columns** (checked by default) — toggle visibility of skipped fields
- **Show what will change** — toggle the "What will change" column detail

### 9.6 Your Original Data (Source Preview)

Below the mapping table, a preview of the first 3 rows of your raw uploaded data is displayed in a monospace table, showing:

| Row | Supplier Name | Bank Sort Code | Account Number | Amount (GBP) | Payment Reference | Payment Date |
|---|---|---|---|---|---|---|
| 1 | Acme Supplies Ltd | 20-45-67 | 12345678 | 1,250.00 | INV-2026-0451 | 28/02/2026 |
| 2 | Global Services Inc | 30-12-89 | 87654321 | 3,750.50 | PO-8892 | 28/02/2026 |
| 3 | UK Office Furniture | 40-22-11 | 5678901 | 890.00 | FRN-2026-Q1 | 28/02/2026 |

This helps users verify that the mapping makes sense by seeing actual data values.

### 9.7 Target Data Preview

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

### 9.8 Actions

- **"Accept All Mappings"** button (top-right, green) — approve the entire proposal
- **"← Back"** button — return to Import Detail

---

## 10. Screen 5 — Validation Results

**Purpose:** Show all validation findings, allow auto-fix, and support re-validation.

### 10.1 Validation Summary Bar (5-column)

| Metric | Value | Colour |
|---|---|---|
| Checks Run | 85 | Grey |
| Passed | 74 | Green |
| Errors | 4 | Red |
| Warnings | 8 | Amber |
| Can Be Fixed Automatically | 6 | Blue |

### 10.2 Filter Tabs

Four toggle buttons to filter the table:
- **All (11)** — show everything (default, green active state)
- **Errors (4)** — show only errors
- **Warnings (8)** — show only warnings
- **Can Be Fixed Automatically (6)** — show only issues that can be automatically fixed

### 10.3 Validation Results Table

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

### 10.4 Validation Issues Demonstrated

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

### 10.5 Inline Fix Preview

Clicking **"🔧 Fix"** on any auto-fixable row expands an inline panel showing:
- **Before value** (red, strikethrough): e.g., `4022-1`
- **Arrow** (→)
- **After value** (green): e.g., `402211`
- **Transform name**: e.g., "Remove hyphens, pad digits"
- **"Apply Fix"** button

### 10.6 Action Buttons (top-right)

| Button | Description |
|---|---|
| **← Back** | Return to Import Detail |
| **🔧 Fix 6 Issues Automatically** | Opens the Auto-Fix Preview modal (see [Section 13.4](#134-auto-fix-preview-modal)) to batch-review all fixable issues |
| **🔄 Re-check All** | Trigger a fresh validation run |

---

## 11. Screen 6 — Publish & Approve

**Purpose:** Final review gate before the map is published and the output file is generated.

### 11.1 Map Name Input

A text field where the user names the map for future reuse: `ERP-Supplier-Payments-v4`. This name appears in the Maps Registry.

### 11.2 Mapping Summary

| Field | Value |
|---|---|
| Source Format | ERP CSV |
| Target Format | CBO CSV (BACS) |
| Fields Mapped | 10 of 12 (2 ignored) |
| Transforms Applied | 8 changes to your data |
| High Confidence | 10 fields |
| User Overrides | 1 field corrected |

### 11.3 Validation Summary

Visual summary with coloured dots:
- ✅ 74 Passed
- 🔴 4 Errors → 3 Auto-Fixed, 1 Manual
- 🟡 8 Warnings → 3 Auto-Fixed, 5 Acknowledged

Green success banner: "All critical errors resolved. File is safe to publish."

### 11.4 User-Supplied Values

Table showing the values the user provided for missing fields:

| Field | Value |
|---|---|
| Debit Sort Code | `309634` |
| Debit Account Number | `12345678` |
| Processing Date | `02/03/2026 (Monday)` |

### 11.5 Audit Trail

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

### 11.6 Review Checklist (right sidebar)

A sticky checklist showing all prerequisites are met:
- ✅ All mappings reviewed
- ✅ Validation passed
- ✅ Missing fields provided
- ✅ Auto-fixes applied

### 11.7 Options

- ☑️ **Save this map for future reuse** (checked by default)
- ☐ **Remember supplemental values as defaults**

### 11.8 Action Buttons

| Button | Description |
|---|---|
| **✅ Approve & Publish** | Publishes the map and triggers output generation. Opens success modal |
| **✗ Reject** | Cancels the publish. Returns to Import Detail |

### 11.9 Output Info

Note at the bottom: "Output: CBO CSV will be generated with 156 payment entries and made available for download."

---

## 12. Screen 7 — Maps Registry

**Purpose:** Browse and manage published mapping definitions that can be reused for future imports.

### 12.1 Search

Search box to filter maps by name.

### 12.2 Map Cards

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

### 12.3 Maps Demonstrated

| Map | Format | Reuse Count | Notes |
|---|---|---|---|
| ERP-Supplier-Payments-v3 | ERP CSV → CBO CSV | 14 | Most recent supplier payment map |
| BACS-Payroll-Monthly | BACS XML → CBO CSV | 28 | High-reuse payroll map |
| Std18-Fixed-Payroll | Standard 18 → CBO CSV | 7 | Fixed-length format |
| CBO-Bulk-Direct-Debit | CBO CSV → CBO CSV (v2) | 3 | Deprecated map |

---

## 13. Modals & Dialogs

### 13.1 Missing Fields Modal

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

### 13.2 Publish Success Modal

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

### 13.3 Explainability Panel

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

### 13.4 Auto-Fix Preview Modal

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

## 14. User Journey Map (Happy Path)

The journey map below shows the end-to-end AI Assisted Import flow. Follow it left-to-right through the prototype.

---

### 🟢 Primary Journey — AI Assisted Import

```
┌─────────────────┐     ┌─────────────────────┐     ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│  LANDING PAGE   │     │   AI WARNING PAGE    │     │     UPLOAD       │     │    DASHBOARD     │     │  IMPORT DETAIL   │     │     PUBLISH      │
│                 │     │                      │     │                  │     │                  │     │                  │     │                  │
│  Choose import  │────▶│  Review what AI      │────▶│  Auto-simulated  │────▶│  View import     │────▶│  Review pipeline │────▶│  Final review    │
│  mode           │     │  will do             │     │  file upload &   │     │  history table   │     │  status & cards  │     │  & approve       │
│                 │     │                      │     │  processing bar  │     │                  │     │                  │     │                  │
│  🖱 Click "AI   │     │  🖱 Click "Proceed   │     │  ⏳ Progress bar  │     │  🖱 Click row    │     │  🖱 Click         │     │  🖱 Click         │
│    Assisted"    │     │    with AI Import"   │     │     auto-runs    │     │    "a3f1b2c4"    │     │    "Provide      │     │    "Approve &    │
│                 │     │                      │     │                  │     │                  │     │     Missing      │     │     Publish"     │
└─────────────────┘     └──────────────────────┘     └──────────────────┘     └──────────────────┘     │     Fields →"    │     └────────┬─────────┘
                                                                                                      └──────────────────┘              │
                                                                                                                                        ▼
                                                                                                                              ┌──────────────────┐
                                                                                                                              │  SUCCESS MODAL   │
                                                                                                                              │                  │
                                                                                                                              │  ✅ Published!    │
                                                                                                                              │  Download CBO /  │
                                                                                                                              │  BACS output     │
                                                                                                                              │                  │
                                                                                                                              │  🖱 Click "Back   │
                                                                                                                              │    to Dashboard" │
                                                                                                                              └──────────────────┘
```

---

### 🔵 Exploration Branches (from Import Detail)

These branches let you dive deeper into mapping and validation before publishing.

```
                                          ┌──────────────────────┐
                                          │   MAPPING REVIEW     │
                                          │                      │
                                    ┌────▶│  Confidence scores   │────┐
                                    │     │  Source → Target      │    │
                                    │     │  preview              │    │
                                    │     │  🧠 Learning labels   │    │
┌──────────────────┐                │     └──────────────────────┘    │     ┌──────────────────┐
│  IMPORT DETAIL   │                │                                 │     │  IMPORT DETAIL   │
│                  │────────────────┤                                 ├────▶│  (return)        │
│  🖱 Click card   │                │                                 │     └──────────────────┘
└──────────────────┘                │     ┌──────────────────────┐    │
                                    │     │  VALIDATION RESULTS  │    │
                                    │     │                      │    │
                                    └────▶│  85 checks run       │────┘
                                          │  Inline fix previews │
                                          │  🖱 🔧 Auto-fix 6    │──────▶ Auto-Fix Modal
                                          │  🖱 ℹ️ Explainability │──────▶ Explain Panel
                                          └──────────────────────┘
```

---

### ⚪ Manual Import Path (holding page)

```
┌─────────────────┐     ┌──────────────────────┐
│  LANDING PAGE   │     │  MANUAL IMPORT       │
│                 │     │  (Holding Page)       │
│  🖱 Click       │────▶│                       │
│  "Manual File   │     │  "Coming Soon"        │
│    Import"      │     │  5 steps listed       │
│                 │     │  🖱 "← Back" to return │
└─────────────────┘     └──────────────────────┘
```

---

### Journey Summary Table

| Stage | Screen | User Action | System Response | Emotion |
|---|---|---|---|---|
| **Choose** | Landing Page | Selects "AI Assisted File Import" | Shows AI warning page | 🤔 Curious |
| **Understand** | AI Warning | Reads 5-step explainer, clicks "Proceed" | Auto-uploads file, shows progress bar | 😊 Reassured |
| **Orient** | Dashboard | Clicks import row `a3f1b2c4` | Shows import detail with pipeline stepper | 👀 Exploring |
| **Provide** | Import Detail → Modal | Fills in 3 missing BACS fields | Validates input, enables publish | ✍️ Contributing |
| **Review** | Mapping Review *(optional)* | Checks field mappings & confidence | Highlights learned/remembered mappings | 🧐 Verifying |
| **Validate** | Validation Results *(optional)* | Reviews errors, clicks auto-fix | Shows before/after diffs | 🔍 Inspecting |
| **Approve** | Publish | Reviews summary, clicks "Approve & Publish" | Generates output file, shows success | ✅ Confident |
| **Download** | Success Modal | Downloads CBO CSV or BACS XML | File ready | 🎉 Complete |

---

## 15. Status Values Reference

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

## 16. Prototype Limitations

This is a **static HTML prototype** for stakeholder feedback. The following are intentional limitations:

| Area | Limitation |
|---|---|
| **Backend** | No live API — all data is hardcoded mock data |
| **File upload** | No actual file is uploaded; clicking the drop zone simulates a file selection |
| **Manual import** | The Manual File Import path shows a holding page only — manual screens are not implemented |
| **AI import flow** | Clicking "Proceed" on the AI Warning page auto-simulates upload and navigates to the Dashboard |
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
10. **Is the landing page clear?** Is the choice between Manual and AI import intuitive?
11. **Is the AI warning page helpful?** Does it adequately explain what the AI process will do?
12. **Missing anything?** Are there screens, fields, or actions you'd expect to see?
