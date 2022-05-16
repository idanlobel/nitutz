package SuppliersServiceLayer;

import SuppliersBusinessLayer.*;
import SuppliersBusinessLayer.Contracts.Contract;
import SuppliersBusinessLayer.Responses.IsError;
import SuppliersBusinessLayer.Responses.Response;
import SuppliersBusinessLayer.Responses.IsValue;

import java.util.HashMap;
import java.util.List;

public class SupplyModuleService {
    private final Controller controller;

    public SupplyModuleService(){
        controller=new Controller();
    }

    public Response<Supplier> AddSupplier(Integer companyNumber, String name,  String bankNumber,String address, List<ContactPerson> contactPeople,String orderingCP){
        try{
            return new IsValue<Supplier>(controller.AddSupplier(name, companyNumber, bankNumber,address, contactPeople,orderingCP),"Supplier added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response ChangeSupplierAddress(Integer companyNumber,String newAddress){
        try {
            controller.changeAddress(companyNumber,newAddress);
            return new IsValue(null, "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<Supplier> ChangeSupplierBankNumber(Integer companyNumber,String newBankNumber){
        try {
            controller.changeBankNum(companyNumber,newBankNumber);
            return new IsValue(null, "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<Supplier> getSupplier(int companyNum) {

        try {
            return new IsValue<Supplier>(controller.getSupplier(companyNum), "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<List<Supplier>> getSupplierList() {

        try {
            return new IsValue<List<Supplier>>(controller.getSupplierList(), "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<ContactPerson> AddContactPerson(Integer companyNumber,String name,String Email,String cellNumber){
        try {
            return new IsValue<ContactPerson>(controller.AddContact(companyNumber, name, Email, cellNumber),"Contact added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response RemoveContractPerson(int companyNumber,String name){
        try {
            controller.RemoveContactPerson(companyNumber,name);
            return new IsValue<>(null,"Contact added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public void ChangeContractPersonEmail(int companyNumber,String name,String newEmail){
        try{
            controller.ChangeContactPersonMail(companyNumber,name,newEmail);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void ChangeContractPersonNum(int companyNumber,String name,String newNum){
        try{
                controller.ChangeContactPersonPhone(companyNumber,name,newNum);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Response<Contract> SignShortageContract(int companyNumber, List<int[]> idPairsList, HashMap<Integer,List<int[]>> discountsList,List<int[]> generalDiscountsList){
        try {
            return new IsValue<Contract>(controller.SignShortageContract(companyNumber,idPairsList,discountsList,generalDiscountsList),"Contract added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response<Contract> SignPeriodicContract(int companyNumber, List<int[]> idPairsList, HashMap<Integer,List<int[]>> discountsList,List<int[]> generalItemAmountsDiscounts, boolean[] deliveryDays){
        try {
            return new IsValue<Contract>(controller.SignPeriodicContract(companyNumber,idPairsList,discountsList,generalItemAmountsDiscounts,deliveryDays),"Contract added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response ChangeDeliveryDays(int companyNumber,boolean[] days){
        try {
            return null;
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }

    public Response<Contract> getContract(int companyNum) {

        try {
            return new IsValue<Contract>(controller.getContract(companyNum), "contract fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<List<Contract>> getContractList() {

        try {
            return new IsValue<List<Contract>>(controller.getContractList(), "contract fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response OrderProduct(int id,int amount){

        try {
            controller.ShortageOrder(id,amount);
            return new IsValue(null,"Ordering successful");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response AddPeriodicProduct(int id,int amount,int day){

        try {
            controller.AddPeriodicProduct(id,amount,day);
            return new IsValue(null,"Ordering successful");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response ChangePeriodicProduct(int id,int amount,int day){

        try {
            controller.ChangePeriodicProductAmount(id,amount,day);
            return new IsValue(null,"Ordering successful");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response DeletePeriodicProduct(int id,int day){

        try {
            controller.RemovePeriodicProduct(id,day);
            return new IsValue(null,"Ordering successful");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response<Order> getOrder(int orderId) {

        try {
            return new IsValue<Order>(controller.getOrder(orderId), "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<List<Order>> FetchOrders(){
        try {
            return new IsValue<List<Order>>(controller.FetchOrders(), "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
    public Response<List<Order>> getSupplierOrders(int companyNumber) {

        try {
            return new IsValue<List<Order>>(controller.getOrderList(companyNumber), "order fetch successful");
        } catch (Exception e) {
            return new IsError(e.getMessage());
        }
    }
}
