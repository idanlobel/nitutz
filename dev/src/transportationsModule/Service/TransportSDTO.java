package src.transportationsModule.Service;


import java.util.ArrayList;
import java.util.List;

public class TransportSDTO {

    String id;
    String date;
    String departureTime;
    String driverId;
    String truckLicensePlateId;
    String source;
    List<String> destinations;
    List<ProductsDocumentSDTO> productsDocumentSDTOS;


    public TransportSDTO() {
        this.destinations = new ArrayList<>();
        this.productsDocumentSDTOS = new ArrayList<ProductsDocumentSDTO>();
    }

    public TransportSDTO(String id, String date, String departureTime, String driverId, String truckLicensePlateId,
                         String source, List<String> destinations, List<ProductsDocumentSDTO> productsDocumentSDTOS) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.driverId = driverId;
        this.truckLicensePlateId = truckLicensePlateId;
        this.source = source;
        this.destinations = destinations;
        this.productsDocumentSDTOS = productsDocumentSDTOS;
    }

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
        return driverId;
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




    public List<ProductsDocumentSDTO> getTransportProductsDocuments() {
        return productsDocumentSDTOS;
    }
}
