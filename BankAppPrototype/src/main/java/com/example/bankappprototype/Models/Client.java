package com.example.bankappprototype.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final StringProperty password;
    private final IntegerProperty id;
    private final ObjectProperty<Account> checkingAccount;
    private final ObjectProperty<Account> savingsAccount;
    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String fName, String lName, String pAddress, String pword, int id, Account cAccount, Account sAccount, LocalDate date) {
        this.firstName = new SimpleStringProperty(this, "First Name", fName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lName);
        this.payeeAddress = new SimpleStringProperty(this, "Payee Address", pAddress);
        this.password = new SimpleStringProperty(this, "Password", pword);
        this.id = new SimpleIntegerProperty(this,"ID",id);
        this.checkingAccount = new SimpleObjectProperty<>(this, "Checking Account", cAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this, "Savings Account", sAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date", date);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty pAddressProperty() {
        return payeeAddress;
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty pwordProperty() { return  password; }
    public ObjectProperty<Account> checkingAccountProperty() {
        return checkingAccount;
    }

    public ObjectProperty<Account> savingsAccountProperty() {
        return savingsAccount;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return dateCreated;
    }

}
