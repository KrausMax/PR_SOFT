package com.example.bankappprototype.Controllers.Client;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SankeyDiagramController {

    public void generatePDF(double salary, double additionalIncome, Map<String, Map<String, Double>> categories) {
        // Create a new Canvas
        Canvas canvas = new Canvas(1000, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw nodes and edges
        drawSankeyDiagram(gc, salary, additionalIncome, categories);

        // Display the canvas in a new window
        Stage stage = new Stage();
        Pane pane = new Pane();
        pane.getChildren().add(canvas);
        Scene scene = new Scene(pane, 1000, 600);
        stage.setScene(scene);
        stage.show();

        // Save the diagram as an image
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, null), null), "png", new File("sankey_diagram.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawSankeyDiagram(GraphicsContext gc, double salary, double additionalIncome, Map<String, Map<String, Double>> categories) {
        double totalIncome = salary + additionalIncome;
        double width = 1000;
        double height = 600;

        // Set background color
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        // Colors
        Color salaryColor = Color.rgb(173, 216, 230); // Light blue
        Color additionalIncomeColor = Color.rgb(135, 206, 250); // Sky blue
        Color budgetColor = Color.rgb(100, 149, 237); // Cornflower blue
        Color[] categoryColors = {Color.rgb(255, 160, 122), Color.rgb(255, 228, 196), Color.rgb(152, 251, 152), Color.rgb(255, 182, 193), Color.rgb(240, 230, 140)};

        // Income Nodes
        double incomeX = 50;
        double incomeY = 100;
        double incomeWidth = 200;
        double incomeHeight = 40;

        // Income Bars
        gc.setFill(salaryColor);
        gc.fillRect(incomeX, incomeY, incomeWidth, incomeHeight);
        gc.setFill(additionalIncomeColor);
        gc.fillRect(incomeX, incomeY + 60, incomeWidth, incomeHeight);

        gc.setFill(Color.BLACK);
        gc.fillText("Gehalt: " + String.format("%.2f", salary) + "€", incomeX + 10, incomeY + 25);
        gc.fillText("Weitere Einkünfte: " + String.format("%.2f", additionalIncome) + "€", incomeX + 10, incomeY + 85);

        // Budget Bar
        double budgetX = 300;
        double budgetY = 130;
        double budgetWidth = 200;
        double budgetHeight = 40;

        gc.setFill(budgetColor);
        gc.fillRect(budgetX, budgetY, budgetWidth, budgetHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("Budget: " + String.format("%.2f", totalIncome) + "€", budgetX + 10, budgetY + 25);

        // Main Expense Categories
        double mainCategoryX = 600;
        double mainCategoryY = 70;
        double mainCategoryWidth = 200;
        double mainCategoryHeight = 40;
        double currentY = mainCategoryY;

        int colorIndex = 0;
        for (Map.Entry<String, Map<String, Double>> category : categories.entrySet()) {
            String categoryName = category.getKey();
            Map<String, Double> subcategories = category.getValue();

            // Calculate total for this main category
            double totalCategory = subcategories.values().stream().mapToDouble(Double::doubleValue).sum();

            // Draw main category
            gc.setFill(categoryColors[colorIndex % categoryColors.length]);
            gc.fillRect(mainCategoryX, currentY, mainCategoryWidth, mainCategoryHeight);
            gc.setFill(Color.BLACK);
            gc.fillText(categoryName + ": " + String.format("%.2f", totalCategory) + "€", mainCategoryX + 10, currentY + 25);

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
                gc.strokeLine(mainCategoryX + mainCategoryWidth, currentY + 20, subCategoryX, subCurrentY + 20);

                subCurrentY += 60;
            }

            currentY += 140;
            colorIndex++;
        }

        // Draw edges from income to budget
        gc.setStroke(salaryColor);
        gc.setLineWidth(5);
        gc.strokeLine(incomeX + incomeWidth, incomeY + 20, budgetX, budgetY + 20);
        gc.setStroke(additionalIncomeColor);
        gc.strokeLine(incomeX + incomeWidth, incomeY + 80, budgetX, budgetY + 20);
    }
}
