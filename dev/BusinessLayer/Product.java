package BusinessLayer;

public class Product {
    private int id;
    public Product(int id){
        this.id=id;

    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "ID=" + id;
    }
}
