package SuppliersModule.SuppliersBusinessLayer.Contracts;

import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;

import java.util.HashMap;
import java.util.List;

public class PeriodicContract extends Contract {

    private boolean[] deliveryDays; //size=7, true= delivery day

    public PeriodicContract(int companyNumber,String orderingCP, List<SupplierProduct> products, HashMap<Integer, List<int[]>> discounts,List<int[]> generalDiscounts, boolean[] deliveryDays,boolean selfDel) {
        super(companyNumber,orderingCP,products,discounts,generalDiscounts,selfDel);
        this.deliveryDays=deliveryDays;
    }
    public boolean[] getDeliveryDays(){
        return deliveryDays;
    }

    public void setDeliveryDays(boolean[] deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public int getType(){
        return 1;
    }
}
