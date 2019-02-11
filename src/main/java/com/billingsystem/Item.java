package com.billingsystem;

/* Class containing the details of each item **/
public class Item {

    private String itemName ;
    private int quantity;
    private double totalPrice;
    private double tarrif;

    public Item(String itemName, int quantity){
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTarrif() {
        return tarrif;
    }

    public void setTarrif(double tarrif) {
        this.tarrif = tarrif;
    }

}

