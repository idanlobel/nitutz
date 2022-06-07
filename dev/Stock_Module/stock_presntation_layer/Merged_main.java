package Stock_Module.stock_presntation_layer;

import Stock_Module.stock_service_layer.Stock_Manager;
import SuppliersModule.SuppliersPresentationLayer.SuppliersMain;
import SuppliersModule.SuppliersServiceLayer.SupplyModuleService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Merged_main {
    public static void main(String[] args) throws Exception {
        SupplyModuleService serviceObj=new SupplyModuleService();
        Stock_Manager stock_manager=new Stock_Manager(serviceObj);
        Stock_main stock_main=new Stock_main(stock_manager);
        Scanner scanner=new Scanner(System.in);
        String current_order="";
        String[] order_in_array=new String[200];
        SuppliersMain suppliersMain=new SuppliersMain();
        ScheduledExecutorService executorService=Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(() -> System.out.println(stock_manager.check_shortage_order().getMsg()),
                LocalDateTime.now().until(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.of(5,0)), ChronoUnit.MINUTES),60*24, TimeUnit.MINUTES);
        executorService.scheduleAtFixedRate(() -> System.out.println(serviceObj.HandlePeriodicOrder().getMsg()),
                LocalDateTime.now().until(LocalDateTime.of(LocalDate.now().plusDays(1),LocalTime.of(5,0)), ChronoUnit.MINUTES),60*24, TimeUnit.MINUTES);
        System.out.println("if you want to load stock data please type yes");
        current_order=scanner.nextLine();
        if(current_order.equals("yes"))
        {
            stock_manager.Auto_start();
            System.out.println("loaded data successfully");
        }

        while(!current_order.equals("exit"))
        {
            System.out.println("please type stock or supplier to access the desired functions.\n" +
                    "to handle scheduled actions now, type handle");
            current_order=scanner.nextLine();
            order_in_array=current_order.split(" ");
            if(order_in_array[0].toLowerCase(Locale.ROOT).equals("stock"))
            {
                System.out.println("welcome to my stock!");
                current_order=scanner.nextLine();
                order_in_array=current_order.split(" ");
                stock_main.Command(order_in_array);


            }
            else if(order_in_array[0].toLowerCase(Locale.ROOT).equals("supplier"))
                {
                    suppliersMain.RunSuppliers(serviceObj,stock_manager);
                }
            else if(order_in_array[0].toLowerCase(Locale.ROOT).equals("handle")){
                System.out.println(stock_manager.check_shortage_order().getMsg());
                System.out.println(serviceObj.HandlePeriodicOrder().getMsg());
            }
            else
                {
                    System.out.println("please enter stock or supplier.\n");
                }

        }
        System.out.println("exiting.. see you later");

     }
}
