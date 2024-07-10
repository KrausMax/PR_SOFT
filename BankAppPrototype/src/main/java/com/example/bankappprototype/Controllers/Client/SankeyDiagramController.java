package com.example.bankappprototype.Controllers.Client;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class SankeyDiagramController {

    public void generatePDF(double salary, double additionalIncome, Map<String, Double> incomeCategories, Map<String, Map<String, Double>> expenseCategories) {
        // Create a new Canvas
        Canvas canvas = new Canvas(1200, 1600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw nodes and edges
        drawSankeyDiagram(gc, salary, additionalIncome, incomeCategories, expenseCategories);

        // Save the diagram as an image
        File imageFile = new File("sankey_diagram.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, null), null), "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the image to a PDF
        File pdfFile = new File("sankey_diagram.pdf");
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A2);
            document.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFileByContent(imageFile, document);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            }

            document.save(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display the canvas in a new window with a scrollable pane
        Stage stage = new Stage();
        Pane pane = new Pane();
        pane.getChildren().add(canvas);
        ScrollPane scrollPane = new ScrollPane(pane);
        Scene scene = new Scene(scrollPane, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }

    private void drawSankeyDiagram(GraphicsContext gc, double salary, double additionalIncome, Map<String, Double> incomeCategories, Map<String, Map<String, Double>> expenseCategories) {
        double totalIncome = salary + additionalIncome;
        double totalExpenses = expenseCategories.values().stream()
                .flatMap(subcategories -> subcategories.values().stream())
                .mapToDouble(Double::doubleValue).sum();
        double difference = totalIncome - totalExpenses;
        double width = 1200;
        double height = 1600;

        // Set background color
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        // Colors
        Color salaryColor = Color.rgb(173, 216, 230); // Light blue
        Color additionalIncomeColor = Color.rgb(135, 206, 250); // Sky blue
        Color budgetColor = Color.rgb(100, 149, 237); // Cornflower blue
        Color[] categoryColors = {Color.rgb(255, 160, 122), Color.rgb(255, 228, 196), Color.rgb(152, 251, 152), Color.rgb(255, 182, 193), Color.rgb(240, 230, 140)};

        // Summary text
        String summaryText;
        if (difference == 0) {
            summaryText = "Die Summe deines Budgets beläuft sich auf " + String.format("%.2f", totalIncome) + "€ und die deiner Ausgaben auf " + String.format("%.2f", totalExpenses) + "€. Deine Ausgaben werden somit von deinem Budget gedeckt.";
        } else if (difference > 0) {
            summaryText = "Die Summe deines Budgets beläuft sich auf " + String.format("%.2f", totalIncome) + "€ und die deiner Ausgaben auf " + String.format("%.2f", totalExpenses) + "€. Von deinem Budget bleiben " + String.format("%.2f", difference) + "€ übrig.";
        } else {
            summaryText = "Die Summe deines Budgets beläuft sich auf " + String.format("%.2f", totalIncome) + "€ und die deiner Ausgaben auf " + String.format("%.2f", totalExpenses) + "€. Deine Ausgaben übersteigen somit dein Budget um " + String.format("%.2f", -difference) + "€.";
        }
        gc.setFill(Color.BLACK);
        gc.fillText(summaryText, 50, 50);

        // Income Nodes
        double incomeX = 50;
        double incomeY = 100;
        double incomeWidth = 200;
        double incomeHeight = 40;
        double incomeSpacing = 60;

        // Draw Income Bars
        double currentIncomeY = incomeY;
        for (Map.Entry<String, Double> entry : incomeCategories.entrySet()) {
            gc.setFill(salaryColor);
            gc.fillRect(incomeX, currentIncomeY, incomeWidth, incomeHeight);
            gc.setFill(Color.BLACK);
            gc.fillText(entry.getKey() + ": " + String.format("%.2f", entry.getValue()) + "€", incomeX + 10, currentIncomeY + 25);
            currentIncomeY += incomeSpacing;
        }

        // Budget Bar
        double budgetX = 300;
        double budgetY = incomeY + incomeCategories.size() * incomeSpacing / 2;
        double budgetWidth = 200;
        double budgetHeight = 40;

        gc.setFill(budgetColor);
        gc.fillRect(budgetX, budgetY, budgetWidth, budgetHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("Budget: " + String.format("%.2f", totalIncome) + "€", budgetX + 10, budgetY + 25);

        // Draw edges from income to budget
        double currentEdgeY = incomeY + incomeHeight / 2;
        for (int i = 0; i < incomeCategories.size(); i++) {
            gc.setStroke(salaryColor);
            gc.setLineWidth(5);
            gc.strokeLine(incomeX + incomeWidth, currentEdgeY, budgetX, budgetY + budgetHeight / 2);
            currentEdgeY += incomeSpacing;
        }

        // Expense Categories
        double mainCategoryX = 600;
        double mainCategoryY = 70;
        double mainCategoryWidth = 200;
        double mainCategoryHeight = 40;
        double currentY = mainCategoryY;

        int colorIndex = 0;
        for (Map.Entry<String, Map<String, Double>> category : expenseCategories.entrySet()) {
            String categoryName = category.getKey();
            Map<String, Double> subcategories = category.getValue();

            // Calculate total for this main category
            double totalCategory = subcategories.values().stream().mapToDouble(Double::doubleValue).sum();

            // Draw main category
            gc.setFill(categoryColors[colorIndex % categoryColors.length]);
            gc.fillRect(mainCategoryX, currentY, mainCategoryWidth, mainCategoryHeight);
            gc.setFill(Color.BLACK);
            gc.fillText(categoryName + ": " + String.format("%.2f", totalCategory) + "€", mainCategoryX + 10, currentY + 25);

            // Draw edges from budget to main categories
            gc.setStroke(categoryColors[colorIndex % categoryColors.length]);
            gc.setLineWidth(5);
            gc.strokeLine(budgetX + budgetWidth, budgetY + budgetHeight / 2, mainCategoryX, currentY + mainCategoryHeight / 2);

            // Draw subcategories
            double subCategoryX = 850;
            double subCategoryY = currentY;
            double subCategoryWidth = 200;
            double subCategoryHeight = 40;
            double subCurrentY = subCategoryY;

            for (Map.Entry<String, Double> subcategory : subcategories.entrySet()) {
                String subcategoryName = subcategory.getKey();
                double subcategoryValue = subcategory.getValue();

                gc.setFill(categoryColors[colorIndex % categoryColors.length]);
                gc.fillRect(subCategoryX, subCurrentY, subCategoryWidth, subCategoryHeight);
                gc.setFill(Color.BLACK);
                gc.fillText(subcategoryName + ": " + String.format("%.2f", subcategoryValue) + "€", subCategoryX + 10, subCurrentY + 25);

                // Draw edges
                gc.setStroke(categoryColors[colorIndex % categoryColors.length]);
                gc.setLineWidth(5);
                gc.strokeLine(mainCategoryX + mainCategoryWidth, currentY + mainCategoryHeight / 2, subCategoryX, subCurrentY + subCategoryHeight / 2);

                subCurrentY += 60;
            }

            currentY += 200;
            colorIndex++;
        }

        // Draw text summary below the Sankey diagram
        drawTextSummary(gc, salary, additionalIncome, incomeCategories, expenseCategories, totalIncome, totalExpenses, difference);
    }

    private void drawTextSummary(GraphicsContext gc, double salary, double additionalIncome, Map<String, Double> incomeCategories, Map<String, Map<String, Double>> expenseCategories, double totalIncome, double totalExpenses, double difference) {
        gc.setFill(Color.BLACK);
        gc.setFont(new javafx.scene.text.Font("Arial", 14));
        double textX = 50;
        double textY = 1300;
        double lineHeight = 20;

        gc.fillText("EINKOMMEN", textX, textY);
        textY += lineHeight;

        for (Map.Entry<String, Double> entry : incomeCategories.entrySet()) {
            double percentage = (entry.getValue() / totalIncome) * 100;
            String formattedText = String.format("%-30s %10.2f € %10.2f %%", entry.getKey(), entry.getValue(), percentage);
            gc.fillText(formattedText, textX, textY);
            textY += lineHeight;
        }

        String formattedIncomeSum = String.format("%-30s %10.2f € %10.2f %%", "SUMME", totalIncome, 100.0);
        gc.fillText(formattedIncomeSum, textX, textY);
        textY += lineHeight * 2;

        gc.fillText("AUSGABEN", textX, textY);
        textY += lineHeight;

        for (Map.Entry<String, Map<String, Double>> entry : expenseCategories.entrySet()) {
            String mainCategory = entry.getKey();
            Map<String, Double> subcategories = entry.getValue();

            double totalMainCategory = subcategories.values().stream().mapToDouble(Double::doubleValue).sum();
            double mainCategoryPercentage = (totalMainCategory / totalExpenses) * 100;
            String formattedMainCategory = String.format("%-30s %10.2f € %10.2f %%", mainCategory, totalMainCategory, mainCategoryPercentage);
            gc.fillText(formattedMainCategory, textX, textY);
            textY += lineHeight;

            for (Map.Entry<String, Double> subcategory : subcategories.entrySet()) {
                double percentage = (subcategory.getValue() / totalExpenses) * 100;
                String formattedSubcategory = String.format("  %-28s %10.2f € %10.2f %%", subcategory.getKey(), subcategory.getValue(), percentage);
                gc.fillText(formattedSubcategory, textX, textY);
                textY += lineHeight;
            }
        }

        String formattedExpenseSum = String.format("%-30s %10.2f € %10.2f %%", "SUMME", totalExpenses, 100.0);
        gc.fillText(formattedExpenseSum, textX, textY);
        textY += lineHeight * 2;

        String formattedDifference = String.format("%-30s %10.2f €", "DIFFERENZ", difference);
        gc.fillText(formattedDifference, textX, textY);
    }

}
