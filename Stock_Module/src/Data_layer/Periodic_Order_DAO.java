package Data_layer;

import java.sql.*;

public class Periodic_Order_DAO {


    private String table_name="Order";
    private String connection_string;


    public Periodic_Order_DAO(String connection_string)
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


        String sql = "CREATE TABLE IF NOT EXISTS Order (\n"
                + " id integer PRIMARY KEY,\n"
                + " day_of_week text,\n"
                + " catalog_number long,\n"
                + " quantity integer,\n"
                + " manufacturer text,\n"
                + " main_category text,\n"
                + " sub_category text,\n"
                + " sub_sub_category text,\n"
                + " name text,\n"
                + " cost double,\n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insert(int id, String day_of_week, long catalog_number,int quantity,String manufactorer,String category,String sub_category,String sub_sub_category,String name,Double cost) {
        String sql = "INSERT INTO Order(id, day_of_week, catalog_number, quantity, manufactorer, category, sub_category, sub_sub_category, name, cost) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, day_of_week);
            pstmt.setLong(3,catalog_number);
            pstmt.setInt(4,quantity);
            pstmt.setString(5,manufactorer);
            pstmt.setString(6,category);
            pstmt.setString(7,sub_category);
            pstmt.setString(8,sub_sub_category);
            pstmt.setString(9,name);
            pstmt.setDouble(10,cost);


            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
