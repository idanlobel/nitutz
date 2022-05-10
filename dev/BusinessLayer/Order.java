package BusinessLayer;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
    private final int id;
    private final int supplyCompanyNumber;
  //  private final Contract contract;
    private final HashMap<Product,int[]> itemInfos; //[0]=amount,[1]=initial price [2]=discount
    private final ContactPerson contactPerson;
    private int totalPrice;
    private final LocalDate orderDate;
    private final LocalDate arrivalDate;


    public Order(int id,Contract contract, String contactPerson, LocalDate arrivalDate) {
        this.supplyCompanyNumber= contract.getSupplier().getCompanyNumber();
        this.id=id;
        //   this.contract = contract;
        this.itemInfos = new HashMap<>();
        this.contactPerson = contract.getSupplier().getContact(contactPerson);
        this.totalPrice = 0;
        this.orderDate = LocalDate.now();
        this.arrivalDate = arrivalDate;
    }
    public void AddProduct(SupplierProduct product, int amount, int initPrice,int discount){
        if(itemInfos.containsKey(product))
            throw new IllegalArgumentException("User Error: Duplicate "+product.getId()+" in order"); // TODO: replace with name after integration
        if(amount==0)
            throw new IllegalArgumentException("User Error: Item "+product.getId()+ " amount can not be 0");
        if(initPrice==0)
            throw new IllegalArgumentException("User Error: Item "+product.getId()+ " price can not be 0");
        itemInfos.put(product,new int[]{amount,initPrice,discount});
        totalPrice+=initPrice*amount*((double)discount/100);
    }

    public int getSupplyCompanyNumber() {
        return supplyCompanyNumber;
    }

    @Override
    public String toString() {
        StringBuilder acc= new StringBuilder();
        acc.append("Order number ").append(id).append("\nContact Person: ").append(contactPerson.getName()).append("\nTotal Price: ").append(totalPrice).append("\nOrder Date: ").append(orderDate).append("\nArrival Date: ").append(arrivalDate);
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
        return totalPrice;
    }
}
