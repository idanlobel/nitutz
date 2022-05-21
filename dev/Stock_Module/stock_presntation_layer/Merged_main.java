package Stock_Module.stock_presntation_layer;

import Stock_Module.stock_service_layer.Stock_Manager;
import SuppliersModule.SuppliersPresentationLayer.SuppliersMain;
import SuppliersModule.SuppliersServiceLayer.SupplyModuleService;

import java.util.Locale;
import java.util.Scanner;

public class Merged_main {
    public static void main(String[] args) throws Exception {
        Stock_Manager stock_manager=new Stock_Manager();
        SupplyModuleService serviceObj=new SupplyModuleService();
        Stock_main stock_main=new Stock_main(stock_manager);
        Scanner scanner=new Scanner(System.in);
        String current_order="";
        String[] order_in_array=new String[200];
        SuppliersMain suppliersMain=new SuppliersMain();
        System.out.println("if you want to load stock data please type yes");
        current_order=scanner.nextLine();
        if(current_order.equals("yes"))
        {
            stock_manager.Auto_start();
            System.out.println("loaded data successfully");
        }

        while(!current_order.equals("exit"))
        {
            System.out.println(stock_manager.check_shortage_order().getValue());
            System.out.println(stock_manager.check_periodic_order().getValue());

            System.out.println("please type stock or supplier to access the desired functions");
            current_order=scanner.nextLine();
            order_in_array=current_order.split(" ");
            if(order_in_array[0].toLowerCase(Locale.ROOT).equals("stock"))
            {
                System.out.println("welcome to my stock!");
                current_order=scanner.nextLine();
                order_in_array=current_order.split(" ");
                stock_main.Command(order_in_array);


            }
            else
            {
                if(order_in_array[0].toLowerCase(Locale.ROOT).equals("supplier"))
                {
                    suppliersMain.RunSuppliers(serviceObj,stock_manager);
                }
                else
                {
                    System.out.println("please enter stock or supplier.\n");
                }
            }

        }
        System.out.println("exiting.. see you later");

     }
}
