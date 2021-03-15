package com.example.splittr;

import java.util.Arrays;
import java.util.Hashtable;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class SplittrMath {

    private static final BigDecimal taxRate = BigDecimal.valueOf(0.08375);
    private static final int roundingMethod = BigDecimal.ROUND_HALF_UP;

    private Receipt currentReceipt = null;
    private final Hashtable<User, BigDecimal> userSubtotals = new Hashtable<>();
    private final Hashtable<User, BigDecimal> userAdditionals = new Hashtable<>();
    private final Hashtable<User, BigDecimal> userFinalTotals = new Hashtable<>();

    private BigDecimal weightedAdditional = BigDecimal.ZERO;

    public SplittrMath() { }

    public SplittrMath(Receipt receipt) {
        this.currentReceipt = receipt;
        this.processReceiptOwes(false);
        this.calculateFinal();
    }

    public SplittrMath(Receipt receipt, double weightedAdditional) {
        this.currentReceipt = receipt;
        this.weightedAdditional = BigDecimal.valueOf(weightedAdditional);
        this.processReceiptOwes(false);
        this.processWeighted();
        this.calculateFinal();
    }

    public void processReceiptOwes(boolean applyTax) {
        for (Item item : currentReceipt.getItems()) {
            this.processItemOwes(item);
            if (applyTax)
                this.processTax(item);
        }
    }

    private void processItemOwes(Item item) {
        BigDecimal splitCost = item.getCost().divide(BigDecimal.valueOf(item.ownerCount()), roundingMethod);
        for (User user : item.getOwners()) {
            addToSubtotal(user, splitCost);
        }
    }

    private void processTax(Item item) {
        if (item.isTaxable()) {
            BigDecimal splitTax = getTaxedValue(item.getCost()).divide(BigDecimal.valueOf(item.ownerCount()), 10, roundingMethod);
            System.out.printf("Value of %s is $%.05f and taxed is $%.05f.\n", item.getName(), item.getCost(), getTaxedValue(item.getCost()));
            for (User user : item.getOwners()) {
                addToAdditional(user, splitTax);
            }
        }
    }

    private void processWeighted() {
        BigDecimal subtotal = getSubtotal();
        System.out.printf("The subtotal is $%.05f and the weighted additional is $%.05f\n", subtotal, this.weightedAdditional);
        for (User user : userSubtotals.keySet()) {
            BigDecimal ratio = userSubtotals.get(user).divide(subtotal, 10, roundingMethod);
            addToAdditional(user, this.weightedAdditional.multiply(ratio));
            System.out.printf("%s's ratio is %.06f and owes $%.06f.\n", user.getName(), ratio, this.weightedAdditional.multiply(ratio));
        }
    }

    private void calculateFinal() {
        for (User user : userSubtotals.keySet()) {
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

    private BigDecimal addToSubtotal(User user, BigDecimal amount) {
        if (userSubtotals.containsKey(user)) {
            userSubtotals.replace(user, userSubtotals.get(user).add(amount));
        } else {
            userSubtotals.put(user, amount);
        }
        return userSubtotals.get(user);
    }

    private BigDecimal addToAdditional(User user, BigDecimal amount) {
        if (userAdditionals.containsKey(user)) {
            userAdditionals.replace(user, userAdditionals.get(user).add(amount));
        } else {
            userAdditionals.put(user, amount);
        }
        return userAdditionals.get(user);
    }

    public void printOwes() {
        Hashtable<User, BigDecimal> userTotal = new Hashtable<>();
        System.out.println("===== USER SUBTOTALS =====");
        BigDecimal subtotal = BigDecimal.ZERO;
        for (User user : this.userSubtotals.keySet()) {
            System.out.printf("%s owes $%.5f\n", user.getName(), userSubtotals.get(user));
            subtotal = subtotal.add(userSubtotals.get(user));
        }
        System.out.printf("TOTAL VALUE: $%.5f\n", subtotal);

        System.out.println("===== USER ADDITIONAL (TAX/TIP) =====");
        BigDecimal additionalSubtotal = BigDecimal.ZERO;
        for (User user : this.userAdditionals.keySet()) {
            System.out.printf("%s owes $%.5f\n", user.getName(), userAdditionals.get(user));
            additionalSubtotal = additionalSubtotal.add(userAdditionals.get(user));
        }
        System.out.printf("TOTAL VALUE: $%.5f\n", additionalSubtotal);

        System.out.println("===== USER FINAL TOTAL =====");
        BigDecimal finalTotal = BigDecimal.ZERO;
        for (User user : this.userAdditionals.keySet()) {
            System.out.printf("%s owes $%.5f\n", user.getName(), userFinalTotals.get(user));
            finalTotal = finalTotal.add(userFinalTotals.get(user));
        }
        System.out.printf("TOTAL VALUE: $%.5f\n", finalTotal);
    }

    public static void main(String[] args) {
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

        SplittrMath mather = new SplittrMath(testReceipt, 24.62);
        mather.printOwes();
    }
}
