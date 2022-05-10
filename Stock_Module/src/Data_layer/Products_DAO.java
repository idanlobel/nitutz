package Data_layer;
import java.sql.*;

public class Products_DAO {

    private String table_name="Products";
    private String connection_string;


    public Products_DAO(String connection_string)
    {
        this.connection_string=connection_string;
        connect();
        create_table(connection_string);
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update_products_quantity(int quantity,int shelf_quantity,long catalog_number) {
        String sql="UPDATE "+table_name+" SET quantity=?,shelf_quantity=? WHERE catalog_number=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,quantity);
            pstmt.setInt(2,shelf_quantity);
            pstmt.setLong(3,catalog_number);
            pstmt.executeUpdate();
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
