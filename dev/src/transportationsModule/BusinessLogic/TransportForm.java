package src.transportationsModule.BusinessLogic;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class TransportForm {

    String id;
    String date;
    String departureTime;
    String driverId;
    String source;
    TransportStatus status;
    String transportWeight;
    List<String> destinations;
    String truckLicenceNumber;
    List<TransportProductsDocument> transportProductsDocuments;

    public TransportStatus getStatus() {
        return status;
    }
    public String getDate() {
        return date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getSource() {
        return source;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public String getTruckLicenceNumber() {
        return truckLicenceNumber;
    }

    public List<TransportProductsDocument> getTransportProductsDocuments() {
        return transportProductsDocuments;
    }

    public TransportForm(String id, String date, String departureTime, String driverId, String source, List<String> destinations, String truckLicenceNumber,TransportStatus transportStatus,String transportWeight) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.driverId = driverId;
        this.source = source;
        this.destinations = destinations;
        this.truckLicenceNumber =truckLicenceNumber;
        transportProductsDocuments = new ArrayList<>();
        this.status=transportStatus;
        this.transportWeight=transportWeight;
    }

    public String getId() {
        return id;
    }



    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "TransportForm{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", driver=" + driverId +
                ", source=" + source +
                ", destinations=" + destinations +
                ", status=" + status.toString() +
                '}';
    }

    public void addDocument(String id, String destinationId, HashMap<String, Integer> products) {
        transportProductsDocuments.add(new TransportProductsDocument(id,destinationId,products));
    }

    public void update(String id, String toChangeField, String newVal) {
        switch (toChangeField) {

            case "id": {
                this.id = newVal;
                break;
            }
            case "date": {
                date = newVal;
                break;
            }
            case "departureTime": {
                departureTime = newVal;
                break;
            }
            case "driver": {
                driverId = newVal;
                break;
            }
            case "transportStatus":{
                status=TransportStatus.valueOf(newVal);
                break;
            }
            case "transportWeight":{
                transportWeight=newVal;
                break;
            }
//todo add support to
//        this.source = source;
//        this.destinations = destinations;
//        this.truckLicenceNumber =truckLicenceNumber;
//        transportProductsDocuments = new ArrayList<>();



        }

    }

    public String getTransportWeight() {
        return transportWeight;
    }
}
