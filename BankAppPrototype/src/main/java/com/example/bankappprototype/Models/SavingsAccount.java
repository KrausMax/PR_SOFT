package com.example.bankappprototype.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a savings account with withdrawal limits and additional properties.
 */
public class SavingsAccount extends Account {
    // The withdrawal limit from the savings account
    private final DoubleProperty withdrawalLimit;
    private final StringProperty spaceName;
    private final StringProperty spaceImage;

    /**
     * Constructs a new SavingsAccount with the specified parameters.
     *
     * @param owner the ID of the account owner
     * @param accountNumber the account number
     * @param balance the account balance
     * @param withdrawalLimit the withdrawal limit
     * @param id the account ID
     * @param spaceName the name of the space
     * @param spaceImage the image associated with the space
     */
    public SavingsAccount(int owner, String accountNumber, double balance, double withdrawalLimit, int id, String spaceName, String spaceImage) {
        super(owner, accountNumber, balance, id);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "Withdrawal Limit", withdrawalLimit);
        this.spaceName = new SimpleStringProperty(this, "Space Name", spaceName);
        this.spaceImage = new SimpleStringProperty(this, "Space Image", spaceImage);
    }

    /**
     * Gets the withdrawal limit property.
     *
     * @return the withdrawal limit property
     */
    public DoubleProperty withdrawalLimitProperty() {
        return withdrawalLimit;
    }

    /**
     * Gets the space name.
     *
     * @return the space name
     */
    public String getSpaceName() {
        return spaceName.get();
    }

    /**
     * Gets the space name property.
     *
     * @return the space name property
     */
    public StringProperty spaceNameProperty() {
        return spaceName;
    }

    /**
     * Gets the space image.
     *
     * @return the space image
     */
    public String getSpaceImage() {
        return spaceImage.get();
    }

    /**
     * Gets the space image property.
     *
     * @return the space image property
     */
    public StringProperty spaceImageProperty() {
        return spaceImage;
    }

    /**
     * Returns a string representation of the account.
     *
     * @return the account number
     */
    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
