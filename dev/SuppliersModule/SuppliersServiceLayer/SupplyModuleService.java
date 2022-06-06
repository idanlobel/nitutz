package SuppliersModule.SuppliersServiceLayer;

import SuppliersModule.SuppliersBusinessLayer.Controllers.ContractController;
import SuppliersModule.SuppliersBusinessLayer.Controllers.OrderController;
import SuppliersModule.SuppliersBusinessLayer.Controllers.SuppliersController;
import SuppliersModule.SuppliersBusinessLayer.*;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Responses.IsError;
import SuppliersModule.SuppliersBusinessLayer.Responses.Response;
import SuppliersModule.SuppliersBusinessLayer.Responses.IsValue;

import java.util.HashMap;
import java.util.List;

public class SupplyModuleService {
    private final ContractController contractController;
    private final SuppliersController suppliersController;
    private final OrderController orderController;

    public SupplyModuleService()  {
        contractController = new ContractController();
        suppliersController=new SuppliersController();
        orderController=new OrderController();
    }

    public Response<Supplier> AddSupplier(Integer companyNumber, String name,  String bankNumber,String address, List<ContactPerson> contactPeople){
        try{
            return new IsValue<Supplier>(suppliersController.AddSupplier(name, companyNumber, bankNumber,address, contactPeople),"Supplier added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response ChangeSupplierAddress(Integer companyNumber,String newAddress){
        try {
            suppliersController.changeAddress(companyNumber,newAddress);
            return new IsValue(null, "Address Changed");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<Supplier> ChangeSupplierBankNumber(Integer companyNumber,String newBankNumber){
        try {
            suppliersController.changeBankNum(companyNumber,newBankNumber);
            return new IsValue(null, "Bank Number Changed");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<Supplier> getSupplier(int companyNum) {

        try {
            return new IsValue<Supplier>(suppliersController.getSupplier(companyNum), "supplier fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<List<Supplier>> getSupplierList() {

        try {
            return new IsValue<List<Supplier>>(suppliersController.getSupplierList(), "supplier list fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<ContactPerson> AddContactPerson(Integer companyNumber,String name,String Email,String cellNumber){
        try {
            return new IsValue<ContactPerson>(suppliersController.AddContact(companyNumber, name, Email, cellNumber),"Contact added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response removeContractPerson(int companyNumber,String name){
        try {
            suppliersController.RemoveContactPerson(companyNumber,name);
            return new IsValue<>(null,"Contact removed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response changeContractPersonEmail(int companyNumber,String name,String newEmail){
        try{
            suppliersController.ChangeContactPersonMail(companyNumber,name,newEmail);
            return new IsValue<>(null,"Contact Email Changed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response changeContractPersonNum(int companyNumber,String name,String newNum){
        try{
            suppliersController.ChangeContactPersonPhone(companyNumber,name,newNum);
            return new IsValue<>(null,"Contact phone number changed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response changeSupplierOrderingCP(int companyNumber,String name){
        try{
            suppliersController.hasContractPerson(companyNumber,name);
            contractController.ChangeContractCP(companyNumber,name);
            return new IsValue<>(null,"Contract ordering contact changed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response<Contract> SignShortageContract(int companyNumber,String orderCP, List<int[]> idPairsList, HashMap<Integer,List<int[]>> discountsList,List<int[]> generalDiscountsList){
        try {
            return new IsValue<Contract>(contractController.SignShortageContract(companyNumber,orderCP,idPairsList,discountsList,generalDiscountsList),"Contract added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response<Contract> SignPeriodicContract(int companyNumber,String name, List<int[]> idPairsList, HashMap<Integer,List<int[]>> discountsList,List<int[]> generalItemAmountsDiscounts, boolean[] deliveryDays){
        try {
            return new IsValue<Contract>(contractController.SignPeriodicContract(companyNumber,name,idPairsList,discountsList,generalItemAmountsDiscounts,deliveryDays),"Contract added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response<Contract> getContract(int companyNum) {

        try {
            return new IsValue<Contract>(contractController.getContract(companyNum), "contract fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response addProductToContract(int companyNumber,int catalogNumber,int supplierId,int price, List<int[]> discounts){
        try {
            contractController.addProduct(companyNumber,catalogNumber,supplierId,price,discounts);
            return new IsValue(null,"product added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response removeProductToContract(int companyNumber,int catalogNumber){
        try {
            contractController.removeProduct(companyNumber,catalogNumber);
            return new IsValue(null,"product removed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response changeProductPrice(int companyNumber,int catalogNumber,int price){
        try {
            contractController.changeProductPrice(companyNumber,catalogNumber,price);
            return new IsValue(null,"product price changed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response putDiscount(int companyNumber,int catalogNumber,int amount, int discount){
        try {
            contractController.putDiscount(companyNumber,catalogNumber,amount,discount);
            return new IsValue(null,"discount added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response putGeneralDiscount(int companyNumber,int amount, int discount){
        try {
            contractController.putGeneralDiscount(companyNumber,amount,discount);
            return new IsValue(null,"discount added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response removeDiscount(int companyNumber,int catalogNumber,int amount){
        try {
            contractController.removeDiscount(companyNumber,catalogNumber,amount);
            return new IsValue(null,"discount removed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response removeGeneralDiscount(int companyNumber,int amount){
        try {
            contractController.removeGeneralDiscount(companyNumber,amount);
            return new IsValue(null,"discount removed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response putDeliveryDays(int companyNumber,boolean[] days){
        try {
            contractController.changeDeliveryDays(companyNumber,days);
            return new IsValue(null,"delivery days changed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response OrderProduct(int id,int amount){

        try {
            orderController.orderProduct(contractController.ShortageOrder(id,amount),id,amount);
            return new IsValue(null,"Ordering successful");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response HandlePeriodicOrder(){

        try {
            orderController.handlePeriodicOrder(contractController);
            return new IsValue(null,"periodic products ordered");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response AddPeriodicProduct(int id,int amount,int day){

        try {
            orderController.AddPeriodicProduct(id,amount,day);
            return new IsValue(null,"periodic product added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response ChangePeriodicProduct(int id,int amount,int day){

        try {
            orderController.ChangePeriodicProductAmount(id,amount,day);
            return new IsValue(null,"periodic product changed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response DeletePeriodicProduct(int id,int day){

        try {
            orderController.RemovePeriodicProduct(id,day);
            return new IsValue(null,"periodic product removed");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response<Order> getOrder(int orderId) {

        try {
            return new IsValue<Order>(orderController.getOrder(orderId), "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    //public Response<List<Order>> FetchOrders(){
    //    try {
    //        return new IsValue<List<Order>>(contractController.FetchOrders(), "orders fetch successful");
    //    } catch (Exception e) {
    //        return new IsError(e.getMessage());
    //    }
    //}
    public Response<List<Order>> getSupplierOrders(int companyNumber) {

        try {
            return new IsValue<List<Order>>(orderController.getOrderList(companyNumber), "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response demoMode(){

        try {
            suppliersController.clearDataBase();
      //      controller.clearDataBase();
            orderController.populateOrderDatabase();
            contractController.populateDataBase();
            return new IsValue(null,"You are now running the demo data");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
}
