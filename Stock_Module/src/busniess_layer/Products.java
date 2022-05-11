package busniess_layer;

import busniess_layer.Product;

import java.time.LocalDate;
import java.util.*;

public class Products {
    private List<Product> product_list;
    private int quantity;
    private int shelf_quantity;
    private int storage_quantity;
    private String manufactorer;
    private String main_category;
    private String sub_category;
    private String sub_sub_category;
    private int sold_quantity;
    private int min_quantity;
    private List<ProductPrice> prices_history;
    private List<Integer> sales_history;
    private long catalog_number;
    private String name;
    private Double current_sell_price;
    private Double overall_sale_percentage;


    public Products(long catalog_number, String name, int quantity,Double cost_price,Double sell_price,String expiry,String manufactorer,String category,String sub_category,String sub_sub_category)
    {
        this.product_list=new ArrayList<>();
        this.catalog_number=catalog_number;
        this.name=name;

        this.storage_quantity=0;
        this.shelf_quantity=0;
        this.quantity=shelf_quantity+storage_quantity;
        this.manufactorer=manufactorer;
        this.main_category=category;
        this.prices_history=new LinkedList<>();
        ProductPrice originalPrice=new ProductPrice(this.catalog_number,this.current_sell_price,LocalDate.now().toString(),null);
        this.prices_history.add(originalPrice);
        this.current_sell_price=sell_price;
        this.overall_sale_percentage=0.0;
        this.min_quantity=10;
        this.sub_category=sub_category;
        this.sub_sub_category=sub_sub_category;
        this.sales_history=new ArrayList<>();
        this.update_quantity(quantity,cost_price,expiry);


    }
    public int getQuantity() {
        return quantity;
    }
    public  int getMin_quantity()
    {
        return this.min_quantity;
    }
    public long getCatalog_number()
    {
        return this.catalog_number;
    }
    public List<Product> getProduct_list(){return this.product_list;}



    public List<Product> update_quantity(int number,double cost_price,String expiry)
    {
        List<Product> new_items=new ArrayList<>();
        for(int i=0;i<number;i++)
        {
            Product new_product=new Product (this.name,LocalDate.now().toString(),cost_price,current_sell_price,expiry,ID_Generator.getInstance().Get_ID());
            new_items.add(new_product);
            this.product_list.add(new_product);

        }

        this.storage_quantity=this.storage_quantity+number;
        this.quantity=this.shelf_quantity+storage_quantity;
        return new_items;

    }

    public void update_prices(double new_sell_price)
    {
        for (Product product:this.product_list) {
            product.set_sell_price(new_sell_price);

        }
    }

    public void Record_sale(double precentage,String start_date,int sale_id)
    {
        Double price_after_sale=this.current_sell_price-(this.current_sell_price*precentage);
        this.current_sell_price=price_after_sale;
        //pls update the previous price end date to date.now
        ProductPrice current_price=new ProductPrice(this.catalog_number,this.current_sell_price,start_date,null);
        this.prices_history.add(current_price);
        this.overall_sale_percentage=this.overall_sale_percentage+precentage;
        update_prices(price_after_sale);
        this.sales_history.add(sale_id);
    }


    public void sale_is_over(double percentage)
    {
        Double price_before_sale=this.current_sell_price/(1.0-percentage);
        this.current_sell_price=price_before_sale;
        ProductPrice lastPrice=this.prices_history.get(this.prices_history.size()-1);
        lastPrice.setEnd_date(LocalDate.now().toString());
        ProductPrice newPrice=new ProductPrice(this.catalog_number,this.current_sell_price,LocalDate.now().toString(),null);
        this.overall_sale_percentage=this.overall_sale_percentage-percentage;
        update_prices(price_before_sale);
    }

    public Double getsellprice()
    {
        return this.current_sell_price;
    }


    public String getName(){return this.name;}
    public String getManufactorer()
    {
        return  this.manufactorer;
    }
    public int getShelf_quantity()
    {
        return  this.shelf_quantity;
    }

    public int getStorage_quantity() {
        return storage_quantity;
    }

    public int get_store_quantity()
    {
        return this.shelf_quantity+this.storage_quantity;
    }

    public List<Integer> getSales_history()
    {
        return this.sales_history;
    }

    public String getMain_category()
    {
        return this.main_category;
    }

    public String getSub_category()
    {
        return this.sub_category;
    }


    public String getSub_sub_category()
    {
        return this.sub_sub_category;
    }

    public List<Integer> getsaleshistory()
    {
        return this.sales_history;
    }

    public void set_min_quantity(int new_min)
    {
        this.min_quantity=new_min;
    }

    public boolean set_broken_item(int id)
    {
        boolean found=false;

        for(Product p:this.product_list)
        {
            if(p.get_id()==id) {
                p.set_is_broken();
                found = true;
            }
        }
        return found;
    }
    public void update_quantity_after_removing_items()
    {
        this.storage_quantity=this.product_list.size();
        this.quantity=this.shelf_quantity+this.storage_quantity;
    }

    public void remove_product(List<Product> items_to_remove) {
        for(Product p:items_to_remove)
        {
            if(this.product_list.contains(p))
            {
                this.product_list.remove(p);
            }
        }
    }
}
