package com.example.splittr.receiptobjects;

import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class Item {

    private int id;
    private String name;
    private BigDecimal cost;
    private boolean taxable;
    private ArrayList<String> owners;

    public Item(int id, String name, double cost, boolean taxable) {
        this.id = id;
        this.name = name;
        this.cost = BigDecimal.valueOf(cost);
        this.taxable = taxable;
        this.owners = new ArrayList<>();
    }

    public Item(int id, String name, double cost, boolean taxable, ArrayList<String> owners) {
        this.id = id;
        this.name = name;
        this.cost = BigDecimal.valueOf(cost);
        this.taxable = taxable;
        this.owners = owners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public ArrayList<String> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<String> owners) {
        this.owners = owners;
    }

    public void addOwner(String user) {
        this.owners.add(user);
    }

    public void addOwners(List<String> users) {
        this.owners.addAll(users);
    }

    public void removeOwner(User user) { this.owners.remove(user); }

    public int ownerCount() {
        return this.owners.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}