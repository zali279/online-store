package com.store.demo.service;

import com.store.demo.model.CartItem;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PdfGenerator {
    public static void generateReceipt(List<CartItem> items, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.beginText();
                contentStream.newLineAtOffset(220, 750);
                contentStream.showText("Receipt");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);

                float y = 700;
                double total = 0;

                for (CartItem item : items) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, y);
                    contentStream.showText(item.getProduct().getName() + " - $" + String.format("%.2f", item.getProduct().getPrice()));
                    contentStream.endText();
                    y -= 20;
                    total += item.getProduct().getPrice();
                }

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, y - 10);
                contentStream.showText("Total: $" + String.format("%.2f", total));
                contentStream.endText();
            }

            document.save(filePath);
        }
    }
}
