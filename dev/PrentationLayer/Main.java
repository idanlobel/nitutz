package PrentationLayer;

import BusinessLayer.ContactPerson;
import BusinessLayer.Contract;
import BusinessLayer.Responses.Response;
import ServiceLayer.SupplyModuleService;
import javafx.util.Pair;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.text.DateFormat;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            String line = "choose action (type the numeration to execute):\n1: Add a supplier\n2: Add contact person to a supplier\n" +
                    "3: Sign contract with a supplier\n4: Order from a supplier\n5: Quit program\n";
            choice = requestNumberInput(line, scanner, 5,1);
            if(choice==1) addSupplierChain(serviceObj,scanner);
            if(choice==2) addContactChain(serviceObj,scanner);
            if(choice==3) signContractChain(serviceObj,scanner);
            if(choice==4) commitOrderChain(serviceObj,scanner);

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
        handleResponse(service.AddSupplier(companyNumber,companyName,bankNum,contactPeople));
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
            idPairs.add(new int[]{id, -1}); //second id is -1 cause we have to internal stock sorting yet
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
        handleResponse(service.SignContract(companyNumber,idPairs,discounts,par));
    }
    public static void commitOrderChain(SupplyModuleService service,Scanner scanner){
        int companyNumber=requestNumberInput("Please state the company number: ",scanner,-1,1);
        System.out.print("Please state the contact person's name: ");
        String cName=scanner.next();
        System.out.print("Enter Date(DD-MM-YY): ");
        LocalDate date=LocalDate .parse(scanner.next(),DateTimeFormatter.ofPattern("yy-MM-dd"));
        ArrayList<int[]> products=new ArrayList<>();
        int typeNum=requestNumberInput("Enter how many product types to order: ",scanner,-1,1);
        for(int i=1;i<=typeNum;i++){
            int id=requestNumberInput("Product "+i+" id:",scanner,-1,1);
            int amount=requestNumberInput("Enter amount: ",scanner,-1,1);
            products.add(new int[]{id,amount});
        }
        handleResponse(service.OrderProducts(companyNumber,cName,date,products));
    }
    public static void handleResponse(Response response){
        if(response.isError())
            System.out.println("ERROR! "+response.getMsg());
        else System.out.println("Command executed successfully");
    }
}
