package com.example.splittr.receiptobjects;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Item class for handling data regarding a single item from a receipt
public class Item {

    // initialize variables
    private int id;
    private String name;
    private BigDecimal cost;
    private boolean taxable;
    private ArrayList<String> owners;

    // constructor
    public Item(int id, String name, double cost, boolean taxable) {
        this.id = id;
        this.name = name;
        this.cost = BigDecimal.valueOf(cost);
        this.taxable = taxable;
        this.owners = new ArrayList<>();
    }

    // constructor
    public Item(int id, String name, double cost, boolean taxable, ArrayList<String> owners) {
        this.id = id;
        this.name = name;
        this.cost = BigDecimal.valueOf(cost);
        this.taxable = taxable;
        this.owners = owners;
    }


    // getter and setter for var name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // getter and setter for var cost
    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    // getter and setter for var taxable
    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    // getter and setter for var owners
    public ArrayList<String> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<String> owners) {
        this.owners = owners;
    }

    // adder for adding users to owners ArrayList
    public void addOwner(String user) {
        this.owners.add(user);
    }

    // adder for adding multiple users to the owners ArrayList
    public void addOwners(List<String> users) {
        this.owners.addAll(users);
    }

    // removing users from the owners ArrayList
    public void removeOwner(String user) {
        this.owners.remove(user);
    }

    // get the size of the owners ArrayList
    public int ownerCount() {
        return this.owners.size();
    }

    // getter and setter for var id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}