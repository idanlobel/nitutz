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
    private LocalDate expire_date;
    private LocalDate delivery_date;
    private LocalDate sell_date;
    private boolean sold;

    public Product(String name,LocalDate delivery_date,Double cost_price,Double sell_price,LocalDate expire)
    {
        this.name=name;
        this.delivery_date=delivery_date;
        this.expire_date=expire;
        this.sell_price=sell_price;
        this.cost_price=cost_price;
        this.location=Location.storage;
        this.broken=false;
        this.sold=false;
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
        return this.expire_date;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isBroken()
    {
        return this.broken;
    }



}
