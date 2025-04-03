package com.pdfgenerator.service;

import com.pdfgenerator.model.InvoiceRequest;
import com.pdfgenerator.util.PdfUtil;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class PdfService {
    public File generatePdf(InvoiceRequest request) throws Exception {
        String filePath = PdfUtil.generatePdf(request);

        // Validate file path
        if (filePath.isEmpty()) {
            throw new Exception("PDF generation failed: File path is null or empty");
        }

        File pdfFile = new File(filePath);

        // Ensure file exists before returning
        if (!pdfFile.exists()) {
            throw new Exception("PDF file not found at: " + filePath);
        }

        return pdfFile;
    }
}
