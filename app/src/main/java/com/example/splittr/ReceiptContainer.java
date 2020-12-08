package com.example.splittr;

import java.util.ArrayList;

public class ReceiptContainer {

    private int num_receipts;
    private String owner;
    private ArrayList<Receipt> receipts;

    public ReceiptContainer(int id, String owner) {
        this.num_receipts = 0;
        this.owner = owner;
        this.receipts = new ArrayList<>();
    }

    public void createReceipt() {
        receipts.add(new Receipt(num_receipts++, "test label"));
    }

    public void deleteReceipt(int index) {
        receipts.remove(index);
    }

    public int getReceiptCount() {
        return num_receipts;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public static void main(String[] args) {
        ReceiptContainer sample_container = new ReceiptContainer(0, "user");
        sample_container.createReceipt();
        sample_container.createReceipt();
        sample_container.createReceipt();
        System.out.println(String.format("Number of receipts: %d", sample_container.getReceiptCount()));

        Receipt sample_receipt = new Receipt(3, "Today's McDeez");
        sample_receipt.addItem(new Item("Cheeseburger", 1.50, true));
        sample_receipt.addItem(new Item("20pc McNuggets", 5.00, true));
        sample_receipt.addItem(new Item("Quarter-Pounder Deluxe", 6.29, true));
        sample_receipt.addItem(new Item("Large Drink", 1.00, true));
        sample_receipt.printItems();
    }
}