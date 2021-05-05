package com.example.splittr.receiptobjects;

// User class for handling data for a single user of the app (UNUSED)
public class User {

    // initialize variables
    private final boolean registered;

    private int id;
    private String name;

    // constructor
    public User(String name) {
        this.name = name;
        this.registered = false;
    }

    // constructor
    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.registered = true;
    }

    // getter and setter for var id
    public int getId() {
        if (!this.registered)
            return -1;
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // getter and setter for var name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
