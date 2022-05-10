package Data_layer;

import java.sql.*;

public class Report_DAO {

    private String table_name="Report";
    private String connection_string;


    public Report_DAO(String connection_string)
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
