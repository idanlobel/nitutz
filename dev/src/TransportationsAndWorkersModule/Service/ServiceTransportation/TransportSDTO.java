package src.TransportationsAndWorkersModule.Service.ServiceTransportation;


import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.TransportStatus;

import java.util.ArrayList;
import java.util.List;

public class TransportSDTO {

    String id;
    String date;
    String departureTime;
    String driverId;
    String truckLicensePlateId;
    String source;
    TransportStatus status;
    String transportWeight;
    List<String> destinations;
    List<ProductsDocumentSDTO> productsDocumentSDTOS;


    public TransportSDTO() {
        this.destinations = new ArrayList<>();
        this.productsDocumentSDTOS = new ArrayList<ProductsDocumentSDTO>();
        transportWeight="";
        status=TransportStatus.PreTransported;
    }

    public TransportSDTO(String id, String date, String departureTime, String driverId, String truckLicensePlateId,
                         String source, List<String> destinations, List<ProductsDocumentSDTO> productsDocumentSDTOS,TransportStatus status,String transportWeight) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.driverId = driverId;
        this.truckLicensePlateId = truckLicensePlateId;
        this.source = source;
        this.destinations = destinations;
        this.productsDocumentSDTOS = productsDocumentSDTOS;
        this.status=status;
        this.transportWeight=transportWeight;
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

    public String getDriverId() {
        return driverId;
    }

    public TransportStatus getStatus() {
        return status;
    }

    public String getTransportWeight() {
        return transportWeight;
    }

    public List<ProductsDocumentSDTO> getProductsDocumentSDTOS() {
        return productsDocumentSDTOS;
    }
}
