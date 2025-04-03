package com.pdfgenerator.controller;

import com.pdfgenerator.model.InvoiceRequest;
import com.pdfgenerator.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody InvoiceRequest request) {

        try {
            File pdfFile = pdfService.generatePdf(request);

            // Ensure file is generated successfully
            if (pdfFile == null || !pdfFile.exists()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to generate PDF".getBytes());
            }

            byte[] fileContent = Files.readAllBytes(pdfFile.toPath());

            // Clean up temporary file (if applicable)
            Files.deleteIfExists(pdfFile.toPath());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFile.getName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(fileContent);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }
}
