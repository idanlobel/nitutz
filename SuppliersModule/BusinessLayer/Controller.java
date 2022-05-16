package BusinessLayer;

import BusinessLayer.Contracts.Contract;
import BusinessLayer.Contracts.PeriodicContract;
import BusinessLayer.Products.PeriodicProduct;
import BusinessLayer.Products.SupplierProduct;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Controller {
    private final Hashtable<Integer,Supplier> suppliers;
    private final Hashtable<Integer, Contract> shortageContracts;
    private final HashMap<Integer,Order> toDeliverOrders;
    private final HashMap<Integer,Collection<PeriodicProduct>> periodicProducts; //0-sunday, 6-saturday
    private final HashMap<Integer,Collection<PeriodicContract>> periodicSuppliers; //0-sunday, 6-saturday
    private int orderIdTracker=0;
    private final Hashtable<Integer,Order> orderHistory;
    public Controller(){
        toDeliverOrders=new HashMap<>();
        suppliers=new Hashtable<>();
        shortageContracts=new Hashtable<>();
        orderHistory=new Hashtable<>();
        periodicProducts=new HashMap<>();
        periodicSuppliers=new HashMap<>();
        for(int i=0;i<7;i++){
            periodicProducts.put(i,new ArrayList<>());
            periodicSuppliers.put(i,new ArrayList<>());
        }
    }

    public Supplier AddSupplier(String name, Integer companyNumber, String bankNumber, List<ContactPerson> contactPeople,String orderingCP) {
        if(suppliers.containsKey(companyNumber))
            throw new IllegalArgumentException("Company number already exists in the system");
        Supplier supplier=new Supplier(name,companyNumber,bankNumber,contactPeople,orderingCP);
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

    public Contract SignPeriodicContract(int companyNumber, List<int[]> itemInfoList, HashMap<Integer,List<int[]>> discountsList, boolean[] deliveryDays) {
        if(!suppliers.containsKey(companyNumber))
            throw new IllegalArgumentException("USER ERROR: supplier with id "+companyNumber+" does not exist");
        if(deliveryDays.length != 7)
            throw new IllegalArgumentException("SYSTEM ERROR: Delivery days must be 7 days array");
        Supplier supplier=suppliers.get(companyNumber);
        ArrayList<SupplierProduct> SupplierItems=new ArrayList<>();
        for(int[] itemInfo: itemInfoList){
            SupplierItems.add(new SupplierProduct(itemInfo[0],itemInfo[1],itemInfo[2]));
        }
        PeriodicContract contract=new PeriodicContract(supplier,SupplierItems,discountsList,deliveryDays);
        for(int i=0;i<7;i++)
            if(deliveryDays[i])
                periodicSuppliers.get(i).add(contract);
        return contract;
    }
    public Contract SignShortageContract(int companyNumber, List<int[]> itemInfoList, HashMap<Integer, List<int[]>> discountsList) {
        if(!suppliers.containsKey(companyNumber))
            throw new IllegalArgumentException("USER ERROR: supplier with id "+companyNumber+" does not exist");
        Supplier supplier=suppliers.get(companyNumber);
        ArrayList<SupplierProduct> SupplierItems=new ArrayList<>();
        for(int[] itemInfo: itemInfoList){
            SupplierItems.add(new SupplierProduct(itemInfo[0],itemInfo[1],itemInfo[2]));
        }
        Contract contract=new Contract(supplier,SupplierItems,discountsList);
        shortageContracts.put(companyNumber,contract);
        return contract;
    }
    //public Order OrderProducts(int companyNumber, List<int[]> productsAndAmounts, String contactPerson, LocalDate arrivalTime) { //product[0]=supplierId, [1]=amount
    //    if(!contracts.containsKey(companyNumber))
    //        throw new IllegalArgumentException("USER ERROR: Supplier has not signed contract with us");
    //    Contract contract=contracts.get(companyNumber);
    //    HashMap<Integer,List<int[]>> discounts=contract.getDiscounts();
    //    Order order=new Order(orderIdTracker,contract,contactPerson,arrivalTime);
    //    for(int[] productAndAmount: productsAndAmounts){
    //        if(!contract.getProductsIds().contains(productAndAmount[0]))
    //            throw new IllegalArgumentException("Trying to order item not in contract");
    //        int id=productAndAmount[0],amount=productAndAmount[1];
    //  //      Product product=items.get(id);
    //        SupplierProduct item=contract.getProduct(id);
    //        int discountPercent=1, maxDisAmount=0;
    //        List<int[]> discountsForItem=discounts.get(id);
    //        for(int[] discount: discountsForItem){
    //            if(amount>=discount[0] && discount[0]>maxDisAmount){
    //                maxDisAmount=discount[0];
    //                discountPercent=discount[1];
    //            }
    //        }
    //        order.AddProduct(item,amount,item.getPrice(),discountPercent);
    //    }
    //    orderHistory.put(orderIdTracker,order);
    //    orderIdTracker++;
    //    return order;
    //}

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
        else if(!shortageContracts.containsKey(companyNum))
            throw new IllegalArgumentException("USER ERROR: Supplier "+companyNum+" has no contract");
        else return shortageContracts.get(companyNum);
    }
    /*public void ChangeContractCP(int companyNum,String newCP){
        Contract contract=
    }*/
    public List<Contract> getContractList() {
        return new ArrayList<>(shortageContracts.values());
    }
    public Supplier getSupplier(int companyNum) {
        if(!suppliers.containsKey(companyNum))
            throw new IllegalArgumentException("USER ERROR: Supplier with company number "+companyNum+" not in system");
        else return suppliers.get(companyNum);
    }

    public List<Supplier> getSupplierList() {
        return new ArrayList<>(suppliers.values());
    }

    public List<Order> FetchOrders() {
        for(Integer id:toDeliverOrders.keySet()){
           orderHistory.put(id,toDeliverOrders.get(id));
        }
        ArrayList<Order> tmp= new ArrayList<>(toDeliverOrders.values());
        toDeliverOrders.clear();
        return tmp;
    }
    public void AddPeriodicProduct(int id,int amount,int weekDay){
        periodicProducts.get(weekDay).add(new PeriodicProduct(id,amount));
    }
    public void RemovePeriodicProduct(int id,int weekDay){
        for(PeriodicProduct periodicProduct:periodicProducts.get(weekDay)){
            if(periodicProduct.getId()==id){
                periodicProducts.get(weekDay).remove(periodicProduct);
                break;
            }
        }
    }
    public void ChangePeriodicProductAmount(int id,int newAmount,int weekDay){
        for(PeriodicProduct periodicProduct:periodicProducts.get(weekDay)){
            if(periodicProduct.getId()==id){
                periodicProduct.setAmount(newAmount);
                break;
            }
        }
    }
    public void ShortageOrder(int id,int amount){
        OrderProduct(id,amount,shortageContracts.values());
    }
    public void PeriodicOrder(int id,int amount, int weekDay){
        if(periodicSuppliers.get(weekDay).isEmpty())
            throw new RuntimeException("USER ERROR: No suppliers are delivering on a "+DayOfWeek.of(weekDay).toString());
        OrderProduct(id,amount,new ArrayList<>(periodicSuppliers.get(weekDay)));
    }
    private void OrderProduct(int id, int amount,Collection<Contract> supplierOptions) {
        int chosenSupp=-1,bestPrice=-1,discount=100;
        Contract chosenContract=null;
        for(Contract contract: supplierOptions){
            if(contract.ContainsProduct(id)){
                chosenSupp=contract.getSupplier().getCompanyNumber();
                chosenContract=contract;
                discount=contract.getDiscount(id,amount);
                int price=contract.getProduct(id).getPrice()*amount*discount;
                if(bestPrice==-1 || price<bestPrice)
                    bestPrice=price;
            }
        }
        Order order;
        if(chosenSupp == -1)
            throw new RuntimeException("USER ERROR: No supplier sells item "+id);
        SupplierProduct supplierProduct=chosenContract.getProduct(id);
        if(!toDeliverOrders.containsKey(chosenSupp)) {
            order = new Order(orderIdTracker, chosenContract.getSupplier().getCompanyNumber(),
                    chosenContract.getSupplier().getOrderingCP(), LocalDate.now().plusDays(1)); //TODO: PLACEHOLDER. CALC ARRIVAL DATE??
            toDeliverOrders.put(chosenSupp, order);
            order.AddProduct(supplierProduct, amount, supplierProduct.getPrice(), discount);
        }
        else{
            toDeliverOrders.get(chosenSupp).AddProduct(supplierProduct,amount,supplierProduct.getPrice(),discount);
        }
    }
   // public static LocalDate getArrivalDate(boolean[] days){ //return nearest date of a weekday that's also a delivery day
   //     int currWeekDay=LocalDate.now().getDayOfWeek().getValue(),daysTillDel=0;
   //     for(int i=currWeekDay+1;i<=7;i=i+1%7){
   //         daysTillDel++;
   //         if(days[i])
   //             return LocalDate.now().plusDays(daysTillDel);
   //     }
   //     throw new RuntimeException("Logic Error in Controller => getArrivalDate");
   // }


}
