package Data_layer;

import busniess_layer.ProductPrice;
import busniess_layer.Sale;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

public class SalesHistoryDAO {
    private String table_name="SalesHistory";
    private String connection_string;
    private WeakHashMap<Integer, Sale> salesHistoryMap;

    public SalesHistoryDAO(String connection_string)
    {
        this.connection_string=connection_string;
        create_table(connection_string);
        salesHistoryMap=new WeakHashMap<>();
    }



    public List<Sale> getSalesHistory(long catalog_number){
        String sql = "SELECT * FROM "+table_name+ "WHERE catalog_number="+catalog_number;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            LinkedList<Sale> sales_history=new LinkedList<>();
            while (res.next()) {
                int idForRes = res.getInt(0);
                sales_history.add(DAL_controller.getInstance().getSale_table().getSale(idForRes));
            }
            return sales_history;
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
                + " sale_id integer PRIMARY KEY,\n"
                + " catalog_number Long, \n"
                + "  FOREIGN KEY (catalog_number) REFERENCES Products(catalog_number), \n"
                + " Foreign KEY (sale_id) REFERENCES Sale(id) \n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {

        }

    }

    public void insert(int sale_id, long catalog_number){
        String sql = "INSERT INTO SalesHistory(sale_id,catalog_number) VALUES(?,?)";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sale_id);
            pstmt.setLong(2, catalog_number);
            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }
}