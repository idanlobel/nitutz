package Data_layer;

import busniess_layer.Report;
import busniess_layer.Sale;

import java.sql.*;
import java.util.WeakHashMap;

public class Sale_DAO {

    private String table_name="Sale";
    private String connection_string;
    private WeakHashMap<Integer, Sale> salesMap;

    public Sale_DAO(String connection_string)
    {
        this.connection_string=connection_string;
        create_table(connection_string);
        salesMap=new WeakHashMap<>();
    }
    public Sale getSale(int id){
        Sale s= salesMap.get(id);
        if(s==null)
            s=readSale(id);
        return s;
    }

    private Sale readSale(int id) {
        String sql = "SELECT * FROM "+table_name+ "WHERE id="+id;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            return (Sale) res;
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

            }
        }
        return conn;
    }


    private void create_table(String connection_string) {


        String sql = "CREATE TABLE IF NOT EXISTS Sale (\n"
                + " id integer PRIMARY KEY,\n"
                + " percentage double,\n"
                + " start_date text,\n"
                + " end_date text,\n"
                + " reason text\n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {

        }

    }

    public void insert(int id,double percentage,String start_date, String end_date,String reason){
        String sql = "INSERT INTO Sale(id, percentage, start_date, end_date, reason) VALUES(?,?,?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setDouble(2, percentage);
            pstmt.setString(3,start_date);
            pstmt.setString(4,end_date);
            pstmt.setString(5,reason);


            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void delete_sale(int id)
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

}
