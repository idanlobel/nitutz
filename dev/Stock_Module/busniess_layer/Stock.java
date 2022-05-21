package Stock_Module.busniess_layer;


import Stock_Module.Data_layer.DAL_controller;
import SuppliersModule.SuppliersServiceLayer.SupplyModuleService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Stock {
    private List<Products> products_list;
    private List<Sale> sales_list;
    private List<Periodic_Order> periodic_orders_list;
    private DAL_controller dal_controller;
    private SupplyModuleService supplyModuleService;

    public Stock() {

        this.products_list = new ArrayList<>();
        this.sales_list = new ArrayList<>();
        this.periodic_orders_list = new ArrayList<>();
        dal_controller = DAL_controller.getInstance();
        supplyModuleService=new SupplyModuleService();

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
                if(!(supplyModuleService.OrderProduct((int) products_catalog_number,quantity)).isError()) {
                    products.update_quantity(quantity, cost, expiry, dal_controller.getProduct_table());
                    added = true;
                }
                else
                    added=false;
            }
        }
        if (!added) {
            //maybe we should ask the client if there is a default price for selling , for exmaple : cost * 1.5
            Products products = new Products(products_catalog_number, name, quantity, cost, cost * 1.5, expiry, manufactorer, category,sub_cat,sub_sub_cat,dal_controller.getProducts_table(),dal_controller.getProduct_table(),dal_controller.getSales_history_table());
            products_list.add(products);
        }



    }
    public boolean ValidateCatalogNum(List<Long> catalogNumberList) {
        for (Long catalogNumber : catalogNumberList) {
            boolean found = false;
            for (Products products : this.products_list) {
                if ((products.getCatalog_number() == catalogNumber)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }
        return true;
    }
    public void define_periodic_orders(String day,long products_catalog_number, int quantity, Double cost,  String name, String manufactorer, String category,String sub_cat,String sub_sub_cat)
    {
        Periodic_Order order=new Periodic_Order(day,products_catalog_number,quantity,cost,name,manufactorer,category,sub_cat,sub_sub_cat,dal_controller.getPeriodic_order_table());
        supplyModuleService.AddPeriodicProduct((int) products_catalog_number,quantity, DayOfWeek.valueOf(day.toUpperCase(Locale.ROOT)).getValue());
        this.periodic_orders_list.add(order);
    }


    public void Sale(long products_catalog_number, String startdate, String endate, String reason, double percentage) throws Exception {


        //this checks the products list to add sale to products with provided id
        for (Products products : this.products_list) {
            if (products.getCatalog_number() == products_catalog_number) {
                    Sale new_sale = new Sale(percentage, LocalDate.now().toString(), endate, reason,dal_controller.getSale_table());
                    new_sale.Add_Products(products);
                    this.sales_list.add(new_sale);

            }

        }



    }


    //@@@@@@@@@@@@@@ here add update to sales_history_table
    public void Add_to_sale(long products_catalog_number,int sales_id) throws Exception {
        boolean added=false;
        for(Products p:this.products_list)
        {
            if(p.getCatalog_number()==products_catalog_number)
            {
                for(Sale sale:this.sales_list)
                {
                    sale.Add_Products(p);
                    added=true;

                }
            }
        }
        if(!added)
        {
            throw new Exception("id or catalog number not found!");
        }

    }

    public void sale_by_category(String startdate, String endate, String reason, double percentage,String category,String sub_category,String sub_sub_category) throws Exception {
        Sale new_sale=new Sale(percentage, startdate,endate,reason,dal_controller.getSale_table());

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
//                    }
                }
            }


            if(!sub_is_null & sub_sub_is_null) {
                if (p.getMain_category().equals(category) & p.getSub_category().equals(sub_category) ) {
                    new_sale.Add_Products(p);

                }
            }


            if(sub_is_null & !sub_sub_is_null) {
                if (p.getMain_category().equals(category)  & p.getSub_sub_category().equals(sub_sub_category)) {
                    new_sale.Add_Products(p);
                }
            }

            if(sub_is_null & sub_sub_is_null) {
                if (p.getMain_category().equals(category) ) {
                    new_sale.Add_Products(p);
                }
            }


        }
        this.sales_list.add(new_sale);
    }



    public void remove_sale(int sale_id) throws Exception {
        boolean found=false;
        for (Sale sale : this.sales_list) {
            if (sale.getId() == sale_id) {
                found =true;
                sale.sale_is_over();
                dal_controller.remove_sale(sale_id);
            }

        }
        if(!found)
        {
            throw new Exception("no sale with this id: "+sale_id+"!!");
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
        Report repo=new Report(Report.Subject.stock_report,answer_list,null,null);

        repo.Fill_Me();
        return repo;

}


    public List<Sale> getsales()
    {
        return  this.sales_list;
    }

    public Report make_stock_report()
    {
        Report r=new Report(Report.Subject.stock_report, this.products_list,null,null);
        r.Fill_Me();

        return r;
    }

    public Report make_prices_report()
    {
        Report r=new Report(Report.Subject.prices_report,null,null,get_every_product());
        r.Fill_Me();

        return r;
    }


    public Report make_sales_report()
    {
        Report r=new Report(Report.Subject.sales_report, this.products_list,this.sales_list,null);
        r.Fill_Me();

        return r;
    }

    public Report make_defective_report()
    {
        Report r=new Report(Report.Subject.defective_items_report, null,null,get_every_product());
        r.Fill_Me();

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
        Report r=new Report(Report.Subject.sortedby_expiry_report, null,null,this.sorted_by_expiration());

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
                answer+="WARNING!!  "+p.getName()+" has reached minimum quantity!   quantity:"+p.getQuantity()+"\n";
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
                if(LocalDate.now().plusDays(1).getDayOfWeek().toString().toLowerCase(Locale.ROOT).equals(o.getDay_of_week()))
                {
                    throw new Exception("sorry can't update periodic order day before");
                }
                else {
                    o.set_quantity(quantity);
                    found = true;
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
                if(LocalDate.parse(LocalDate.now().plusDays(1).toString()).getDayOfWeek().toString().toLowerCase(Locale.ROOT).equals(o.getDay_of_week()))
                {
                    throw new Exception("sorry can't update periodic order day before");
                }
                else {
                    o.setDay_of_week(day);
                    found = true;
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
                if(LocalDate.parse(LocalDate.now().plusDays(1).toString()).getDayOfWeek().toString().toLowerCase(Locale.ROOT).equals(o.getDay_of_week()))
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

        List<Product> items_to_remove=new ArrayList<>();
      for(Product p :all_items_in_store)
      {
          if(p.isBroken() || p.getExpire_date().isBefore(LocalDate.now()))
          {

              items_to_remove.add(p);
              dal_controller.remove_product(p.get_id());
          }
      }



      for(Products p:products_list)
      {
         p.remove_product(items_to_remove);
         p.update_quantity_after_removing_items();



      }




    }
    public List<Periodic_Order> get_periodic_order_list()
    {
        return this.periodic_orders_list;
    }



}
