# Payment File Import — Clickable Prototype

> **Version:** 1.2 &nbsp;|&nbsp; **Date:** 26 February 2026

## Quick Start

### Option A — Double-click (simplest)

1. Navigate to the `prototype/` folder in Finder / File Explorer.
2. Double-click **`index.html`** — it opens in your default browser.
3. That's it. Click through the screens using the left sidebar navigation.

### Option B — From the terminal

```bash
# macOS
open prototype/index.html

# Linux
xdg-open prototype/index.html

# Windows (PowerShell)
start prototype/index.html
```

### Option C — VS Code Live Preview

1. Install the **Live Preview** extension (`ms-vscode.live-server`) in VS Code.
2. Right-click `index.html` → **Show Preview** (or **Open with Live Server**).
3. The prototype opens in an embedded browser tab with hot-reload on save.

### Option D — Python one-liner (if you need a local HTTP server)

```bash
cd prototype
python3 -m http.server 8080
```

Then open [http://localhost:8080](http://localhost:8080) in your browser.

> **No build step, no npm, no internet connection required.** All assets — HTML, JS (Tailwind), and CSS — are self-contained in this folder.

```
prototype/
├── index.html         # Single-file clickable prototype (~1 620 lines)
├── tailwindcss.js     # Tailwind CSS Play CDN — local copy (~400 KB)
├── PROTOTYPE_GUIDE.md # Detailed feature walkthrough
└── README.md          ← you are here
```

### Navigating the prototype

- Use the **left sidebar** to switch between the 7 screens (Dashboard, Upload, Import Detail, etc.).
- Clickable buttons and links trigger **modals** (Missing Fields, Auto-Fix Preview, Explainability, Publish Success).
- The prototype is **read-only** — form inputs and buttons simulate behaviour but do not persist data.

## Tailwind CSS — Offline / Corporate Network Setup

The prototype uses **Tailwind CSS** for styling. The default CDN URL (`https://cdn.tailwindcss.com`) is often blocked on corporate networks; therefore a local copy of the script is bundled.

### How it works

`index.html` loads Tailwind from a local file:

```html
<script src="tailwindcss.js"></script>
```

The script compiles Tailwind utility classes at runtime in the browser — identical behaviour to the CDN version, but with zero network dependency.

### Refreshing the local copy

If you need a newer version of Tailwind CSS, re-download the file:

```bash
curl -sL -o prototype/tailwindcss.js https://cdn.tailwindcss.com
```

> **Note:** This must be done from a machine that can reach the CDN (e.g. your personal laptop). The resulting `tailwindcss.js` can then be committed to the repo or shared with colleagues.

### Reverting to CDN (for personal/non-corporate use)

Replace the local script tag in `index.html`:

```html
<!-- Local (current — works offline) -->
<script src="tailwindcss.js"></script>

<!-- CDN (requires internet — may be blocked on corporate networks) -->
<script src="https://cdn.tailwindcss.com"></script>
```

## Feature Guide

See [PROTOTYPE_GUIDE.md](PROTOTYPE_GUIDE.md) for a detailed walkthrough of all 7 screens, 4 modals, and the end-to-end user journey.

## Screens

| # | Screen | Description |
|---|--------|-------------|
| 1 | Dashboard | Import history, status overview, self-learning banner |
| 2 | Upload | Drag-and-drop file upload with format auto-detection |
| 3 | Import Detail | State timeline, file metadata, action buttons |
| 4 | Mapping Review | Source → target field mapping with smart suggestions & learning indicators |
| 5 | Validation Results | Rule-by-rule results, auto-fix counts, header sequence duplicate detection |
| 6 | Publish & Approve | BACS output preview, approval workflow, publish confirmation |
| 7 | Maps Registry | Reusable mapping templates with usage stats |

## Requirements

- A modern web browser (ES2020+)
- **No** Node.js, npm, or build tools needed
- **No** internet connection needed (Tailwind CSS is bundled locally)
