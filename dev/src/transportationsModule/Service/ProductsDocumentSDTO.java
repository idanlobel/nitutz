package src.transportationsModule.Service;

import java.util.Dictionary;
import java.util.Hashtable;

public class ProductsDocumentSDTO {

    String id;
    String[] destinationId; //site name and site region
    Dictionary<String, Integer> products;


    public ProductsDocumentSDTO() {
        products=new Hashtable<String, Integer>();
    }

    public ProductsDocumentSDTO(String id, String[] destinationId, Object[][] items) {
        this.id = id;
        this.destinationId = destinationId;
        this.products = new Hashtable<>();
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

    public String[] getDestinationId() {
        return destinationId;
    }


    public Dictionary<String, Integer> getProducts() {
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
