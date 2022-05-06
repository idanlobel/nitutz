package src.transportationsModule.BusinessLogic;

import java.util.Dictionary;

public class TransportProductsDocument {

    String id;
    String[] destinationId;
    Dictionary<String,Integer> products;

    public TransportProductsDocument(String id, String[] destinationId, Dictionary<String, Integer> products){
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
}
