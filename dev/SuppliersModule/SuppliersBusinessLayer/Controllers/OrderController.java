package SuppliersModule.SuppliersBusinessLayer.Controllers;

import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.OrderDAO;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Order;
import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class OrderController {
    private final OrderDAO orderDAO = new OrderDAO();
    private final Hashtable<Integer, Order> orderHistory;
    private final HashMap<Integer, Order> toDeliverOrders;
    private int orderIdTracker;

    public OrderController(){
        orderHistory = new Hashtable<>();
        toDeliverOrders = new HashMap<>();
        orderIdTracker=orderDAO.getIdTracking();
        List<Order> orderList = orderDAO.getAllOrders();
        for (Order order : orderList) {
            orderHistory.put(order.getId(), order);
        }
    }
    public void orderProduct(Contract contract,int id,int amount){
        Order order;
        int companyNumber=contract.getCompanyNumber();
        SupplierProduct supplierProduct=contract.getProduct(id);
        if(!toDeliverOrders.containsKey(companyNumber)) {
            order = new Order(orderIdTracker, contract.getCompanyNumber(),
                    contract.getOrderingCP(), LocalDate.now().plusDays(1)); //TODO: PLACEHOLDER. CALC ARRIVAL DATE??
            orderIdTracker++;
            toDeliverOrders.put(companyNumber, order);
            order.AddProduct(supplierProduct, amount, supplierProduct.getPrice(), contract.getDiscount(id,amount),contract.getGeneralDiscounts());
        }
        else{
            toDeliverOrders.get(companyNumber).AddProduct(supplierProduct,amount,supplierProduct.getPrice(),contract.getDiscount(id,amount),contract.getGeneralDiscounts());
        }
    }
    public Order getOrder(int orderId) throws Exception {
        if(!orderHistory.containsKey(orderId) && !orderDAO.exists(orderId)) {
            throw new IllegalArgumentException("USER ERROR: Order with id "+orderId+" not in system");
        }
        else return orderHistory.get(orderId);
    }
    public List<Order> getOrderList(int companyNum) throws Exception {
        ArrayList<Order> acc= new ArrayList<>();
        /*for(Order order:orderDAO.getAllOrders())
            if(order.getSupplyCompanyNumber()==companyNum)
                acc.add(order);*/
        for(Order order:orderHistory.values())
            if(order.getSupplyCompanyNumber()==companyNum)
                acc.add(order);
        return acc;
    }
    public void populateOrderDatabase(){
        try {
            List<Order> orderList = orderDAO.getAllOrders();
            for (Order order : orderList) {
                orderHistory.put(order.getId(), order);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
