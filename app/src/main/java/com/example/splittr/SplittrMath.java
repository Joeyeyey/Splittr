package com.example.splittr;

import java.util.Arrays;
import java.util.Hashtable;

public class SplittrMath {

    private Receipt currentReceipt;
    private Hashtable<User, Double> userSubtotal;
    private Hashtable<User, Double> userAdditional;

    private double taxRate = 0.08265;
    private double subtotal = 0;
    private double tip = 0;

    public SplittrMath() {
        this.currentReceipt = null;
        this.userSubtotal = new Hashtable<>();
    }

    public SplittrMath(Receipt receipt) {
        this.currentReceipt = receipt;
        this.userSubtotal = new Hashtable<>();
        this.processTax();
        this.processReceiptOwes();
    }

    public SplittrMath(Receipt receipt, double tip) {
        this.currentReceipt = receipt;
        this.userSubtotal = new Hashtable<>();
        this.tip = tip;
        this.processTax();
        this.processTip();
        this.processReceiptOwes();
    }

    public void processReceiptOwes() {
        for (Item item : currentReceipt.getItems()) {
            this.processItemOwes(item);
        }
    }

    private void processItemOwes(Item item) {
        double splitCost = getItemSplitCost(item);
        for (User user : item.getOwners()) {
            if (userSubtotal.containsKey(user)) {
                this.userSubtotal.replace(user, userSubtotal.get(user) + splitCost);
            } else {
                this.userSubtotal.put(user, splitCost);
            }
        }
    }

    private static double getItemSplitCost(Item item) {
        return item.getCost() / item.getOwners().size();
    }

    private double processTax() {
        double totalTax = 0;
        for (Item item : currentReceipt.getItems()) {
            if (item.isTaxable()) {
                item.setCost(item.getCost() + item.getCost() * this.taxRate);
                totalTax += item.getCost() * this.taxRate;
            }
        }
        return totalTax;
    }

    private void processTip() {
        double currentSubtotal = getSubtotal();
        for (User user : userSubtotal.keySet()) {
            userSubtotal.replace(user, userSubtotal.get(user) + tip * (userSubtotal.get(user) / currentSubtotal));
        }
    }

    public double getUserOwes(User user) {
        return userSubtotal.getOrDefault(user, 0.);
    }

    private double getSubtotal() {
        double subtotal = 0;
        for (double value : userSubtotal.values()) {
            subtotal += value;
        }
        this.subtotal = subtotal;
        return subtotal;
    }

    public void printOwes() {
        for (User user : userSubtotal.keySet()) {
            System.out.printf("%s owes $%.2f\n", user.getName(), userSubtotal.get(user));
        }
    }

    public static void main(String[] args) {
        System.out.println("Test");
        Receipt testReceipt = new Receipt(0, "YUXIANG 6/6");

        User user1 = new User("Jonathan");
        User user2 = new User("Tiffany");
        User user3 = new User("Vicki");
        User user4 = new User("Candace");
        User user5 = new User("Solina");
        User user6 = new User("Michael");

        Item food1 = new Item("Jjajangmyeon", 8.00, true);
        Item food2 = new Item("Jjajangmyeon", 8.00, true);
        Item food3 = new Item("Jjajangmyeon", 8.00, true);
        Item food4 = new Item("Tangsuyuk", 16.00, true);
        Item food5 = new Item("Homemade Deep Fried Dumplings", 7.50, true);
        Item food6 = new Item("Homemade Deep Fried Dumplings", 7.50, true);
        Item food7 = new Item("Jjajangmyeon", 8.00, true);
        Item food8 = new Item("Salt Pepper Squid", 9.95, true);
        Item food9 = new Item("Chicken Wings", 8.50, true);

        food1.addOwner(user2);
        food2.addOwner(user5);
        food3.addOwner(user3);
        food4.addOwners(Arrays.asList(user1, user6, user2, user3));
        food5.addOwner(user4);
        food6.addOwners(Arrays.asList(user5, user2, user3));
        food7.addOwner(user4);
        food8.addOwner(user1);
        food9.addOwner(user6);

        testReceipt.addItems(Arrays.asList(food1, food2, food3, food4, food5, food6, food7, food8, food9));
        SplittrMath mather = new SplittrMath(testReceipt, 17.55);

        System.out.println("Test");
        mather.printOwes();
    }
}
