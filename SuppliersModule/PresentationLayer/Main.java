package PresentationLayer;

import BusinessLayer.ContactPerson;
import BusinessLayer.Contracts.Contract;
import BusinessLayer.Order;
import BusinessLayer.Responses.Response;
import BusinessLayer.Supplier;
import ServiceLayer.SupplyModuleService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main { //note: this code is assumed to be made as a placeholder till we have a gui, a such is written quick and dirty

    public static void main(String[] args) {
        SupplyModuleService serviceObj=new SupplyModuleService();
        Scanner scanner=new Scanner(System.in);
        int choice=-1;
        while(choice!=11) {
            String line = "choose action (type the numeration to execute):\n" +
                    "1: Add a supplier\n" +
                    "2: View a supplier\n" +
                    "3: View all suppliers\n" +
                    "4: Add contact person to a supplier\n" +
                    "5: Sign contract with a supplier\n" +
                    "6: View a contract\n" +
                    "7: View all contracts\n" +
                    "8: Order from a supplier\n" +
                    "9: View an order\n" +
                    "10: View all orders from a supplier\n" +
                    "11: Quit program\n";
            choice = requestNumberInput(line, scanner, 11,1);
            if(choice==1) addSupplierChain(serviceObj,scanner);
            if(choice==2) getSupplierChain(serviceObj,scanner);
            if(choice==3) getSupplierListChain(serviceObj,scanner);
            if(choice==4) addContactChain(serviceObj,scanner);
            if(choice==5) signContractChain(serviceObj,scanner);
            if(choice==6) getContractChain(serviceObj,scanner);
            if(choice==7) getContractListChain(serviceObj,scanner);
            if(choice==8) commitOrderChain(serviceObj,scanner);
            if(choice==9) getOrderChain(serviceObj,scanner);
            if(choice==10) getSupplierOrderListChain(serviceObj,scanner);
        }
        System.out.println("Program end. goodbye :(");

    }
    public static int requestNumberInput(String string,Scanner scanner,int max,int min){
        boolean validChoice=false;
        int choice=-1;
        while(!validChoice) {
            System.out.print(string);
            try {
                choice = scanner.nextInt();
                if (max!=-1 && choice > max | choice < min)
                    throw new IllegalArgumentException("ignore");
                validChoice = true;
            } catch (IllegalArgumentException e) {
                System.out.println(choice + " is not a valid option!");
            } catch (Exception e) {
                System.out.println("Invalid Input!");
                scanner.nextLine();
            }
        }
        return choice;
    }
    public static void addSupplierChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Company number: ",scanner,-1,0);
        System.out.print("Company name: ");
        String companyName=scanner.next();
        System.out.print("Bank number: ");
        String bankNum=scanner.next();
        ArrayList<ContactPerson> contactPeople=new ArrayList<>();
        int amount=requestNumberInput("Please state the amount of contact people (at least one): ",scanner,-1,1);
        for(int i=1;i<=amount;i++){
            System.out.println("Person "+i);
            System.out.print("Name: ");
            String cName=scanner.next();
            System.out.print("cell number: ");
            String cellNum=scanner.next();
            System.out.print("Email: ");
            String Email=scanner.next();
            contactPeople.add(new ContactPerson(cName,Email,cellNum));
        }
    //    handleResponse(service.AddSupplier(companyNumber,companyName,bankNum,contactPeople));
    }
    public static void getSupplierChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        Response<Supplier> response=service.getSupplier(companyNumber);
        handleResponse(response);
        if(!response.isError())
            System.out.println(response.getValue().toString());
    }
    public static void getSupplierListChain(SupplyModuleService service,Scanner scanner){
        Response<List<Supplier>> response=service.getSupplierList();
        handleResponse(response);
        if(!response.isError())
            System.out.println(response.getValue().toString());
    }
    public static void addContactChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Contact name: ");
        String cName=scanner.next();
        System.out.print("Contact cell number: ");
        String cellNum=scanner.next();
        System.out.print("Contact Email: ");
        String Email=scanner.next();
        handleResponse(service.AddContactPerson(companyNumber,cName,Email,cellNum));
    }
    public static void signContractChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number with which the contract is signed with: ",scanner,-1,1);
        int amount=requestNumberInput("Enter item amount: ",scanner,-1,1);
        ArrayList<int[]> idPairs=new ArrayList<>();
        HashMap<Integer,List<int[]>> discounts=new HashMap<>();
        for(int i=1;i<=amount;i++){
            int id=requestNumberInput("Item "+i+" id: ",scanner,-1,1);
            int price=requestNumberInput("Item "+i+" price: ",scanner,-1,1);
            idPairs.add(new int[]{id, price,-1}); //second id is -1 cause we have to internal stock sorting yet
            int disAmount=requestNumberInput("Enter amount of discounts: ",scanner,-1,0);
            ArrayList<int[]> itemDisList=new ArrayList<>();
            for(int j=1;j<=disAmount;j++) {
                System.out.println("Discount "+j+" for item "+id);
                int minDisAmount=requestNumberInput("Enter minimal amount for discount: ",scanner,-1,1);
                int disMult=requestNumberInput("Enter percent(eg 20,30,40 - no %): ",scanner,99,1);
                itemDisList.add(new int[]{minDisAmount,disMult});
            }
            discounts.put(id,itemDisList);
            }
        System.out.println("Enter delivery days(eg. 1,2,3 for Sun,Mon,Tue): ");
        String days=scanner.next();
        boolean[] par=new boolean[7];
        String[] split=days.split(",");
        for(String day:split)
            par[Integer.parseInt(day)-1]=true;
        //handleResponse(service.SignContract(companyNumber,idPairs,discounts,par));
    }
    public static void getContractChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number with which the contract is signed with: ",scanner,-1,1);
        Response<Contract> response=service.getContract(companyNumber);
        handleResponse(response);
        if(!response.isError())
            System.out.println(response.getValue().toString());

    }
    public static void getContractListChain(SupplyModuleService service,Scanner scanner){
        Response<List<Contract>> response=service.getContractList();
        handleResponse(response);
        if(!response.isError())
            System.out.println(response.getValue().toString());
    }
    public static void commitOrderChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Please state the contact person's name: ");
        String cName=scanner.next();
        System.out.print("Enter Date(DD-MM-YY): ");
        boolean done=false;
        LocalDate date=null;
        while(!done) {
            try {
                date = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd-MM-yy"));
                done=true;
            }
            catch (Exception e){
                System.out.println("Invalid date!");
            }
        }
        ArrayList<int[]> products=new ArrayList<>();
        int typeNum=requestNumberInput("Enter how many product types to order: ",scanner,-1,1);
        for(int i=1;i<=typeNum;i++){
            int id=requestNumberInput("Product "+i+" id:",scanner,-1,1);
            int amount=requestNumberInput("Enter amount: ",scanner,-1,1);
            products.add(new int[]{id,amount});
        }
     //   handleResponse(service.OrderProducts(companyNumber,cName,date,products));
    }
    public static void getOrderChain(SupplyModuleService service,Scanner scanner){
        int orderId=requestNumberInput("Please state an order id: ",scanner,-1,1);
        Response<Order> response=service.getOrder(orderId);
        handleResponse(response);
        if(!response.isError())
            System.out.println(response.getValue());
    }
    public static void getSupplierOrderListChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        Response<List<Order>> response=service.getSupplierOrders(companyNumber);
        if(!response.isError())
            for(Order order:response.getValue())
                System.out.println(order);
        handleResponse(response);
    }
    public static void handleResponse(Response response){
        if(response.isError())
            System.out.println("ERROR! "+response.getMsg());
        else System.out.println("Command executed successfully");
    }
}
