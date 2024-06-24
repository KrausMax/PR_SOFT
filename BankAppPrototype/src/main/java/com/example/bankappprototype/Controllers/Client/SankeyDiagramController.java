package com.example.bankappprototype.Controllers.Client;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SankeyDiagramController implements Initializable {

    @FXML
    private Canvas canvas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize method, if needed
    }

    public void generatePDF(double salary, double additionalIncome, double rent, double electricity,
                            double groceries, double car, double saving) {
        try {
            File file = new File("sankey_diagram.pdf");
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Draw Sankey diagram on the PDF page
            drawSankeyDiagram(canvas.getGraphicsContext2D());

            // Convert JavaFX canvas to image
            WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), null);
            File tempFile = new File("sankey_diagram.png");
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", tempFile);

            // Embed image into PDF
            PDImageXObject pdImage = PDImageXObject.createFromFileByContent(tempFile, document);
            contentStream.drawImage(pdImage, 50, 500, pdImage.getWidth() / 2, pdImage.getHeight() / 2);

            contentStream.close();
            document.save(file);
            document.close();

            new Alert(Alert.AlertType.INFORMATION, "PDF file generated successfully!").show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error generating PDF file.").show();
        }
    }

    private void drawSankeyDiagram(GraphicsContext gc) {
        // Example simple Sankey diagram drawing
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        // Draw nodes
        gc.strokeRect(50, 50, 100, 50); // Node A
        gc.strokeRect(300, 50, 100, 50); // Node B

        // Draw flows
        gc.strokeLine(150, 75, 250, 75); // Flow from Node A to Node B
    }
}
