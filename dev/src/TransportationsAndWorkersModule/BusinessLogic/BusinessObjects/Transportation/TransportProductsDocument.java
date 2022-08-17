package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation;

import java.util.HashMap;

public class TransportProductsDocument {

    String id;
    String destinationId;
    HashMap<String,Integer> products;
    public TransportProductsDocument(){

        this.products = new HashMap<>();
    }
    public TransportProductsDocument(String id, String destinationId, HashMap<String, Integer> products){
        this.id = id;
        this.destinationId = destinationId;
        this.products = products;
    }


    @Override
    public String toString() {
        return "TransportProductsDocument{" +
                "id='" + id + '\'' +
                ", destination=" + destinationId +
                ", products=" + products +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }
}
