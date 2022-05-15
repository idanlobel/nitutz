package src.TransportationsAndWorkersModule.Dal.Workers;

import java.sql.*;

public class DatabaseManager {

    private static DatabaseManager instance = null;
    private void createWorkersModuleTables() throws Exception {
        Connection connection = null;
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE if not exists \"workers\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL,\n" +
                    "\t\"name\"\tTEXT NOT NULL,\n" +
                    "\t\"password\"\tTEXT NOT NULL,\n" +
                    "\t\"email\"\tTEXT NOT NULL,\n" +
                    "\t\"bank_id\"\tINTEGER NOT NULL,\n" +
                    "\t\"bank_branch\"\tINTEGER NOT NULL,\n" +
                    "\t\"salary\"\tINTEGER NOT NULL,\n" +
                    "\t\"rec_date\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"workerJobs\" (\n" +
                    "\t\"worker_id\"\tINTEGER NOT NULL,\n" +
                    "\t\"job\"\tTEXT NOT NULL\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"workerPresence\" (\n" +
                    "\t\"present\"\tINTEGER NOT NULL,\n" +
                    "\t\"day\"\tINTEGER NOT NULL,\n" +
                    "\t\"shift_type\"\tINTEGER NOT NULL,\n" +
                    "\t\"worker_id\"\tINTEGER NOT NULL\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"shiftWorkerBook\" (\n" +
                    "\t\"week_id\"\tINTEGER NOT NULL,\n" +
                    "\t\"shift_type\"\tINTEGER NOT NULL,\n" +
                    "\t\"shift_day\"\tINTEGER NOT NULL,\n" +
                    "\t\"worker_id\"\tINTEGER NOT NULL,\n" +
                    "\t\"job\"\tTEXT\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"jobs\" (\n" +
                    "\t\"job\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"job\")\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"weeklySchedule\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ");");
            stmt.execute("CREATE TABLE if not exists \"driversData\" (\n" +
                    "\t\"id\"\tINTEGER,\n" +
                    "\t\"license\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\")\n" +
                    ");");
            connection.commit();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
    }
    private DatabaseManager() throws Exception {
        createWorkersModuleTables();
    }
    public static DatabaseManager getInstance() throws Exception {
        if (instance==null)instance = new DatabaseManager();
        return instance;
    }

    public Connection connect() throws Exception {
        Connection conn = null;
        try {
            // db parameters

            String url = "jdbc:sqlite:superLee.db";
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
