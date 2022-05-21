package SuppliersModule.SuppliersPresentationLayer;

import Stock_Module.stock_service_layer.Stock_Manager;
import SuppliersModule.SuppliersBusinessLayer.ContactPerson;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Order;
import SuppliersModule.SuppliersBusinessLayer.Responses.Response;
import SuppliersModule.SuppliersBusinessLayer.Supplier;
import SuppliersModule.SuppliersServiceLayer.SupplyModuleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SuppliersMain { //note: this code is assumed to be made as a placeholder till we have a gui, a such is written quick and dirty

    public  void RunSuppliers(SupplyModuleService serviceObj, Stock_Manager stock_manager) {
     //   SupplyModuleService serviceObj=new SupplyModuleService();
        Scanner scanner=new Scanner(System.in);
        int choice=-1;
        while(choice!=5) {
            String line = "choose action (type the numeration to execute):\n" +
                    "1: Manage Suppliers\n" +
                    "2: Manage contact people\n" +
                    "3: Manage Contracts\n" +
                    "4: View Orders\n" +
                    "5: Back to main menu\n" +
                    "6: Load demo\n" +
                    "note: at any point input -1 to cancel operation and go back here\n";
            /*String line = """
                    choose action (type the numeration to execute):
                    1: Manage Suppliers
                    2: Manage contact people
                    3: Manage Contracts
                    4: View Orders
                    5: Back to main menu
                    note: at any point input -1 to cancel operation and go back here
                    """;*/
            try {
                choice = requestNumberInput(line, scanner, 6, 1);
                if (choice == 1) ManageSuppliersChain(serviceObj, scanner);
                if (choice == 2) ManageContactPeople(serviceObj, scanner);
                if (choice == 3) ManageContractsChain(serviceObj,stock_manager, scanner);
                if (choice == 4) ViewOrders(serviceObj, scanner);
                if (choice == 6) demoModeStart(serviceObj);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Exiting Supplier System...");

    }
    public static int requestNumberInput(String string,Scanner scanner,int max,int min){
        boolean validChoice=false;
        int choice=-1;
        while(!validChoice) {
            System.out.print(string);
            try {
                choice = scanner.nextInt();
                if ((max!=-1 && choice > max) | choice < min)
                    throw new IllegalArgumentException("ignore");
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
        String line= "1: Add a supplier\n" +
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
    public static void ManageContractsChain(SupplyModuleService serviceObj,Stock_Manager stock_manager,Scanner scanner){
        String line= "1: Sign a contract\n" +
                "2: Add an item\n" +
                "3: Remove an item\n" +
                "4: Change product price\n" +
                "5: Put a discount (add or replace)\n" +
                "6: Remove a discount\n" +
                "7: Change Delivery Days (only applicable to periodic contracts)\n" +
                "8: <-- Back\n";

        int choice=requestNumberInput(line,scanner,8,1);
        if(choice==1) signContractChain(serviceObj,stock_manager, scanner);
        if(choice==2) addProductToContractChaim(serviceObj,scanner);
        if(choice==3) removeProductToContractChain(serviceObj,scanner);
        if(choice==4) changeProductPriceChain(serviceObj,scanner);
        if(choice==5) putDiscountChain(serviceObj,scanner);
        if(choice==6) removeDiscount(serviceObj,scanner);
        if(choice==7) changeDeliveryDays(serviceObj,scanner);
    }
    public static void ManageContactPeople(SupplyModuleService serviceObj,Scanner scanner){
        String line= "----Managing Supplier's Contact People----note: a supplier may not have two contact people with the same name,mix in their family names to avoid duplicates.\n" +
                "1: Add a contract person\n" +
                "2: Remove a contract person\n" +
                "3: Change contact phone number\n" +
                "4: Change contact Email\n" +
                "5: Change the designated order contact person\n" +
                "6: <-- Back\n";
        int choice=requestNumberInput(line,scanner,6,1);
        if(choice==1) addContactChain(serviceObj, scanner);
        if(choice==2) removeContactChain(serviceObj,scanner);
        if(choice==3) changeContactPhoneNum(serviceObj,scanner);
        if(choice==4) changeContactEmailChain(serviceObj,scanner);
        if(choice==5) changeSupplierOrderingCP(serviceObj,scanner);
    }
    public static void ViewOrders(SupplyModuleService serviceObj,Scanner scanner){
        String line= "----Viewing Orders----\n" +
                "1: View an order by id\n" +
                "2: View all suppliers orders\n" +
                "3: <-- Back\n";
        int choice=requestNumberInput(line,scanner,3,1);
        if(choice==1) getOrderChain(serviceObj, scanner);
        if(choice==2) getSupplierOrderListChain(serviceObj,scanner);


    }
    private static void addProductToContractChaim(SupplyModuleService serviceObj, Scanner scanner) {
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,0);
        int catalogNumber=requestNumberInput("Please state the product catalog number: ",scanner,-1,0);
        int price=requestNumberInput("Please state the product price: ",scanner,-1,0);
        int supplierId=requestNumberInput("Please state the product id in the supplier's stock: ",scanner,-1,0);
        int disAmount=requestNumberInput("Please state the amount of product discounts: ",scanner,-1,0);
        ArrayList<int[]> discounts=new ArrayList<>();
        for(int i=0;i<disAmount;i++){
            int amount=requestNumberInput("Please state the minimal amount for a discount: ",scanner,-1,0);
            int discount=requestNumberInput("Please state the discount: ",scanner,99,1);
            discounts.add(new int[]{amount,discount});
        }
        handleResponse(serviceObj.addProductToContract(companyNumber,catalogNumber,supplierId,price,discounts));
    }
    private static void removeProductToContractChain(SupplyModuleService serviceObj, Scanner scanner) {
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,0);
        int catalogNumber=requestNumberInput("Please state the product catalog number: ",scanner,-1,0);
        handleResponse(serviceObj.removeProductToContract(companyNumber,catalogNumber));
    }
    private static void changeProductPriceChain(SupplyModuleService serviceObj, Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,0);
        int catalogNumber=requestNumberInput("Please state the product catalog number: ",scanner,-1,0);
        int price=requestNumberInput("Please state the new product price: ",scanner,-1,0);
        handleResponse(serviceObj.changeProductPrice(companyNumber,catalogNumber,price));
    }
    private static void putDiscountChain(SupplyModuleService serviceObj, Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,0);
        int catalogNumber=requestNumberInput("Please state the product catalog number (0 for general order discount): ",scanner,-1,0);
        int amount=requestNumberInput("Please state the minimal amount for a discount: ",scanner,-1,0);
        int discount=requestNumberInput("Please state the discount: ",scanner,99,1);
        if(discount==0)
            handleResponse(serviceObj.putGeneralDiscount(companyNumber,amount,discount));
        else handleResponse(serviceObj.putDiscount(companyNumber,catalogNumber,amount,discount));
    }
    private static void removeDiscount(SupplyModuleService serviceObj, Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,0);
        int catalogNumber=requestNumberInput("Please state the product catalog number (0 for general order discount): ",scanner,-1,0);
        int amount=requestNumberInput("Please state the minimal amount for a discount: ",scanner,-1,0);
        if(catalogNumber==0)
            handleResponse(serviceObj.removeGeneralDiscount(companyNumber,amount));
        else handleResponse(serviceObj.removeDiscount(companyNumber,catalogNumber,amount));
    }
    private static void changeDeliveryDays(SupplyModuleService serviceObj, Scanner scanner) {
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,0);
        boolean done=false;
        while (!done) {
            try {

                System.out.println("Enter delivery days(eg. 1,2,3 for Sun,Mon,Tue): ");
                String days = scanner.next();
                boolean[] par = new boolean[7];
                String[] split = days.split(",");
                for (String day : split)
                    par[Integer.parseInt(day) - 1] = true;
                handleResponse(serviceObj.putDeliveryDays(companyNumber, par));
                done = true;
            } catch (Exception e) {
                System.out.print("Invalid syntax");
            }
        }
    }
    public static void addSupplierChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Company number: ",scanner,-1,0);
        System.out.print("Company name: ");
        String companyName=scanner.next();
        checkCancel(companyName);
        System.out.print("Address: ");
        String address=scanner.next();
        checkCancel(address);
        System.out.print("Bank number: ");
        String bankNum=scanner.next();
        checkCancel(bankNum);
        ArrayList<ContactPerson> contactPeople=new ArrayList<>();
        int amount=requestNumberInput("Please state the amount of contact people (at least one): ",scanner,-1,1);
        for(int i=1;i<=amount;i++){
            System.out.println("Person "+i);
            System.out.print("Name: ");
            String cName=scanner.next();
            checkCancel(cName);
            System.out.print("cell number: ");
            String cellNum=scanner.next();
            checkCancel(cellNum);
            System.out.print("Email: ");
            String Email=scanner.next();
            checkCancel(Email);
            contactPeople.add(new ContactPerson(cName,Email,cellNum));
        }
        System.out.printf("please state the name of the contact person to handle orders: ");
        String name=scanner.next();
        checkCancel(name);
        handleResponse(service.AddSupplier(companyNumber,companyName,bankNum,address,contactPeople,name));
    }
    public static void checkCancel(String str){
        if(str.equals("-1")) throw new RuntimeException("Operation Canceled.");
    }
    public static void setSupplierBankNumber(SupplyModuleService service,Scanner scanner) {
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Please state the new bank number (make sure no one is looking): ");
        String in=scanner.next();
        checkCancel(in);
        handleResponse(service.ChangeSupplierBankNumber(companyNumber,in));
    }
    public static void setSupplierAddress(SupplyModuleService service,Scanner scanner) {
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Please state the new address: ");
        String in=scanner.next();
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
    public static void removeContactChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Contact name: ");
        String cName=scanner.next();
        handleResponse(service.removeContractPerson(companyNumber,cName));
    }
    public static void changeContactEmailChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Contact name: ");
        String cName=scanner.next();
        System.out.print("Contact Email: ");
        String Email=scanner.next();
        handleResponse(service.changeContractPersonEmail(companyNumber,cName,Email));
    }
    public static void changeContactPhoneNum(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Contact name: ");
        String cName=scanner.next();
        System.out.print("Contact cell number: ");
        String cellNum=scanner.next();
        handleResponse(service.changeContractPersonNum(companyNumber,cName,cellNum));
    }
    public static void changeSupplierOrderingCP(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Contact name: ");
        String cName=scanner.next();
        handleResponse(service.changeSupplierOrderingCP(companyNumber,cName));
    }
    public static void signContractChain(SupplyModuleService service,Stock_Manager stock_manager,Scanner scanner){
        ArrayList<Long> catalogNumberList=new ArrayList<>();
        int companyNumber=requestNumberInput("Please state the company number with which the contract is signed with: ",scanner,-1,1);
        int amount=requestNumberInput("Enter item amount: ",scanner,-1,1);
        ArrayList<int[]> idPairs=new ArrayList<>();
        HashMap<Integer,List<int[]>> discounts=new HashMap<>();
        for(int i=1;i<=amount;i++){
            int id=requestNumberInput("Item "+i+" supplier's id: ",scanner,-1,1);
            int price=requestNumberInput("Item "+i+" price: ",scanner,-1,1);
            int catalogNumber=requestNumberInput("Item "+i+" catalog number: ",scanner,-1,1);
            idPairs.add(new int[]{id, price,catalogNumber}); //second id is -1 cause we have to internal stock sorting yet
            catalogNumberList.add((long) catalogNumber);
            int disAmount=requestNumberInput("Enter amount of discounts: ",scanner,-1,0);
            ArrayList<int[]> itemDisList=new ArrayList<>();
            for(int j=1;j<=disAmount;j++) {
                System.out.println("Discount "+j+" for item "+id);
                int minDisAmount=requestNumberInput("Enter minimal amount for discount: ",scanner,-1,1);
                int disMult=requestNumberInput("Enter percent(eg 20,30,40 - no %): ",scanner,99,1);
                itemDisList.add(new int[]{minDisAmount,disMult});
            }
            discounts.put(catalogNumber,itemDisList);
            }
        int genDis=requestNumberInput("Enter General quantity discount amount: ",scanner,-1,0);
        ArrayList<int[]> genDisPairs=new ArrayList<>();
        for(int i=0;i<genDis;i++){
            System.out.println("--Discount "+i+"--");
            int num=requestNumberInput("Enter minimal product amount: ",scanner,-1,0);
            int dis=requestNumberInput("Enter Discount: ",scanner,100,1);
            genDisPairs.add(new int[]{num,dis});
        }
        int isPer=requestNumberInput("is the contract for periodic orders or shortage? (0-shortage, 1-periodic): ",scanner,1,0);
        boolean done=false;
        if(stock_manager.ValidateCatalogNumber(catalogNumberList).getValue()) {
            if (isPer == 1) {
                while (!done)
                    try {
                        System.out.println("Enter delivery days(eg. 1,2,3 for Sun,Mon,Tue): ");
                        String days = scanner.next();
                        boolean[] par = new boolean[7];
                        String[] split = days.split(",");
                        for (String day : split)
                            par[Integer.parseInt(day) - 1] = true;

                        handleResponse(service.SignPeriodicContract(companyNumber, idPairs, discounts, genDisPairs, par));
                        done = true;
                    } catch (Exception e) {
                        System.out.print("Invalid syntax");
                    }
            } else {
                handleResponse(service.SignShortageContract(companyNumber, idPairs, discounts, genDisPairs));
            }
        }
        else System.out.println("Invalid catalog items: some of them are unrecognized");
    }
    public static void getContractChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number with which the contract is signed with: ",scanner,-1,1);
        Response<Contract> response=service.getContract(companyNumber);
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
    public static void demoModeStart(SupplyModuleService service){
        handleResponse(service.demoMode());
    }
}
