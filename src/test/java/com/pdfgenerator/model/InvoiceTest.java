package com.pdfgenerator.model;

import java.util.Arrays;

public class InvoiceTest {
    public static void main(String[] args) {
        // Create Items
        Item item1 = new Item("Item A", "10", 50.0, 500.0);
        Item item2 = new Item("Item B", "5", 100.0, 500.0);

        // Create Invoice Request using Constructor
        InvoiceRequest request = new InvoiceRequest(
                "Seller Name", "GSTIN123", "123 Street, City",
                "Buyer Name", "GSTIN456", "456 Avenue, City",
                Arrays.asList(item1, item2)
        );

        // Debug Output
        System.out.println("Invoice created for: " + request.getSeller());
    }
}
