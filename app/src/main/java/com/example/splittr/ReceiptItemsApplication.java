package com.example.splittr;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class ReceiptItemsApplication extends Application {

    private static List<ReceiptComponents> receiptArrayList = new ArrayList<>();
    //keep track of next item in list
    private static int nextId = receiptArrayList.size() + 1;

    public ReceiptItemsApplication() {
        fillArrayList();
    }

    private void fillArrayList() {
        ReceiptComponents p0 = new ReceiptComponents(0,"Eggs", 5.50, "Jonathan");
        ReceiptComponents p1 = new ReceiptComponents(1,"Milk", 6.80, "Richard");
        ReceiptComponents p2 = new ReceiptComponents(2,"Cheese", 3.70, "William");
        ReceiptComponents p3 = new ReceiptComponents(3,"Hot Dog", 10.00, "Michael");
        ReceiptComponents p4 = new ReceiptComponents(4,"Spam", 7.85, "Richard");
        ReceiptComponents p5 = new ReceiptComponents(5,"Ice Cream", 2.12, "Michael");
        ReceiptComponents p6 = new ReceiptComponents(6,"Beef", 5.21, "William");
        ReceiptComponents p7 = new ReceiptComponents(7,"Chicken", 0, "Richard");
        ReceiptComponents p8 = new ReceiptComponents(8,"Lettuce", 1, "Jonathan");
        ReceiptComponents p9 = new ReceiptComponents(9,"Tomato", 0.6, "Michael");
        ReceiptComponents p10 = new ReceiptComponents(10,"Onion", 2, "William");
        ReceiptComponents p11 = new ReceiptComponents(11,"Scallion", 0.90, "Richard");
        ReceiptComponents p12 = new ReceiptComponents(12,"Clorox", 8.59, "Michael");
        ReceiptComponents p13 = new ReceiptComponents(13,"Paper Towel", 19.99, "Jonathan");
//        ReceiptComponents p14 = new ReceiptComponents(14,"Bacon", 50.39, "None");
//        ReceiptComponents p15 = new ReceiptComponents(15,"Cart", 50.39, "None");
//        ReceiptComponents p16 = new ReceiptComponents(16,"Bag", 50.39, "None");
//        ReceiptComponents p17 = new ReceiptComponents(17,"Pants", 50.39, "None");

        receiptArrayList.add(p0);
        receiptArrayList.add(p1);
        receiptArrayList.add(p2);
        receiptArrayList.add(p3);
        receiptArrayList.add(p4);
        receiptArrayList.add(p5);
        receiptArrayList.add(p6);
        receiptArrayList.add(p7);
        receiptArrayList.add(p8);
        receiptArrayList.add(p9);
        receiptArrayList.add(p10);
        receiptArrayList.add(p11);
        receiptArrayList.add(p12);
        receiptArrayList.add(p13);
//        receiptArrayList.add(p14);
//        receiptArrayList.add(p15);
//        receiptArrayList.add(p16);
//        receiptArrayList.add(p17);

    }

    public static List<ReceiptComponents> getReceiptArrayList() {
        return receiptArrayList;
    }

    public static void setReceiptArrayList(List<ReceiptComponents> receiptArrayList) {
        ReceiptItemsApplication.receiptArrayList = receiptArrayList;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        ReceiptItemsApplication.nextId = nextId;
    }
}
