package com.example.SecurityAssistant.service;

import com.example.SecurityAssistant.entities.Recommendation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class toPDF {
    
    public byte[] generatePdf(ArrayList<Recommendation> recommendations) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // Create a new PDF document
            PDDocument document = new PDDocument();

            // Create a new page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Create a new content stream to write to the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font and font size for the content
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Add content to the PDF document from the recommendations list
            for (Recommendation recommendation : recommendations) {
                String title = recommendation.getTitle();
                String imformation = recommendation.getInformation();

                // Remove newline and carriage return characters from the text
                title = title.replace("\n", "").replace("\r", "");
                imformation = imformation.replace("\n", "").replace("\r", "");

                // Add the recommendation details to the PDF as paragraphs
                // Begin a new text block
                contentStream.beginText();

                // Set the starting position for the text
                /* contentStream.newLineAtOffset(50, 700); */
                contentStream.showText("Title: " + title);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Description: " + imformation);
                contentStream.endText();
               /*  contentStream.newLineAtOffset(0, -40); // Add space between recommendations */
            }

            // Close the content stream
            contentStream.close();

            // Save the PDF document to the ByteArrayOutputStream
            document.save(baos);

            // Close the document
            document.close();
            System.out.println("PDF generated successfully in byte array.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieve the generated PDF content as a byte array
        return baos.toByteArray();
    }
}