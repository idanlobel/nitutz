package ServiceLayer;

import BusinessLayer.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class SupplyModuleService {
    private Controller controller;

    public SupplyModuleService(){
        controller=new Controller();
    }

    public Supplier AddSupplier(String name, Integer companyNumber, String bankNumber, List<ContactPerson> contactPeople){
        try{
            return controller.AddSupplier(name, companyNumber, bankNumber, contactPeople);
        }
        catch (Exception e){
            return null;
        }
    }
    public ContactPerson AddContactPerson(Integer companyNumber,String name,String Email,String cellNumber){
        return controller.AddContact(companyNumber,name,Email,cellNumber);
    }
    public Contract SignContract(int companyNumber, List<Integer[]> idPairsList, HashMap<Integer,List<int[]>> discountsList, boolean[] deliveryDays){
        return controller.SignContract(companyNumber,idPairsList,discountsList,deliveryDays);
    }
    public Order OrderProducts(int companyNumber, List<int[]> products,ContactPerson contactPerson, LocalDateTime arrivalDate){
        return controller.OrderProducts(companyNumber,products,contactPerson,arrivalDate);
    }
}
