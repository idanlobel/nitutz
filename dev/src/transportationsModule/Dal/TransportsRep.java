package src.transportationsModule.Dal;

import src.transportationsModule.BusinessLogic.TransportForm;
import src.transportationsModule.BusinessLogic.TransportProductsDocument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static src.transportationsModule.Dal.Connect.getConnection;

public class TransportsRep {



    List<TransportForm> transportFormsCache;
    Map<String,TransportForm> stringTransportFormMap;
    List<String> datesList;
    //connection sqlite connection
    //Map<,> trucksIdentityMap

    public TransportsRep() {
        transportFormsCache = new LinkedList<>();
        stringTransportFormMap = new HashMap<>();
        datesList=new ArrayList<>();
    }


    public void addTransportForm(TransportForm transportForm){
    //todo add to DB

        try{
        insert(transportForm);
        System.out.println(transportForm.getId() + " added to transports cache");
        transportFormsCache.add(transportForm);}
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public TransportForm getTransportForm(){
        return null;
    }


    public List<String> getTransportFormsbyDate(String date) {
        //todo change to pull db
        List<String> formsIds=new ArrayList<>();
        for (TransportForm tf: transportFormsCache) {
            if(tf.getDate().equals(date))
                formsIds.add(tf.getId());
        }
        return  formsIds;
    }

    public TransportForm getTransportFormById(String id) {
        //todo change to pull db

        for (TransportForm tf: transportFormsCache) {
            if(tf.getId().equals(id))
                return tf;
        }
        return null;
    }

    public void insert(TransportForm transportForm){
        String sql = "INSERT INTO transportForms(id,date, departureTime,driverId,source,truckLicenceNumber) VALUES(?,?,?,?,?,?)";
//                + " id text PRIMARY KEY,\n"
//                + " date text NOT NULL,\n"
//                + " departureTime text NOT NULL,\n"
//                + " driverId text NOT NULL,\n"
//                + " source text NOT NULL,\n"
//                + " truckLicenceNumber text NOT NULL\n"
        try{
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, transportForm.getId());
            pstmt.setString(2, transportForm.getDate());
            pstmt.setString(3, transportForm.getDepartureTime());
            pstmt.setString(4, transportForm.getDriverId());
            pstmt.setString(5, transportForm.getSource());
            pstmt.setString(6, transportForm.getTruckLicenceNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for (TransportProductsDocument tpd:transportForm.getTransportProductsDocuments()) {
            String sql2 = "INSERT INTO transportDocuments(id,destinationId, TransportPortId) VALUES(?,?,?)";
//            + " id text PRIMARY KEY,\n"
//                    + " destinationId text NOT NULL,"
//                    + " TransportPortId text NOT NULL,"
//                    + " FOREIGN KEY (TransportPortId) REFERENCES transportForms(id)"
            try{
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, tpd.getId());
                pstmt.setString(2, tpd.getDestinationId());
                pstmt.setString(3, transportForm.getId());
                pstmt.executeUpdate();
                for (Map.Entry<String, Integer> entry:tpd.getProducts().entrySet()) {
                    String sql3 = "INSERT INTO DocumentProducts(productName,quantity, TransportDocumentId) VALUES(?,?,?)";
//                    + " productName text NOT NULL,\n"
//                            + " quantity text NOT NULL,"
//                            + " TransportDocumentId text NOT NULL,"
                    try{
                        pstmt = conn.prepareStatement(sql3);
                        pstmt.setString(1, entry.getKey());
                        pstmt.setString(2, entry.getValue().toString());
                        pstmt.setString(3, tpd.getId());

                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public void update(TransportForm transportForm){

    }
    public TransportForm get(String id){
        //connection
        // TODO: 10/05/2022  
        //transportFormsCache.add(new TransportForm());
        return null;
    }


}
