package BusinessLayer;




import java.util.HashMap;
import java.util.List;

public class Contract {
    private final Supplier supplier;
    private List<Product> products; //TODO: ITEM IS A PLACEHOLDER- COMPLETE POST STORAGE MERGE
    private HashMap<Integer,List<int[]>> discounts; //[1]=amount, [2]=percent (itemId is our system's id, not the supplier's)
    private boolean[] deliveryDays; //size=7, true= delivery day

    public Contract(Supplier supplier, List<Product> products, HashMap<Integer,List<int[]>> discounts) {
        this.supplier =supplier;
        this.products=products;
        this.discounts=discounts;
        //TODO: HANDLE DYNAMIC DELIVERY DAYS
    }
    public Contract(Supplier supplier, List<Product> products, HashMap<Integer,List<int[]>> discounts, boolean[] deliveryDays) {
        this.supplier =supplier;
        this.products=products;
        this.discounts=discounts;
        this.deliveryDays=deliveryDays;
    }

    public HashMap<Integer,List<int[]>> getDiscounts() {
        return discounts;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public List<Product> getProducts() {
        return products;
    }
}
