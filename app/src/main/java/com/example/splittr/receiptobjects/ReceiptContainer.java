package com.example.splittr.receiptobjects;

import android.app.Application;

import java.util.ArrayList;
import java.util.Locale;

public class ReceiptContainer extends Application {

    private int num_receipts;
    private String owner;
    private ArrayList<Receipt> receipts;

    public ReceiptContainer(int id, String owner) {
        this.num_receipts = 0;
        this.owner = owner;
        this.receipts = new ArrayList<>();
    }

    public Receipt createReceipt() {
        receipts.add(new Receipt(num_receipts++, "test label"));
        return receipts.get(num_receipts - 1);
    }

    public Receipt createReceipt(String label) {
        receipts.add(new Receipt(num_receipts++, label));
        return receipts.get(num_receipts - 1);
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

    public void setReceipts(ArrayList<Receipt> n_receipts) {
        receipts = n_receipts;
    }

    public Receipt getReceipt(int index) {
        return receipts.get(index);
    }

    public void setReceipt(int index, Receipt receipt) {
        receipts.set(index, receipt);
    }

    public int getNextId() {
        return num_receipts;
    }

    public void setNextId(int nextId) {
        num_receipts = nextId;
    }

    public static void main(String[] args) {
        ReceiptContainer sample_container = new ReceiptContainer(0, "user");
        sample_container.createReceipt();
        sample_container.createReceipt();
        sample_container.createReceipt();
        System.out.println(String.format(Locale.US, "Number of receipts: %d", sample_container.getReceiptCount()));

        Receipt sample_receipt = new Receipt(3, "Today's McDeez");
        sample_receipt.addItem(new Item(0, "Cheeseburger", 1.50, true));
        sample_receipt.addItem(new Item(1, "20pc McNuggets", 5.00, true));
        sample_receipt.addItem(new Item(2, "Quarter-Pounder Deluxe", 6.29, true));
        sample_receipt.addItem(new Item(3, "Large Drink", 1.00, true));
        sample_receipt.printItems();
    }
}