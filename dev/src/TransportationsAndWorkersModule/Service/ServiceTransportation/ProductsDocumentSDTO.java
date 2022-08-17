package src.TransportationsAndWorkersModule.Service.ServiceTransportation;

import java.util.HashMap;

public class ProductsDocumentSDTO {

    String id;
    String destinationId; //site name and site region
    HashMap<String, Integer> products;


    public ProductsDocumentSDTO() {
        products=new HashMap<String, Integer>();
    }

    public ProductsDocumentSDTO(String id, String destinationId, Object[][] items) {
        this.id = id;
        this.destinationId = destinationId;
        this.products = new HashMap<>();
        for (int i  =0;i<items.length;i++){
            products.put((String) items[i][0], (Integer) items[i][1]);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestinationId() {
        return destinationId;
    }


    public HashMap<String, Integer> getProducts() {
        return products;
    }


    @Override
    public String toString() {
        return "TransportProductsDocument{" +
                "id='" + id + '\'' +
                ", destination=" + destinationId +
                ", products=" + products +
                '}';
    }
}
