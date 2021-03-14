package com.example.splittr;

import java.util.Hashtable;

public class SplittrMath {
    private Receipt currentReceipt;
    private Hashtable<User, Double> userOwes;

    public SplittrMath() {
        this.currentReceipt = null;
        this.userOwes = new Hashtable<>();
    }

    public SplittrMath(Receipt receipt) {
        this.currentReceipt = receipt;
        this.userOwes = new Hashtable<>();
        this.processReceiptOwes();
    }

    private void processReceiptOwes() {
        for (Item item : currentReceipt.getItems()) {
            this.processItemOwes(item);
        }
    }

    private void processItemOwes(Item item) {
        for (User user : item.getOwners()) {
            this.userOwes.put(user, getItemSplitCost(item));
        }
    }

    private static double getItemSplitCost(Item item) {
        return item.getCost() / item.getOwners().size();
    }

    private double getUserOwes(User user) {
        return userOwes.getOrDefault(user, 0.);
    }

}
