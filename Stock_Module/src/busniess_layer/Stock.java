package busniess_layer;

import Data_layer.DAL_controller;

import java.time.LocalDate;
import java.util.*;

public class Stock {
    private List<Products> products_list;
    private List<Sale> sales_list;
    private  List<Periodic_Order> periodic_orders_list;
    private DAL_controller dal_controller;


    public Stock() {

        this.products_list = new ArrayList<>();
        this.sales_list = new ArrayList<>();
        this.periodic_orders_list=new ArrayList<>();
        //dal_controller=new DAL_controller();
    }


    //sale
    //returns a list of products that reached their minimal quanitity
    public List<Products> get_low_products() {
        List<Products> answerlist = new ArrayList<>();
        for (Products products : this.products_list) {
            if (products.getQuantity() <= products.getMin_quantity()) {
                answerlist.add(products);
            }

        }
        return answerlist;
    }

    //orders products with required quantity
    //order  id
    public void Order(long products_catalog_number, int quantity, Double cost, String expiry, String name, String manufactorer, String category,String sub_cat,String sub_sub_cat) {
        boolean added = false;
        for (Products products : this.products_list) {
            if (products.getCatalog_number()== products_catalog_number) {
                List<Product> new_items=products.update_quantity(quantity, cost, expiry);
                //update products table quantitiy
                dal_controller.update_products_quantity(products.getQuantity(), products.getShelf_quantity(), products.getCatalog_number());
                added = true;
                for(Product p : new_items)
                {
                    dal_controller.insert_product(p.get_id(),p.getName(),p.getLocation().toString(),p.getcostprice(),p.getsoldprice(),p.getExpire_date().toString(),p.isBroken(),p.getDelivery_date(),p.getSell_date(),p.isSold(),products.getCatalog_number());
                }
            }
        }
        if (!added) {
            //maybe we should ask the client if there is a default price for selling , for exmaple : cost * 1.5
            Products products = new Products(products_catalog_number, name, quantity, cost, cost * 1.5, expiry, manufactorer, category,sub_cat,sub_sub_cat);
            products_list.add(products);
            dal_controller.insert_products(products_catalog_number,name,quantity,products.getsellprice(),manufactorer,category,sub_cat,sub_sub_cat, products.getMin_quantity());
            List<Product> new_items=products.getProduct_list();
            for(Product p: new_items)
            {
                dal_controller.insert_product(p.get_id(),p.getName(),p.getLocation().toString(),p.getcostprice(),p.getsoldprice(),p.getExpire_date().toString(),p.isBroken(),p.getDelivery_date(),p.getSell_date(),p.isSold(),products.getCatalog_number());
            }


        }

    }
    public void add_to_orders(String day,long products_catalog_number, int quantity, Double cost,  String name, String manufactorer, String category,String sub_cat,String sub_sub_cat)
    {
        Periodic_Order order=new Periodic_Order(day,products_catalog_number,quantity,cost,name,manufactorer,category,sub_cat,sub_sub_cat);
        this.periodic_orders_list.add(order);
        dal_controller.insert_periodic_order(order.getID(),day,products_catalog_number,quantity,manufactorer,category,sub_cat,sub_sub_cat,name,cost);

    }


    public void Sale(long products_catalog_number, String startdate, String endate, String reason, double percentage) throws Exception {


        //this checks the products list to add sale to products with provided id
        for (Products products : this.products_list) {
            if (products.getCatalog_number() == products_catalog_number) {
                    Sale new_sale = new Sale(percentage, ID_Generator.getInstance().Get_ID(), LocalDate.now().toString(), endate, reason);
                    new_sale.Add_Products(products);
                    this.sales_list.add(new_sale);
                    dal_controller.insert_sale(new_sale.getId(),new_sale.getpercentage(),new_sale.getStart_date(),new_sale.getEnd_date(),new_sale.getReason());
                    dal_controller.update_products_sell_price(products_catalog_number, products.getsellprice());
                List<Product> all_item=products.getProduct_list();
                for(Product product:all_item)
                {
                    dal_controller.update_product_sell_price(product.get_id(),products.getsellprice());
                }
            }

        }



    }


    //@@@@@@@@@@@@@@ here add update to sales_history_table
    public void Add_to_sale(long products_catalog_number,int sales_id) throws Exception {
        for(Products p:this.products_list)
        {
            if(p.getCatalog_number()==products_catalog_number)
            {
                for(Sale sale:this.sales_list)
                {
                    sale.Add_Products(p);
                    dal_controller.update_products_sell_price(p.getCatalog_number(), p.getsellprice());
                    List<Product> all_item=p.getProduct_list();
                    for(Product product:all_item)
                    {
                        dal_controller.update_product_sell_price(product.get_id(),p.getsellprice());
                    }
                }
            }
        }
    }

    public void sale_by_category(String startdate, String endate, String reason, double percentage,String category,String sub_category,String sub_sub_category) throws Exception {
        Sale new_sale=new Sale(percentage, ID_Generator.getInstance().Get_ID(), startdate,endate,reason);
        this.dal_controller.insert_sale(new_sale.getId(),percentage,startdate,endate,reason);
        boolean sub_is_null=false;
        boolean sub_sub_is_null=false;
        if(sub_category.equals("null"))
           sub_is_null=true;
        if (sub_sub_category.equals("null"))
           sub_sub_is_null=true;
        for(Products p :this.products_list)
        {
            if(!sub_is_null & !sub_sub_is_null) {
                if (p.getMain_category().equals(category) & p.getSub_category().equals(sub_category) & p.getSub_sub_category().equals(sub_sub_category)) {
                    new_sale.Add_Products(p);
                    dal_controller.update_products_sell_price(p.getCatalog_number(), p.getsellprice());
                    List<Product> all_item=p.getProduct_list();
                    for(Product product:all_item)
                    {
                        dal_controller.update_product_sell_price(product.get_id(),p.getsellprice());
                    }
                }
            }


            if(!sub_is_null & sub_sub_is_null) {
                if (p.getMain_category().equals(category) & p.getSub_category().equals(sub_category) ) {
                    new_sale.Add_Products(p);
                    dal_controller.update_products_sell_price(p.getCatalog_number(), p.getsellprice());
                    List<Product> all_item=p.getProduct_list();
                    for(Product product:all_item)
                    {
                        dal_controller.update_product_sell_price(product.get_id(),p.getsellprice());
                    }
                }
            }


            if(sub_is_null & !sub_sub_is_null) {
                if (p.getMain_category().equals(category)  & p.getSub_sub_category().equals(sub_sub_category)) {
                    new_sale.Add_Products(p);
                    dal_controller.update_products_sell_price(p.getCatalog_number(), p.getsellprice());
                    List<Product> all_item=p.getProduct_list();
                    for(Product product:all_item)
                    {
                        dal_controller.update_product_sell_price(product.get_id(),p.getsellprice());
                    }
                }
            }

            if(sub_is_null & sub_sub_is_null) {
                if (p.getMain_category().equals(category) ) {
                    new_sale.Add_Products(p);
                    dal_controller.update_products_sell_price(p.getCatalog_number(), p.getsellprice());
                    List<Product> all_item=p.getProduct_list();
                    for(Product product:all_item)
                    {
                        dal_controller.update_product_sell_price(product.get_id(),p.getsellprice());
                    }
                }
            }


        }
        this.sales_list.add(new_sale);
    }



    public void remove_sale(int sale_id) {
        for (Sale sale : this.sales_list) {
            if (sale.getId() == sale_id) {
                List<Products> prodcuts_in_sale=sale.getProducts_in_sale();

                sale.sale_is_over();
                for(Products p : prodcuts_in_sale)
                {
                    dal_controller.update_products_sell_price(p.getCatalog_number(), p.getsellprice());
                    List<Product> all_item=p.getProduct_list();
                    for(Product product:all_item)
                    {
                        dal_controller.update_product_sell_price(product.get_id(),p.getsellprice());
                    }
                }
                dal_controller.remove_sale(sale_id);


            }

        }
    }

    public Products get_products(int catalog_number) {
        for (Products products : this.products_list) {
            if (products.getCatalog_number() == catalog_number) {
                return products;
            }


        }


        return null;


    }

    public Report get_products_by_categories(String main_cat,String sub_cat,String sub_sub_cat) {
        List<Products> answer_list=new ArrayList<>();


        boolean sub_is_null=false;
        boolean sub_sub_is_null=false;
        if(sub_cat.equals("null"))
            sub_is_null=true;
        if (sub_sub_cat.equals("null"))
            sub_sub_is_null=true;

            if(!sub_is_null & !sub_sub_is_null) {
                for (Products products : this.products_list) {
                    if (products.getMain_category().equals(main_cat) & products.getSub_category().equals(sub_cat) & products.getSub_sub_category().equals(sub_sub_cat)) {
                        answer_list.add(products);
                    }
                }
            }



            if(!sub_is_null & sub_sub_is_null) {
                for (Products products : this.products_list) {
                    if (products.getMain_category().equals(main_cat) & products.getSub_category().equals(sub_cat) ){
                        answer_list.add(products);
                    }
                }
            }


            if(sub_is_null & !sub_sub_is_null) {
                for (Products products : this.products_list) {
                    if (products.getMain_category().equals(main_cat) & products.getSub_sub_category().equals(sub_sub_cat)) {
                        answer_list.add(products);
                    }
                }
            }

            if(sub_is_null & sub_sub_is_null) {
                for (Products products : this.products_list) {
                    if (products.getMain_category().equals(main_cat) ){
                        answer_list.add(products);
                    }
            }



        }
        Report repo=new Report(Report.Subject.stock_report,ID_Generator.getInstance().Get_ID(),answer_list,null,null);

        repo.Fill_Me();
        return repo;

}


    public List<Sale> getsales()
    {
        return  this.sales_list;
    }

    public Report make_stock_report()
    {
        Report r=new Report(Report.Subject.stock_report, ID_Generator.getInstance().Get_ID(),this.products_list,null,null);
        r.Fill_Me();
        dal_controller.insert_report(r.getId(),r.getSubject().toString());
        return r;
    }

    public Report make_prices_report()
    {
        Report r=new Report(Report.Subject.prices_report, ID_Generator.getInstance().Get_ID(),null,null,get_every_product());
        r.Fill_Me();
        dal_controller.insert_report(r.getId(),r.getSubject().toString());
        return r;
    }


    public Report make_sales_report()
    {
        Report r=new Report(Report.Subject.sales_report, ID_Generator.getInstance().Get_ID(),this.products_list,this.sales_list,null);
        r.Fill_Me();
        dal_controller.insert_report(r.getId(),r.getSubject().toString());
        return r;
    }

    public Report make_defective_report()
    {
        Report r=new Report(Report.Subject.defective_items_report, ID_Generator.getInstance().Get_ID(),null,null,get_every_product());
        r.Fill_Me();
        dal_controller.insert_report(r.getId(),r.getSubject().toString());
        return r;
    }



    public List<Product> sorted_by_expiration()
    {
        List<Product> sorted_list=new ArrayList<>();
        for (Products p:this.products_list)
        {
            for(Product product:p.getProduct_list())
            {
                sorted_list.add(product);
            }
        }
        Collections.sort(sorted_list, Comparator.comparing(Product::getExpire_date));




        return sorted_list;
    }

    public Report make_sorted_by_expiration_report()
    {
        Report r=new Report(Report.Subject.sortedby_expiry_report,ID_Generator.getInstance().Get_ID(), null,null,this.sorted_by_expiration());
                dal_controller.insert_report(r.getId(),r.getSubject().toString());
        return r;
    }

    public List<Product> get_every_product()
    {
        List<Product> everyproductinstore=new ArrayList<>();
        for(Products p :this.products_list)
        {
            for(Product product:p.getProduct_list())
            {
                everyproductinstore.add(product);
            }

        }
        return everyproductinstore;
    }


    public boolean set_product_broken(int id,int catalog_number)
    {
        boolean found=false;
        for(Products p:this.products_list)
        {
            if(p.getCatalog_number()==catalog_number)
            {
                found=p.set_broken_item(id);
                dal_controller.update_product_broken_state(id,true);
            }
        }
        return found;
    }

    public String shortage_order() {
        String answer="";

        int quantity_to_order=0;
        for(Products p:this.products_list)
        {
            if(p.getMin_quantity()>p.getQuantity())
            {
                quantity_to_order= p.getMin_quantity()-p.getQuantity()+1;

                Order(p.getCatalog_number(),quantity_to_order,p.getProduct_list().get(0).getcostprice(), LocalDate.parse(LocalDate.now().plusDays(7).toString()).toString(),p.getName(),p.getManufactorer(),p.getMain_category(),p.getSub_category(),p.getSub_sub_category());
                answer+= "shortage order was executed of item:  " + p.getName()+"  quantity:  "+quantity_to_order+"\n";

            }
        }
        if(answer.equals(""))
        {
            answer+="nothing is below minimum quantity, so nothing was ordered";
        }
        return  answer;
    }

    public String periodic_order() {
        String answer="";


        for(Periodic_Order o:this.periodic_orders_list)
        {
            if(LocalDate.now().getDayOfWeek().toString().toLowerCase(Locale.ROOT).equals(o.getDay_of_week()))
            {
                if(!o.has_ordered()) {
                    Order(o.getCatalog_number(), o.getQuantity(), o.getCost(), LocalDate.parse(LocalDate.now().plusDays(7).toString()).toString(), o.getName(), o.getManufactorer(), o.getCategory(), o.getSub_cat(), o.getSub_sub_cat());
                    answer += "periodic order was executed of item:  " + o.getName() + "  quantity:  " + o.getQuantity() + "\n";
                    o.set_ordered_to_true();
                }
            }
            if(LocalDate.now().plusDays(6).getDayOfWeek().equals(o.getDay_of_week()))
            {
                o.reset();
            }
        }

        return  answer;
    }

    public void update_periodic_order_quantity(int id, int quantity) throws Exception {

        boolean found=false;
        for (Periodic_Order o:this.periodic_orders_list)
        {
            if(o.getID()==id)
            {
                if(LocalDate.now().plusDays(6).getDayOfWeek().toString().toLowerCase(Locale.ROOT).equals(o.getDay_of_week()))
                {
                    throw new Exception("sorry can't update periodic order day before");
                }
                else {
                    o.set_quantity(quantity);
                    found = true;
                    dal_controller.update_periodic_order_quantity(id,quantity);
                    break;
                }
            }
        }
        if(!found)
            throw new Exception("no order with this id : "+id);



    }


    public void update_periodic_order_day(int id, String day) throws Exception {

        boolean found=false;
        for (Periodic_Order o:this.periodic_orders_list)
        {
            if(o.getID()==id)
            {
                if(LocalDate.parse(LocalDate.now().plusDays(6).toString()).getDayOfWeek().toString().toLowerCase(Locale.ROOT).equals(o.getDay_of_week()))
                {
                    throw new Exception("sorry can't update periodic order day before");
                }
                else {
                    o.setDay_of_week(day);
                    found = true;
                    dal_controller.update_periodic_order_day(id,day);
                    break;
                }
            }
        }
        if(!found)
            throw new Exception("no order with this id : "+id);



    }

    public void remove_periodic_order(int id) throws Exception {
       int index=-1;
        for (Periodic_Order o:this.periodic_orders_list)
        {
            if(o.getID()==id)
            {
                if(LocalDate.parse(LocalDate.now().plusDays(6).toString()).getDayOfWeek().toString().toLowerCase(Locale.ROOT).equals(o.getDay_of_week()))
                {
                    throw new Exception("sorry can't remove periodic order day before");
                }
                else {
                   index=periodic_orders_list.indexOf(o);

                   break;
                }
            }
        }
        if(index==-1)
            throw new Exception("no order with this id : "+id);
        else {
            periodic_orders_list.remove(index);
            dal_controller.remove_periodic_order(id);
        }


    }

    public void remove_defective_and_expired_products()
    {
      List<Product> all_items_in_store=  get_every_product();
     // List<Integer> all_ids_to_remove=new ArrayList<>();
        List<Product> items_to_remove=new ArrayList<>();
      for(Product p :all_items_in_store)
      {
          if(p.isBroken() || p.getExpire_date().isBefore(LocalDate.now()))
          {
              //all_ids_to_remove.add(p.get_id());
              items_to_remove.add(p);
          }
      }


//      for(Integer id:all_ids_to_remove)
//      {
//          for(Products p:products_list)
//          {
//              for(Product product:p.getProduct_list())
//              {
//                  if(product.get_id()==id)
//                  {
//                items_to_remove.add(product);
//                  }
//              }
//          }
//
//      }
      for(Products p:products_list)
      {
         p.remove_product(items_to_remove);
         p.update_quantity_after_removing_items();
         dal_controller.update_products_quantity(p.getQuantity(),p.getShelf_quantity(),p.getCatalog_number());


      }
      for(Product p:items_to_remove)
      {
          dal_controller.remove_product(p.get_id());
      }

    }
}
