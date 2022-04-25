package BusinessLayer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Controller {
    private final Hashtable<Integer,Supplier> suppliers;
    private final Hashtable<Integer,Contract> contracts;
    private int orderIdTracker=0;
    private final Hashtable<Integer,Order> orderHistory;
    private final Hashtable<Integer, Product> items; //TODO: GET ITEMS FROM STOCK MODULE
    public Controller(){
        suppliers=new Hashtable<>();
        contracts=new Hashtable<>();
        orderHistory=new Hashtable<>();
        items=new Hashtable<Integer, Product>();
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

    public Contract SignContract(int companyNumber, List<int[]> itemInfoList, HashMap<Integer,List<int[]>> discountsList, boolean[] deliveryDays) {
        if(!suppliers.containsKey(companyNumber))
            throw new IllegalArgumentException("USER ERROR: supplier with id "+companyNumber+" does not exist");
        Supplier supplier=suppliers.get(companyNumber);
        ArrayList<Product> SupplierItems=new ArrayList<>();
        for(int[] itemInfo: itemInfoList){
            SupplierItems.add(new Product(itemInfo[0],itemInfo[1],itemInfo[2]));
        }
        Contract contract=new Contract(supplier,SupplierItems,discountsList,deliveryDays);
        contracts.put(companyNumber,contract);
        return contract;
    }

    public Order OrderProducts(int companyNumber, List<int[]> productsAndAmounts, String contactPerson, LocalDate arrivalTime) { //product[0]=supplierId, [1]=amount
        if(!contracts.containsKey(companyNumber))
            throw new IllegalArgumentException("USER ERROR: Supplier has not signed contract with us");
        Contract contract=contracts.get(companyNumber);
        HashMap<Integer,List<int[]>> discounts=contract.getDiscounts();
        Order order=new Order(orderIdTracker,contract,contactPerson,arrivalTime);
        for(int[] productAndAmount: productsAndAmounts){
            if(!contract.getProductsIds().contains(productAndAmount[0]))
                throw new IllegalArgumentException("Trying to order item not in contract");
            int id=productAndAmount[0],amount=productAndAmount[1];
      //      Product product=items.get(id);
            Product item=contract.getProduct(id);
            int discountPercent=1, maxDisAmount=0;
            List<int[]> discountsForItem=discounts.get(id);
            for(int[] discount: discountsForItem){
                if(amount>=discount[0] && discount[0]>maxDisAmount){
                    maxDisAmount=discount[0];
                    discountPercent=discount[1];
                }
            }
            order.AddProduct(item,amount,item.getBuyPrice(),discountPercent);
        }
        orderHistory.put(orderIdTracker,order);
        orderIdTracker++;
        return order;
    }

    public Order getOrder(int orderId) {
        if(!orderHistory.containsKey(orderId))
            throw new IllegalArgumentException("USER ERROR: Order with id "+orderId+" not in system");
        else return orderHistory.get(orderId);
    }
    public List<Order> getOrderList(int companyNum){
        ArrayList<Order> acc= new ArrayList<>();
        for(Order order:orderHistory.values())
            if(order.getSupplyCompanyNumber()==companyNum)
                acc.add(order);
        return acc;
    }

    public Contract getContract(int companyNum) {
        if(!suppliers.containsKey(companyNum))
            throw new IllegalArgumentException("USER ERROR: Supplier with company number "+companyNum+" not in system");
        else if(!contracts.containsKey(companyNum))
            throw new IllegalArgumentException("USER ERROR: Supplier "+companyNum+" has no contract");
        else return contracts.get(companyNum);
    }
    public List<Contract> getContractList() {
        return new ArrayList<>(contracts.values());
    }
    public Supplier getSupplier(int companyNum) {
        if(!suppliers.containsKey(companyNum))
            throw new IllegalArgumentException("USER ERROR: Supplier with company number "+companyNum+" not in system");
        else return suppliers.get(companyNum);
    }

    public List<Supplier> getSupplierList() {
        return new ArrayList<>(suppliers.values());
    }
}
