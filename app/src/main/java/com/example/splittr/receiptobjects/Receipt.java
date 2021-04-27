package com.example.splittr.receiptobjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private int id;
    private String label;
    private LocalDate creation_time;
    private ArrayList<Item> items;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public Receipt(int id, String label) {
        this.id = id;
        this.label = label;
        this.creation_time = LocalDate.now();
        this.items = new ArrayList<>();
    }

    public Receipt(int id, String label, String formattedDate) {
        this.id = id;
        this.label = label;
        this.creation_time = LocalDate.parse(formattedDate, formatter);
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDate getCreationDateTime() {
        return creation_time;
    }

    public String getCreationDate(DateTimeFormatter formatting) {
        return creation_time.format(formatting);
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