package SuppliersModule.Controllers;

import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.OrderDAO;
import SuppliersModule.SuppliersBusinessLayer.Order;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class OrderController {
    private OrderDAO orderDAO = new OrderDAO();
    private final Hashtable<Integer, Order> orderHistory;


    public OrderController(){
        orderHistory = new Hashtable<>();
        List<Order> orderList = orderDAO.getAllOrders();
        for (Order order : orderList) {
            orderHistory.put(order.getId(), order);
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
