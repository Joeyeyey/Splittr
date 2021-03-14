package com.example.splittr;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class Item {

    private String name;
    private BigDecimal cost;
    private boolean taxable;
    private ArrayList<User> owners;

    public Item(String name, double cost, boolean taxable) {
        this.name = name;
        this.cost = new BigDecimal(cost);
        this.taxable = taxable;
        this.owners = new ArrayList<>();
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

    public ArrayList<User> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<User> owners) {
        this.owners = owners;
    }

    public void addOwner(User user) {
        this.owners.add(user);
    }

    public void addOwners(List<User> users) {
        this.owners.addAll(users);
    }

    public int ownerCount() {
        return this.owners.size();
    }
}