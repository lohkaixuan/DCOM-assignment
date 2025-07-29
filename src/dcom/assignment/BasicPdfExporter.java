package dcom.assignment;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public final class BasicPdfExporter {

    // Money shown as RM 12,345.67
    private static final DecimalFormat MONEY = new DecimalFormat("#,##0.00");
    // Percent shown as 10, 10.5 or 10.55 (no trailing zeros)
    private static final DecimalFormat PCT   = new DecimalFormat("#0.##");

    private BasicPdfExporter() {}

    public static void exportPayrollRowToPDF(PayrollRowDTO dto, File file) throws IOException {
        // --- Page coordinates ---
        final float PAGE_W = 595f;    // A4 width (unused but kept for reference)
        final float PAGE_H = 842f;    // A4 height

        // ===== CHANGED: start near the top-left with margins =====
        final float LEFT_MARGIN = 50f;               // left margin (~0.7")
        final float TOP_MARGIN  = 60f;               // top margin  (~0.8")
        final float COL_X       = LEFT_MARGIN;       // all lines start from here
        final float TITLE_Y     = PAGE_H - TOP_MARGIN; // title y-position (top-left)
        final float GAP         = 18f;               // line gap

        // Font sizes
        final float TITLE_SIZE = 20f;   // bigger title
        final float SUB_SIZE   = 14f;   // subheader
        final float BODY_SIZE  = 13f;   // body

        String period = "Month / Year: " + dto.getMonth() + " " + dto.getYear();

        // Build content stream (PDF text commands)
        StringBuilder c = new StringBuilder();

        // --- Title & period (top-left) ---
        textAt(c, "Payroll Details", COL_X, TITLE_Y,       TITLE_SIZE, true);
        textAt(c, period,            COL_X, TITLE_Y - 24f, SUB_SIZE,   false);

        float y = TITLE_Y - 52f;

        // --- Employee (optional) ---
        textAt(c, "Employee: " + dto.getEmployeeName(), COL_X, y, BODY_SIZE, false);
        y -= (GAP + 4f);

        // --- Group 1: Basic + Tax% ---
        textAt(c, "Basic Salary: " + rm(dto.getBasicSalary()), COL_X, y, BODY_SIZE, false); y -= GAP;
        textAt(c, "Tax: "          + pct(dto.getTaxAmount()),  COL_X, y, BODY_SIZE, false);

        // group gap
        y -= (GAP + 10f);

        // --- Group 2: Gross, Deduction, Net ---
        textAt(c, "Gross Pay: "    + rm(dto.getGrossPay()),    COL_X, y, BODY_SIZE, false); y -= GAP;
        textAt(c, "Deduction: "    + rm(dto.getDeduction()),   COL_X, y, BODY_SIZE, false); y -= GAP;
        textAt(c, "Net Pay: "      + rm(dto.getNetPay()),      COL_X, y, BODY_SIZE, false); y -= GAP;

        // --- Hours at the end ---
        y -= 6f;
        textAt(c, "Hours: " + MONEY.format(dto.getHoursWorked()), COL_X, y, BODY_SIZE, false);

        // Build a minimal single‑page PDF with Helvetica
        byte[] pdf = buildSimplePdf(c.toString().getBytes(StandardCharsets.US_ASCII));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(pdf);
        }
    }

    // ========= Helpers =========

    private static String rm(double v) { return "RM " + MONEY.format(v); }
    private static String pct(double frac) { return PCT.format(frac * 100.0) + "%"; }

    /** Writes one line of text at absolute (x,y) with chosen size and weight. */
    private static void textAt(StringBuilder sb, String text, float x, float y, float size, boolean bold) {
        sb.append("BT\n");
        sb.append("/F1 ").append(size).append(" Tf ");
        sb.append("1 0 0 1 ").append(x).append(" ").append(y).append(" Tm ");
        if (bold) {
            // faux bold: draw twice with slight offset
            sb.append("(").append(pdfEscape(text)).append(") Tj ");
            sb.append("0.5 0 Td ");
            sb.append("(").append(pdfEscape(text)).append(") Tj\n");
        } else {
            sb.append("(").append(pdfEscape(text)).append(") Tj\n");
        }
        sb.append("ET\n");
    }

    /** Escape parentheses and backslashes for PDF string literals. */
    private static String pdfEscape(String s) {
        return s.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }

    /**
     * Build a minimal 1‑page PDF (A4) with Helvetica font.
     * Objects: 1 Catalog, 2 Pages, 3 Page, 4 Font, 5 Content
     */
    private static byte[] buildSimplePdf(byte[] contentStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        long[] xref = new long[6];

        out.write("%PDF-1.4\n".getBytes(StandardCharsets.US_ASCII));

        OutputStreamWithOffset w = new OutputStreamWithOffset(out);

        // 1: Catalog
        xref[1] = w.offset();
        w.writeObj(1, "<< /Type /Catalog /Pages 2 0 R >>\n");

        // 2: Pages
        xref[2] = w.offset();
        w.writeObj(2, "<< /Type /Pages /Count 1 /Kids [3 0 R] >>\n");

        // 3: Page (A4 = 595x842)
        xref[3] = w.offset();
        w.writeObj(3, "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] " +
                "/Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >>\n");

        // 4: Font (Helvetica)
        xref[4] = w.offset();
        w.writeObj(4, "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\n");

        // 5: Content stream
        xref[5] = w.offset();
        String len = String.valueOf(contentStream.length);
        w.write("5 0 obj\n<< /Length ".getBytes(StandardCharsets.US_ASCII));
        w.write(len.getBytes(StandardCharsets.US_ASCII));
        w.write(" >>\nstream\n".getBytes(StandardCharsets.US_ASCII));
        w.write(contentStream);
        w.write("\nendstream\nendobj\n".getBytes(StandardCharsets.US_ASCII));

        // xref
        long xrefStart = w.offset();
        w.write("xref\n0 6\n".getBytes(StandardCharsets.US_ASCII));
        w.write(String.format("%010d %05d f \n", 0, 65535).getBytes(StandardCharsets.US_ASCII));
        for (int i = 1; i <= 5; i++) {
            w.write(String.format("%010d %05d n \n", xref[i], 0).getBytes(StandardCharsets.US_ASCII));
        }

        // trailer
        w.write(("trailer\n<< /Size 6 /Root 1 0 R >>\nstartxref\n" + xrefStart + "\n%%EOF\n")
                .getBytes(StandardCharsets.US_ASCII));

        return out.toByteArray();
    }

    /** Utility to track byte offsets while writing. */
    private static class OutputStreamWithOffset {
        private final OutputStream out;
        private long count = 0;
        OutputStreamWithOffset(OutputStream out) { this.out = out; }
        long offset() { return count; }
        void write(byte[] b) throws IOException { out.write(b); count += b.length; }
        void write(String s) throws IOException {
            byte[] b = s.getBytes(StandardCharsets.US_ASCII);
            out.write(b); count += b.length;
        }
        void writeObj(int id, String body) throws IOException {
            write(id + " 0 obj\n"); write(body); write("endobj\n");
        }
    }
}
