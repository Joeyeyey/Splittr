package com.example.splittr;

public class ReceiptComponents {
    private int id;
    private String item;
    private double cost;
    private String person;

    public ReceiptComponents(int id, String item, double cost, String person) {
        this.id = id;
        this.item = item;
        this.cost = cost;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReceiptComponents{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", cost=" + cost +
                ", person='" + person + '\'' +
                '}';
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
