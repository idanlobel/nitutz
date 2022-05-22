package SuppliersModule.SupplierDataAccessLayer;

import java.sql.*;

public class DatabaseManager {

    private static DatabaseManager instance = null;
    private static  String url = "jdbc:sqlite:suppliersDB.db";
    //"jdbc:sqlite:"+System.getProperty("user.dir")+"\\dev\\suppliersDB.db";
    //"jdbc:sqlite:suppliersDB.db";
    private void createSuppliersModuleTables() throws Exception {
        Connection connection = null;
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE if not exists \"ContactPeople\" (\n" +
                    "\t\"FullName\"\tTEXT NOT NULL,\n" +
                    "\t\"Email\"\tTEXT NOT NULL,\n" +
                    "\t\"CellNumber\"\tTEXT NOT NULL,\n" +
                    "\t\"CompanyNumber\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"CompanyNumber\",\"FullName\"),\n" +
                    "\tFOREIGN KEY(\"CompanyNumber\") REFERENCES \"Suppliers\"(\"CompanyNumber\")\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"Contracts\" (\n" +
                    "\t\"CompanyNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"DeliveryDays\"\tTEXT,\n" +
                    "\t\"ContractType\"\tINTEGER NOT NULL,\n" +
                    "\t\"DeliveredBySupplier\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"CompanyNumber\"),\n" +
                    "\tFOREIGN KEY(\"CompanyNumber\") REFERENCES \"Suppliers\"(\"CompanyNumber\")\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"Orders\" (\n" +
                    "\t\"Id\"\tINTEGER NOT NULL,\n" +
                    "\t\"OrderDate\"\tTEXT NOT NULL,\n" +
                    "\t\"ArrivalDate\"\tTEXT,\n" +
                    "\t\"ContactPerson\"\tTEXT NOT NULL,\n" +
                    "\t\"CompanyNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"OrderedItems\"\tTEXT,\n" +
                    "\t\"TotalPrice\"\tINTEGER NOT NULL,\n" +
                    "\t\"TotalItemAmount\"\tINTEGER NOT NULL,\n" +
                    "\t\"TotalDiscount\"\tINTEGER,\n" +
                    "\tPRIMARY KEY(\"Id\"),\n" +
                    "\tFOREIGN KEY(\"CompanyNumber\") REFERENCES \"Suppliers\"(\"CompanyNumber\")\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"SupplierProducts\" (\n" +
                    "\t\"CompanyNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"CatalogNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"SupplierCatalogNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"Discounts\"\tTEXT,\n" +
                    "\t\"Price\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"CompanyNumber\",\"CatalogNumber\"),\n" +
                    "\tFOREIGN KEY(\"CompanyNumber\") REFERENCES \"Suppliers\"(\"CompanyNumber\")\n" +
                    ")");
            stmt.execute("CREATE TABLE if not exists \"Suppliers\" (\n" +
                    "\t\"CompanyNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"BankNumber\"\tINTEGER NOT NULL,\n" +
                    "\t\"Name\"\tTEXT NOT NULL,\n" +
                    "\t\"Address\"\tTEXT NOT NULL,\n" +
                    "\t\"OrderingCP\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"CompanyNumber\")\n" +
                    ")");
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
        createSuppliersModuleTables();
    }
    public static DatabaseManager getInstance() throws Exception {
        if (instance==null)instance = new DatabaseManager();
        return instance;
    }

    public Connection connect() throws Exception {
        Connection conn = null;
        try {
            // db parameters
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false); //todo delete this?
            return conn;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public static Connection connect2() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url); // connecting to our database
            System.out.println("Connected!");
        } catch (ClassNotFoundException | SQLException e ) {
            // TODO Auto-generated catch block
            System.out.println(e+"");
        }

        return con;
    }

    public static void ChangeURL(String url2){//this is for testing convenience so we can create another db for testings
        url=url2;
    }
}

