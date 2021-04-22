package com.example.splittr;

public class User {
    private int id;
    private String name;
    private boolean registered;

    public User(String name) {
        this.name = name;
        this.registered = false;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.registered = true;
    }

    public int getId() {
        if (!this.registered)
            return -1;
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
