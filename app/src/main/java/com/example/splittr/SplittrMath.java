package com.example.splittr;

import com.example.splittr.receiptobjects.Item;
import com.example.splittr.receiptobjects.Receipt;
import com.example.splittr.receiptobjects.User;

import java.util.Arrays;
import java.util.Hashtable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class SplittrMath {

    private static final BigDecimal taxRate = BigDecimal.valueOf(0.08375);
    private static final int roundingMethod = BigDecimal.ROUND_HALF_UP;

    private Receipt currentReceipt = null;
    private final Hashtable<String, BigDecimal> userSubtotals = new Hashtable<>();
    private final Hashtable<String, BigDecimal> userAdditionals = new Hashtable<>();
    private final Hashtable<String, BigDecimal> userFinalTotals = new Hashtable<>();

    private BigDecimal weightedAdditional = BigDecimal.ZERO;
    private BigDecimal unweightedAdditional = BigDecimal.ZERO;

    private boolean approximateTaxes = false;

    public SplittrMath() { }

    public SplittrMath(Receipt receipt) {
        this.currentReceipt = receipt;
    }

    public SplittrMath(Receipt receipt, double weightedAdditional, boolean approximateTax) {
        this.currentReceipt = receipt;
        this.weightedAdditional = BigDecimal.valueOf(weightedAdditional);
        this.approximateTaxes = approximateTax;
        this.processReceiptOwes();
    }

    public void processReceiptOwes() {
        userSubtotals.clear();
        for (Item item : currentReceipt.getItems()) {
            BigDecimal splitCost = item.getCost().divide(BigDecimal.valueOf(item.ownerCount()), roundingMethod);
            for (String user : item.getOwners()) {
                addToSubtotal(user, splitCost);
            }
            if (approximateTaxes)
                this.processTax(item);
        }
    }

    private void processTax(Item item) {
        if (item.isTaxable()) {
            BigDecimal splitTax = getTaxedValue(item.getCost()).divide(BigDecimal.valueOf(item.ownerCount()), 10, roundingMethod);
            System.out.printf("Value of %s is $%.02f and taxed is $%.02f.\n", item.getName(), item.getCost(), getTaxedValue(item.getCost()));
            for (String user : item.getOwners()) {
                addToAdditional(user, splitTax);
            }
        }
    }

    private void processWeighted() {
        BigDecimal subtotal = getSubtotal();
        for (String user : userSubtotals.keySet()) {
            BigDecimal ratio = userSubtotals.get(user).divide(subtotal, 10, roundingMethod);
            addToAdditional(user, this.weightedAdditional.multiply(ratio));
        }
    }

    private void processUnweighted() {
        BigDecimal subtotal = getSubtotal();
        for (String user : userSubtotals.keySet()) {
            addToAdditional(user, this.unweightedAdditional.divide(subtotal, 10, roundingMethod));
        }
    }

    private void calculateFinal() {
        processReceiptOwes();
        for (String user : userSubtotals.keySet()) {
            userFinalTotals.put(user, userSubtotals.get(user).add(userAdditionals.getOrDefault(user, BigDecimal.ZERO)));
        }
    }

    private static BigDecimal getTaxedValue(BigDecimal amount) {
        return amount.multiply(taxRate);
    }

    private BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (BigDecimal value : userSubtotals.values()) {
            subtotal = subtotal.add(value);
        }
        return subtotal;
    }

    private BigDecimal addToSubtotal(String user, BigDecimal amount) {
        if (userSubtotals.containsKey(user)) {
            userSubtotals.replace(user, userSubtotals.get(user).add(amount));
        } else {
            userSubtotals.put(user, amount);
        }
        return userSubtotals.get(user);
    }

    private BigDecimal addToAdditional(String user, BigDecimal amount) {
        if (userAdditionals.containsKey(user)) {
            userAdditionals.replace(user, userAdditionals.get(user).add(amount));
        } else {
            userAdditionals.put(user, amount);
        }
        return userAdditionals.get(user);
    }

    public String getUserOwes(String user) {
        NumberFormat localCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return localCurrencyFormat.format(userFinalTotals.getOrDefault(user, BigDecimal.ZERO).doubleValue());
    }

    public void printOwes() {
        System.out.println("===== USER SUBTOTALS =====");
        BigDecimal subtotal = BigDecimal.ZERO;
        for (String user : this.userSubtotals.keySet()) {
            System.out.printf("%s owes $%.2f\n", user, userSubtotals.get(user));
            subtotal = subtotal.add(userSubtotals.get(user));
        }
        System.out.printf("TOTAL VALUE: $%.2f\n", subtotal);

        System.out.println("===== USER ADDITIONAL (TAX/TIP) =====");
        BigDecimal additionalSubtotal = BigDecimal.ZERO;
        for (String user : this.userAdditionals.keySet()) {
            System.out.printf("%s owes $%.2f\n", user, userAdditionals.get(user));
            additionalSubtotal = additionalSubtotal.add(userAdditionals.get(user));
        }
        System.out.printf("TOTAL VALUE: $%.2f\n", additionalSubtotal);

        System.out.println("===== USER FINAL TOTAL =====");
        BigDecimal finalTotal = BigDecimal.ZERO;
        for (String user : this.userFinalTotals.keySet()) {
            System.out.printf("%s owes %s\n", user, getUserOwes(user));
            finalTotal = finalTotal.add(userFinalTotals.get(user));
        }
        System.out.printf("TOTAL VALUE: $%.2f\n", finalTotal);
    }

    public static void main(String[] args) {
        Receipt testReceipt = new Receipt(0, "YUXIANG 6/6");

        String user1 = new String("Jonathan");
        String user2 = new String("Tiffany");
        String user3 = new String("Vicki");
        String user4 = new String("Candace");
        String user5 = new String("Solina");
        String user6 = new String("Michael");

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
        mather.printOwes();
    }
}
