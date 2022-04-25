package Service;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class TransportForm {

    String id;
    String date;
    String departureTime;
    String driverid;
    String truckLicensePlateId;
    String source;
    List<String> destinations;
    List<TransportProductsDocument> transportProductsDocuments;
    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDriverid() {
        return driverid;
    }

    public String getTruckLicensePlateId() {
        return truckLicensePlateId;
    }

    public String getSource() {
        return source;
    }

    public List<String> getDestinations() {
        return destinations;
    }



    public TransportForm() {
        this.destinations = new ArrayList<>();
        this.transportProductsDocuments= new ArrayList<TransportProductsDocument>();
    }

    public List<TransportProductsDocument> getTransportProductsDocuments() {
        return transportProductsDocuments;
    }
}
