package SuppliersModule.SuppliersBusinessLayer;


import SuppliersModule.SuppliersBusinessLayer.Products.Product;
import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;
import java.util.*;
import java.time.LocalDate;
import java.util.HashMap;

public class Order {
    private final int id;
    private final int supplyCompanyNumber;
    private final HashMap<Product,int[]> itemInfos; //[0]=amount,[1]=initial price [2]=discount
    private final String contactPerson;
    private int totalPrice;
    private final LocalDate orderDate;
    private final LocalDate arrivalDate;
    private int generalDiscount;
    private int totalItemAmount;
    public Order(int id, int supplyCompanyNumber,String contactPerson, LocalDate arrivalDate) {
        this.supplyCompanyNumber= supplyCompanyNumber;
        this.id=id;
        //   this.contract = contract;
        this.itemInfos = new HashMap<>();
        this.contactPerson = contactPerson;
        this.totalPrice = 0;
        this.orderDate = LocalDate.now();
        this.arrivalDate = arrivalDate;
        this.generalDiscount=100;
        this.totalItemAmount=0;

    }
    public void AddProduct(SupplierProduct product, int amount, int initPrice, int discount,List<int[]> generalDiscounts){
        if(itemInfos.containsKey(product))
            throw new IllegalArgumentException("User Error: Duplicate "+product.getId()+" in order");
        if(amount==0)
            throw new IllegalArgumentException("User Error: Item "+product.getId()+ " amount can not be 0");
        if(initPrice==0)
            throw new IllegalArgumentException("User Error: Item "+product.getId()+ " price can not be 0");
        itemInfos.put(product,new int[]{amount,initPrice,discount});
        totalItemAmount+=amount;
        for(int[] discountPair:generalDiscounts)
            if(totalItemAmount>discountPair[0] & generalDiscount>discountPair[1])
                generalDiscount=discountPair[1];
        totalPrice+=initPrice*amount*((double)discount/100);
    }

    public int getSupplyCompanyNumber() {
        return supplyCompanyNumber;
    }
    @Override
    public String toString() {
        StringBuilder acc= new StringBuilder();
        acc.append("Order number ").append(id).append("\nContact Person: ").append(contactPerson).append("\nTotal Price: ").append(getTotalPrice()).append("\nOrder Date: ").append(orderDate).append("\nArrival Date: ").append(arrivalDate);
        acc.append("\n----------Item List---------=\n");
        int count=1;
        for(Product product: itemInfos.keySet()){
            int[] data=itemInfos.get(product);
            acc.append(count+": id: "+ product.getId()+
                    ", amount: "+data[0]+ ", single item price: "+data[1]+
                    ", discount: "+data[2]+"%, total pre discount price: "+data[0]*data[1]+
                    ", total post discount price: "+data[0]*data[1]*(data[2]/100)); //TODO: ADD PRODUCT NAME
        }


        return acc.toString();
    }

    public int getTotalPrice() {
        return totalPrice*generalDiscount;
    }

    public int getId(){
        return id;
    }
    public String getOrderDate(){
        return orderDate.toString();
    }
    public String getArrivalDate(){
        return arrivalDate.toString();
    }
    public String getContactPerson(){
        return contactPerson;
    }
    public int getCompanyNumber(){
        return supplyCompanyNumber;
    }
    public int getPrice(){
        return totalPrice;
    }
    public int getTotalItemAmount(){
        return totalItemAmount;
    }
    public int getTotalDiscount(){
        return generalDiscount;
    }
}
