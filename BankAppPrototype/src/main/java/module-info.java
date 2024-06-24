module com.example.bankappprototype {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires javafx.swing;


    opens com.example.bankappprototype to javafx.fxml;
    exports com.example.bankappprototype;
    exports com.example.bankappprototype.Controllers;
    exports com.example.bankappprototype.Controllers.Admin;
    exports com.example.bankappprototype.Controllers.Client;
    exports com.example.bankappprototype.Models;
    exports com.example.bankappprototype.Views;
}