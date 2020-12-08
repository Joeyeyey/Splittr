package com.example.splittr;

public class Item {

    private String name;
    private float cost;
    private boolean taxable;

    public Item(String name, float cost, boolean taxable) {
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

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }
}