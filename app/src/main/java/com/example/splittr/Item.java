package com.example.splittr;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Item {

    private String name;
    private double cost;
    private boolean taxable;
    private ArrayList<User> owners;

    public Item(String name, double cost, boolean taxable) {
        this.name = name;
        this.cost = cost;
        this.taxable = taxable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
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
}