package com.example.splittr;

import com.example.splittr.receiptobjects.Item;
import com.example.splittr.receiptobjects.Receipt;

import java.util.Arrays;
import java.util.Hashtable;
import java.math.BigDecimal;

public class SplittrMath {

    private static final int roundingMethod = BigDecimal.ROUND_HALF_UP; // method for rounding during arithmetic operations
    private static BigDecimal taxRate = BigDecimal.valueOf(0.08375); // tax rate as a decimal percentage

    private Receipt currentReceipt; // the receipt that this math class is being applied on

    private Hashtable<String, BigDecimal> userSubtotals = new Hashtable<>(); // hashmap of user -> subtotal owes
    private Hashtable<String, BigDecimal> userAdditionals = new Hashtable<>(); // hashmap of user -> additional owes
    private Hashtable<String, BigDecimal> userFinalTotals = new Hashtable<>(); // hashmap of user -> total owes (calculated by summing the other to hashmaps

    private BigDecimal weightedAdditional; // additional owes that are split amongst users based on their ratio of subtotal owes to the receipt total
    private BigDecimal unweightedAdditional; // additional owes that are split amongst users evenly

    private boolean approximateTaxes;

    // Constructor
    public SplittrMath(Receipt receipt) {
        this.currentReceipt = receipt;
        this.weightedAdditional = BigDecimal.ZERO;
        this.unweightedAdditional = BigDecimal.ZERO;
        this.approximateTaxes = false;
    }

    // Constructor overload
    public SplittrMath(Receipt receipt, double weightedAdditional, boolean approximateTax) {
        this.currentReceipt = receipt;
        this.weightedAdditional = BigDecimal.valueOf(weightedAdditional);
        this.unweightedAdditional = BigDecimal.ZERO;
        this.approximateTaxes = approximateTax;
        this.processSubtotal();
    }

    // Changes the default tax rate
    public BigDecimal setTaxRate(double percentage) {
        taxRate = BigDecimal.valueOf(percentage).divide(BigDecimal.valueOf(100), roundingMethod);
        return taxRate;
    }

    // Gets the list of items from the receipt and adds the amount each owner owes into the user subtotals hashmap
    public void processSubtotal() {
        userSubtotals.clear();
        for (Item item : currentReceipt.getItems()) {
            if (item.ownerCount() > 0) {
                BigDecimal splitCost = item.getCost().divide(BigDecimal.valueOf(item.ownerCount()), roundingMethod);
                for (String user : item.getOwners()) {
                    addToSubtotal(user, splitCost);
                }
                if (approximateTaxes)
                    this.processTax(item);
            }
        }
    }

    // add the taxed value of the item to the items owners distributed evenly
    public void processTax(Item item) {
        if (item.isTaxable()) {
            BigDecimal splitTax = getTaxedValue(item.getCost()).divide(BigDecimal.valueOf(item.ownerCount()), 10, roundingMethod);
            System.out.printf("Value of %s is $%.02f and taxed is $%.02f.\n", item.getName(), item.getCost(), getTaxedValue(item.getCost()));
            for (String user : item.getOwners()) {
                addToAdditional(user, splitTax);
            }
        }
    }

    // adds the taxed value of all user subtotals using the set tax rate (assumes all items are taxed)
    public void processGlobalTax() {
        for (String user : userSubtotals.keySet()) {
            addToAdditional(user, getTaxedValue(userSubtotals.get(user)));
        }
    }

    // process additional owes that are split amongst users based on their ratio of subtotal owes to the receipt total into the user additional owes hashmap
    public void processWeighted() {
        BigDecimal subtotal = getSumSubtotal();
        for (String user : userSubtotals.keySet()) {
            BigDecimal ratio = userSubtotals.get(user).divide(subtotal, 10, roundingMethod);
            addToAdditional(user, this.weightedAdditional.multiply(ratio));
        }
    }

    // process additional owes that are split amongst users evenly into the user additional owes hashmap
    public void processUnweighted() {
        BigDecimal subtotal = getSumSubtotal();
        for (String user : userSubtotals.keySet()) {
            addToAdditional(user, this.unweightedAdditional.divide(subtotal, 10, roundingMethod));
        }
    }

    // calculates the user final owes by processing all other amounts into their hashmaps and summing the two other hashmaps
    public void calculateFinal() {
        resetFinalTotals();
        for (String user : userSubtotals.keySet()) {
            userFinalTotals.put(user, userSubtotals.get(user).add(userAdditionals.getOrDefault(user, BigDecimal.ZERO)));
        }
    }

    // get the taxed value of an item
    private static BigDecimal getTaxedValue(BigDecimal amount) {
        return amount.multiply(taxRate);
    }

    // Gets the sum of all user subtotals from the user subtotal hashmap
    private BigDecimal getSumSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (BigDecimal value : userSubtotals.values()) {
            subtotal = subtotal.add(value);
        }
        System.out.println("getSumSubtotal() -> " + subtotal.toString());
        return subtotal;
    }

    // Adds 'amount' for 'user' in the user subtotals hashmap
    private BigDecimal addToSubtotal(String user, BigDecimal amount) {
        if (userSubtotals.containsKey(user)) {
            userSubtotals.replace(user, userSubtotals.get(user).add(amount));
        } else {
            userSubtotals.put(user, amount);
        }
        return userSubtotals.get(user);
    }

    // Adds 'amount' for 'user' in the additional subtotals hashmap
    private BigDecimal addToAdditional(String user, BigDecimal amount) {
        if (userAdditionals.containsKey(user)) {
            userAdditionals.replace(user, userAdditionals.get(user).add(amount));
        } else {
            userAdditionals.put(user, amount);
        }
        return userAdditionals.get(user);
    }

    // calculates the percentage value of sum of the subtotals
    public BigDecimal calculateSumSubtotalPercentage(double percentage) {
        return getSumSubtotal().multiply(BigDecimal.valueOf(percentage).divide(BigDecimal.valueOf(100), 4, roundingMethod));
    }

    // add
    public void addWeightedTipPercentage(double percentage) {
        BigDecimal tipAmount = calculateSumSubtotalPercentage(percentage);
        System.out.println("tipAmount = " + tipAmount.toString());
        addToWeighted(tipAmount);
    }

    // add an amount to the weighted additionals
    public BigDecimal addToWeighted(BigDecimal amount) {
        weightedAdditional = weightedAdditional.add(amount);
        return weightedAdditional;
    }

    // add an amount to the unweighted additionals
    public BigDecimal addToUnweighted(BigDecimal amount) {
        unweightedAdditional = unweightedAdditional.add(amount);
        return unweightedAdditional;
    }

    public void clearWeightedAdditionals() {
        weightedAdditional = BigDecimal.ZERO;
    }

    public void clearUnweightedAdditionals() {
        unweightedAdditional = BigDecimal.ZERO;
    }

    public void resetSubtotals() {
        userSubtotals.clear();
    }

    // clear the user additional owes hashmap
    public void resetAdditionals() {
        userAdditionals.clear();
    }

    public void resetFinalTotals() {
        userFinalTotals.clear();
    }

    public BigDecimal getWeightedAdditional() {
        return weightedAdditional;
    }

    public BigDecimal getUnweightedAdditional() {
        return unweightedAdditional;
    }

    // getter for the user subtotals hashmap
    public Hashtable<String, BigDecimal> getUserSubtotals() {
        return userSubtotals;
    }

    // getter for the user additional owes hashmap
    public Hashtable<String, BigDecimal> getUserAdditionals() {
        return userAdditionals;
    }

    // getter for the user final totals hashmap
    public Hashtable<String, BigDecimal> getUserFinalTotals() {
        return userFinalTotals;
    }

    public static void printHashmap(Hashtable<String, BigDecimal> table) {
        for (String name: table.keySet()) {
            String key = name;
            String value = table.get(name).toString();
            System.out.println(key + " " + value);
        }
    }

    // main function for testing the class
    public static void main(String[] args) {
        Receipt testReceipt = new Receipt(0, "YUXIANG 6/6");

        String user1 = "Jonathan";
        String user2 = "Tiffany";
        String user3 = "Vicki";
        String user4 = "Candace";
        String user5 = "Solina";
        String user6 = "Michael";

        Item food1 = new Item(0, "Jjajangmyeon", 8.00, true);
        Item food2 = new Item(1, "Jjajangmyeon", 8.00, true);
        Item food3 = new Item(2, "Jjajangmyeon", 8.00, true);
        Item food4 = new Item(3, "Tangsuyuk", 16.00, true);
        Item food5 = new Item(4, "Homemade Deep Fried Dumplings", 7.50, true);
        Item food6 = new Item(5, "Homemade Deep Fried Dumplings", 7.50, true);
        Item food7 = new Item(6, "Jjajangmyeon", 8.00, true);
        Item food8 = new Item(7, "Salt Pepper Squid", 9.95, true);
        Item food9 = new Item(8, "Chicken Wings", 8.50, true);

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

        SplittrMath mather = new SplittrMath(testReceipt, 24.62, false);
        mather.calculateFinal();
    }
}
