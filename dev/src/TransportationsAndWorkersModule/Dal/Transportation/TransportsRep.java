package src.TransportationsAndWorkersModule.Dal.Transportation;

import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.TransportForm;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.TransportProductsDocument;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.TransportStatus;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import java.sql.*;
import java.util.*;


public class TransportsRep {



    List<TransportForm> transportFormsCache;
    List<String> datesList;

    //connection sqlite connection
    //Map<,> trucksIdentityMap

    public TransportsRep() {
        transportFormsCache = new LinkedList<>();
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
    public void addTransportFormWithoutDB(TransportForm transportForm){
        //todo add to DB

        try{
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
                formsIds.add(tf.toString());
        }
        return  formsIds;
    }

    public TransportForm getTransportFormById(String id) {
        //todo change to pull db

        for (TransportForm tf: transportFormsCache) {
            if(tf.getId().equals(id))
                return tf;
        }
        selectByIdToCache(id);
        for (TransportForm tf: transportFormsCache) {
            if(tf.getId().equals(id))
                return tf;
        }

        return null;
    }

    public void insert(TransportForm transportForm){
        String sql = "INSERT INTO transportForms(id,date, departureTime,driverId,source,truckLicenceNumber,transportStatus,transportWeight) VALUES(?,?,?,?,?,?,?,?)";
//                + " id text PRIMARY KEY,\n"
//                + " date text NOT NULL,\n"
//                + " departureTime text NOT NULL,\n"
//                + " driverId text NOT NULL,\n"
//                + " source text NOT NULL,\n"
//                + " truckLicenceNumber text NOT NULL\n"
        Connection conn = null;
        try{
            conn = DatabaseManager.getInstance().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, transportForm.getId());
            pstmt.setString(2, transportForm.getDate());
            pstmt.setString(3, transportForm.getDepartureTime());
            pstmt.setString(4, transportForm.getDriverId());
            pstmt.setString(5, transportForm.getSource());
            pstmt.setString(6, transportForm.getTruckLicenceNumber());
            pstmt.setString(7, transportForm.getStatus().toString());
            pstmt.setString(8, transportForm.getTransportWeight());
            pstmt.executeUpdate();
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
        for (TransportProductsDocument tpd:transportForm.getTransportProductsDocuments()) {
            String sql2 = "INSERT INTO transportDocuments(id,destinationId, TransportPortId) VALUES(?,?,?)";
//            + " id text PRIMARY KEY,\n"
//                    + " destinationId text NOT NULL,"
//                    + " TransportPortId text NOT NULL,"
//                    + " FOREIGN KEY (TransportPortId) REFERENCES transportForms(id)"
            try{
                conn = DatabaseManager.getInstance().connect();
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

    }
    public void update(TransportForm transportForm){
        String sql = "UPDATE transportForms " +
                "SET date = ?, departureTime = ? ,driverId = ? ,source= ? ,truckLicenceNumber= ? ,transportStatus= ? ,transportWeight= ? " +
                "WHERE id = ?";

        try{
            Connection conn = DatabaseManager.getInstance().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, transportForm.getDate());
            pstmt.setString(2, transportForm.getDepartureTime());
            pstmt.setString(3, transportForm.getDriverId());
            pstmt.setString(4, transportForm.getSource());
            pstmt.setString(5, transportForm.getTruckLicenceNumber());
            pstmt.setString(6, transportForm.getStatus().toString());
            pstmt.setString(7, transportForm.getTransportWeight());
            pstmt.setString(8, transportForm.getId());

            pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (TransportProductsDocument tpd:transportForm.getTransportProductsDocuments()) {
            String sql2 = "UPDATE transportDocuments SET destinationId = ?, TransportPortId = ?  WHERE id = ?";
//            + " id text PRIMARY KEY,\n"
//                    + " destinationId text NOT NULL,"
//                    + " TransportPortId text NOT NULL,"
//                    + " FOREIGN KEY (TransportPortId) REFERENCES transportForms(id)"
            try{
                Connection conn = DatabaseManager.getInstance().connect();
                PreparedStatement pstmt = conn.prepareStatement(sql2);
                pstmt.setString(3, tpd.getId());
                pstmt.setString(1, tpd.getDestinationId());
                pstmt.setString(2, transportForm.getId());
                pstmt.executeUpdate();
                for (Map.Entry<String, Integer> entry:tpd.getProducts().entrySet()) {
                    String sql3 = "UPDATE DocumentProducts SET quantity = ?  WHERE productName = ? AND  TransportDocumentId = ?";
//                    + " productName text NOT NULL,\n"
//                            + " quantity text NOT NULL,"
//                            + " TransportDocumentId text NOT NULL,"
                    try{
                        pstmt = conn.prepareStatement(sql3);
                        pstmt.setString(2, entry.getKey());
                        pstmt.setString(1, entry.getValue().toString());
                        pstmt.setString(3, tpd.getId());

                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void selectByIdToCache(String id){
        //TransportForm toRet= new TransportForm();

        String date="";
        String departureTime="";
        String driverId="";
        String source="";
        List<String> destinations=new ArrayList<>();
        String truckLicenceNumber="";
        TransportStatus transportStatus=TransportStatus.PreTransported;
        String transportWeight= "";
        List<TransportProductsDocument> transportProductsDocuments= new ArrayList<>();

        String sql = "SELECT *\n" +
                "  FROM transportForms\n" +
                "       JOIN\n" +
                "       transportDocuments JOIN DocumentProducts ON DocumentProducts.TransportDocumentId=transportDocuments.id AND\n" +
                "       transportDocuments.TransportPortId = transportForms.id\n" +
                "       WHERE transportForms.id = ?";

        try {
            Connection conn = DatabaseManager.getInstance().connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs    = pstmt.executeQuery();

            // loop through the result set
            TransportProductsDocument transportProductsDocument=new TransportProductsDocument();
            while (rs.next()) {

                date=rs.getString("date");
                departureTime=rs.getString("departureTime");
                driverId=rs.getString("driverId");
                source=rs.getString("source");
                truckLicenceNumber=rs.getString("truckLicenceNumber");
                transportWeight=rs.getString("transportWeight");
                transportStatus=TransportStatus.valueOf(rs.getString("transportStatus"));
                if(!destinations.contains(rs.getString("destinationId")))
                    destinations.add(rs.getString("destinationId"));
                transportProductsDocument.getProducts()
                        .put(rs.getString("destinationId"),Integer.parseInt(rs.getString("quantity")));
                if(!transportProductsDocuments.contains(transportProductsDocument))
                    transportProductsDocuments.add(transportProductsDocument);
            }
            TransportForm ts=new TransportForm(id,date,departureTime,driverId,source,destinations,truckLicenceNumber,transportStatus,transportWeight);
            for (TransportProductsDocument trdoc:transportProductsDocuments
                 ) {
                ts.addDocument(trdoc.getId(),trdoc.getDestinationId(),trdoc.getProducts());
            }
            addTransportFormWithoutDB(ts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
