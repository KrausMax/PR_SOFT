package com.example.bankappprototype.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    public Button expense_savings_btn;
    public Button expense_dailyneeds_btn;
    public Button expense_living_btn;
    public Button income_add_btn;
    public Label car_lbl;
    public Label groceries_lbl;
    public Label electricity_lbl;
    public Label rent_lbl;
    public Label additional_income_lbl;
    public Label income_lbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

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

    public void deleteEntry(ActionEvent event) {
        Button button = (Button) event.getSource();
        HBox parentHBox = (HBox) button.getParent();
        VBox parentVBox = (VBox) parentHBox.getParent();

        int index = parentVBox.getChildren().indexOf(parentHBox);

        if(index != -1) {
            parentVBox.getChildren().remove(index);
            if (index < parentVBox.getChildren().size()) {
                parentVBox.getChildren().remove(index);
            }

            parentVBox.layout();
        }
    }

    private Label getAssociatedLabel(Button button) {
        if (button.getParent() instanceof HBox) {
            HBox parentHBox = (HBox) button.getParent();
            if (parentHBox.getChildren().size() > 0 && parentHBox.getChildren().get(0) instanceof Label) {
                return (Label) parentHBox.getChildren().get(0);
            }
        }
        return null;
    }

    public void calculateButtonClicked(ActionEvent event) {
        try {
            double salary = Double.parseDouble(salaryField.getText());
            double additionalIncome = Double.parseDouble(additionalIncomeField.getText());
            double rent = Double.parseDouble(rentField.getText());
            double electricity = Double.parseDouble(electricityField.getText());
            double groceries = Double.parseDouble(groceriesField.getText());
            double car = Double.parseDouble(carField.getText());
            double saving = Double.parseDouble(savingField.getText());

            // Load SankeyDiagramController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/sankeydiagram.fxml"));
            Parent root = loader.load();
            SankeyDiagramController sankeyDiagramController = loader.getController();

            // Generate PDF using SankeyDiagramController
            sankeyDiagramController.generatePDF(salary, additionalIncome, rent, electricity, groceries, car, saving);
            Desktop.getDesktop().open(new File("sankey_diagram.pdf"));

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error Loading SankeyDiagram.fxml.").show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format.").show();
        }

    }
}