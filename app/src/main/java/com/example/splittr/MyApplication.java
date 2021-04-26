package com.example.splittr;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static List<ReceiptComponents> receiptArrayList = new ArrayList<>();
    //keep track of next item in list
    private static int nextId = 12;

    public MyApplication() {
        fillArrayList();
    }

    private void fillArrayList() {
        ReceiptComponents p0 = new ReceiptComponents(0,"Eggs", 16.49, "None");
        ReceiptComponents p1 = new ReceiptComponents(1,"Tomatoes", 22.29, "None");
        ReceiptComponents p2 = new ReceiptComponents(2,"Milk", 12.49, "None");
        ReceiptComponents p3 = new ReceiptComponents(3,"Bread", 5.89, "None");
        ReceiptComponents p4 = new ReceiptComponents(4,"Apples", 10.99, "None");
        ReceiptComponents p5 = new ReceiptComponents(5,"Bananas", 5.89, "None");
        ReceiptComponents p6 = new ReceiptComponents(6,"Chicken", 10.99, "None");
        ReceiptComponents p7 = new ReceiptComponents(7,"Pork", 5.89, "None");

        receiptArrayList.add(p0);
        receiptArrayList.add(p1);
        receiptArrayList.add(p2);
        receiptArrayList.add(p3);
        receiptArrayList.add(p4);
        receiptArrayList.add(p5);
        receiptArrayList.add(p6);
        receiptArrayList.add(p7);
    }

    public static List<ReceiptComponents> getReceiptArrayList() {
        return receiptArrayList;
    }

    public static void setReceiptArrayList(List<ReceiptComponents> receiptArrayList) {
        com.example.splittr.MyApplication.receiptArrayList = receiptArrayList;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        com.example.splittr.MyApplication.nextId = nextId;
    }
}
