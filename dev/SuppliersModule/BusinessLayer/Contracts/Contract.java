package SuppliersModule.BusinessLayer.Contracts;



import SuppliersModule.BusinessLayer.Products.SupplierProduct;
import SuppliersModule.BusinessLayer.Supplier;

import java.util.*;

public class Contract {
    private final HashSet<Integer> productIds; //for contains
    private final Supplier supplier;
    private final HashMap<Integer, SupplierProduct> products; //TODO: ITEM IS A PLACEHOLDER- COMPLETE POST STORAGE MERGE
    private final HashMap<Integer, List<int[]>> discounts; //[0]=amount, [1]=percent (itemId is our system's id, not the supplier's)
    // public Contract(Supplier supplier, List<Product> products, HashMap<Integer,List<int[]>> discounts) {
    //    this.supplier =supplier;
    //   this.products=products;
    //  this.discounts=discounts;
    //  this.idList=new ArrayList<>();
    //  for(Product product:products)
    //     idList.add(product.getId());
    //TODO: HANDLE DYNAMIC DELIVERY DAYS
    //}
    public Contract(Supplier supplier, List<SupplierProduct> products, HashMap<Integer, List<int[]>> discounts) {
        this.productIds=new HashSet<>();
        this.supplier = supplier;
        this.products = new HashMap<>();
        this.discounts = discounts;
        for (SupplierProduct product : products) {
            this.products.put(product.getId(), product);
            this.productIds.add(product.getId());
        }
    }

    protected enum WeekDay {Sun,Mon,Tue,Wed,thu,Fri,Sat};
    public HashMap<Integer,List<int[]>> getDiscounts() {
        return discounts;
    }

    public Supplier getSupplier() {
        return supplier;
    }
    public Collection<Integer> getProductsIds() {
        return products.keySet();
    }
    public SupplierProduct getProduct(int id){
        return products.get(id);
    }
    public int getDiscount(int id,int amount){
        int dis=100;
        for(int[] discount:discounts.get(id)){
            if(discount[0]<amount && discount[1]<dis)
                dis=discount[1];
        }
        return dis;
    }
    public boolean ContainsProduct(int id){
        return productIds.contains(id);
    }
}
