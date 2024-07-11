package com.example.bankappprototype;

import com.example.bankappprototype.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application class for the Bank App Prototype.
 */
public class App extends Application {

    /**
     * Starts the JavaFX application by displaying the login window.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
