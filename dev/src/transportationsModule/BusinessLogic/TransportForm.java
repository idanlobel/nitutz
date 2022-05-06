package src.transportationsModule.BusinessLogic;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class TransportForm {

    String id;
    String date;
    String departureTime;
    String driverId;
    String[] source;
    List<String[]> destinations;
    String truckLicenceNumber;
    List<TransportProductsDocument> transportProductsDocuments;

    public TransportForm(String id, String date, String departureTime, String driverId, String[] source, List<String[]> destinations, String truckLicenceNumber) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.driverId = driverId;
        this.source = source;
        this.destinations = destinations;
        this.truckLicenceNumber =truckLicenceNumber;
        transportProductsDocuments = new ArrayList<>();
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
                '}';
    }

    public void addDocument(String id, String[] destinationId, Dictionary<String, Integer> products) {
        transportProductsDocuments.add(new TransportProductsDocument(id,destinationId,products));
    }
}
