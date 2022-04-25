package ServiceLayer;

import BusinessLayer.*;
import BusinessLayer.Responses.IsError;
import BusinessLayer.Responses.Response;
import BusinessLayer.Responses.IsValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class SupplyModuleService {
    private Controller controller;

    public SupplyModuleService(){
        controller=new Controller();
    }

    public Response<Supplier> AddSupplier(Integer companyNumber, String name,  String bankNumber, List<ContactPerson> contactPeople){
        try{
            return new IsValue<Supplier>(controller.AddSupplier(name, companyNumber, bankNumber, contactPeople),"Supplier added");
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
    public Response<Contract> SignContract(int companyNumber, List<int[]> idPairsList, HashMap<Integer,List<int[]>> discountsList, boolean[] deliveryDays){
        try {
            return new IsValue<Contract>(controller.SignContract(companyNumber,idPairsList,discountsList,deliveryDays),"Contract added");
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
    public Response<Order> OrderProducts(int companyNumber, String contactPersonName, LocalDate arrivalDate, List<int[]> products){

        try {
            return new IsValue<Order>(controller.OrderProducts(companyNumber,products,contactPersonName,arrivalDate),"Ordering successful");
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
}
