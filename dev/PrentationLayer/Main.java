package PrentationLayer;

import ServiceLayer.SupplyModuleService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SupplyModuleService serviceObj=new SupplyModuleService();
        Scanner scanner=new Scanner(System.in);
        int choice=-1;
        while(choice!=5) {
            String line = "choose action (type the numeration to execute):\n1: Add a supplier\n2: Add contact person to a supplier\n" +
                    "3: Sign contract with a supplier\n4: Order from a supplier\n5: Quit program";
            choice = requestNumberInput(line, scanner, 5);
            if(choice==1) addSupplierChain(serviceObj,scanner);
            if(choice==2) addContactChain(serviceObj,scanner);
            if(choice==3) signContractChain(serviceObj,scanner);
            if(choice==4) commitOrderChain(serviceObj,scanner);

        }
        System.out.println("Program end. goodbye :(");

    }
    public static int requestNumberInput(String string,Scanner scanner,int max){
        boolean validChoice=false;
        int choice=-1;
        while(!validChoice) {
            System.out.println(string);
            try {
                choice = scanner.nextInt();
                if (choice > max | choice <= 0)
                    throw new IllegalArgumentException("ignore");
                validChoice = true;
            } catch (IllegalArgumentException e) {
                System.out.println(choice + " is not a valid option!");
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        }
        return choice;
    }
    public static void addSupplierChain(SupplyModuleService service,Scanner scanner){
        System.out.println("test 1");
    }
    public static void addContactChain(SupplyModuleService service,Scanner scanner){
        System.out.println("test 2");
    }
    public static void signContractChain(SupplyModuleService service,Scanner scanner){
        System.out.println("test 3");
    }
    public static void commitOrderChain(SupplyModuleService service,Scanner scanner){
        System.out.println("test 4");
    }

}
