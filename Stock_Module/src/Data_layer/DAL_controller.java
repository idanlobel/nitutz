package Data_layer;

public  class DAL_controller {

    protected String table_name;
    protected String connection_string;
    private Products_DAO products_table;
    private Product_DAO product_table;
    private Report_DAO report_table;
    private Sale_DAO sale_table;
    private Periodic_Order_DAO periodic_order_table;


    public DAL_controller()
    {

       // this.connection_string="jdbc:sqlite:"+System.getProperty("user.dir")+"\\..\\dev\\database.db";
        this.connection_string="jdbc:sqlite:"+System.getProperty("user.dir")+"\\database.db";
       // connect();
        this.products_table=new Products_DAO(connection_string);
        this.product_table=new Product_DAO(connection_string);
        this.report_table=new Report_DAO(connection_string);
        sale_table=new Sale_DAO(connection_string);
        periodic_order_table=new Periodic_Order_DAO(connection_string);
    }



//    public void connect() {
//        Connection conn = null;
//        try {
//
//
//            // create a connection to the database
//            conn = DriverManager.getConnection(connection_string);
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }

    public void insert_product(int id,String name,String location, double cost,double sell_price , String expiry, boolean borken, String delivery_date, String sold_date, boolean is_sold)
    {
        this.product_table.insert(id,name,location,cost,sell_price,expiry,borken,delivery_date,sold_date,is_sold);
    }




    public void insert_sale(int id, Double percentage, String start_date, String end_date, String reason) {
        this.sale_table.insert(id,percentage,start_date,end_date,reason);
    }


    public void insert_products(long catalog_number, String name, int quantity,Double sell_price,String manufactorer,String category,String sub_category,String sub_sub_category,int min_quantity) {
        this.products_table.insert(catalog_number,name,quantity,sell_price,manufactorer,category,sub_category,sub_sub_category,min_quantity);
    }



    public void insert_periodic_order(int id, String day, long products_catalog_number, int quantity, String manufactorer, String category, String sub_cat, String sub_sub_cat, String name, Double cost) {
        this.periodic_order_table.insert(id,day,products_catalog_number,quantity,manufactorer,category,sub_cat,sub_sub_cat,name,cost);
    }

    public void insert_report(int id, String subject) {
        this.report_table.insert(id,subject);
    }

    public void remove_product(int id)
    {
        this.product_table.delete_product(id);
    }

    public void remove_sale(int id)
    {
        this.sale_table.delete_sale(id);
    }

    public void remove_products(int id)
    {
        this.products_table.delete_products(id);
    }

    public void remove_periodic_order(int id)
    {
        this.periodic_order_table.delete_periodic_order(id);
    }

    public void remove_report(int id)
    {
        this.report_table.delete_report(id);
    }

    public void update_product_broken_state(int id,boolean broken_state)
    {
        this.product_table.update_product_broken_state(id,broken_state);
    }

    public void update_periodic_order_quantity(int id,int quantity)
    {
        this.periodic_order_table.update_periodic_order_quantity(id,quantity);
    }

    public void update_periodic_order_day(int id, String day) {
        this.periodic_order_table.update_periodic_order_day(id,day);
    }
}
