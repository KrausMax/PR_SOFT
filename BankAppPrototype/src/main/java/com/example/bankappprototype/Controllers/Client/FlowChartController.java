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

/**
 * Controller class for managing the flowchart view, allowing users to add, edit, and delete categories and subcategories
 * for income and expenses, and generating a Sankey diagram PDF based on the input data.
 */
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

    /**
     * Initializes the controller class.
     *
     * @param url             The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle  The resources used to localize the root object, or null if the root object was not localized.
     */
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

    /**
     * Sets up a TextField to only accept numeric input and updates the corresponding sum label.
     *
     * @param textField The TextField to be set up.
     * @param sumLabel  The Label to be updated with the sum.
     */
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

    /**
     * Updates the sum label with the value from the TextField.
     *
     * @param sumLabel   The Label to be updated.
     * @param textField  The TextField containing the value.
     */
    private void updateSumLabel(Label sumLabel, TextField textField) {
        double sum = 0;
        try {
            sum = Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            // ignore, the sum is set to 0
        }
        sumLabel.setText(String.format("%.2f €", sum));
    }

    /**
     * Parses a String to a double, returning 0.0 if the String is not a valid number.
     *
     * @param value The String to be parsed.
     * @return The parsed double value, or 0.0 if the String is not a valid number.
     */
    private double parseDoubleOrZero(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Changes the title of a subcategory when the associated button is clicked.
     *
     * @param event The ActionEvent triggered by clicking the button.
     */
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

    /**
     * Changes the title of a main category when the associated button is clicked.
     *
     * @param event The ActionEvent triggered by clicking the button.
     */
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

    /**
     * Deletes an entry (subcategory) when the associated button is clicked.
     *
     * @param event The ActionEvent triggered by clicking the button.
     */
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

    /**
     * Gets the Label associated with a button in the same HBox.
     *
     * @param button The button whose associated label is to be found.
     * @return The associated Label, or null if not found.
     */
    private Label getAssociatedLabel(Button button) {
        if (button.getParent() instanceof HBox parentHBox) {
            if (!parentHBox.getChildren().isEmpty() && parentHBox.getChildren().get(0) instanceof Label) {
                return (Label) parentHBox.getChildren().get(0);
            }
        }
        return null;
    }

    /**
     * Handles the calculation of the Sankey diagram when the "Calculate" button is clicked.
     */
    public void calculateButtonClicked() {
        try {
            double salary = parseDoubleOrZero(salaryField.getText());
            double additionalIncome = parseDoubleOrZero(additionalIncomeField.getText());

            Map<String, Double> incomeCategories = new HashMap<>();
            Map<String, Map<String, Double>> expenseCategories = new HashMap<>();

            // Segregate income and expense categories
            for (Node node : incomeVBox.getChildren()) {
                if (node instanceof TitledPane titledPane) {
                    VBox categoryVBox = (VBox) titledPane.getContent();
                    for (Node subNode : categoryVBox.getChildren()) {
                        if (subNode instanceof VBox subCategoryVBox) {
                            HBox labelBox = (HBox) subCategoryVBox.getChildren().get(0);
                            Label label = (Label) labelBox.getChildren().get(0);
                            String subCategory = label.getText();

                            HBox inputBox = (HBox) subCategoryVBox.getChildren().get(1);
                            TextField textField = (TextField) inputBox.getChildren().get(0);
                            double value = parseDoubleOrZero(textField.getText());

                            incomeCategories.put(subCategory, value);
                        }
                    }
                }
            }

            for (Node node : expensesVBox.getChildren()) {
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
                            double value = parseDoubleOrZero(textField.getText());

                            subcategories.put(subCategory, value);
                        }
                    }

                    expenseCategories.put(mainCategory, subcategories);
                }
            }

            SankeyDiagramController sankeyDiagramController = new SankeyDiagramController();
            sankeyDiagramController.generatePDF(salary, additionalIncome, incomeCategories, expenseCategories);

            try {
                File pdfFile = new File("sankey_diagram.pdf");
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("PDF file does not exist.");
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Error opening PDF. Please try again.").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred. Please try again.").show();
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to add a new main category.
     *
     * @param event The ActionEvent triggered by clicking the button.
     */
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

    /**
     * Creates a new main category and adds it to the appropriate VBox.
     *
     * @param name The name of the new main category.
     * @param type The type of the new main category (income or expense).
     */
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

    /**
     * Adds a new subcategory from the event of clicking the button.
     *
     * @param event The ActionEvent triggered by clicking the button.
     */
    public void addSubCategoryFromEvent(ActionEvent event) {
        Button button = (Button) event.getSource();
        VBox parentVBox = (VBox) button.getParent();
        addSubCategory(event, parentVBox);
    }

    /**
     * Adds a new subcategory to the specified parent VBox.
     *
     * @param event      The ActionEvent triggered by clicking the button.
     * @param parentVBox The VBox to which the new subcategory will be added.
     */
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

    /**
     * Opens a dialog to delete a main category.
     *
     * @param event The ActionEvent triggered by clicking the button.
     */
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

    /**
     * Deletes the specified main category.
     *
     * @param selectedCategory The main category to be deleted.
     */
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

    /**
     * Deletes a main category from the specified VBox.
     *
     * @param vBox The VBox from which the main category will be deleted.
     * @param name The name of the main category to be deleted.
     */
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
