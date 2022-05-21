package Stock_Module.Data_layer;

import Stock_Module.busniess_layer.ProductPrice;

import java.sql.*;
import java.util.WeakHashMap;

public class PricesHistoryDAO {
    private String table_name="ProductPrice";
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
        String sql = "SELECT * FROM "+table_name+ " WHERE id="+id;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            Integer id_=Integer.parseInt(res.getObject(1).toString());
            Long catalog_number=Long.parseLong(res.getObject(2).toString());
            String start_date=res.getObject(3).toString();
            String end_date=res.getObject(4).toString();
            Double price=Double.parseDouble(res.getObject(5).toString());
            ProductPrice productprice=new ProductPrice(id_,catalog_number,price,start_date,end_date);


stmt.close();
            conn.close();
            return productprice;

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


        String sql = "CREATE TABLE IF NOT EXISTS ProductPrice (\n"
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " catalog_number Long, \n"
                + " start_date text, \n"
                + " end_date text, \n"
                + "price double NOT Null, \n"
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

    public int get_current_id()
    {
        String query = "select seq from sqlite_sequence WHERE name = 'ProductPrice'";

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

    public void insert(long catalog_number,String start_date, String end_date,double price){
        String sql = "INSERT INTO ProductPrice(catalog_number,start_date,end_date,price) VALUES(?,?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, catalog_number);
            pstmt.setString(2, start_date);
            pstmt.setString(3,end_date);
            pstmt.setDouble(4,price);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
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
            pstmt.close();
            conn.close();
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
            pstmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    public int check_if_exists(long catalog_number, String start_date, String end_date, double price) {
        String sql = "SELECT id FROM "+table_name+ " WHERE catalog_number= "+catalog_number +" AND end_date IS NULL"+" AND price= "+price;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if(res.next()) {
                Integer id_ = Integer.parseInt(res.getObject(1).toString());
                conn.close();
                return id_;
            }
            stmt.close();
            conn.close();

            return -1;



        } catch (SQLException e) {
            return -1;
        }
    }

    public void insert_in_map(int id, ProductPrice productPrice) {
        this.pricesMap.put(id,productPrice);
    }
}
