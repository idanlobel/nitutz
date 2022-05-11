package Data_layer;

import busniess_layer.Periodic_Order;
import busniess_layer.Products;
import busniess_layer.Report;

import java.sql.*;
import java.util.WeakHashMap;

public class Report_DAO {

    private String table_name="Report";
    private String connection_string;
    private WeakHashMap<Integer, Report> reportsMap;

    public Report_DAO(String connection_string)
    {
        this.connection_string=connection_string;
        create_table(connection_string);
        reportsMap=new WeakHashMap<>();
    }

    public Report getReport(int id){
        Report r= reportsMap.get(id);
        if(r==null)
            r=readReport(id);
        return r;
    }

    private Report readReport(int id) {
        String sql = "SELECT * FROM "+table_name+ "WHERE id="+id;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            return res.getObject(1,Report.class);
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


        String sql = "CREATE TABLE IF NOT EXISTS Report (\n"
                + " id integer PRIMARY KEY,\n"
                + " subject text\n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void insert(int id,String subject){
        String sql = "INSERT INTO Report(id, subject) VALUES(?,?)";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, subject);


            pstmt.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void delete_report(int id)
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
