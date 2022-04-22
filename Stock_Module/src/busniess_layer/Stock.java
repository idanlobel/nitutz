package busniess_layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Stock {
    private List<Products> products_list;
    private List<Sale> sales_list;


    public Stock() {

        this.products_list = new ArrayList<>();
        this.sales_list = new ArrayList<>();
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
    public void Order(long products_catalog_number, int quantity, Double cost, String expiry, String name, String manufactorer, String category,String sub_cat,String sub_sub_cat) {
        boolean added = false;
        for (Products products : this.products_list) {
            if (products.getCatalog_number()== products_catalog_number) {
                products.update_quantity2(quantity, cost, expiry);
                added = true;
            }
        }
        if (!added) {
            //maybe we should ask the client if there is a default price for selling , for exmaple : cost * 1.5
            Products products = new Products(products_catalog_number, name, quantity, cost, cost * 1.5, expiry, manufactorer, category,sub_cat,sub_sub_cat);
            products_list.add(products);
        }

    }

    public void Sale(long products_catalog_number, String startdate, String endate, String reason, double percentage) throws Exception {


        //this checks the products list to add sale to products with provided id
        for (Products products : this.products_list) {
            if (products.getCatalog_number() == products_catalog_number) {
                    Sale new_sale = new Sale(percentage, ID_Generator.getInstance().Get_ID(), LocalDate.now().toString(), endate, reason);
                    new_sale.Add_Products(products);
                    this.sales_list.add(new_sale);
            }

        }


    }

    public void Add_to_sale(long products_catalog_number,int sales_id) throws Exception {
        for(Products p:this.products_list)
        {
            if(p.getCatalog_number()==products_catalog_number)
            {
                for(Sale sale:this.sales_list)
                {
                    sale.Add_Products(p);
                }
            }
        }
    }

    public void sale_by_category(String startdate, String endate, String reason, double percentage,String category,String sub_category,String sub_sub_category) throws Exception {
        Sale new_sale=new Sale(percentage, ID_Generator.getInstance().Get_ID(), startdate,endate,reason);
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

    public void remove_sale(int sale_id) {
        for (Sale sale : this.sales_list) {
            if (sale.getId() == sale_id) {
                sale.sale_is_over();

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
        return r;
    }

    public Report make_prices_report()
    {
        Report r=new Report(Report.Subject.prices_report, ID_Generator.getInstance().Get_ID(),null,null,get_every_product());
        r.Fill_Me();
        return r;
    }


    public Report make_sales_report()
    {
        Report r=new Report(Report.Subject.sales_report, ID_Generator.getInstance().Get_ID(),this.products_list,this.sales_list,null);
        r.Fill_Me();
        return r;
    }

    public Report make_defective_report()
    {
        Report r=new Report(Report.Subject.defective_items_report, ID_Generator.getInstance().Get_ID(),null,null,get_every_product());
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
        return new Report(Report.Subject.sortedby_expiry_report,ID_Generator.getInstance().Get_ID(), null,null,this.sorted_by_expiration());
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
            }
        }
        return found;
    }

}
