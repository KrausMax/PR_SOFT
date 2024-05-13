package com.example.bankappprototype.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transaction {
    private final StringProperty sender;
    private final StringProperty receiver;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;
    private final StringProperty transaction_type;

    public Transaction(String sender, String receiver, double amount, LocalDate date, String message, String transactionType){
        this.sender = new SimpleStringProperty(this, "Sender", sender);
        this.receiver = new SimpleStringProperty(this, "Receiver", receiver);
        this.amount = new SimpleDoubleProperty(this, "Amount", amount);
        this.date = new SimpleObjectProperty<>(this, "Date", date);
        this.message = new SimpleStringProperty(this, "Message", message);
        this.transaction_type = new SimpleStringProperty(this, "Transaction_type",transactionType);
    }

    public StringProperty senderProperty() {
        return this.sender;
    }

    public StringProperty receiverProperty() {
        return this.receiver;
    }

    public DoubleProperty amountProperty() {
        return this.amount;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return this.date;
    }

    public StringProperty messageProperty() {
        return this.message;
    }

    public StringProperty transactionTypeProperty() {
        return this.transaction_type;
    }
}
