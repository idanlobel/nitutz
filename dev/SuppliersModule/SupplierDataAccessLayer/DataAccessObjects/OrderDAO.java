package SuppliersModule.SupplierDataAccessLayer.DataAccessObjects;

import SuppliersModule.SupplierDataAccessLayer.DatabaseManager;
import SuppliersModule.SuppliersBusinessLayer.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class OrderDAO {
    private boolean readAll = false;
    private static HashMap<Integer, Order> cacheOrders = new HashMap<>();
    private static boolean retry = false;
    public Order get(int id) throws Exception{
        Order order = cacheOrders.get(id);
        if (order != null) return order;
        Connection conn = null;
        try {
            /*List<ContactPerson> contactPersonList = new ArrayList<>();
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs3 = statement.executeQuery("select * from ContactPeople where CompanyNumber = '"+companyNumber+"'");
            while (rs3.next()){
                String cpName = rs3.getString("FullName");
                String email = rs3.getString("Email");
                String cellNumber = rs3.getString("CellNumber");
                contactPersonList.add(new ContactPerson(cpName, email, cellNumber));
            }
            conn.close();*/
            conn = DatabaseManager.getInstance().connect();
            ResultSet rs = conn.createStatement().executeQuery("select * from Orders where Id = '"+id+"'");
            if (rs.next()){
                int companyNumber = rs.getInt("CompanyNumber");
                String contactPersonName = rs.getString("ContactPerson");
                String arrivalDate = rs.getString("ArrivalDate");
                int itemAmount = rs.getInt("TotalItemAmount");
                String itemS = rs.getString("OrderedItems");
                String orderDate = rs.getString("OrderDate");
                int price = rs.getInt("TotalPrice");
                int discount = rs.getInt("TotalDiscount");
                //LocalDate localDate = LocalDate.parse(arrivalDate);
                //order =  new Order(id, companyNumber, contactPersonName, localDate);
                order = new Order(id, orderDate, arrivalDate, contactPersonName, companyNumber, itemS, price, itemAmount, discount);
            }
        } catch (Exception e) {
            if (!retry){retry=true; get(id);}
            else{
                retry=false;
                throw new Exception(e.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        if (order == null)throw new Exception("order doesn't exist");
        cacheOrders.put(order.getId(), order);
        return order;
        //take from the db and insert to the cache then return
    }

    public List<Order> getAllOrders() throws Exception{
        List<Order> list = new ArrayList();
        if (!readAll){
            //read from DB and insert into cache orders;
            Connection conn=null;
            List<Integer> ordersIDS = new LinkedList<>();
            try {
                conn = DatabaseManager.getInstance().connect();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select Id from Orders");
                while ( rs.next() ) {
                    int id = rs.getInt("Id");
                    ordersIDS.add(id);
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    throw new Exception(ex.getMessage());
                }
            }
            for (int i:ordersIDS) {
                Order order = get(i);
                if (!cacheOrders.containsKey(i)) cacheOrders.put(i,order);
            }
            //readAll = true;
        }
        list.addAll(cacheOrders.values());
        return list;
    }
    public void create(Order order) throws Exception {
        if (cacheOrders.containsKey(order.getId())) throw new Exception("supplier already exists");
        Connection conn=null;
        String sql = "INSERT INTO Orders(Id,OrderDate,ArrivalDate,ContactPerson,CompanyNumber,OrderedItems,TotalPrice,TotalItemAmount,TotalDiscount) VALUES(";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            //set order data
            sql+="'"+order.getId()+"','"+order.getOrderDate()+"','"+order.getArrivalDate()+"','"+order.getContactPerson()+
                    "','"+order.getCompanyNumber()+"','"+order.getItemsDetails()+"','"+order.getPrice()+
                    "','"+order.getTotalItemAmount()+ "','"+order.getTotalDiscount()+"');";
            rs.addBatch(sql);
            //set his CPs;
            /*for (ContactPerson contactPerson:supplier.getContactList().values()) {
                String sql2="\nINSERT INTO ContactPeople(FullName,Email,CellNumber,CompanyNumber) VALUES('"
                        +contactPerson.getName()+"','"
                        +contactPerson.getEmail() +"','"
                        +contactPerson.getCellNumber() +"','"
                        +supplier.getCompanyNumber()+"');";
                rs.addBatch(sql2);
            }*/
            //PreparedStatement rs = conn.prepareStatement(sql);
            rs.executeBatch();
            conn.commit();
            cacheOrders.put(order.getId(), order);
        } catch(Exception e) {
            if (!retry){retry=true; create(order);}
            else{
                retry=false;
                throw new Exception(e.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        cacheOrders.put(order.getId(),order);
    }

    public void update(Order order) throws Exception {
        //if (!cacheSuppliers.containsKey(supplier.getCompanyNumber())) throw new Exception("supplier doesn't exist");
        //update to db first
        try {
            delete(order.getId());
            create(order);
        }catch (Exception e){
            System.out.println("failed order update");
            throw new Exception(e.getMessage());
        }
        cacheOrders.put(order.getId(), order);
    }
    public void delete(int id) throws Exception{
        Connection conn=null;
        String sql = "DELETE from Orders where Id = '"+id+"';";
        //String sql2 = "DELETE from ContactPeople where CompanyNumber = '"+cn+"';";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            rs.addBatch(sql);
            //rs.addBatch(sql2);
            rs.executeBatch();
            conn.commit();
            cacheOrders.remove(id);
        } catch(Exception e) {
            if (!retry){retry=true; delete(id);}
            else{
                retry=false;
                throw new Exception(e.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
    }

    public boolean exists(int id) throws Exception {
        Order order = cacheOrders.get(id);
        if (order != null) return true;
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from Orders where Id = '"+id+"'");
            if ( rs.next() ) return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        return false;
    }
}
