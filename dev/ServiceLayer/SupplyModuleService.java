package ServiceLayer;

import BusinessLayer.*;
import BusinessLayer.Contracts.Contract;
import BusinessLayer.Responses.IsError;
import BusinessLayer.Responses.Response;
import BusinessLayer.Responses.IsValue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class SupplyModuleService {
    private final Controller controller;

    public SupplyModuleService(){
        controller=new Controller();
    }

    public Response<Supplier> AddSupplier(Integer companyNumber, String name,  String bankNumber, List<ContactPerson> contactPeople,String orderingCP){
        try{
            return new IsValue<Supplier>(controller.AddSupplier(name, companyNumber, bankNumber, contactPeople,orderingCP),"Supplier added");
        }
        catch (Exception e){
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
    public Response<Contract> SignShortageContract(int companyNumber, List<int[]> idPairsList, HashMap<Integer,List<int[]>> discountsList){
        try {
            return new IsValue<Contract>(controller.SignShortageContract(companyNumber,idPairsList,discountsList),"Contract added");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response<Contract> SignPeriodicContract(int companyNumber, List<int[]> idPairsList, HashMap<Integer,List<int[]>> discountsList, boolean[] deliveryDays){
        try {
            return new IsValue<Contract>(controller.SignPeriodicContract(companyNumber,idPairsList,discountsList,deliveryDays),"Contract added");
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
        //    controller.OrderProduct(id,amount);
            return new IsValue(null,"Ordering successful");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response AddPeriodicProduct(int id,int amount,int day){

        try {
            //    controller.OrderProduct(id,amount);
            return new IsValue(null,"Ordering successful");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response ChangePeriodicProduct(int id,int amount,int day){

        try {
            //    controller.OrderProduct(id,amount);
            return new IsValue(null,"Ordering successful");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }
    public Response DeletePeriodicProduct(int id,int amount,int day){

        try {
            //    controller.OrderProduct(id,amount);
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
