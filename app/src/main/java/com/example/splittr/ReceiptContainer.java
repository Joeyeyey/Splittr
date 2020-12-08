package com.example.splittr;

import java.util.ArrayList;

public class ReceiptContainer {

    private static int receipt_count = 0;

    private int id;
    private String owner;
    private ArrayList<Receipt> receipts;

    public ReceiptContainer(int id, String owner) {
        this.id = id;
        this.owner = owner;
        this.receipts = new ArrayList<>();
    }

    public void createReceipt() {
        receipts.add(new Receipt(generateId(), "test label"));
    }

    public void deleteReceipt(int id) {
        receipts.remove(id);
    }

    private static int generateId() {
        return receipt_count++;
    }

    public static int getReceiptCount() {
        return receipt_count;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<Receipt> getReceipts() {
        return receipts;
    }

    public static void main(String[] args) {

    }
}