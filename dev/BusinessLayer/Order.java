package BusinessLayer;

import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Contract contract;
    private final List<Pair<Product,Integer>> itemAmounts;
    private final List<Pair<Product,Integer>> postDiscountPrices;
    private final ContactPerson contactPerson;
    private int totalPrice;
    private final LocalDateTime orderDate;
    private final LocalDateTime arrivalDate;


    public Order(Contract contract, ContactPerson contactPerson, LocalDateTime arrivalDate) {
        this.contract = contract;
        this.itemAmounts = new ArrayList<>();
        this.postDiscountPrices = new ArrayList<>();
        this.contactPerson = contactPerson;
        this.totalPrice = 0;
        this.orderDate = LocalDateTime.now();
        this.arrivalDate = arrivalDate;
    }
    public void AddProduct(Product item, int amount, int price){
        itemAmounts.add(new Pair<>(item,amount));
        postDiscountPrices.add(new Pair<>(item,price));
        totalPrice+=price;

    }
}
