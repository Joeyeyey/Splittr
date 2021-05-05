package com.example.splittr;

import android.app.Application;

import com.example.splittr.receiptobjects.Item;
import com.example.splittr.receiptobjects.Receipt;
import com.example.splittr.receiptobjects.ReceiptContainer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// singleton class for variable storage/access and overall management of receipts throughout the app
public class SplittrApplication extends Application {

    // initialize variables
    private static ReceiptContainer receiptContainer;
    private static int nextReceiptId;

    private static Receipt selectedReceipt;
    private static int nextItemId;

    // main SplitterMath object that all activities will refer to
    private static SplittrMath splitter;

    // variables for handling json responses from the Tesseract post request
    public static String globalPostResponse;
    public static JSONObject globalJSONObj;

    // initial constructor
    public SplittrApplication() {
        SplittrApplication.receiptContainer = new ReceiptContainer(0, "null");
        SplittrApplication.nextReceiptId = receiptContainer.getReceiptCount() + 1;
        SplittrApplication.testFill();
    }

    // function for testing the class
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

    // getter and setter for var receiptContainer
    public static ReceiptContainer getReceiptContainer() {
        return receiptContainer;
    }

    public static void setReceiptContainer(ReceiptContainer receiptContainer) {
        SplittrApplication.receiptContainer = receiptContainer;
    }

    // getter and setter for var nextReceiptId
    public static int getNextReceiptId() {
        return nextReceiptId;
    }

    public static void setNextReceiptId(int nextReceiptId) {
        SplittrApplication.nextReceiptId = nextReceiptId;
    }

    // getter and setter for var selectedReceipt
    public static Receipt getSelectedReceipt() {
        return selectedReceipt;
    }

    public static void setSelectedReceipt(Receipt selectedReceipt) {
        SplittrApplication.selectedReceipt = selectedReceipt;
        SplittrApplication.nextItemId = selectedReceipt.getItems().size() + 1;
        SplittrApplication.splitter = new SplittrMath(selectedReceipt);
    }

    // getter and setter for var nextItemId
    public static int getNextItemId() {
        return nextItemId;
    }

    public static void setNextItemId(int nextItemId) {
        SplittrApplication.nextItemId = nextItemId;
    }

    // getter for var splitter
    public static SplittrMath getSplitter() {
        return splitter;
    }

    // reset receipt ids based on their index position
    public static void recalculateReceiptIds() {
        for (int i = 0; i < receiptContainer.getReceipts().size(); i++) {
            receiptContainer.getReceipts().get(i).setId(i);
        }
    }

    // reset item ids based on their index position
    public static void recalculateItemIds() {
        for (int i = 0; i < selectedReceipt.getItems().size(); i++) {
            selectedReceipt.getItems().get(i).setId(i);
        }
    }

    // add receipt data from json information
    public static void addReceiptFromJson() {
        try {
            Receipt newReceipt = receiptContainer.createReceipt("New Receipt");
            JSONArray itemdata = globalJSONObj.getJSONArray("data");
            for (int i = 0; i < itemdata.length(); i++) {
                JSONObject item = itemdata.getJSONObject(i);
                newReceipt.addItem(new Item(i, (String) item.get("label"),
                        Double.parseDouble((String) item.get("cost")), false));
            }
            recalculateReceiptIds();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}