package Stock_Module.stock_presntation_layer;


import Stock_Module.stock_service_layer.Stock_Manager;

import java.util.Locale;

public class Stock_main {

    private Stock_Manager stock_manager;
        public Stock_main(Stock_Manager stock_manager)
        {
            this.stock_manager=stock_manager;
        }

    public void Command(String [] order_in_array)
    {
        boolean excuted=false;
        if(order_in_array.length>2 && order_in_array[0].toLowerCase(Locale.ROOT).equals("periodic") & order_in_array[1].toLowerCase(Locale.ROOT).equals("order"))
        {


            stock_manager.create_order(order_in_array[2],order_in_array[3],order_in_array[4],order_in_array[5],order_in_array[6],order_in_array[7],order_in_array[8],order_in_array[9],order_in_array[10]);
            System.out.println("created new periodic order done successfully");
            excuted=true;
        }


        if(order_in_array.length>4 && order_in_array[0].toLowerCase(Locale.ROOT).equals("update") & order_in_array[1].toLowerCase(Locale.ROOT).equals("periodic") & order_in_array[2].toLowerCase(Locale.ROOT).equals("order") & order_in_array[3].toLowerCase(Locale.ROOT).equals("quantity"))
        {
            excuted=true;

            try {
                stock_manager.update_periodic_order_quantity(order_in_array[4],order_in_array[5]);
                System.out.println("updated  periodic order quantity done successfully");

            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }



        }


        if(order_in_array.length>4 && order_in_array[0].toLowerCase(Locale.ROOT).equals("update") & order_in_array[1].toLowerCase(Locale.ROOT).equals("periodic") & order_in_array[2].toLowerCase(Locale.ROOT).equals("order") & order_in_array[3].toLowerCase(Locale.ROOT).equals("day"))
        {
            excuted=true;

            try {
                stock_manager.update_periodic_order_day(order_in_array[4],order_in_array[5]);
                System.out.println("updated  periodic order day done successfully");

            }
            catch (Exception e)
            {
                System.out.println(e.toString());

            }






        }


        if(order_in_array.length>3 && order_in_array[0].toLowerCase(Locale.ROOT).equals("remove") & order_in_array[1].toLowerCase(Locale.ROOT).equals("periodic") & order_in_array[2].toLowerCase(Locale.ROOT).equals("order") )
        {

            excuted=true;
            try {
                stock_manager.remove_periodic_order(order_in_array[3]);
                System.out.println("removed periodic order done successfully");
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }



        }

        if(order_in_array.length>2 && order_in_array[0].toLowerCase(Locale.ROOT).equals("remove") & order_in_array[1].toLowerCase(Locale.ROOT).equals("sale") )
        {

            excuted=true;
            try {
                stock_manager.remove_sale(order_in_array[2]);
                System.out.println("sale with id : "+order_in_array[2]+" is over!");
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
                excuted=true;
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
                excuted=true;
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
            excuted=true;
            try {
                stock_manager.add_product_to_sale(order_in_array[1],order_in_array[3]);
                System.out.println("added to existing sale done successfully");
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
            }
        }

        if(order_in_array.length==4 && order_in_array[0].equals("Show") | order_in_array[0].equals("show"))
        {
            excuted=true;
            System.out.println(stock_manager.show_by_catgeory(order_in_array[1],order_in_array[2],order_in_array[3]).getValue().tostring());
        }

        if(order_in_array[0].equals("report") | order_in_array[0].equals("Report"))
        {

            switch (order_in_array[1].toLowerCase(Locale.ROOT)) {
                case "stock": {
                    excuted=true;
                    System.out.println("showing stock report");
                    System.out.println(stock_manager.make_stock_report().getValue().tostring());
                    break;
                }

                case "prices":{
                    excuted=true;
                    System.out.println("showing prices report");
                    System.out.println(stock_manager.make_prices_report().getValue().tostring());
                    break;
                }
                case "sales":{
                    excuted=true;
                    System.out.println("showing sales report");
                    System.out.println(stock_manager.make_sales_report().getValue().tostring());
                    break;
                }
                case "defective":{
                    excuted=true;
                    System.out.println("showing defective report");
                    System.out.println(stock_manager.make_defective_report().getValue().tostring());
                    break;
                }
                case "expiry":{
                    excuted=true;
                    System.out.println("showing expiry report");
                    System.out.println(stock_manager.make_expiry_report().getValue().tostring());
                    break;
                }
            }
        }

        if(order_in_array.length>3 && order_in_array[0].toLowerCase(Locale.ROOT).equals("set") & order_in_array[1].toLowerCase(Locale.ROOT).equals("broken") & order_in_array[2].toLowerCase(Locale.ROOT).equals("item"))
        {
            excuted=true;
            if(stock_manager.set_broken_product(order_in_array[3],order_in_array[4]).getValue())
            {
                System.out.println("successfully set item to broken state");
            }
            else
            {
                System.out.println("failed to set item to broken state, item is missing");
            }
        }

        if(order_in_array.length>2 && order_in_array[0].toLowerCase(Locale.ROOT).equals("remove") & order_in_array[1].toLowerCase(Locale.ROOT).equals("broken") & order_in_array[2].toLowerCase(Locale.ROOT).equals("items"))
        {
            excuted=true;
            stock_manager.remove_broken_items();
            System.out.println("successfully removed broken and expired items");
        }

            if(!excuted)
            {
             System.out.println("please enter valid command as provided in the instruction pdf next time");
            }

    }

    }















