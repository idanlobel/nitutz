package SuppliersModule.BusinessLayer.Contracts;

import SuppliersModule.BusinessLayer.Products.SupplierProduct;
import SuppliersModule.BusinessLayer.Supplier;

import java.util.HashMap;
import java.util.List;

public class PeriodicContract extends Contract {

    private final boolean[] deliveryDays; //size=7, true= delivery day

    public PeriodicContract(Supplier supplier, List<SupplierProduct> products, HashMap<Integer, List<int[]>> discounts, boolean[] deliveryDays) {
        super(supplier,products,discounts);
        this.deliveryDays=deliveryDays;
    }
    public boolean[] getDeliveryDays(){
        return deliveryDays;
    }

}
