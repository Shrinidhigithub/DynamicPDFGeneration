package com.pdfgenerator.util;

import com.pdfgenerator.model.InvoiceRequest;
import com.pdfgenerator.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class PdfUtilTest {
    private static final String TEST_PDF_PATH = "generated-pdfs/TestSeller_invoice.pdf";

    private InvoiceRequest testInvoice;

    @BeforeEach
    void setUp() {
        // Creating a sample InvoiceRequest object
        testInvoice = new InvoiceRequest();
        testInvoice.setSeller("TestSeller");
        testInvoice.setSellerAddress("123 Test Street, City");
        testInvoice.setSellerGstin("GST12345678");
        testInvoice.setBuyer("TestBuyer");
        testInvoice.setBuyerAddress("456 Buyer Street, City");
        testInvoice.setBuyerGstin("GST87654321");

        // Sample Items
        Item item1 = new Item("Item A", "10", 50.0, 500.0);
        Item item2 = new Item("Item B", "5", 100.0, 500.0);

        testInvoice.setItems(Arrays.asList(item1, item2));
    }

    @Test
    void testGeneratePdf() throws Exception {
        // Generate the PDF
        String filePath = PdfUtil.generatePdf(testInvoice);
        File pdfFile = new File(filePath);

        // Assertions
        assertTrue(pdfFile.exists(), "PDF file should exist");
        assertTrue(pdfFile.length() > 0, "PDF file should not be empty");
    }

    @AfterEach
    void tearDown() {
        // Cleanup generated PDF after test
        File pdfFile = new File(TEST_PDF_PATH);
        if (pdfFile.exists()) {
            assertTrue(pdfFile.delete(), "PDF file should be deleted after test");
        }
    }
}
