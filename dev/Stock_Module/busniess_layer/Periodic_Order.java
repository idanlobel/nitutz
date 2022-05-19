package Stock_Module.busniess_layer;


import Stock_Module.Data_layer.Periodic_Order_DAO;

public class Periodic_Order {
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
    private boolean ordered;
    private Periodic_Order_DAO periodic_order_dao;

    public Periodic_Order(String day_of_week, long catalog_number, int quantity, double cost, String name, String manufactorer , String category, String sub_cat, String sub_sub_cat, Periodic_Order_DAO poda)
    {

        this.day_of_week=day_of_week;
        this.catalog_number=catalog_number;
        this.quantity=quantity;
        this.cost=cost;
        this.name=name;
        this.manufactorer=manufactorer;
        this.category=category;
        this.sub_cat=sub_cat;
        this.sub_sub_cat=sub_sub_cat;
        ordered=false;
        this.periodic_order_dao=poda;



        int id_=poda.check_if_exists(day_of_week,catalog_number,quantity,manufactorer,category,sub_cat,sub_sub_cat,name,cost);
        if(id_==-1)
        {
            poda.insert(day_of_week,catalog_number,quantity,manufactorer,category,sub_cat,sub_sub_cat,name,cost);
            this.ID=poda.get_current_id();
            poda.insert_in_map(ID,this);
        }
        else
        {
            this.ID=id_;
        }



    }


    public Periodic_Order(int id,String day_of_week, long catalog_number, int quantity, double cost, String name, String manufactorer , String category, String sub_cat, String sub_sub_cat, Periodic_Order_DAO poda)
    {

        this.day_of_week=day_of_week;
        this.catalog_number=catalog_number;
        this.quantity=quantity;
        this.cost=cost;
        this.name=name;
        this.manufactorer=manufactorer;
        this.category=category;
        this.sub_cat=sub_cat;
        this.sub_sub_cat=sub_sub_cat;
        ordered=false;
        this.periodic_order_dao=poda;
        this.ID=id;


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
        this.periodic_order_dao.update_periodic_order_quantity(ID,new_quantitiy);
    }

    public void setDay_of_week(String day)
    {
        this.day_of_week=day;
        this.periodic_order_dao.update_periodic_order_day(ID,day);
    }
    public void set_ordered_to_true()
    {
        this.ordered=true;
    }

    public void reset()
    {
        this.ordered=false;
    }

    public boolean has_ordered()
    {
        return this.ordered;
    }
}
