package com.example.splittr;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static List<ReceiptComponents> receiptArrayList = new ArrayList<ReceiptComponents>();
    //keep track of next item in list
    private static int nextId = 12;

    public MyApplication() {
        fillArrayList();
    }

    private void fillArrayList() {
        ReceiptComponents p0 = new ReceiptComponents(0,"Eggs", 50.39, "None");
        ReceiptComponents p1 = new ReceiptComponents(1,"Eggs", 100, "None");
        ReceiptComponents p2 = new ReceiptComponents(2,"Eggs", 50.39, "None");
        ReceiptComponents p3 = new ReceiptComponents(3,"Eggs", 50.39, "None");
        ReceiptComponents p4 = new ReceiptComponents(4,"Eggs", 50.39, "None");
        ReceiptComponents p5 = new ReceiptComponents(5,"Eggs", 50.39, "None");
        ReceiptComponents p6 = new ReceiptComponents(6,"Eggs", 0, "None");
        ReceiptComponents p7 = new ReceiptComponents(7,"Eggs", 50.39, "None");
        ReceiptComponents p8 = new ReceiptComponents(8,"Eggs", 50.39, "None");
        ReceiptComponents p9 = new ReceiptComponents(9,"Eggs", 50.39, "None");
        ReceiptComponents p10 = new ReceiptComponents(10,"Eggs", 50.39, "None");
        ReceiptComponents p11 = new ReceiptComponents(11,"Eggs", 50.39, "None");

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
