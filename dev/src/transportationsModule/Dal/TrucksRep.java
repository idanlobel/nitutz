package src.transportationsModule.Dal;

import src.transportationsModule.BusinessLogic.Truck;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static src.transportationsModule.Dal.Connect.getConnection;

public class TrucksRep {

    List<Truck> trucksCache;
    //connection sqlite connection
    //Map<,> trucksIdentityMap

    public TrucksRep() {
        trucksCache = new LinkedList<Truck>();
        //new map and get connection.
    }

    public void addTruck(Truck truck){
        //add truck also to DB
        try{
        insert(truck.getNumber(),truck.getLicenseType().toString(),truck.getLicenseNumber(),truck.getModel(),truck.getWeight(),truck.getMaxWeight());
        trucksCache.add(truck);
        System.out.println(truck.getLicenseNumber() + " added to trucks cache");}
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Truck getTruck(String licenseNumber){
        return null;
    }

    public List<Truck> getAllTrucks() {
        // TODO: 06/05/2022
        //get all trucks also from DB
        this.selectAll();
        return trucksCache;
    }

    //getByLicenseType
    public void insert(String number,String licenseType,String licenseNumber,String model,String weight,String maxWeight) {

        String sql = "INSERT INTO trucks(number,licenseType, licenseNumber,model,weight,maxWeight) VALUES(?,?,?,?,?,?)";

        try{
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, number);
            pstmt.setString(2, licenseType);
            pstmt.setString(3, licenseNumber);
            pstmt.setString(4, model);
            pstmt.setString(5, weight);
            pstmt.setString(6, maxWeight);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAll(){
        String sql = "SELECT * FROM trucks";

        try {
            Connection conn = getConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                //    String number;
                //    LicenseType licenseType;
                //    String licenseNumber;
                //    String model;
                //    String weight;
                //    String maxWeight;
                System.out.println(rs.getString("number") +  "\t" +
                        rs.getString("licenseType") + "\t" +
                        rs.getString("licenseNumber") + "\t" +
                        rs.getString("model") + "\t" +
                        rs.getString("weight") + "\t" +
                        rs.getString("maxWeight"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
