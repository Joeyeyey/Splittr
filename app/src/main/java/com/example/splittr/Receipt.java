package com.example.splittr;

import java.util.ArrayList;
import java.time.LocalDateTime;

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
}