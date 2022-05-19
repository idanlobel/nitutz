package Stock_Module.Data_layer;


import Stock_Module.busniess_layer.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class Product_DAO {

    private String table_name="Product";
    private String connection_string;
    private WeakHashMap<Integer, Product> productMap;

    public Product_DAO(String connection_string)
    {
        this.connection_string=connection_string;
        create_table(connection_string);
        productMap=new WeakHashMap<>();
    }

    public Product getProduct(int id){
        Product p= productMap.get(id);
        if(p==null)
            p=readProduct(id);
        return p;
    }

    private Product readProduct(int id) {
        String sql = "SELECT * FROM "+table_name+ " WHERE ="+id;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            return res.getObject(1,Product.class);
        } catch (SQLException e) {
            return null;
        }
    }

    public Connection connect() {
        Connection conn = null;
        try {


            // create a connection to the database
            conn = DriverManager.getConnection(connection_string);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

        String sql = "CREATE TABLE IF NOT EXISTS Product (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " name text,\n"
                + " location text,\n"
                + " cost_price double,\n"
                + " sell_price double,\n"
                + " broken boolean,\n"
                + " expire_date text,\n"
                + " delivery_date text,\n"
                + " sell_date text,\n"
                + " sold boolean,\n"
                + " catalog_number Long,\n"
                + "  FOREIGN KEY (catalog_number) REFERENCES Products(catalog_number)\n"
                + ");";




        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {

        }

    }

    public void insert(String name,String location, Double cost_price,Double sell_price,String expire,boolean broken, String delivery_date,String sold_date, boolean sold, long catalog_number){
        String sql = "INSERT INTO Product(name, location, cost_price, sell_price, broken, expire_date, delivery_date, sell_date, sold, catalog_number) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2,location);
            pstmt.setDouble(3,cost_price);
            pstmt.setDouble(4,sell_price);
            pstmt.setBoolean(5,broken);
            pstmt.setString(6,expire);

            pstmt.setString(7,delivery_date);
            pstmt.setString(8,sold_date);
            pstmt.setBoolean(9,sold);
            pstmt.setLong(10,catalog_number);

            pstmt.executeUpdate();
    pstmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    public int get_current_id()
    {
        String query = "select seq from sqlite_sequence WHERE name = 'Product'";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            int current_id=res.getInt(1);
            stmt.close();
            conn.close();
            return current_id;

        } catch (SQLException e) {

        }

        return -1;

    }
    public void delete_product(int id)
    {
        String sql = "DELETE FROM "+table_name+" WHERE id = ?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    public void update_product_broken_state(int id,boolean state)
    {
        String sql="UPDATE "+table_name+" SET broken=? WHERE id=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1,state);
            pstmt.setInt(2,id);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

        } catch (SQLException e) {

        }
    }


    public void update_product_sell_price(int id, Double new_sell_price) {
        String sql="UPDATE "+table_name+" SET sell_price=? WHERE id=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1,new_sell_price);
            pstmt.setInt(2,id);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }


    public List<Product> getProductlist(long catalog_number) {
        String sql = "SELECT * FROM "+table_name+ " WHERE catalog_number = "+catalog_number;

        List<Product> list_of_product=new ArrayList<>();
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next())
            {
                Integer id_=Integer.parseInt(res.getObject(1).toString());
                String name=res.getObject(2).toString();
                String location=res.getObject(3).toString();
                Double cost_price=Double.parseDouble(res.getObject(4).toString());
                Double sell_price=Double.parseDouble(res.getObject(5).toString());
                boolean broken=Boolean.parseBoolean(res.getObject(6).toString());
                String expire_date=res.getObject(7).toString();
                String delivry_date=res.getObject(8).toString();
                boolean sold=Boolean.parseBoolean(res.getObject(10).toString());
                list_of_product.add(new Product(id_,name,delivry_date,cost_price,sell_price,expire_date,location,broken,sold,catalog_number));


            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            return null;
        }
        return list_of_product;
    }
}
