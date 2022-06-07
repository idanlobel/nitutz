package SuppliersModule.SuppliersBusinessLayer.Controllers;



import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.ContractDAO;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Contracts.PeriodicContract;
import SuppliersModule.SuppliersBusinessLayer.Products.PeriodicProduct;
import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;

import java.time.DayOfWeek;
import java.util.*;

public class ContractController {
    private final ContractDAO contractDAO = new ContractDAO();
    private final Hashtable<Integer, Contract> shortageContracts;
    private final HashMap<Integer, Collection<PeriodicProduct>> periodicProducts; //0-sunday, 6-saturday
    private final HashMap<Integer, Collection<PeriodicContract>> periodicSuppliers; //0-sunday, 6-saturday

    public ContractController()  {
        shortageContracts = new Hashtable<>();
        periodicProducts = new HashMap<>();
        periodicSuppliers = new HashMap<>();
        try {

            List<Contract> contractList = contractDAO.getAllContracts();
            for (Contract contract : contractList) {
                if (contract.getType() == 0)
                    shortageContracts.put(contract.getCompanyNumber(), contract);
            }
            for (int i = 0; i < 7; i++) {
                periodicProducts.put(i, new ArrayList<>());
                periodicSuppliers.put(i, new ArrayList<>());
            }
        }
        catch (Exception e){
            System.out.println("System contract data load failed. sql error message: "+e.getMessage());
        }
    }
    public Contract SignPeriodicContract(int companyNumber,String name, List<int[]> itemInfoList, HashMap<Integer,List<int[]>> discountsList,List<int[]> generalDiscountsList, boolean[] deliveryDays,boolean selfDel) throws Exception {
        if(deliveryDays.length != 7)
            throw new IllegalArgumentException("SYSTEM ERROR: Delivery days must be 7 days array");
        ArrayList<SupplierProduct> SupplierItems=new ArrayList<>();
        for(int[] itemInfo: itemInfoList){
            SupplierItems.add(new SupplierProduct(itemInfo[0],itemInfo[1],itemInfo[2]));
        }
        PeriodicContract contract=new PeriodicContract(companyNumber,name,SupplierItems,discountsList,generalDiscountsList,deliveryDays,selfDel);
        for(int i=0;i<7;i++)
            if(deliveryDays[i])
                periodicSuppliers.get(i).add(contract);
        contractDAO.create(contract);
        return contract;
    }
    public void changeDeliveryDays(int companyNumber,boolean[] days) {
        Contract contract = getContract(companyNumber);
        if(contract.isPeriodic())
            ((PeriodicContract) contract).setDeliveryDays(days);
        contractDAO.update(getContract(companyNumber));
    }
    public Contract SignShortageContract(int companyNumber,String name, List<int[]> itemInfoList, HashMap<Integer, List<int[]>> discountsList,List<int[]> generalDiscountsList,boolean selfDel) throws Exception {
        ArrayList<Integer> catalogs=new ArrayList<>();
        for(int[] info:itemInfoList)
            catalogs.add(info[2]);
        ArrayList<SupplierProduct> SupplierItems = new ArrayList<>();
        for(int[] itemInfo: itemInfoList){
            SupplierItems.add(new SupplierProduct(itemInfo[0],itemInfo[1],itemInfo[2]));
        }
        Contract contract = new Contract(companyNumber,name,SupplierItems,discountsList,generalDiscountsList,selfDel);
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
    public Contract getContract(int companyNum)  {
        Contract contract = null;
        try {
            if (shortageContracts.containsKey(companyNum))
                return shortageContracts.get(companyNum);
         //   else if (contractDAO.exists(companyNum)) //uncomment if implementing lazy loading
         //       return contractDAO.get(companyNum);
            for (int i = 0; i < 6; i++) {
                for (PeriodicContract periodicContract : periodicSuppliers.get(i)) {
                    if (periodicContract.getCompanyNumber() == companyNum)
                        return periodicContract;
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        throw new IllegalArgumentException("USER ERROR: Supplier "+companyNum+" has no contract");
    }
    public void ChangeContractCP(int companyNum,String newCP) {
       getContract(companyNum).setOrderingCP(newCP);
    }
   // public List<Order> FetchOrders() {
   //     for(Integer id:toDeliverOrders.keySet()){
   //         orderHistory.put(id,toDeliverOrders.get(id));
   //     }
   //     ArrayList<Order> tmp= new ArrayList<>(toDeliverOrders.values());
   //     toDeliverOrders.clear();
   //     return tmp;
   // }
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
    public Contract ShortageOrder(int id,int amount){
          return chooseContract(id,amount,shortageContracts.values());
    }
    public Contract PeriodicOrder(int id,int amount, int weekDay){
        if(periodicSuppliers.get(weekDay).isEmpty())
            throw new RuntimeException("USER ERROR: No suppliers are delivering on a "+DayOfWeek.of(weekDay).toString());
        return chooseContract(id,amount,new ArrayList<>(periodicSuppliers.get(weekDay)));
    }
    private Contract chooseContract(int id, int amount,Collection<Contract> supplierOptions) {
        int chosenSupp=-1,bestPrice=-1,discount=100;
        Contract chosenContract=null;
        for(Contract contract: supplierOptions){
            if(contract.ContainsProduct(id)){
                chosenSupp=contract.getCompanyNumber();
                chosenContract=contract;
                discount=contract.getDiscount(id,amount);
                int price=contract.getProduct(id).getPrice()*amount*discount;
                if(bestPrice==-1 || price<bestPrice)
                    bestPrice=price;
            }
        }
        if(chosenSupp == -1)
            throw new RuntimeException("USER ERROR: No supplier sells item "+id);
        return chosenContract;

    }

    public void populateDataBase() {
        try{
            List<Contract> contractList = contractDAO.getAllContracts();
            for (Contract contract : contractList) {
                if (contract.getType() == 0)
                    shortageContracts.put(contract.getCompanyNumber(), contract);
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
