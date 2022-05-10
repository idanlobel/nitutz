package Data_layer;

import java.sql.*;

public class Product_DAO {

    private String table_name="Product";
    private String connection_string;


    public Product_DAO(String connection_string)
    {
        this.connection_string=connection_string;
        create_table(connection_string);
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
                + " id integer PRIMARY KEY,\n"
                + " name text,\n"
                + " location text,\n"
                + " cost_price double,\n"
                + " sell_price double,\n"
                + " broken boolean,\n"
                + " expire_date text,\n"
                + " delivery_date text,\n"
                + " sell_date text,\n"
                + " sold boolean\n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {

        }

    }

    public void insert(int id,String name,String location, Double cost_price,Double sell_price,String expire,boolean broken, String delivery_date,String sold_date, boolean sold){
        String sql = "INSERT INTO Product(id, name, location, cost_price, sell_price, broken, expire_date, delivery_date, sell_date, sold) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3,location);
            pstmt.setDouble(4,cost_price);
            pstmt.setDouble(5,sell_price);
            pstmt.setString(6,expire);
            pstmt.setBoolean(7,broken);

            pstmt.setString(8,delivery_date);
            pstmt.setString(9,sold_date);
            pstmt.setBoolean(10,sold);

            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }
    public void delete_product(int id)
    {
        String sql = "DELETE FROM "+table_name+" WHERE id = ?";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void update_product_broken_state(int id,boolean state)
    {
        String sql="UPDATE "+table_name+" SET broken=? WHERE id=?";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1,state);
            pstmt.setInt(2,id);

            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

}
