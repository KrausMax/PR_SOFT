package com.example.bankappprototype.Models;

import javafx.beans.property.*;

/**
 * Represents a bank card with an account, card number, sequence number, and PIN.
 */
public class Card {
    private final IntegerProperty account;
    private final StringProperty cardNumber;
    private final IntegerProperty sequenceNumber;
    private final IntegerProperty pin;

    /**
     * Constructs a Card with the specified account, card number, sequence number, and PIN.
     *
     * @param account the account ID associated with the card
     * @param cardNumber the card number
     * @param sequenceNumber the sequence number of the card
     * @param pin the PIN of the card
     */
    public Card(int account, String cardNumber, int sequenceNumber, int pin) {
        this.account = new SimpleIntegerProperty(this,"Account", account);
        this.cardNumber = new SimpleStringProperty(this, "Card Number", cardNumber);
        this.sequenceNumber = new SimpleIntegerProperty(this, "Sequence Number", sequenceNumber);
        this.pin = new SimpleIntegerProperty(this, "PIN", pin);
    }

    /**
     * Gets the account property associated with the card.
     *
     * @return the account property
     */
    public IntegerProperty accountProperty() {
        return account;
    }

    /**
     * Gets the card number property of the card.
     *
     * @return the card number property
     */
    public StringProperty cardNumberProperty() {
        return cardNumber;
    }

    /**
     * Gets the sequence number property of the card.
     *
     * @return the sequence number property
     */
    public IntegerProperty sequenceNumberProperty() {
        return sequenceNumber;
    }

    /**
     * Gets the PIN property of the card.
     *
     * @return the PIN property
     */
    public IntegerProperty pinProperty() {
        return pin;
    }
}
