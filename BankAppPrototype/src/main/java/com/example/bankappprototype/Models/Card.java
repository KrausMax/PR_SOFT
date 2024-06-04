package com.example.bankappprototype.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card {
    private final IntegerProperty account;
    private final StringProperty cardNumber;
    private final IntegerProperty sequenceNumber;
    private final IntegerProperty pin;

    public Card(int account, String cardNumber, int sequenceNumber, int pin) {
        this.account = new SimpleIntegerProperty(this,"Account", account);
        this.cardNumber = new SimpleStringProperty(this, "Card Number", cardNumber);
        this.sequenceNumber = new SimpleIntegerProperty(this, "Sequence Number", sequenceNumber);
        this.pin = new SimpleIntegerProperty(this, "PIN", pin);
    }

    public IntegerProperty accountProperty() {
        return account;
    }
    public StringProperty cardNumberProperty() {
        return cardNumber;
    }
    public IntegerProperty sequenceNumberProperty() {
        return sequenceNumber;
    }
    public IntegerProperty pinProperty() {
        return pin;
    }
}
