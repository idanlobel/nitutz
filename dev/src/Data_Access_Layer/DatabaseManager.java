package src.Data_Access_Layer;

import java.sql.*;

public class DatabaseManager {
    public static Connection connect() throws Exception {
        Connection conn = null;
        try {
            // db parameters
            //"jdbc:sqlite:C:\\Users\\amiha\\Documents\\adss_group_j\\dev\\superlee.db"
            String url = "jdbc:sqlite:C:\\Users\\Ori\\Desktop\\uni\\nitutz\\adss_group_j\\dev\\superlee.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            return conn;
            //Statement statement = conn.createStatement();
            //ResultSet rs = statement.executeQuery("select * from workers");
            //System.out.println("ID\t Name\t\t");
            //while ( rs.next() ) {
            //    int id = rs.getInt("id");
            //    String name = rs.getString("name");
             //   System.out.println(id + "\t " + name );
            //}
            //System.out.println("Connection to SQLite has been established.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
