package busniess_layer;

public class Order {
    private int ID;
    private String day_of_week;
    private long catalog_number;
    private int quantity;
    private Double cost;
    private String name;
    private String manufactorer;
    private String category;
    private String sub_cat;
    private String sub_sub_cat;

    public Order(String day_of_week,long catalog_number, int quantity, double cost,String name, String manufactorer ,String category, String sub_cat,String sub_sub_cat)
    {
        this.ID=ID_Generator.getInstance().Get_ID();
        this.day_of_week=day_of_week;
        this.catalog_number=catalog_number;
        this.quantity=quantity;
        this.cost=cost;
        this.name=name;
        this.manufactorer=manufactorer;
        this.category=category;
        this.sub_cat=sub_cat;
        this.sub_sub_cat=sub_sub_cat;
    }

    public String getDay_of_week()
    {
        return this.day_of_week;
    }
    public long getCatalog_number()
    {
        return this.catalog_number;
    }
    public int getQuantity()
    {
        return this.quantity;
    }
    public Double getCost()
    {
        return this.cost;
    }
    public String getName()
    {
        return this.name;
    }
    public String getManufactorer()
    {
        return this.manufactorer;
    }
    public String getCategory() {
        return this.category;
    }
    public String getSub_cat()
    {
        return this.sub_cat;
    }
    public String getSub_sub_cat()
    {
        return sub_sub_cat;
    }

    public int getID()
    {
        return ID;
    }
    public void set_quantity(int new_quantitiy)
    {
        this.quantity=new_quantitiy;
    }

    public void setDay_of_week(String day)
    {
        this.day_of_week=day;
    }
}
