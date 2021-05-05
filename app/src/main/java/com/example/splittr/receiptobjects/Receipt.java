package com.example.splittr.receiptobjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Container class for holding all the items for a receipt for a user
public class Receipt {

    // initialize variables
    private final LocalDate creation_time;
    // formatter for formatting calendar dates
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private int id;
    private String label;
    private ArrayList<Item> items;

    // constructor
    public Receipt(int id, String label) {
        this.id = id;
        this.label = label;
        this.creation_time = LocalDate.now();
        this.items = new ArrayList<>();
    }

    // constructor
    public Receipt(int id, String label, String formattedDate) {
        this.id = id;
        this.label = label;
        this.creation_time = LocalDate.parse(formattedDate, formatter);
        this.items = new ArrayList<>();
    }

    // getter and setter for var id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // getter and setter for var label
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // getter for var creation_time
    public LocalDate getCreationDateTime() {
        return creation_time;
    }

    // getter for var creation_time using a formatter
    public String getCreationDate(DateTimeFormatter formatting) {
        return creation_time.format(formatting);
    }

    // getter and setter for var items
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    // adder for adding items to the items ArrayList
    public void addItem(Item new_item) {
        items.add(new_item);
    }

    // adder for adding multiple items to the items ArrayList
    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }

    // removing items from the items ArrayList
    public void removeItem(int index) {
        items.remove(index);
    }

    // easy printing of items in this receipt
    public void printItems() {
        for (Item item : items) {
            System.out.printf(" > %s = $%.2f\n", item.getName(), item.getCost());
        }
    }
}