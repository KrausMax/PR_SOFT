package com.example.bankappprototype.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Represents a transaction with details such as sender, receiver, amount, date, and message.
 */
public class Transaction {
    private final StringProperty sender;
    private final StringProperty receiver;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;
    private final StringProperty transactionType;

    /**
     * Constructs a new Transaction with the specified parameters.
     *
     * @param sender the sender of the transaction
     * @param receiver the receiver of the transaction
     * @param amount the amount of the transaction
     * @param date the date of the transaction
     * @param message the message associated with the transaction
     * @param transactionType the type of the transaction
     */
    public Transaction(String sender, String receiver, double amount, LocalDate date, String message, String transactionType) {
        this.sender = new SimpleStringProperty(this, "Sender", sender);
        this.receiver = new SimpleStringProperty(this, "Receiver", receiver);
        this.amount = new SimpleDoubleProperty(this, "Amount", amount);
        this.date = new SimpleObjectProperty<>(this, "Date", date);
        this.message = new SimpleStringProperty(this, "Message", message);
        this.transactionType = new SimpleStringProperty(this, "TransactionType", transactionType);
    }

    /**
     * Gets the sender property.
     *
     * @return the sender property
     */
    public StringProperty senderProperty() {
        return this.sender;
    }

    /**
     * Gets the receiver property.
     *
     * @return the receiver property
     */
    public StringProperty receiverProperty() {
        return this.receiver;
    }

    /**
     * Gets the amount property.
     *
     * @return the amount property
     */
    public DoubleProperty amountProperty() {
        return this.amount;
    }

    /**
     * Gets the date property.
     *
     * @return the date property
     */
    public ObjectProperty<LocalDate> dateProperty() {
        return this.date;
    }

    /**
     * Gets the message property.
     *
     * @return the message property
     */
    public StringProperty messageProperty() {
        return this.message;
    }

    /**
     * Gets the transaction type property.
     *
     * @return the transaction type property
     */
    public StringProperty transactionTypeProperty() {
        return this.transactionType;
    }
}
