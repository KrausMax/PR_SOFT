package com.example.bankappprototype.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Represents a friend relationship between clients.
 */
public class Friends {

    private final StringProperty client;
    private final StringProperty friend;
    private final IntegerProperty sharedSpace;

    /**
     * Constructs a Friends instance with the specified client, friend, and shared space.
     *
     * @param client      the client
     * @param friend      the friend
     * @param sharedSpace the shared space
     */
    public Friends(String client, String friend, int sharedSpace) {
        this.client = new SimpleStringProperty(this, "Client", client);
        this.friend = new SimpleStringProperty(this, "Friend", friend);
        this.sharedSpace = new SimpleIntegerProperty(this, "Shared Space", sharedSpace);
    }

    /**
     * Gets the client property.
     *
     * @return the client property
     */
    public StringProperty clientProperty() {
        return this.client;
    }

    /**
     * Gets the friend property.
     *
     * @return the friend property
     */
    public StringProperty friendProperty() {
        return this.friend;
    }

    /**
     * Gets the shared space property.
     *
     * @return the shared space property
     */
    public IntegerProperty sharedSpaceProperty() {
        return this.sharedSpace;
    }
}
