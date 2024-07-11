package com.example.bankappprototype.Controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that allows the user to create and send reports.
 */
public class ReportController implements Initializable {

    public TextArea report_textarea;
    public Button report_send_btn;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add initialization code here if necessary
    }

    /**
     * Handles the action when the "Send Report" button is clicked.
     * This method should be connected to the button's onAction property in the FXML file.
     */
    public void handleSendReport() {
        String reportText = report_textarea.getText();
        if (reportText.isEmpty()) {
            // Show an error message if the report is empty
            showError("Report cannot be empty.");
            return;
        }

        // Process the report (e.g., send it to the server or save it to a file)
        boolean success = sendReport(reportText);

        if (success) {
            showConfirmation("Report sent successfully.");
            report_textarea.clear();
        } else {
            showError("Failed to send the report. Please try again.");
        }
    }

    /**
     * Sends the report text to the server or saves it to a file.
     *
     * @param reportText The text of the report to be sent.
     * @return True if the report was sent successfully, false otherwise.
     */
    private boolean sendReport(String reportText) {
        // Implement the logic to send the report
        // Return true if successful, false otherwise
        return true;
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    private void showError(String message) {
        // Implement the logic to show an error message (e.g., using an Alert dialog)
    }

    /**
     * Displays a confirmation message to the user.
     *
     * @param message The confirmation message to be displayed.
     */
    private void showConfirmation(String message) {
        // Implement the logic to show a confirmation message (e.g., using an Alert dialog)
    }
}
