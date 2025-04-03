package com.pdfgenerator.util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.pdfgenerator.model.InvoiceRequest;
import java.io.File;
import java.io.FileOutputStream;

public class PdfUtil {
    private static final String PDF_DIRECTORY = "generated-pdfs/";

    public static String generatePdf(InvoiceRequest request) throws Exception {
        String filePath = PDF_DIRECTORY + request.getSeller().replaceAll("\\s+", "") + "_invoice.pdf";

        // Ensure directory exists
        File pdfDir = new File(PDF_DIRECTORY);
        if (!pdfDir.exists() && !pdfDir.mkdirs()) {
            throw new Exception("Failed to create PDF directory: " + PDF_DIRECTORY);
        }

        // Create PDF document
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Load fonts
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // Seller & Buyer Table
        Table sellerBuyerTable = new Table(new float[]{1, 1});
        sellerBuyerTable.useAllAvailableWidth();
        sellerBuyerTable.setBorder(new SolidBorder(1)); // Outer border

        // Seller Cell
        Cell sellerCell = new Cell()
                .add(new Paragraph("Seller:").setFont(boldFont))
                .add(new Paragraph(request.getSeller()).setFont(normalFont))
                .add(new Paragraph(request.getSellerAddress()).setFont(normalFont))
                .add(new Paragraph("GSTIN: " + request.getSellerGstin()).setFont(normalFont))
                .setTextAlignment(TextAlignment.LEFT)
                .setPadding(10)
                .setBorderRight(new SolidBorder(1))
                .setBorderLeft(new SolidBorder(1))
                .setBorderTop(new SolidBorder(1));

        // Buyer Cell
        Cell buyerCell = new Cell()
                .add(new Paragraph("Buyer:").setFont(boldFont))
                .add(new Paragraph(request.getBuyer()).setFont(normalFont))
                .add(new Paragraph(request.getBuyerAddress()).setFont(normalFont))
                .add(new Paragraph("GSTIN: " + request.getBuyerGstin()).setFont(normalFont))
                .setTextAlignment(TextAlignment.LEFT)
                .setPadding(10)
                .setBorderLeft(new SolidBorder(1))
                .setBorderTop(new SolidBorder(1))
                .setBorderRight(new SolidBorder(1));

        sellerBuyerTable.addCell(sellerCell);
        sellerBuyerTable.addCell(buyerCell);

        document.add(sellerBuyerTable);

        // Items Table
        Table itemTable = new Table(new float[]{3, 2, 2, 2});
        itemTable.useAllAvailableWidth();
        itemTable.setBorder(new SolidBorder(1)); // Outer border

        // Header Row
        itemTable.addCell(new Cell().add(new Paragraph("Item").setFont(boldFont))
                .setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(1)));
        itemTable.addCell(new Cell().add(new Paragraph("Quantity").setFont(boldFont))
                .setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(1)));
        itemTable.addCell(new Cell().add(new Paragraph("Rate").setFont(boldFont))
                .setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(1)));
        itemTable.addCell(new Cell().add(new Paragraph("Amount").setFont(boldFont))
                .setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(1)));

        // Items List
        for (var item : request.getItems()) {
            itemTable.addCell(new Cell().add(new Paragraph(item.getName()))
                    .setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(1)));
            itemTable.addCell(new Cell().add(new Paragraph(item.getQuantity()))
                    .setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(1)));
            itemTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", item.getRate())))
                    .setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(1)));
            itemTable.addCell(new Cell().add(new Paragraph(String.format("%.2f", item.getAmount())))
                    .setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(1)));
        }

        document.add(itemTable);

        // Extended Blank Table
        Table blankTable = new Table(1);
        blankTable.useAllAvailableWidth();
        blankTable.setBorder(new SolidBorder(1)); // Match the previous table's border

        Cell blankCell = new Cell().setBorder(new SolidBorder(1)).setHeight(50);
        blankTable.addCell(blankCell);

        document.add(blankTable);

        document.close();

        return filePath;
    }
}
