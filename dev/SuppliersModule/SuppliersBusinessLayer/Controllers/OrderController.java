package SuppliersModule.SuppliersBusinessLayer.Controllers;

import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.OrderDAO;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Order;
import SuppliersModule.SuppliersBusinessLayer.Products.PeriodicProduct;
import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class OrderController {
    private final OrderDAO orderDAO = new OrderDAO();
    private final Hashtable<Integer, Order> orderHistory;
    private final HashMap<Integer, Order> toDeliverOrders;

    private HashMap<Integer, Collection<PeriodicProduct>> periodicProducts; //0-sunday, 6-saturday
    private final boolean[] daysHandled;

    private List<PeriodicProduct> scheduledPeriodicOrder; //preload an order a day early, to prevent it changing less than a day before processing

    private int orderIdTracker;

    public OrderController(){
        daysHandled=new boolean[] {false,false,false,false,false,false,false};
        try {
            periodicProducts = orderDAO.getAllPeriodicProducts();
        }
        catch (Exception e) {
            System.out.println("Error while loading periodic products: " + e.getMessage());
            periodicProducts=new HashMap<>();
              for (int i = 0; i < 7; i++) {
                  periodicProducts.put(i, new ArrayList<>());
              }
        }
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
    public void handlePeriodicOrder(ContractController contractController){ //handles tomorrow's orders
        int weekDay = LocalDate.now().getDayOfWeek().getValue();
        if (daysHandled[weekDay])
            throw new RuntimeException("ERROR: weekday already had its periodic orders handled");

        for (PeriodicProduct periodicProduct : scheduledPeriodicOrder)
            orderProduct(contractController.periodicOrder(periodicProduct, weekDay), periodicProduct.getId(), periodicProduct.getAmount());
        daysHandled[weekDay] = true;
        setScheduledOrder();

    }
    public void AddPeriodicProduct(int id,int amount,int weekDay){
        PeriodicProduct periodicProduct=new PeriodicProduct(id,amount);
        periodicProducts.get(weekDay).add(periodicProduct);
        orderDAO.createPeriodicProduct(periodicProduct,weekDay);
    }
    public void RemovePeriodicProduct(int id,int weekDay){
        for(PeriodicProduct periodicProduct:periodicProducts.get(weekDay)){
            if(periodicProduct.getId()==id){
                periodicProducts.get(weekDay).remove(periodicProduct);
                orderDAO.deletePeriodicProduct(periodicProduct.getId(),weekDay);
                break;
            }
        }
    }
    public void ChangePeriodicProductAmount(int id,int newAmount,int weekDay){
        for(PeriodicProduct periodicProduct:periodicProducts.get(weekDay)){
            if(periodicProduct.getId()==id){
                periodicProduct.setAmount(newAmount);
                orderDAO.updatePeriodicProduct(periodicProduct,weekDay);
                break;
            }
        }
    }
    public void endWeek(){
        for(boolean day: daysHandled)
            day=false;
    }
    public void setScheduledOrder(){ //deep copies tomorrow's periodic orders
        scheduledPeriodicOrder =new ArrayList<>();
        for(PeriodicProduct periodicProduct:periodicProducts.get(LocalDate.now().getDayOfWeek().getValue()+1))
            scheduledPeriodicOrder.add(new PeriodicProduct(periodicProduct.getId(),periodicProduct.getAmount()));
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
            for (int i = 0; i < 7; i++) {
                periodicProducts.put(i, new ArrayList<>());
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
