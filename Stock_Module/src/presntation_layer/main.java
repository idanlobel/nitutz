package presntation_layer;

import service_layer.Stock_Manager;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws Exception {
        Stock_Manager stock_manager=new Stock_Manager();

        System.out.println(LocalDate.now().getDayOfWeek().toString().toLowerCase(Locale.ROOT));
        System.out.println("welcome to my stock!!!");


        Scanner scanner=new Scanner(System.in);
        String current_order="";
        String[] order_in_array=new String[200];

        System.out.println("if you want to load data please type yes");
        current_order=scanner.nextLine();
        if(current_order.equals("yes"))
        {
            stock_manager.Auto_start();
            System.out.println("loaded data successfully");
        }


        while(!current_order.equals("exit"))
        {

            System.out.println(stock_manager.check_shortage_order());
            System.out.println(stock_manager.check_periodic_order());

            System.out.println("please enter your command");
             current_order=scanner.nextLine();
            order_in_array=current_order.split(" ");

            if(order_in_array.length>3 && order_in_array[0].toLowerCase(Locale.ROOT).equals("periodic") & order_in_array[1].toLowerCase(Locale.ROOT).equals("order"))
            {


                stock_manager.create_order(order_in_array[2],order_in_array[3],order_in_array[4],order_in_array[5],order_in_array[6],order_in_array[7],order_in_array[8],order_in_array[9],order_in_array[10]);
                System.out.println("created new periodic order done successfully");
            }


            if(order_in_array[0].toLowerCase(Locale.ROOT).equals("update") & order_in_array[1].toLowerCase(Locale.ROOT).equals("periodic") & order_in_array[2].toLowerCase(Locale.ROOT).equals("order") & order_in_array[3].toLowerCase(Locale.ROOT).equals("quantity"))
            {


                try {
                    stock_manager.update_periodic_order_quantity(order_in_array[4],order_in_array[5]);
                    System.out.println("updated  periodic order quantity done successfully");
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }



            }


            if(order_in_array[0].toLowerCase(Locale.ROOT).equals("update") & order_in_array[1].toLowerCase(Locale.ROOT).equals("periodic") & order_in_array[2].toLowerCase(Locale.ROOT).equals("order") & order_in_array[3].toLowerCase(Locale.ROOT).equals("day"))
            {


                try {
                    stock_manager.update_periodic_order_day(order_in_array[4],order_in_array[5]);
                    System.out.println("updated  periodic order day done successfully");
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }



            }


            if(order_in_array[0].toLowerCase(Locale.ROOT).equals("remove") & order_in_array[1].toLowerCase(Locale.ROOT).equals("periodic") & order_in_array[2].toLowerCase(Locale.ROOT).equals("order") )
            {


                try {
                    stock_manager.remove_periodic_order(order_in_array[3]);
                    System.out.println("removed periodic order done successfully");
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }



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

            if(order_in_array.length>3 && order_in_array[0].toLowerCase(Locale.ROOT).equals("set") & order_in_array[1].toLowerCase(Locale.ROOT).equals("broken") & order_in_array[2].toLowerCase(Locale.ROOT).equals("item"))
            {
                if(stock_manager.set_broken_product(order_in_array[3],order_in_array[4]))
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
