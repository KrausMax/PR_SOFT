package com.example.bankappprototype.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SavingsAccount extends Account {
    //the withdrawal Limit from the savings

    private final DoubleProperty withdrawlLimit;
    private final StringProperty spaceName;



    private final StringProperty spaceImage;

    public SavingsAccount(int owner, String accountNumber, double balance, double withdrawalLimit,int id, String spaceName, String spaceImage) {
        super(owner, accountNumber, balance, id);
        this.withdrawlLimit = new SimpleDoubleProperty(this, "Withdrawal Limit", withdrawalLimit);
        this.spaceName = new SimpleStringProperty(this, "Space Name", spaceName);
        this.spaceImage = new SimpleStringProperty(this, "Space Name", spaceImage);
    }

    public DoubleProperty withdrawalLimitProperty(){
        return withdrawlLimit;
    }

    public String getSpaceName() {
        return spaceName.get();
    }

    public StringProperty spaceNameProperty() {
        return spaceName;
    }

    public String getSpaceImage() {
        return spaceImage.get();
    }

    public StringProperty spaceImageProperty() {
        return spaceImage;
    }
    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
