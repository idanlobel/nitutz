package Service;

import businessLogic.Site;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class TransportProductsDocument {

    String id;
    String destination;
    Dictionary<String, Integer> products;


    public TransportProductsDocument() {
        products=new Hashtable<String, Integer>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }


    public Dictionary<String, Integer> getProducts() {
        return products;
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
