package SuppliersBusinessLayer.Contracts;

import SuppliersBusinessLayer.Products.SupplierProduct;
import SuppliersBusinessLayer.Supplier;

import java.util.HashMap;
import java.util.List;

public class PeriodicContract extends Contract {

    private boolean[] deliveryDays; //size=7, true= delivery day

    public PeriodicContract(Supplier supplier, List<SupplierProduct> products, HashMap<Integer, List<int[]>> discounts,List<int[]> generalDiscounts, boolean[] deliveryDays) {
        super(supplier,products,discounts,generalDiscounts);
        this.deliveryDays=deliveryDays;
    }
    public boolean[] getDeliveryDays(){
        return deliveryDays;
    }

    public void setDeliveryDays(boolean[] deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    @Override
    public boolean isPeriodic(){
        return true;
    }
}
