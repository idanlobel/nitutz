package Stock_Module.Data_layer;


import Stock_Module.busniess_layer.Product;
import Stock_Module.busniess_layer.Products;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

public class Products_DAO {

    private String table_name="Products";
    private String connection_string;
    private WeakHashMap<Long, Products> productsMap;

    public Products_DAO(String connection_string)
    {
        this.connection_string=connection_string;
        connect();
        create_table(connection_string);
        productsMap=new WeakHashMap<>();
    }

    public Products getProducts(long catalog_number){
        Products p= productsMap.get(catalog_number);
        if(p==null)
            p=readProducts(catalog_number);
        return p;
    }

    private Products readProducts(long catalog_number) {
        String sql = "SELECT * FROM "+table_name+ " WHERE catalog_number="+catalog_number;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            return res.getObject(1,Products.class);
        } catch (SQLException e) {
            return null;
        }
    }

    private List<Product> readAllProductInstances(long catalog_number) {
        String sql = "SELECT * FROM Product WHERE catalog_number="+catalog_number;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            List<Product> myProductList=new LinkedList<>();
            ResultSetMetaData meta = res.getMetaData();
            while(res.next()){
                for(int i=1;i<=meta.getColumnCount();i++)
                    myProductList.add(res.getObject(i,Product.class));
            }
            stmt.close();
            conn.close();
            return myProductList;
        } catch (SQLException e) {
            return null;
        }
    }
    public Connection connect() {
        Connection conn = null;
        try {


            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(connection_string);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }


    private void create_table(String connection_string) {


        String sql = "CREATE TABLE IF NOT EXISTS Products (\n"
                + " catalog_number Long PRIMARY KEY,\n"
                + " quantity integer,\n"
                + " shelf_quantity integer,\n"
                + " storage_quantity integer,\n"
                + " manufacturer text,\n"
                + " main_category text,\n"
                + " sub_category text,\n"
                + " sub_sub_category text,\n"
                + " sold_quantity integer,\n"
                + " min_quantity integer,\n"
                + " name text,\n"
                + " current_sell_price double,\n"
                + " overall_sale_percentage double\n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {

        }

    }

    public void insert(long catalog_number, String name, int quantity,Double sell_price,String manufactorer,String category,String sub_category,String sub_sub_category,int min_quantity) {
        String sql = "INSERT INTO Products(catalog_number, quantity, shelf_quantity, storage_quantity, manufacturer, main_category, sub_category, sub_sub_category, sold_quantity, min_quantity, name, current_sell_price, overall_sale_percentage) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try{
            Connection conn =   DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, catalog_number);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3,0);
            pstmt.setInt(4,quantity);
            pstmt.setString(5,manufactorer);
            pstmt.setString(6,category);
            pstmt.setString(7,sub_category);
            pstmt.setString(8,sub_sub_category);
            pstmt.setInt(9,0);
            pstmt.setInt(10,min_quantity);
            pstmt.setString(11,name);
            pstmt.setDouble(12,sell_price);
            pstmt.setDouble(13,0);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete_products(long catalog_number)
    {
        String sql = "DELETE FROM "+table_name+" WHERE id = ?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,catalog_number);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update_products_quantity(int quantity,int storage_quantity,long catalog_number) {
        String sql="UPDATE "+table_name+" SET quantity=?,storage_quantity=? WHERE catalog_number=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,quantity);
            pstmt.setInt(2,storage_quantity);
            pstmt.setLong(3,catalog_number);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void update_products_sell_price(long products_catalog_number, Double new_sell_price) {
        String sql="UPDATE "+table_name+" SET current_sell_price=? WHERE catalog_number=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1,new_sell_price);

            pstmt.setLong(2,products_catalog_number);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update_products_min_quantity(long products_catalog_number, int new_min_quantity) {
        String sql="UPDATE "+table_name+" SET min_quantity=? WHERE catalog_number=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,new_min_quantity);

            pstmt.setLong(2,products_catalog_number);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update_products_overall_sale_percentage(long catalog_number, Double overall_sale_percentage) {
        String sql="UPDATE "+table_name+" SET overall_sale_percentage=? WHERE catalog_number=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1,overall_sale_percentage);

            pstmt.setLong(2,catalog_number);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int check_if_exists(long catalog_number) {
        String sql = "SELECT * FROM "+table_name+ " WHERE catalog_number= "+catalog_number ;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if(res.next()) {
                int shelf_quantity=Integer.parseInt(res.getObject(3).toString());
                conn.close();
              return shelf_quantity;
            }
           res.close();
            conn.close();
            return -1;



        } catch (SQLException e) {

        }
        return -1;
    }


}
