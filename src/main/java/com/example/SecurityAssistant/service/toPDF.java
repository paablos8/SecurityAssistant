package com.example.SecurityAssistant.service;

import com.example.SecurityAssistant.entities.Recommendation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class toPDF {

    public byte[] generatePdf(ArrayList<Recommendation> recommendations) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // Create a new empty document
            PDDocument document = new PDDocument();

            // Create a new blank page and add it to the document
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a new font object selecting one of the PDF base fonts
            PDFont font = PDType1Font.HELVETICA_BOLD;

            // Start a new content stream which will "hold" the to be created content
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Add content to the PDF document from the recommendations list
            for (Recommendation recommendation : recommendations) {
                String title = recommendation.getTitle();
                String info = recommendation.getInformation();
                int priority = recommendation.getPriorityScore();
                String originDoc = recommendation.getOriginDocument();
                ArrayList<String> risks = recommendation.getRiskIfNotImplemented();

                title = title.replace("\n", "").replace("\r", "");
                info = info.replace("\n", "").replace("\r", "");

            // Define a text content stream using the selected font, moving the cursor and
            // drawing the text "Hello World"
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Recommendation: " + title);
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("More Info: " + info);
            contentStream.newLineAtOffset(0, -20);
            contentStream.endText();
            }
            // Make sure that the content stream is closed:
            contentStream.close();

            // Save the results and ensure that the document is properly closed:
            document.save(baos);
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieve the generated PDF content as a byte array
        return baos.toByteArray();
    }
}