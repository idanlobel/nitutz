package src.transportationsModule.BusinessLogic;

import java.util.Dictionary;
import java.util.HashMap;

public class TransportProductsDocument {

    String id;
    String destinationId;
    HashMap<String,Integer> products;

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
}
