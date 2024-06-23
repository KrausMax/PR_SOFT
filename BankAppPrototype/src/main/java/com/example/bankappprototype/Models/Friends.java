package com.example.bankappprototype.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Friends {

    private final StringProperty client;
    private final StringProperty friend;
    private final IntegerProperty sharedSpace;

    public Friends(String client, String friend, int sharedSpace){
        this.client = new SimpleStringProperty(this, "Client", client);
        this.friend = new SimpleStringProperty(this, "Friend", friend);
        this.sharedSpace = new SimpleIntegerProperty(this, "Shared Space", sharedSpace);
    }

    public StringProperty clientProperty() {
        return this.client;
    }

    public StringProperty friendProperty() {
        return this.friend;
    }

    public IntegerProperty sharedSpaceProperty() {
        return this.sharedSpace;
    }
}
