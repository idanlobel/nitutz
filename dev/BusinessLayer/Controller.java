package BusinessLayer;

import java.time.LocalDateTime;
import java.util.*;

public class Controller {
    private final Hashtable<Integer,Supplier> suppliers;
    private final Hashtable<Integer,Contract> contracts;
    private final List<Order> orderHistory;
    private final Hashtable<Integer,Item> items; //TODO: GET ITEMS FRO STOCK MODULE
    public Controller(){
        suppliers=new Hashtable<>();
        contracts=new Hashtable<>();
        orderHistory=new ArrayList<>();
        items=new Hashtable<Integer,Item>();
    }
    public Supplier AddSupplier(String name, Integer companyNumber, String bankNumber, List<ContactPerson> contactPeople) {
        if(suppliers.containsKey(companyNumber))
            throw new IllegalArgumentException("Company number already exists in the system");
        Supplier supplier=new Supplier(name,companyNumber,bankNumber,contactPeople);
        suppliers.put(companyNumber,supplier);
        return supplier;
    }
    public ContactPerson AddContact(Integer companyNumber,String name, String Email,String cellNumber){
        if(companyNumber==null | name==null | Email==null)
            throw new IllegalArgumentException("Parameter can not ne empty");
        if(!suppliers.containsKey(companyNumber))
            throw new IllegalArgumentException("No Supplier with this company number");
        ContactPerson contactPerson=new ContactPerson(name,Email,cellNumber);
        suppliers.get(companyNumber).addContact(contactPerson);
        return contactPerson;

    }

    public Contract SignContract(int companyNumber, List<Integer[]> idPairsList, HashMap<Integer,List<int[]>> discountsList, boolean[] deliveryDays) {
        Supplier supplier=suppliers.get(companyNumber);
        ArrayList<Item> SupplierItems=new ArrayList<>();
        for(Integer[] idPair: idPairsList){
            SupplierItems.add(new Item(idPair[0],idPair[1]));
        }
        Contract contract=new Contract(supplier,SupplierItems,discountsList,deliveryDays);
        contracts.put(companyNumber,contract);
        return contract;
    }

    public Order OrderProducts(int companyNumber, List<int[]> productsAndAmounts, ContactPerson contactPerson, LocalDateTime arrivalTime) { //product[0]=supplierId, [1]=amount
        Contract contract=contracts.get(companyNumber);
        HashMap<Integer,List<int[]>> discounts=contract.getDiscounts();
        Order order=new Order(contract,contactPerson,arrivalTime);
        for(int[] productAndAmount: productsAndAmounts){
            int itemPrice=0;
            if(items.containsKey(productAndAmount[0]))
                throw new IllegalArgumentException("Trying to order item not in contract");
            int id=productAndAmount[0],amount=productAndAmount[1];
            Item item=items.get(id);
            int discountPercent=1, maxDisAmount=0;
            List<int[]> discountsForItem=discounts.get(id);
            for(int[] discount: discountsForItem){
                if(amount>=discount[0] && discount[0]>maxDisAmount){
                    maxDisAmount=discount[0];
                    discountPercent=discount[1];
                }
            }
            order.AddProduct(item,amount,item.getBuyPrice()*amount*discountPercent);
        }
        orderHistory.add(order);
        return order;
    }
}
