package src.TransportationsAndWorkersModule.Dal.Transportation;

import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.LicenseType;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.Truck;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;


import java.sql.*;
import java.util.*;


public class TrucksRep {

    Map<String,Truck> trucksCache;
    //connection sqlite connection
    //Map<,> trucksIdentityMap

    public TrucksRep() {
        trucksCache = new HashMap<>();
    //new map and get connection.
    }

    public void addTruck(Truck truck){
        //add truck also to DB
        try{
        insert(truck.getNumber(),truck.getLicenseType().toString(),truck.getLicenseNumber(),truck.getModel(),truck.getWeight(),truck.getMaxWeight());
        trucksCache.put(truck.getNumber(),truck);
        System.out.println(truck.getLicenseNumber() + " added to trucks cache");}
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Truck getTruck(String truckid){
        if(!trucksCache.containsKey(truckid)){
            selectAll();
        }
        if(trucksCache.containsKey(truckid)){
            return trucksCache.get(truckid);
        }
        return null;
    }

    public List<Truck> getAllTrucks() {
        // TODO: 06/05/2022
        //get all trucks also from DB
        this.selectAll();
        return new ArrayList<>(trucksCache.values());
    }

    //getByLicenseType
    public void insert(String number,String licenseType,String licenseNumber,String model,String weight,String maxWeight) {

        String sql = "INSERT INTO trucks(number,licenseType, licenseNumber,model,weight,maxWeight) VALUES(?,?,?,?,?,?)";
        Connection conn=null;
        try{
             conn = DatabaseManager.getInstance().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, number);
            pstmt.setString(2, licenseType);
            pstmt.setString(3, licenseNumber);
            pstmt.setString(4, model);
            pstmt.setString(5, weight);
            pstmt.setString(6, maxWeight);
            pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void selectAll(){
        String sql = "SELECT * FROM trucks";
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
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
                Truck tr =new Truck(rs.getString("number"), LicenseType.valueOf(rs.getString("licenseType")),rs.getString("licenseNumber"),
                        rs.getString("model"),rs.getString("weight"),rs.getString("maxWeight"));
                if(!trucksCache.containsKey(tr.getNumber()))
                    trucksCache.put(tr.getNumber(),tr);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
