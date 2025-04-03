Dynamic PDF Generator:

Build a Spring Boot Application with REST API to generate PDF using Java Template
Engine.

Features:
1.Generate PDFs dynamically using Java
2.RESTful API for PDF creation
3.Uses iText 7 for PDF processing
4.Supports custom layouts, images, and fonts
5.Built with Spring Boot and Maven

Technologies Used:
Java 17
Spring Boot 3.4.4
iText 7 (for PDF generation)
Postman (local)
JUnit (for testing)

Postman : Post : http://localhost:9090/api/pdf/generate

Headers : Key - Content-type
          Value - application/json

Body - raw - JSON
{
    "seller": "XYZ Pvt. Ltd.",
    "sellerGstin": "29AABBCCDD121ZD",
    "sellerAddress": "New Delhi, India",
    "buyer": "Vedant Computers",
    "buyerGstin": "29AABBCCDD131ZD",
    "buyerAddress": "New Delhi, India",

    "items": [
    {
        "name": "Product 1",
        "quantity": "12 Nos",
        "rate": 123.00,
        "amount": 1476.00
    }
    ]
}

Get a PDF -- SUCCESS - 200 OK

For Junit - Run as - DynamicPdfGeneratorApplicationTests.java
2 Test case passed 
Success