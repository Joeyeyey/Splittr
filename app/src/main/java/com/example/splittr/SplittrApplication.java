package com.example.splittr;

import android.app.Application;

import com.example.splittr.receiptobjects.Item;
import com.example.splittr.receiptobjects.Receipt;
import com.example.splittr.receiptobjects.ReceiptContainer;

public class SplittrApplication extends Application {

    private static ReceiptContainer receiptContainer;
    private static int nextReceiptId;

    private static Receipt selectedReceipt;
    private static int nextItemId;

    private static SplittrMath splitter;

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

        receipt1.addItem(new Item(0, "Cheeseburger", 1.50, true));
        receipt1.addItem(new Item(1, "20pc McNuggets", 5.00, true));
        receipt1.addItem(new Item(2, "Quarter-Pounder Deluxe", 6.29, true));
        receipt1.addItem(new Item(3, "Large Drink", 1.00, true));
    }

    public static ReceiptContainer getReceiptContainer() {
        return receiptContainer;
    }

    public static void setReceiptContainer(ReceiptContainer receiptContainer) {
        SplittrApplication.receiptContainer = receiptContainer;
    }

    public static int getNextReceiptId() {
        return nextReceiptId;
    }

    public static void setNextReceiptId(int nextReceiptId) {
        SplittrApplication.nextReceiptId = nextReceiptId;
    }

    public static Receipt getSelectedReceipt() {
        return selectedReceipt;
    }

    public static void setSelectedReceipt(Receipt selectedReceipt) {
        SplittrApplication.selectedReceipt = selectedReceipt;
        SplittrApplication.nextItemId = selectedReceipt.getItems().size() + 1;
        SplittrApplication.splitter = new SplittrMath(selectedReceipt);
    }

    public static int getNextItemId() {
        return nextItemId;
    }

    public static void setNextItemId(int nextItemId) {
        SplittrApplication.nextItemId = nextItemId;
    }

    public static SplittrMath getSplitter() {
        return splitter;
    }

    public static void recalculateReceiptIds() {
        for (int i = 0; i < receiptContainer.getReceipts().size(); i++) {
            receiptContainer.getReceipts().get(i).setId(i);
        }
    }

    public static void recalculateItemIds() {
        for (int i = 0; i < selectedReceipt.getItems().size(); i++) {
            selectedReceipt.getItems().get(i).setId(i);
        }
    }
}
