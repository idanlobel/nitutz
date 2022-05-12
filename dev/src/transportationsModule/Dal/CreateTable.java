package src.transportationsModule.Dal;

import src.transportationsModule.BusinessLogic.ContactPerson;
import src.transportationsModule.BusinessLogic.Garbage.Region;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;

public class CreateTable {

    public static void createNewTrucksTable() {
        //    String number;
        //    LicenseType licenseType;
        //    String licenseNumber;
        //    String model;
        //    String weight;
        //    String maxWeight;
        // SQLite connection string
        String url = "jdbc:sqlite:SuperDuper.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS trucks (\n"
                + " number text PRIMARY KEY,\n"
                + " licenseType text NOT NULL,\n"
                + " licenseNumber text NOT NULL,\n"
                + " model text NOT NULL,\n"
                + " weight text NOT NULL,\n"
                + " maxWeight text NOT NULL\n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createNewTransportFormsTable() {
        //    String id;
        //    String date;
        //    String departureTime;
        //    String driverId;
        //    String source;
        //    List<String> destinations; //todo create sub table with id
        //    String truckLicenceNumber;
        //    List<TransportProductsDocument> transportProductsDocuments; //todo create sub table with id
        String url = "jdbc:sqlite:SuperDuper.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS transportForms (\n"
                + " id text PRIMARY KEY,\n"
                + " date text NOT NULL,\n"
                + " departureTime text NOT NULL,\n"
                + " driverId text NOT NULL,\n"
                + " source text NOT NULL,\n"
                + " truckLicenceNumber text NOT NULL,\n"
                + " transportStatus text NOT NULL,\n"
                + " transportWeight text \n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createNewTransportDocumentTable() {
//        String id;
//        String destinationId;
//        Dictionary<String,Integer> products; //todo create sub table with id
        String url = "jdbc:sqlite:SuperDuper.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS transportDocuments (\n"
                + " id text PRIMARY KEY,\n"
                + " destinationId text NOT NULL,"
                + " TransportPortId text NOT NULL,"
                + " FOREIGN KEY (TransportPortId) REFERENCES transportForms(id)"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createNewDocumentProductsTable() {
//        String id;
//        String destinationId;
//        Dictionary<String,Integer> products; //todo create sub table with id
        String url = "jdbc:sqlite:SuperDuper.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS DocumentProducts (\n"
                + " productName text NOT NULL,\n"
                + " quantity text NOT NULL,"
                + " TransportDocumentId text NOT NULL,"
                + " PRIMARY KEY (productName,TransportDocumentId),\n"
                + " FOREIGN KEY (TransportDocumentId) REFERENCES transportDocuments(id)"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createSitesTable() {
//        public String name;
//        public String address;
//        public ContactPerson contactPerson;
//        public Region region;
//        public String siteType;
        String url = "jdbc:sqlite:SuperDuper.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS sites (\n"
                + " name text PRIMARY KEY,\n"
                + " address text NOT NULL,\n"
                + " contactPersonName text NOT NULL,\n"
                + " contactPersonPhone text NOT NULL,\n"
                + " region text NOT NULL\n"
                + ");";

        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createNewTrucksTable();
    }

}