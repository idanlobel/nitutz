package Data_layer;

import busniess_layer.Product;
import busniess_layer.ProductPrice;

import java.sql.*;
import java.util.WeakHashMap;

public class PricesHistoryDAO {
    private String table_name="ProductsPrice";
    private String connection_string;
    private WeakHashMap<Integer, ProductPrice> pricesMap;

    public PricesHistoryDAO(String connection_string)
    {
        this.connection_string=connection_string;
        create_table(connection_string);
        pricesMap=new WeakHashMap<>();
    }

    public ProductPrice getPrice(int id){
        ProductPrice p= pricesMap.get(id);
        if(p==null)
            p=readProductPrice(id);
        return p;
    }

    private ProductPrice readProductPrice(int id) {
        String sql = "SELECT * FROM "+table_name+ "WHERE id="+id;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            return res.getObject(1,ProductPrice.class);
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


        String sql = "CREATE TABLE IF NOT EXISTS ProductPrices (\n"
                + " id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
                + " catalog_number Long FOREIGN KEY REFERENCES Products(catalog_number), \n"
                + " start_date text NOT Null, \n"
                + " end_date text, \n"
                + "price double NOT Null, \n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {

        }

    }

    public void insert(long catalog_number,String start_date, String end_date,double price){
        String sql = "INSERT INTO ProductsPrice(catalog_number,start_date,end_date,price) VALUES(?,?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, catalog_number);
            pstmt.setString(2, start_date);
            pstmt.setString(3,end_date);
            pstmt.setDouble(4,price);

            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }
    public void delete_product(int id)
    {
        String sql = "DELETE FROM "+table_name+" WHERE id = ?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void update_end_date(int id,String end_date)
    {
        String sql="UPDATE "+table_name+" SET end_date=? WHERE id=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,end_date);
            pstmt.setInt(2,id);

            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }
}
