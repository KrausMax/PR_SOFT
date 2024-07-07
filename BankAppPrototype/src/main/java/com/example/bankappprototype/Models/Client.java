package com.example.bankappprototype.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final StringProperty password;
    private final IntegerProperty id;
    private final ObjectProperty<CheckingAccount> checkingAccount;
    private ObjectProperty<SavingsAccount> savingsAccount;
    private final ObjectProperty<LocalDate> dateCreated;
    private StringProperty image;

    public Client(String fName, String lName, String pAddress, String pword, int id, CheckingAccount cAccount, SavingsAccount sAccount, LocalDate date, String image) {
        this.firstName = new SimpleStringProperty(this, "First Name", fName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lName);
        this.payeeAddress = new SimpleStringProperty(this, "Payee Address", pAddress);
        this.password = new SimpleStringProperty(this, "Password", pword);
        this.id = new SimpleIntegerProperty(this,"ID",id);
        this.checkingAccount = new SimpleObjectProperty<>(this, "Checking Account", cAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this, "Savings Account", sAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date", date);
        this.image = new SimpleStringProperty(this, "Image", image);
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

    public ObjectProperty<CheckingAccount> checkingAccountProperty() {
        return checkingAccount;
    }

    public ObjectProperty<SavingsAccount> savingsAccountProperty() {
        return savingsAccount;
    }
    public void setSavingsAccount(SavingsAccount space) {
        this.savingsAccount = new SimpleObjectProperty<>(this, "Savings Account", space);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return dateCreated;
    }
    public StringProperty imageProperty() {
        return image;
    }

}
