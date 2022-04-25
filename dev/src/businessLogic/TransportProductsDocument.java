package businessLogic;

import java.util.Dictionary;

public class TransportProductsDocument {

    String id;
    Site destination;
    Dictionary<String,Integer> products;

    public TransportProductsDocument(String id, Site destination, Dictionary<String, Integer> products){
        this.id = id;
        this.destination = destination;
        this.products = products;
    }


    @Override
    public String toString() {
        return "TransportProductsDocument{" +
                "id='" + id + '\'' +
                ", destination=" + destination +
                ", products=" + products +
                '}';
    }
}
