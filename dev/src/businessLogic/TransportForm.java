package businessLogic;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class TransportForm {

    String id;
    String date;
    String departureTime;
    Driver driver;
    Site source;
    List<Site> destinations;
    Truck truck;
    List<TransportProductsDocument> transportProductsDocuments;

    public TransportForm(String id, String date, String departureTime, Driver driver, Site source, List<Site> destinations,Truck truck) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.driver = driver;
        this.source = source;
        this.destinations = destinations;
        this.truck =truck;
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
                ", driver=" + driver +
                ", source=" + source +
                ", destinations=" + destinations +
                '}';
    }

    public void addDocument(String id, Site destination, Dictionary<String, Integer> products) {
        transportProductsDocuments.add(new TransportProductsDocument(id,destination,products));
    }
}
