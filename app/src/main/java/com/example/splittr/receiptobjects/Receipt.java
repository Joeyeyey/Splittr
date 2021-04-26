package com.example.splittr.receiptobjects;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

public class Receipt {

    private int id;
    private String label;
    private LocalDateTime creation_time;
    private ArrayList<Item> items;

    public Receipt(int id, String label) {
        this.id = id;
        this.label = label;
        this.creation_time = LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDateTime getCreationTime() {
        return creation_time;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item new_item) {
        items.add(new_item);
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    public void printItems() {
        for (Item item : items) {
            System.out.printf(" > %s = $%.2f\n", item.getName(), item.getCost());
        }
    }
}