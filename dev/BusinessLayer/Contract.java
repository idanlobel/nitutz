package BusinessLayer;




import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Contract {
    private final Supplier supplier;
    private List<Item> products; //TODO: ITEM IS A PLACEHOLDER- COMPLETE POST STORAGE MERGE
    private HashMap<Integer,List<int[]>> discounts; // [0]=itemId,[1]=amount, [2]=percent (itemId is our system's id, not the supplier's)
    private boolean[] deliveryDays; //size=7, true= delivery day

    public Contract(Supplier supplier,List<Item> products,HashMap<Integer,List<int[]>> discounts) {
        this.supplier =supplier;
        this.products=products;
        this.discounts=discounts;
        //TODO: HANDLE DYNAMIC DELIVERY DAYS
    }
    public Contract(Supplier supplier,List<Item> products,HashMap<Integer,List<int[]>> discounts,boolean[] deliveryDays) {
        this.supplier =supplier;
        this.products=products;
        this.discounts=discounts;
        this.deliveryDays=deliveryDays;
    }

    public HashMap<Integer,List<int[]>> getDiscounts() {
        return discounts;
    }
}
