package presntation_layer;

import service_layer.Stock_Manager;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Stock_Manager stock_manager=new Stock_Manager();
        System.out.println("welcome to my stock!!!");
        Scanner scanner=new Scanner(System.in);
        String current_order=scanner.nextLine();
        String[] order_in_array=new String[20];
        while(!current_order.equals("exit"))
        {

            order_in_array=current_order.split(" ");
            if(order_in_array[0].equals("order")|order_in_array[0].equals("Order"))
            {
                stock_manager.make_order(order_in_array[1],order_in_array[2],order_in_array[3],order_in_array[4],order_in_array[5],order_in_array[6],order_in_array[7],order_in_array[8],order_in_array[9],order_in_array[10],order_in_array[11]);

            }
            if(order_in_array[0].equals("sale")|order_in_array[0].equals("Sale"))
            {
                if((order_in_array[1].equals("By")| order_in_array[1].equals("by")) &(order_in_array[2].equals("Catalog")| order_in_array[1].equals("catalog")))
                {
                    try {
                        stock_manager.make_sale_by_catgeory(order_in_array[3],order_in_array[4],order_in_array[5],order_in_array[6],order_in_array[7],order_in_array[8],order_in_array[9],order_in_array[10],order_in_array[11]);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.toString());
                    }

                }
                else
                {

                    try {
                        stock_manager.make_sale(order_in_array[1],order_in_array[2],order_in_array[3],order_in_array[4],order_in_array[5],order_in_array[6],order_in_array[7],order_in_array[8],order_in_array[9]);
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
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }
            }


        }



    }


}
