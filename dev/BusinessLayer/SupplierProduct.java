package BusinessLayer;

public class SupplierProduct extends Product{
    private int supplierId;
    private int price;
    public SupplierProduct(int supplierId, int price, int id) {
        super(id);
        this.supplierId=supplierId;
        this.price=price;
    }
    public int getSupplierId() {
        return supplierId;
    }

    public int getPrice() {
        return price;
    }
}
