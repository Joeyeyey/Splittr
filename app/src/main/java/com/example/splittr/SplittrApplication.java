package com.example.splittr;

import android.app.Application;

import com.example.splittr.receiptobjects.Item;
import com.example.splittr.receiptobjects.Receipt;
import com.example.splittr.receiptobjects.ReceiptContainer;

public class SplittrApplication  extends Application {

    private static ReceiptContainer receiptContainer;
    private static int nextReceiptId;

    private static Receipt selectedReceipt;
    private static int nextItemId;

    public SplittrApplication() {
        SplittrApplication.receiptContainer = new ReceiptContainer(0, "null");
        SplittrApplication.nextReceiptId = receiptContainer.getReceiptCount() + 1;
        SplittrApplication.testFill();
    }

    public static void testFill() {
        Receipt receipt1 = receiptContainer.createReceipt("Receipt 1");
        receiptContainer.createReceipt("Receipt 2");
        receiptContainer.createReceipt("Receipt 3");
        receiptContainer.createReceipt("Receipt 4");
        receiptContainer.createReceipt("Receipt 5");

        receipt1.addItem(new Item("Cheeseburger", 1.50, true));
        receipt1.addItem(new Item("20pc McNuggets", 5.00, true));
        receipt1.addItem(new Item("Quarter-Pounder Deluxe", 6.29, true));
        receipt1.addItem(new Item("Large Drink", 1.00, true));
    }

    public static ReceiptContainer getReceiptContainer() {
        return receiptContainer;
    }

    public static void setReceiptArrayList(ReceiptContainer receiptContainer) {
        SplittrApplication.receiptContainer = receiptContainer;
    }

    public static int getNextReceiptId() {
        return nextReceiptId;
    }

    public static void setNextReceiptId(int nextReceiptId) {
        SplittrApplication.nextReceiptId = nextReceiptId;
    }
}
