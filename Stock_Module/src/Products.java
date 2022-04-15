import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Products {
    private List<Product> product_list;
    private int quantity;
    private int shelf_quantity;
    private int storage_quantity;
    private String manufactorer;
    private String category;
    private int sold_quantity;
    private int min_quantity;
    private Dictionary<LocalDate,Double> prices_history;
    private int ID;
    private String name;
    private Double current_sell_price;
    private Double percentage;


    public Products(int id, String name, int quantity,Double cost_price,Double sell_price,LocalDate expiry,String manufactorer,String category)
    {
        this.product_list=new ArrayList<>();
        this.ID=id;
        this.name=name;
        this.update_quantity(quantity,cost_price,expiry);
        this.storage_quantity=quantity;
        this.shelf_quantity=0;
        this.quantity=shelf_quantity+storage_quantity;
        this.manufactorer=manufactorer;
        this.category=category;
        this.prices_history=new Hashtable<>();
        this.prices_history.put(LocalDate.now(),sell_price);
        this.current_sell_price=sell_price;
        this.percentage=0.0;
        this.min_quantity=10;

    }
    public int getQuantity() {
        return quantity;
    }
    public  int getMin_quantity()
    {
        return this.min_quantity;
    }
    public int getID()
    {
        return this.ID;
    }
    public List<Product> getProduct_list(){return this.product_list;}

    public void update_quantity(int number,double cost_price,LocalDate expiry)
    {
        for(int i=0;i<number;i++)
        {
            this.product_list.add(new Product(this.name,LocalDate.now(),cost_price,current_sell_price,expiry));
        }
        this.storage_quantity=this.storage_quantity+number;
        this.quantity=this.shelf_quantity+storage_quantity;

    }

    public void update_prices(double new_sell_price)
    {
        for (Product product:this.product_list) {
            product.set_sell_price(new_sell_price);

        }
    }

    public void Record_sale(double precentage,LocalDate start_date)
    {
        Double price_after_sale=this.current_sell_price-(this.current_sell_price*precentage);
        this.current_sell_price=price_after_sale;
        this.prices_history.put(start_date,price_after_sale);
        this.percentage=this.percentage+percentage;
        update_prices(price_after_sale);
    }


    public void sale_is_over(double percentage)
    {
        Double price_before_sale=this.current_sell_price/(1.0-percentage);
        this.current_sell_price=price_before_sale;
        this.prices_history.put(LocalDate.now(),price_before_sale);
        this.percentage=this.percentage-percentage;
        update_prices(price_before_sale);
    }

    public Double getsellprice()
    {
        return this.current_sell_price;
    }

    public Double get_by_date(LocalDate date)
    {
        return this.prices_history.get(date);
    }
}
