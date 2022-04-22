package busniess_layer;

import java.time.LocalDate;
import java.util.List;


public class Product {
    public enum Location {
        shelf, store , storage
    }

    private String name;
    private Location location;
    private Double cost_price;
    private Double sell_price;
    private boolean broken;
    private String expire_date;
    private String delivery_date;
    private String sell_date;
    private boolean sold;
    private int ID;

    public Product(String name,LocalDate delivery_date,Double cost_price,Double sell_price,String expire,int id)
    {
        this.name=name;
        this.delivery_date=delivery_date.toString();
        this.expire_date=expire;
        this.sell_price=sell_price;
        this.cost_price=cost_price;
        this.location=Location.storage;
        this.broken=false;
        this.sold=false;
        this.ID=id;
    }

    public void set_sell_price(double new_sell_price)
    {
     this.sell_price=new_sell_price;
    }
    public  Location getLocation()
    {
        return  this.location;
    }
    public  boolean isSold()
    {
        return this.sold;
    }

    public double getcostprice()
    {
        return this.cost_price;

    }
    public double getsoldprice()
    {
        return this.sell_price;

    }

    public LocalDate getExpire_date()
    {
        return LocalDate.parse(this.expire_date);
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isBroken()
    {
        return this.broken;
    }

    public int get_id()
    {
        return this.ID;
    }

    public void set_is_broken()
    {
        this.broken=true;
    }



}
