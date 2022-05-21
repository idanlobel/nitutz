package SuppliersModule.SuppliersBusinessLayer.Contracts;

import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;
import SuppliersModule.SuppliersBusinessLayer.Supplier;

import java.util.*;

public class Contract {
    private final HashSet<Integer> productIds; //for contains
    private final Supplier supplier;
    private final HashMap<Integer, SupplierProduct> products;
    private final HashMap<Integer, List<int[]>> discounts; //[0]=amount, [1]=percent (itemId is our system's id, not the supplier's)
    private final List<int[]> generalDiscounts;
    public Contract(Supplier supplier, List<SupplierProduct> products, HashMap<Integer, List<int[]>> discounts,List<int[]> generalDiscounts) {
        this.productIds=new HashSet<>();
        this.supplier = supplier;
        this.products = new HashMap<>();
        this.discounts = discounts;
        this.generalDiscounts=generalDiscounts;
        for (SupplierProduct product : products) {
            this.products.put(product.getId(), product);
            this.productIds.add(product.getId());
        }
    }

    public HashMap<Integer,List<int[]>> getDiscounts() {
        return discounts;
    }

    public List<int[]> getGeneralDiscounts() {
        return generalDiscounts;
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
    public int getGeneralDiscount(int amount){
        int ofTotal=100;
        for(int[] pair:generalDiscounts)
            if(pair[0]>amount && pair[1]<ofTotal)
                ofTotal= pair[1];
        return ofTotal;
    }
    public boolean ContainsProduct(int id){
        return productIds.contains(id);
    }
    public void addProduct(int catalogNumber, int supplierId,int price, List<int[]> discounts){
        if(ContainsProduct(catalogNumber))
            throw new IllegalArgumentException("product numbered "+catalogNumber+" is already sold in contract");
        products.put(catalogNumber,new SupplierProduct(supplierId,price,catalogNumber));
        this.discounts.put(catalogNumber,discounts);
    }
    public void ChangeProductPrice(int catalogNumber,int price){
        if(!ContainsProduct(catalogNumber))
            throw new IllegalArgumentException("product numbered "+catalogNumber+" is not sold in contract");
        products.get(catalogNumber).setPrice(price);
    }
    public void RemoveProduct(int catalogNumber){
        if(!ContainsProduct(catalogNumber))
            throw new IllegalArgumentException("product numbered "+catalogNumber+" is not sold in contract");
        products.remove(catalogNumber);
    }
    public void putDiscount(int catalogNumber, int amount, int discount){
        if(!ContainsProduct(catalogNumber))
            throw new IllegalArgumentException("product numbered "+catalogNumber+" is not sold in contract");
        boolean replaced=false;
        for(int[] pair:discounts.get(catalogNumber)) {
            if (pair[0] == amount)
                if (pair[1] == amount)
                    throw new IllegalArgumentException("amount/discount pair already exists for product");
                else {
                    pair[1]=discount;
                    replaced=true;
                }
                break;
        }
        if(!replaced)
            discounts.get(catalogNumber).add(new int[] {amount,discount});
    }
    public void removeDiscount(int catalogNumber,int amount) {
        boolean failed = true;
        for(int[] disPair:discounts.get(catalogNumber))
            if(disPair[0]==amount){
                discounts.get(catalogNumber).removeIf(pair -> pair[0] == amount);
                failed = false;
            }
        if (failed)
            throw new IllegalArgumentException("ERROR: no discount exists with specified amount");
    }
    public void putGeneralDiscount(int amount, int discount){
        boolean replaced=false;
        for(int[] pair:generalDiscounts) {
            if (pair[0] == amount)
                if (pair[1] == amount)
                    throw new IllegalArgumentException("amount/discount pair already exists for product");
                else {
                    pair[1]=discount;
                    replaced=true;
                }
            break;
        }
        if(!replaced)
            generalDiscounts.add(new int[] {amount,discount});
    }
    public void removeGeneralDiscount(int amount) {
        for(int[] pair: generalDiscounts)
            if(pair[0]==amount)
                generalDiscounts.removeIf(disPair -> disPair[0] == amount);
        throw new IllegalArgumentException("ERROR: no discount amount in contract");
    }
    public boolean isPeriodic(){
        return false;
    }

    public int getType(){
        return 0;
    }

    public HashMap<Integer, SupplierProduct> getAllProducts(){
        return products;
    }

    public boolean[] getDeliveryDays(){
        return new boolean[]{false,false,false,false,false,false,false};
    }
}
