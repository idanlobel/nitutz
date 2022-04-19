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
    public void Order(int id, int quantity, Double cost, LocalDate expiry, String name, String manufactorer, String category,String sub_cat,String sub_sub_cat) {
        boolean added = false;
        for (Products products : this.products_list) {
            if (products.getID() == id) {
                products.update_quantity(quantity, cost, expiry);
                added = true;
            }
        }
        if (!added) {
            //maybe we should ask the client if there is a default price for selling , for exmaple : cost * 1.5
            Products products = new Products(id, name, quantity, cost, cost * 1.5, expiry, manufactorer, category,sub_cat,sub_sub_cat);
            products_list.add(products);
        }

    }

    public void Sale(int products_id, int sales_id, LocalDate startdate, LocalDate endate, String reason, double percentage) throws Exception {
        boolean foundsale = false;
        int index_of_sale = -1;
        //this for checks if this sale already exists and we want to add to it more products
        //if there isn't we will make new instance of sale
        for (Sale sale : this.sales_list) {
            if (sale.getId() == sales_id) {
                foundsale = true;
                index_of_sale = this.sales_list.indexOf(sale);
                break;

            }

        }
        //this checks the products list to add sale to products with provided id
        for (Products products : this.products_list) {
            if (products.getID() == products_id) {
                if (!foundsale) {

                    Sale new_sale = new Sale(percentage,ID_Generator.getInstance().Get_ID(), LocalDate.now(), endate, reason);
                    new_sale.Add_Products(products);
                    this.sales_list.add(new_sale);


                } else {
                    this.sales_list.get(index_of_sale).Add_Products(products);

                }
            }

        }


    }

    public void sale_by_category(String category,int sales_id, LocalDate startdate, LocalDate endate, String reason, double percentage) throws Exception {
        Sale new_sale=new Sale(percentage,ID_Generator.getInstance().Get_ID(), startdate,endate,reason);
        for(Products p :this.products_list)
        {
            if(p.getMain_category().equals(category))
            {
                new_sale.Add_Products(p);
            }
        }
    }

    public void remove_sale(int sale_id) {
        for (Sale sale : this.sales_list) {
            if (sale.getId() == sale_id) {
                sale.sale_is_over();

            }

        }
    }

    public Products get_products(int id) {
        for (Products products : this.products_list) {
            if (products.getID() == id) {
                return products;
            }


        }


        return null;


    }

    public List<Products> get_products_by_categories(String main_cat,String sub_cat,String sub_sub_cat) {
        List<Products> answer_list=new ArrayList<>();
        for (Products products : this.products_list) {
            if (products.getMain_category().equals(main_cat) & products.getSub_category().equals(sub_cat) & products.getSub_sub_category().equals(sub_sub_cat)){



                answer_list.add(products);
            }


        }


        return answer_list;


    }

    public List<Sale> getsales()
    {
        return  this.sales_list;
    }

    public void make_stock_report()
    {
        Report r=new Report(Report.Subject.stock_report,ID_Generator.getInstance().Get_ID());
        r.Fill_Me(this.products_list,null,null);
    }

    public void make_prices_report()
    {
        Report r=new Report(Report.Subject.prices_report,ID_Generator.getInstance().Get_ID());
        r.Fill_Me(null,null,this.get_every_product());
    }


    public void make_sales_report()
    {
        Report r=new Report(Report.Subject.sales_report,ID_Generator.getInstance().Get_ID());
        r.Fill_Me(this.products_list,this.sales_list,null);
    }

    public void make_defective_report()
    {
        Report r=new Report(Report.Subject.defective_items_report,ID_Generator.getInstance().Get_ID());
        r.Fill_Me(null,null,this.get_every_product());
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

}
