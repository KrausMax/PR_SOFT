package com.example.bankappprototype.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Represents a client with personal details and account information.
 */
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

    /**
     * Constructs a Client with the specified details.
     *
     * @param fName the first name of the client
     * @param lName the last name of the client
     * @param pAddress the payee address of the client
     * @param pword the password of the client
     * @param id the ID of the client
     * @param cAccount the checking account of the client
     * @param sAccount the savings account of the client
     * @param date the date the client was created
     * @param image the image path of the client
     */
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

    /**
     * Gets the first name property of the client.
     *
     * @return the first name property
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Gets the last name property of the client.
     *
     * @return the last name property
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Gets the payee address property of the client.
     *
     * @return the payee address property
     */
    public StringProperty pAddressProperty() {
        return payeeAddress;
    }

    /**
     * Gets the ID property of the client.
     *
     * @return the ID property
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Gets the password property of the client.
     *
     * @return the password property
     */
    public StringProperty pwordProperty() {
        return password;
    }

    /**
     * Gets the checking account property of the client.
     *
     * @return the checking account property
     */
    public ObjectProperty<CheckingAccount> checkingAccountProperty() {
        return checkingAccount;
    }

    /**
     * Gets the savings account property of the client.
     *
     * @return the savings account property
     */
    public ObjectProperty<SavingsAccount> savingsAccountProperty() {
        return savingsAccount;
    }

    /**
     * Sets the savings account property of the client.
     *
     * @param space the new savings account
     */
    public void setSavingsAccount(SavingsAccount space) {
        this.savingsAccount = new SimpleObjectProperty<>(this, "Savings Account", space);
    }

    /**
     * Gets the date created property of the client.
     *
     * @return the date created property
     */
    public ObjectProperty<LocalDate> dateProperty() {
        return dateCreated;
    }

    /**
     * Gets the image property of the client.
     *
     * @return the image property
     */
    public StringProperty imageProperty() {
        return image;
    }
}
