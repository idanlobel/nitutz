package SuppliersPresentationLayer;

import SuppliersBusinessLayer.ContactPerson;
import SuppliersBusinessLayer.Contracts.Contract;
import SuppliersBusinessLayer.Order;
import SuppliersBusinessLayer.Responses.Response;
import SuppliersBusinessLayer.Supplier;
import SuppliersServiceLayer.SupplyModuleService;
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
        while(choice!=5) {
            String line = "choose action (type the numeration to execute):\n" +
                    "1: Manage Suppliers\n" +
                    "2: Manage contact people\n" +
                    "3: Manage Contracts\n" +
                    "4: View Orders\n" +
                    "5: Back to main menu\n" +
                    "note: at any point input -1 to cancel operation and go back here";
            try {
                choice = requestNumberInput(line, scanner, 11, 1);
                if (choice == 1) addSupplierChain(serviceObj, scanner);
                if (choice == 2) getSupplierChain(serviceObj, scanner);

                if (choice == 3) getSupplierListChain(serviceObj, scanner);
                if (choice == 4) addContactChain(serviceObj, scanner);
                if (choice == 5) signContractChain(serviceObj, scanner);
                if (choice == 6) getContractChain(serviceObj, scanner);
                if (choice == 7) getContractListChain(serviceObj, scanner);
            //    if (choice == 8) commitOrderChain(serviceObj, scanner);
                if (choice == 9) getOrderChain(serviceObj, scanner);
                if (choice == 10) getSupplierOrderListChain(serviceObj, scanner);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
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
                    throw new RuntimeException("ignore");
                validChoice = true;
            } catch (IllegalArgumentException e) {
                if(choice==-1)
                    throw new RuntimeException("Operation Canceled.");
                System.out.println(choice + " is not a valid option!");
            } catch (Exception e) {
                System.out.println("Invalid Input!");
                scanner.nextLine();
            }
        }
        return choice;
    }
    public static void ManageSuppliersChain(SupplyModuleService serviceObj,Scanner scanner){
        String line="1: Add a supplier\n" +
                "2: Change supplier address\n" +
                "3: Change supplier bank number\n" +
                "4: View a suppliers\n" +
                "5: View all suppliers\n" +
                "6: <-- Back\n";
        int choice=requestNumberInput(line,scanner,6,1);
        if(choice==1) addSupplierChain(serviceObj, scanner);
        if(choice==2) setSupplierAddress(serviceObj,scanner);
        if(choice==3) setSupplierBankNumber(serviceObj,scanner);
        if(choice==4) getSupplierChain(serviceObj,scanner);
        if(choice==5) getSupplierListChain(serviceObj,scanner);
    }
    public static void ManageContractsChain(SupplyModuleService serviceObj,Scanner scanner){
        String line="1: Sign a contract\n" +
                "2: Add an item\n" +
                "3: Remove an item\n" +
                "4: Put a discount (add or replace)\n" +
                "5: Remove a discount\n" +
                "6: Change Delivery Days (only applicble to periodic contracts)\n" +
                "7: Remove a contract\n" +
                "6: <-- Back\n";


    }
    public static void ManageContactPeople(SupplyModuleService serviceObj,Scanner scanner){
        String line="----Managing Supplier's Contact People----" +
                "note: a supplier may not have two contact people with the same name," +
                "mix in their family names to avoid duplicates." +
                "1: Add a contract person\n" +
                "2: Remove a contract person\n" +
                "3: Change contact phone number\n" +
                "4: Change contact Email\n" +
                "5: Change the designated order contact person\n" +
                "6: <-- Back\n";


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
    public static void setSupplierBankNumber(SupplyModuleService service,Scanner scanner) {
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.println("Please state the new bank number (make sure no one is looking):\n");
        String in=scanner.nextLine();
        if(in.equals("-1")) throw new RuntimeException("Operation Canceled.");
        handleResponse(service.ChangeSupplierBankNumber(companyNumber,in));
    }
    public static void setSupplierAddress(SupplyModuleService service,Scanner scanner) {
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.println("Please state the new address:\n");
        String in=scanner.nextLine();
        if(in.equals("-1")) throw new RuntimeException("Operation Canceled.");
        handleResponse(service.ChangeSupplierAddress(companyNumber,in));
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
        int genDis=requestNumberInput("Enter General quantity discount amount",scanner,-1,0);
        ArrayList<int[]> genDisPairs=new ArrayList<>();
        for(int i=0;i<genDis;i++){
            System.out.println("--Discount "+i+"--");
            int num=requestNumberInput("Enter minimal product amount: ",scanner,-1,0);
            int dis=requestNumberInput("Enter Discount: ",scanner,100,1);
            genDisPairs.add(new int[]{num,dis});
        }
        int isPer=requestNumberInput("is the contract for periodic orders or shortage? (0-shortage, 1-periodic)",scanner,1,0);
        if(isPer==1) {
            System.out.println("Enter delivery days(eg. 1,2,3 for Sun,Mon,Tue): ");
            String days = scanner.next();
            boolean[] par = new boolean[7];
            String[] split = days.split(",");
            for (String day : split)
                par[Integer.parseInt(day) - 1] = true;
            handleResponse(service.SignPeriodicContract(companyNumber,idPairs,discounts,genDisPairs,par));
        }
        else {
            handleResponse(service.SignShortageContract(companyNumber,idPairs,discounts,genDisPairs));
        }
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
//    public static void commitOrderChain(SupplyModuleService service,Scanner scanner){
//        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
//        System.out.print("Please state the contact person's name: ");
//        String cName=scanner.next();
//        System.out.print("Enter Date(DD-MM-YY): ");
//        boolean done=false;
//        LocalDate date=null;
//        while(!done) {
//            try {
//                date = LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd-MM-yy"));
//                done=true;
//            }
//            catch (Exception e){
//                System.out.println("Invalid date!");
//            }
//        }
//        ArrayList<int[]> products=new ArrayList<>();
//        int typeNum=requestNumberInput("Enter how many product types to order: ",scanner,-1,1);
//        for(int i=1;i<=typeNum;i++){
//            int id=requestNumberInput("Product "+i+" id:",scanner,-1,1);
//            int amount=requestNumberInput("Enter amount: ",scanner,-1,1);
//            products.add(new int[]{id,amount});
//        }
//     //   handleResponse(service.OrderProducts(companyNumber,cName,date,products));
//    }
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
        else System.out.println(response.getMsg());
    }
}
