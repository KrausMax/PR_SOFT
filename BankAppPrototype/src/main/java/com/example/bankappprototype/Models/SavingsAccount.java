package com.example.bankappprototype.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingsAccount extends Account {
    //the withdrawal Limit from the savings

    private final DoubleProperty withdrawlLimit;

    public SavingsAccount(int owner, String accountNumber, double balance, double withdrawalLimit,int id) {
        super(owner, accountNumber, balance, id);
        this.withdrawlLimit = new SimpleDoubleProperty(this, "Withdrawal Limit", withdrawalLimit);
    }

    public DoubleProperty withdrawalLimitProperty(){
        return withdrawlLimit;
    }

    @Override
    public String toString() {
        return accountNumberProperty().get();
    }
}
