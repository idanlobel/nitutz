package SuppliersModule.SuppliersBusinessLayer.Products;

public class SupplierProduct extends Product {
    private final int supplierId;
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

    public void setPrice(int price) {
        this.price = price;
    }
}
