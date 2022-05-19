package SuppliersModule.SuppliersBusinessLayer.Products;

public class PeriodicProduct extends Product {
    int amount;
    public PeriodicProduct(int id,int amount) {
        super(id);
        this.amount=amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
