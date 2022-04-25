package BusinessLayer;




import java.util.*;

public class Contract {
    private final Supplier supplier;
    private final HashMap<Integer, Product> products; //TODO: ITEM IS A PLACEHOLDER- COMPLETE POST STORAGE MERGE
    private final HashMap<Integer, List<int[]>> discounts; //[0]=amount, [1]=percent (itemId is our system's id, not the supplier's)
    private final boolean[] deliveryDays; //size=7, true= delivery day

    // public Contract(Supplier supplier, List<Product> products, HashMap<Integer,List<int[]>> discounts) {
    //    this.supplier =supplier;
    //   this.products=products;
    //  this.discounts=discounts;
    //  this.idList=new ArrayList<>();
    //  for(Product product:products)
    //     idList.add(product.getId());
    //TODO: HANDLE DYNAMIC DELIVERY DAYS
    //}
    public Contract(Supplier supplier, List<Product> products, HashMap<Integer, List<int[]>> discounts, boolean[] deliveryDays) {
        this.supplier = supplier;
        this.products = new HashMap<>();
        this.discounts = discounts;
        this.deliveryDays = deliveryDays;
        for (Product product : products)
            this.products.put(product.getId(),product);
    }


    public HashMap<Integer,List<int[]>> getDiscounts() {
        return discounts;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Collection<Integer> getProductsIds() {
        return products.keySet();
    }
    public Product getProduct(int id){
        return products.get(id);
    }

    @Override
    public String toString() {
        return "supplier=" + supplier +
                ", products=" + products +
                ", discounts=" + discounts +
                ", deliveryDays=" + Arrays.toString(deliveryDays)+"\n";
    }
}
