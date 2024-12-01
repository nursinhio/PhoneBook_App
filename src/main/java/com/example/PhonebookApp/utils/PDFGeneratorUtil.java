package com.example.PhonebookApp.utils;
import com.example.PhonebookApp.model.Contact;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.util.List;

public class PDFGeneratorUtil {

    public static void generatePDF(List<Contact> contacts, String fileName) throws Exception {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("Contacts Report");
                contentStream.newLine();

                for (Contact contact : contacts) {
                    String contactLine = String.format("Name: %s, Surname: %s, Phone: %s, Email: %s",
                            contact.getName(), contact.getSurname(), contact.getPhoneNumber(), contact.getEmail());
                    contentStream.showText(contactLine);
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            document.save(new File(fileName));
        }
    }
}

