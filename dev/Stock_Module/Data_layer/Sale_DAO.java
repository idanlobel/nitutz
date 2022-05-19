package Stock_Module.Data_layer;


import Stock_Module.busniess_layer.Sale;

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
        String sql = "SELECT * FROM "+table_name+ " WHERE id="+id;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            Integer id_=Integer.parseInt(res.getObject(1).toString());
            Double percentage=Double.parseDouble(res.getObject(2).toString());
            String start_date=(res.getObject(3).toString());
            String end_date=(res.getObject(4).toString());
            String reason=(res.getObject(5).toString());

            Sale sale=new Sale(id_,percentage,start_date,end_date,reason,this);
            stmt.close();
            conn.close();
            return sale;
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
                + " id integer PRIMARY KEY AUTOINCREMENT,\n"
                + " percentage double,\n"
                + " start_date text,\n"
                + " end_date text,\n"
                + " reason text\n"
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
        String query = "select seq from sqlite_sequence WHERE name = 'Sale'";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            int current_id=res.getInt(1);
            conn.close();
            return current_id;

        } catch (SQLException e) {

        }
        return -1;

    }

    public void insert(double percentage,String start_date, String end_date,String reason){
        String sql = "INSERT INTO Sale( percentage, start_date, end_date, reason) VALUES(?,?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1, percentage);
            pstmt.setString(2,start_date);
            pstmt.setString(3,end_date);
            pstmt.setString(4,reason);


            pstmt.executeUpdate();
           pstmt.close();
            conn.close();
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
            conn.close();
        } catch (SQLException e) {

        }
    }

    public int check_if_exists(double percentage, String start_date, String end_date, String reason) {
        String sql = "SELECT id FROM "+table_name+ " WHERE percentage="+percentage +" AND start_date= '"+start_date+"' AND end_date= '"+end_date+"' AND reason= '"+reason+"'";


        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if(res.next()) {
                Integer id_ = Integer.parseInt(res.getObject(1).toString());
                stmt.close();
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
}
