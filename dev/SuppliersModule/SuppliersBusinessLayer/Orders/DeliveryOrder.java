package SuppliersModule.SuppliersBusinessLayer.Orders;

import java.time.LocalDate;

public class DeliveryOrder extends Order {
    public DeliveryOrder(int id, int supplyCompanyNumber, String contactPerson, LocalDate arrivalDate) {
        super(id, supplyCompanyNumber, contactPerson, arrivalDate);
    }
    @Override
    public boolean isDeliveryOrder(){
        return true;
    }
}
