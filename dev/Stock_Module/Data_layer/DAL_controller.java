package Stock_Module.Data_layer;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public  class DAL_controller {


    protected String connection_string;
    private Products_DAO products_table;
    private Product_DAO product_table;



    private SalesHistoryDAO sales_history_table;
    private Sale_DAO sale_table;
    private Periodic_Order_DAO periodic_order_table;

    public PricesHistoryDAO getPricesHistoryDAO() {
        return pricesHistoryDAO;
    }

    private PricesHistoryDAO pricesHistoryDAO;
    private static DAL_controller instance=new DAL_controller();

    public Products_DAO getProducts_table() {
        return products_table;
    }

    public Product_DAO getProduct_table() {
        return product_table;
    }
    public SalesHistoryDAO getSales_history_table() {
        return sales_history_table;
    }


    public Sale_DAO getSale_table() {
        return sale_table;
    }

    public Periodic_Order_DAO getPeriodic_order_table() {
        return periodic_order_table;
    }

    private DAL_controller()
    {

       this.connection_string="jdbc:sqlite:suppliersDB.db";
        //"jdbc:sqlite:"+System.getProperty("user.dir")+"\\dev\\database.db";
        //"jdbc:sqlite:database.db"
        this.products_table=new Products_DAO(connection_string);
        this.product_table=new Product_DAO(connection_string);
      //  this.report_table=new Report_DAO(connection_string);
        sale_table=new Sale_DAO(connection_string);
        periodic_order_table=new Periodic_Order_DAO(connection_string);
        pricesHistoryDAO=new PricesHistoryDAO(connection_string);
        sales_history_table=new SalesHistoryDAO(connection_string);
    }
    public static DAL_controller getInstance(){
        return instance;
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

//    public void insert_product(int id,String name,String location, double cost,double sell_price , String expiry, boolean borken, String delivery_date, String sold_date, boolean is_sold,long catalog_number)
//    {
//        this.product_table.insert(id,name,location,cost,sell_price,expiry,borken,delivery_date,sold_date,is_sold,catalog_number);
//    }
//
//
//
//
//    public void insert_sale(int id, Double percentage, String start_date, String end_date, String reason) {
//        this.sale_table.insert(id,percentage,start_date,end_date,reason);
//    }
//
//
//    public void insert_products(long catalog_number, String name, int quantity,Double sell_price,String manufactorer,String category,String sub_category,String sub_sub_category,int min_quantity) {
//        this.products_table.insert(catalog_number,name,quantity,sell_price,manufactorer,category,sub_category,sub_sub_category,min_quantity);
//    }
//
//
//
//    public void insert_periodic_order(String day, long products_catalog_number, int quantity, String manufactorer, String category, String sub_cat, String sub_sub_cat, String name, Double cost) {
//        this.periodic_order_table.insert(day,products_catalog_number,quantity,manufactorer,category,sub_cat,sub_sub_cat,name,cost);
//    }
//
//    public void insert_report(int id, String subject) {
//        this.report_table.insert(id,subject);
//    }

    public void remove_product(int id)
    {
        this.product_table.delete_product(id);
    }

    public void remove_sale(int id)
    {
        this.sale_table.delete_sale(id);
    }

    public void remove_products(long catalog_number)
    {
        this.products_table.delete_products(catalog_number);
    }

    public void remove_periodic_order(int id)
    {
        this.periodic_order_table.delete_periodic_order(id);
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

    public void update_products_quantity(int quantity,int shelf_quantity,long catalog_num)
    {
        this.products_table.update_products_quantity(quantity,shelf_quantity,catalog_num);
    }

    public void update_products_sell_price(long products_catalog_number, Double new_sell_price) {
        this.products_table.update_products_sell_price(products_catalog_number,new_sell_price);
    }

    public void update_product_sell_price(int id, Double new_sell_price) {
        this.product_table.update_product_sell_price(id,new_sell_price);
    }


    public void clear_database() {
        List<String> tables_names=new ArrayList<>();
        tables_names.add("Periodic_Order");
        tables_names.add("ProductPrice");
        tables_names.add("Product");
        tables_names.add("Products");
        tables_names.add("Sale");
        tables_names.add("SalesHistory");
        tables_names.add("sqlite_sequence");

        for(String table:tables_names)
        {      String sql =" DELETE FROM "+table+";";
            try{
                Connection conn = DriverManager.getConnection(connection_string);
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
                stmt.close();
                conn.close();
            } catch (SQLException e) {

            }

        }

    }



}
