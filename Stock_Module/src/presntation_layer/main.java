package presntation_layer;

import service_layer.Stock_Manager;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Stock_Manager stock_manager=new Stock_Manager();
        System.out.println("welcome to my stock!!!");

        Scanner scanner=new Scanner(System.in);
        String current_order="";
        String[] order_in_array=new String[20];
        while(!current_order.equals("exit"))
        {
            System.out.println("please enter your command");
             current_order=scanner.nextLine();
            order_in_array=current_order.split(" ");
            if(order_in_array[0].equals("order")|order_in_array[0].equals("Order"))
            {


                stock_manager.make_order(order_in_array[1],order_in_array[2],order_in_array[3],order_in_array[4],order_in_array[5],order_in_array[6],order_in_array[7],order_in_array[8],order_in_array[9]);
                System.out.println("order done successfully");
            }
            if(order_in_array[0].equals("sale")|order_in_array[0].equals("Sale"))
            {
                if(order_in_array[1].toLowerCase(Locale.ROOT).equals("by") &(order_in_array[2].toLowerCase(Locale.ROOT).equals("category")))
                {
                    try {
                        stock_manager.make_sale_by_catgeory(order_in_array[3],order_in_array[4],order_in_array[5],order_in_array[6],order_in_array[7],order_in_array[8],order_in_array[9]);
                        System.out.println("sale by category done successfully");
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.toString());
                    }

                }
                else
                {

                    try {
                        stock_manager.make_sale(order_in_array[1],order_in_array[2],order_in_array[3],order_in_array[4],order_in_array[5]);
                        System.out.println("sale done successfully");
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.toString());
                    }
                }
            }

            if(order_in_array[0].equals("Add") | order_in_array[0].equals("add"))
            {

                try {
                    stock_manager.add_product_to_sale(order_in_array[1],order_in_array[3]);
                    System.out.println("added to existing sale done successfully");
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }
            }

            if(order_in_array[0].equals("Show") | order_in_array[0].equals("show"))
            {
                System.out.println(stock_manager.show_by_catgeory(order_in_array[1],order_in_array[2],order_in_array[3]).tostring());
            }

            if(order_in_array[0].equals("report") | order_in_array[0].equals("Report"))
            {
                switch (order_in_array[1].toLowerCase(Locale.ROOT)) {
                    case "stock": {
                        System.out.println("showing stock report");
                        System.out.println(stock_manager.make_stock_report().tostring());
                        break;
                    }

                    case "prices":{
                        System.out.println("showing prices report");
                        System.out.println(stock_manager.make_prices_report().tostring());
                        break;
                    }
                    case "sales":{
                        System.out.println("showing sales report");
                        System.out.println(stock_manager.make_sales_report().tostring());
                        break;
                    }
                    case "defective":{
                        System.out.println("showing defective report");
                        System.out.println(stock_manager.make_defective_report().tostring());
                        break;
                    }
                    case "expiry":{
                        System.out.println("showing expiry report");
                        System.out.println(stock_manager.make_expiry_report().tostring());
                        break;
                    }
                }
            }

            if(order_in_array.length==3 && order_in_array[0].toLowerCase(Locale.ROOT).equals("set") & order_in_array[1].toLowerCase(Locale.ROOT).equals("broken") & order_in_array[2].toLowerCase(Locale.ROOT).equals("item"))
            {
                if(stock_manager.set_broken_product(order_in_array[2],order_in_array[3]))
                {
                    System.out.println("successfully set item to broken state");
                }
                else
                {
                    System.out.println("failed to set item to broken state, item is missing");
                }
            }


        }

        System.out.println("exiting.. see you later");



    }


}
