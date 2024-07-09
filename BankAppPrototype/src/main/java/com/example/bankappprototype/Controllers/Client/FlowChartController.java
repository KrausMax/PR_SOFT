package com.example.bankappprototype.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.swing.text.html.Option;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class FlowChartController implements Initializable {

    public TextField salaryField;
    public TextField additionalIncomeField;
    public TextField rentField;
    public TextField electricityField;
    public TextField groceriesField;
    public TextField carField;
    public TextField savingField;

    public Label car_lbl;
    public Label groceries_lbl;
    public Label electricity_lbl;
    public Label rent_lbl;
    public Label additional_income_lbl;
    public Label income_lbl;
    public Label saving_lbl;

    public Label salarySum;
    public Label additionalIncomeSum;
    public Label rentSum;
    public Label electricitySum;
    public Label groceriesSum;
    public Label carSum;
    public Label savingSum;

    public VBox mainVBox;
    public VBox incomeVBox;
    public VBox expensesVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNumericTextField(salaryField, salarySum);
        setNumericTextField(additionalIncomeField, additionalIncomeSum);
        setNumericTextField(rentField, rentSum);
        setNumericTextField(electricityField, electricitySum);
        setNumericTextField(groceriesField, groceriesSum);
        setNumericTextField(carField, carSum);
        setNumericTextField(savingField, savingSum);
    }

    private void setNumericTextField(TextField textField, Label sumLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^(\\d*)\\.?(\\d*)$")) {
                textField.setText(oldValue);
            } else {
                if (sumLabel != null) {
                    updateSumLabel(sumLabel, textField);
                }
            }
        });
    }

    private void updateSumLabel(Label sumLabel, TextField textField) {
        double sum = 0;
        try {
            sum = Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            // ignore, the sum is set to 0
        }
        sumLabel.setText(String.format("%.2f €", sum));
    }

    // Change Title of SubCategory
    public void changeTitle(ActionEvent event) {
        Button button = (Button) event.getSource();
        Label associatedLabel = getAssociatedLabel(button);
        if (associatedLabel != null) {
            TextInputDialog dialog = new TextInputDialog(associatedLabel.getText());
            dialog.setTitle("Edit Title");
            dialog.setHeaderText("Edit the Title");
            dialog.setContentText("Please enter the new title");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(associatedLabel::setText);
        }
    }

    public void changeMainCategoryTitle(ActionEvent event) {
        Button button = (Button) event.getSource();
        TitledPane titledPane = (TitledPane) button.getParent().getParent();
        TextInputDialog dialog = new TextInputDialog(titledPane.getText());
        dialog.setTitle("Edit Main Category Title");
        dialog.setHeaderText("Edit the Main Category Title");
        dialog.setContentText("Please enter the new title:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(titledPane::setText);
    }

    public void deleteEntry(ActionEvent event) {
        Button button = (Button) event.getSource();
        HBox parentHBox = (HBox) button.getParent();
        VBox parentVBox = (VBox) parentHBox.getParent();

        int index = parentVBox.getChildren().indexOf(parentHBox);

        if (index != -1) {
            parentVBox.getChildren().remove(index);
            if (index < parentVBox.getChildren().size()) {
                parentVBox.getChildren().remove(index);
            }

            parentVBox.layout();
        }
    }

    private Label getAssociatedLabel(Button button) {
        if (button.getParent() instanceof HBox parentHBox) {
            if (!parentHBox.getChildren().isEmpty() && parentHBox.getChildren().get(0) instanceof Label) {
                return (Label) parentHBox.getChildren().get(0);
            }
        }
        return null;
    }

    public void calculateButtonClicked() {
        try {
            double salary = Double.parseDouble(salaryField.getText());
            double additionalIncome = Double.parseDouble(additionalIncomeField.getText());

            Map<String, Map<String, Double>> categories = new HashMap<>();

            for (Node node : mainVBox.getChildren()) {
                if (node instanceof TitledPane titledPane) {
                    String mainCategory = titledPane.getText();
                    Map<String, Double> subcategories = new HashMap<>();

                    VBox categoryVBox = (VBox) titledPane.getContent();
                    for (Node subNode : categoryVBox.getChildren()) {
                        if (subNode instanceof VBox subCategoryVBox) {
                            HBox labelBox = (HBox) subCategoryVBox.getChildren().get(0);
                            Label label = (Label) labelBox.getChildren().get(0);
                            String subCategory = label.getText();

                            HBox inputBox = (HBox) subCategoryVBox.getChildren().get(1);
                            TextField textField = (TextField) inputBox.getChildren().get(0);
                            double value = Double.parseDouble(textField.getText());

                            subcategories.put(subCategory, value);
                        }
                    }

                    categories.put(mainCategory, subcategories);
                }
            }

            SankeyDiagramController sankeyDiagramController = new SankeyDiagramController();
            sankeyDiagramController.generatePDF(salary, additionalIncome, categories);

            try {
                File pdfFile = new File("sankey_diagram.png");
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("PDF file does not exist.");
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error opening PDF. Please try again.").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format. Please ensure all fields contain numeric values.").show();
        }
    }

    public void addMainCategory(ActionEvent event) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Einkommen", "Einkommen", "Ausgaben");
        dialog.setTitle("Neue Hauptkategorie");
        dialog.setHeaderText("Neue Hauptkategorie hinzufügen");
        dialog.setContentText("Wählen Sie den Typ der neuen Hauptkategorie:");

        Optional<String> typeResult = dialog.showAndWait();
        typeResult.ifPresent(type -> {
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("Neue Hauptkategorie");
            nameDialog.setHeaderText("Neue Hauptkategorie hinzufügen");
            nameDialog.setContentText("Bitte geben Sie den Namen der neuen Hauptkategorie ein:");

            Optional<String> nameResult = nameDialog.showAndWait();
            nameResult.ifPresent(name -> createMainCategory(name, type));
        });
    }

    private void createMainCategory(String name, String type) {
        TitledPane mainCategoryPane = new TitledPane();
        mainCategoryPane.setText(name);
        mainCategoryPane.setExpanded(true);
        mainCategoryPane.setTextFill(Color.WHITE); // Set text color to white
        mainCategoryPane.getStyleClass().add("flowChart_titledPane");

        VBox categoryVBox = new VBox(10);
        categoryVBox.setStyle("-fx-padding: 10;");
        mainCategoryPane.setContent(categoryVBox);

        // Add button to add new subcategories
        Button addSubCategoryButton = new Button("+");
        addSubCategoryButton.getStyleClass().add("flowChart_addButton");
        addSubCategoryButton.setOnAction(e -> addSubCategory(e, categoryVBox));

        categoryVBox.getChildren().add(addSubCategoryButton);

        if (type.equals("Einkommen")) {
            incomeVBox.getChildren().add(incomeVBox.getChildren().size() - 1, mainCategoryPane);
        } else if (type.equals("Ausgaben")) {
            expensesVBox.getChildren().add(expensesVBox.getChildren().size() - 1, mainCategoryPane);
        }
    }

    public void addSubCategoryFromEvent(ActionEvent event) {
        Button button = (Button) event.getSource();
        VBox parentVBox = (VBox) button.getParent();
        addSubCategory(event, parentVBox);
    }

    private void addSubCategory(ActionEvent event, VBox parentVBox) {
        VBox newEntry = new VBox(5);
        HBox labelBox = new HBox(10);
        Label newLabel = new Label("Neuer Eintrag");
        newLabel.getStyleClass().add("flowChart_label");
        Label sumLabel = new Label("0 €");
        sumLabel.getStyleClass().add("flowChart_label");
        Button editButton = new Button("✏");
        editButton.getStyleClass().add("flowChart_button");
        editButton.setOnAction(this::changeTitle);
        Button deleteButton = new Button("🗑");
        deleteButton.getStyleClass().add("flowChart_button");
        deleteButton.setOnAction(this::deleteEntry);

        labelBox.getChildren().addAll(newLabel, sumLabel, editButton, deleteButton);

        HBox inputBox = new HBox(10);
        TextField newTextField = new TextField();
        newTextField.setPrefWidth(600.0);  // Set the same width as other text fields
        newTextField.setPromptText("Neuer Eintrag");
        newTextField.getStyleClass().add("flowChart_textField");
        Label euroLabel = new Label("€");
        euroLabel.getStyleClass().add("flowChart_label");

        setNumericTextField(newTextField, sumLabel); // Apply numeric filter and link to sum label

        inputBox.getChildren().addAll(newTextField, euroLabel);

        newEntry.getChildren().addAll(labelBox, inputBox);

        // Add the new VBox before the add button
        parentVBox.getChildren().add(parentVBox.getChildren().size() - 1, newEntry);
    }

    public void deleteMainCategoryDialog(ActionEvent event) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle("Hauptkategorie löschen");
        dialog.setHeaderText("Hauptkategorie löschen");
        dialog.setContentText("Wählen Sie die Hauptkategorie, die Sie löschen möchten:");

        for (Node node : incomeVBox.getChildren()) {
            if (node instanceof TitledPane) {
                TitledPane titledPane = (TitledPane) node;
                dialog.getItems().add("Einkommen: " + titledPane.getText());
            }
        }

        for (Node node : expensesVBox.getChildren()) {
            if (node instanceof TitledPane) {
                TitledPane titledPane = (TitledPane) node;
                dialog.getItems().add("Ausgaben: " + titledPane.getText());
            }
        }

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::deleteMainCategory);
    }

    private void deleteMainCategory(String selectedCategory) {
        String[] parts = selectedCategory.split(": ");
        String type = parts[0];
        String name = parts[1];

        if (type.equals("Einkommen")) {
            deleteMainCategoryFromVBox(incomeVBox, name);
        } else if (type.equals("Ausgaben")) {
            deleteMainCategoryFromVBox(expensesVBox, name);
        }
    }

    private void deleteMainCategoryFromVBox(VBox vBox, String name) {
        for (Node node : vBox.getChildren()) {
            if (node instanceof TitledPane titledPane) {
                if (titledPane.getText().equals(name)) {
                    vBox.getChildren().remove(titledPane);
                    break;
                }
            }
        }
    }
}