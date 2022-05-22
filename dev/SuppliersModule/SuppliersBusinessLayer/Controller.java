package SuppliersModule.SuppliersBusinessLayer;



import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.ContractDAO;
import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.OrderDAO;
import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.SupplierDAO;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Contracts.PeriodicContract;
import SuppliersModule.SuppliersBusinessLayer.Products.PeriodicProduct;
import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Controller {
    private SupplierDAO supplierDAO = new SupplierDAO();
    private ContractDAO contractDAO = new ContractDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private final Hashtable<Integer, Supplier> suppliers;
    private final Hashtable<Integer, Contract> shortageContracts;
    private final HashMap<Integer, Order> toDeliverOrders;
    private final HashMap<Integer, Collection<PeriodicProduct>> periodicProducts; //0-sunday, 6-saturday
    private final HashMap<Integer, Collection<PeriodicContract>> periodicSuppliers; //0-sunday, 6-saturday
    private int orderIdTracker = 0;
    private final Hashtable<Integer, Order> orderHistory;
    public Controller()  {
        try {
            toDeliverOrders = new HashMap<>();
            suppliers = new Hashtable<>();
            List<Supplier> supplierList = supplierDAO.getAllSuppliers();
            for (Supplier supplier : supplierList) {
                suppliers.put(supplier.getCompanyNumber(), supplier);
            }
            shortageContracts = new Hashtable<>();
            List<Contract> contractList = contractDAO.getAllContracts();
            for (Contract contract : contractList) {
                if (contract.getType() == 0)
                    shortageContracts.put(contract.getSupplier().getCompanyNumber(), contract);
            }
            orderHistory = new Hashtable<>();
            List<Order> orderList = orderDAO.getAllOrders();
            for (Order order : orderList) {
                orderHistory.put(order.getId(), order);
            }
            periodicProducts = new HashMap<>();
            periodicSuppliers = new HashMap<>();
            for (int i = 0; i < 7; i++) {
                periodicProducts.put(i, new ArrayList<>());
                periodicSuppliers.put(i, new ArrayList<>());
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public Supplier AddSupplier(String name, Integer companyNumber, String bankNumber, String address, List<ContactPerson> contactPeople, String orderingCP) throws Exception {
        if(suppliers.containsKey(companyNumber))
            throw new IllegalArgumentException("Company number already exists in the system");
        Supplier supplier = new Supplier(name, companyNumber, bankNumber, address, contactPeople, orderingCP);
        suppliers.put(companyNumber, supplier);
        supplierDAO.create(supplier);
        return supplier;
    }
    public void changeAddress(int companyNumber, String address) throws Exception {
        suppliers.get(companyNumber).setAddress(address);
        supplierDAO.update(suppliers.get(companyNumber));
    }
    public void changeBankNum(int companyNumber, String bankNum) throws Exception {
        suppliers.get(companyNumber).setBankNumber(bankNum);
        supplierDAO.update(suppliers.get(companyNumber));
    }
    public ContactPerson AddContact(Integer companyNumber, String name, String Email, String cellNumber) throws Exception {
        if(companyNumber == null || name == null || Email == null)
            throw new IllegalArgumentException("Parameter can not ne empty");
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("No Supplier with this company number");
        ContactPerson contactPerson = new ContactPerson(name ,Email ,cellNumber);
        suppliers.get(companyNumber).addContact(contactPerson);
        supplierDAO.update(suppliers.get(companyNumber));
        return contactPerson;
    }
    public ContactPerson RemoveContactPerson(int companyNumber, String name) throws Exception {
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: no supplier with company number" + companyNumber);
        ContactPerson contactPerson = suppliers.get(companyNumber).RemoveContact(name);
        supplierDAO.update(suppliers.get(companyNumber));
        return contactPerson;
    }
    public void ChangeContactPersonMail(int companyNumber, String name, String newMail) throws Exception {
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: no supplier with company number" + companyNumber);
        suppliers.get(companyNumber).ChangeContactEmail(name, newMail);
        supplierDAO.update(suppliers.get(companyNumber));
    }
    public void ChangeContactPersonPhone(int companyNumber, String name, String newNum) throws Exception {
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: no supplier with company number" + companyNumber);
        suppliers.get(companyNumber).ChangeContactPhoneNum(name, newNum);
        supplierDAO.update(suppliers.get(companyNumber));
    }

    public Contract SignPeriodicContract(int companyNumber, List<int[]> itemInfoList, HashMap<Integer,List<int[]>> discountsList,List<int[]> generalDiscountsList, boolean[] deliveryDays) throws Exception {
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: supplier with id "+companyNumber+" does not exist");
        if(deliveryDays.length != 7)
            throw new IllegalArgumentException("SYSTEM ERROR: Delivery days must be 7 days array");
        Supplier supplier = suppliers.get(companyNumber);
        ArrayList<SupplierProduct> SupplierItems=new ArrayList<>();
        for(int[] itemInfo: itemInfoList){
            SupplierItems.add(new SupplierProduct(itemInfo[0],itemInfo[1],itemInfo[2]));
        }
        PeriodicContract contract=new PeriodicContract(supplier,SupplierItems,discountsList,generalDiscountsList,deliveryDays);
        for(int i=0;i<7;i++)
            if(deliveryDays[i])
                periodicSuppliers.get(i).add(contract);
        contractDAO.create(contract);
        return contract;
    }
    public void changeDeliveryDays(int companyNumber,boolean[] days) throws Exception {
        Contract contract = getContract(companyNumber);
        if(contract.isPeriodic())
            ((PeriodicContract) contract).setDeliveryDays(days);
        contractDAO.update(getContract(companyNumber));
    }
    public Contract SignShortageContract(int companyNumber, List<int[]> itemInfoList, HashMap<Integer, List<int[]>> discountsList,List<int[]> generalDiscountsList) throws Exception {
        ArrayList<Integer> catalogs=new ArrayList<>();
        for(int[] info:itemInfoList)
            catalogs.add(info[2]);

        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: supplier with id "+companyNumber+" does not exist");
        Supplier supplier = suppliers.get(companyNumber);
        ArrayList<SupplierProduct> SupplierItems = new ArrayList<>();
        for(int[] itemInfo: itemInfoList){
            SupplierItems.add(new SupplierProduct(itemInfo[0],itemInfo[1],itemInfo[2]));
        }
        Contract contract = new Contract(supplier,SupplierItems,discountsList,generalDiscountsList);
        shortageContracts.put(companyNumber,contract);
        contractDAO.create(contract);
        return contract;
    }
    public void addProduct(int companyNumber,int catalogNumber,int supplierId,int price,List<int[]> discounts) throws Exception {
        getContract(companyNumber).addProduct(catalogNumber,supplierId,price,discounts);
        contractDAO.update(getContract(companyNumber));
    }
    public void changeProductPrice(int companyNumber,int catalogNumber,int price) throws Exception {
        getContract(companyNumber).ChangeProductPrice(catalogNumber,price);
        contractDAO.update(getContract(companyNumber));
    }
    public void removeProduct(int companyNumber,int catalogNumber) throws Exception {
        getContract(companyNumber).RemoveProduct(catalogNumber);
        contractDAO.update(getContract(companyNumber));
    }
    public void putDiscount(int companyNumber,int catalogNumber,int amount, int discount) throws Exception {
        getContract(companyNumber).putDiscount(catalogNumber, amount, discount);
        contractDAO.update(getContract(companyNumber));
    }
    public void removeDiscount(int companyNumber,int catalogNumber,int amount) throws Exception {
        getContract(companyNumber).removeDiscount(catalogNumber, amount);
        contractDAO.update(getContract(companyNumber));
    }
    public void putGeneralDiscount(int companyNumber,int amount, int discount) throws Exception {
        getContract(companyNumber).putGeneralDiscount(amount, discount);
        contractDAO.update(getContract(companyNumber));
    }
    public void removeGeneralDiscount(int companyNumber,int amount) throws Exception {
        getContract(companyNumber).removeGeneralDiscount(amount);
        contractDAO.update(getContract(companyNumber));
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

    public Order getOrder(int orderId) throws Exception {
        if(!orderHistory.containsKey(orderId) && !orderDAO.exists(orderId)) {
            throw new IllegalArgumentException("USER ERROR: Order with id "+orderId+" not in system");
        }
        else return orderHistory.get(orderId);
    }
    public List<Order> getOrderList(int companyNum) throws Exception {
        ArrayList<Order> acc= new ArrayList<>();
        /*for(Order order:orderDAO.getAllOrders())
            if(order.getSupplyCompanyNumber()==companyNum)
                acc.add(order);*/
        for(Order order:orderHistory.values())
            if(order.getSupplyCompanyNumber()==companyNum)
                acc.add(order);
        return acc;
    }

    public Contract getContract(int companyNum) throws Exception {
        if((!suppliers.containsKey(companyNum))&&(!supplierDAO.exists(companyNum)))
            throw new IllegalArgumentException("USER ERROR: Supplier with company number "+companyNum+" not in system");
        Contract contract = null;
        if(shortageContracts.containsKey(companyNum))
            return shortageContracts.get(companyNum);
        else if(contractDAO.exists(companyNum))
            return contractDAO.get(companyNum);
        for(int i=0;i<6;i++){
            for(PeriodicContract periodicContract: periodicSuppliers.get(i)){
                if(periodicContract.getSupplier().getCompanyNumber()==companyNum)
                    return periodicContract;
            }
        }
        throw new IllegalArgumentException("USER ERROR: Supplier "+companyNum+" has no contract");
    }
    public void ChangeContractCP(int companyNum,String newCP) throws Exception {
        if(!suppliers.get(companyNum).ContainsContact(newCP))
            throw new RuntimeException("USER ERROR: Supplier"+suppliers.get(companyNum).getName()+" does not" +
                    "contain contact named " + newCP);
        suppliers.get(companyNum).setOrderingCP(newCP);
        supplierDAO.update(suppliers.get(companyNum));
    }
    public Supplier getSupplier(int companyNum) throws Exception {
        if(!suppliers.containsKey(companyNum)){
            return supplierDAO.get(companyNum);
            //throw new IllegalArgumentException("USER ERROR: Supplier with company number "+companyNum+" not in system");
        }

        else return suppliers.get(companyNum);
    }

    public List<Supplier> getSupplierList() throws Exception {
        return supplierDAO.getAllSuppliers();
        //return new ArrayList<>(suppliers.values());
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
                    chosenContract.getSupplier().getOrderingCP().getName(), LocalDate.now().plusDays(1)); //TODO: PLACEHOLDER. CALC ARRIVAL DATE??
            toDeliverOrders.put(chosenSupp, order);
            order.AddProduct(supplierProduct, amount, supplierProduct.getPrice(), discount,chosenContract.getGeneralDiscounts());
        }
        else{
            toDeliverOrders.get(chosenSupp).AddProduct(supplierProduct,amount,supplierProduct.getPrice(),discount,chosenContract.getGeneralDiscounts());
        }
    }
    public void clearDataBase() throws Exception {
        supplierDAO.deleteAllData();
    }
    public void populateDataBase() throws Exception {
        supplierDAO.populateDB();
        try {
            List<Supplier> supplierList = supplierDAO.getAllSuppliers();
            for (Supplier supplier : supplierList) {
                suppliers.put(supplier.getCompanyNumber(), supplier);
            }
            List<Contract> contractList = contractDAO.getAllContracts();
            for (Contract contract : contractList) {
                if (contract.getType() == 0)
                    shortageContracts.put(contract.getSupplier().getCompanyNumber(), contract);
            }
            List<Order> orderList = orderDAO.getAllOrders();
            for (Order order : orderList) {
                orderHistory.put(order.getId(), order);
            }
            for (int i = 0; i < 7; i++) {
                periodicProducts.put(i, new ArrayList<>());
                periodicSuppliers.put(i, new ArrayList<>());
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
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
