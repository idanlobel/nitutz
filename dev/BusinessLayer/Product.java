package BusinessLayer;

public class Product {
    private int supplierId;
    private int buyPrice;
    public int getId() {
        return supplierId;
    }
    public Product(int supplierId,int price, int id){
        buyPrice=price;
        this.supplierId=supplierId;

    }

    public int getBuyPrice() {
        return buyPrice;
    }

    @Override
    public String toString() {
        return "supplierId=" + supplierId +
                ", buyPrice=" + buyPrice;
    }
}
