package BusinessLayer;




import java.util.List;
import java.util.Set;

public class Contract {
    private final Supplier supplier;
    private List<Item> products; //TODO: ITEM IS A PLACEHOLDER
    private Set<int[]> discounts;
    private List<Integer> deliveryDays; //1=sunday -- 7=saturday

    public Contract(Supplier supplier,List<Item> products,Set<int[]> discounts) {
        this.supplier =supplier;
        this.products=products;
        this.discounts=discounts;
        //TODO: HANDLE DYNAMIC DELIVERY DAYS
    }
    public Contract(Supplier supplier,List<Item> products,Set<int[]> discounts,List<Integer> deliveryDays) {
        this.supplier =supplier;
        this.products=products;
        this.discounts=discounts;
        this.deliveryDays=deliveryDays;
    }

}
