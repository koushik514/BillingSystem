package com.billingsystem;

/* Class containing the details of each item **/
public class ItemTotal {

    public static double totalPriceOfTheOrder;

    public ItemTotal(double totalPriceOfTheOrder){
        this.totalPriceOfTheOrder = totalPriceOfTheOrder;
    }

    public double getTotalPriceOfTheOrder() {
        return totalPriceOfTheOrder;
    }

    public  void setTotalPriceOfTheOrder(double totalPriceOfTheOrder) {
        this.totalPriceOfTheOrder = totalPriceOfTheOrder;
    }

}

