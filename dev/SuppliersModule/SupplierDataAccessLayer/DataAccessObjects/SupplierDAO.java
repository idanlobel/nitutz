package SuppliersModule.SupplierDataAccessLayer.DataAccessObjects;
import SuppliersModule.SupplierDataAccessLayer.DatabaseManager;
import SuppliersModule.SuppliersBusinessLayer.ContactPerson;
import SuppliersModule.SuppliersBusinessLayer.Supplier;
import java.sql.*;
import java.util.*;


public class SupplierDAO {
    private boolean readAll = false;
    private static HashMap<Integer, Supplier> cacheSuppliers = new HashMap<>();
    private static boolean retry = false;
    public Supplier get(int companyNumber) throws Exception{
        Supplier supplier = cacheSuppliers.get(companyNumber);
        if (supplier != null) return supplier;
        Connection conn = null;
        try {
            List<ContactPerson> contactPersonList = new ArrayList<>();
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs3 = statement.executeQuery("select * from ContactPeople where CompanyNumber = '"+companyNumber+"'");
            while (rs3.next()){
                String cpName = rs3.getString("FullName");
                String email = rs3.getString("Email");
                String cellNumber = rs3.getString("CellNumber");
                contactPersonList.add(new ContactPerson(cpName, email, cellNumber));
            }
            conn.close();
            conn = DatabaseManager.getInstance().connect();
            ResultSet rs = conn.createStatement().executeQuery("select * from Suppliers where CompanyNumber = '"+companyNumber+"'");
            if (rs.next()){
                String supplierName = rs.getString("Name");
                String supplierAddress = rs.getString("Address");
                String bankNumber = rs.getString("BankNumber");
                String orderingCPName = rs.getString("OrderingCP");
                supplier = new Supplier(supplierName, companyNumber, bankNumber, supplierAddress, contactPersonList, orderingCPName);
            }
        } catch (Exception e) {
            if (!retry){retry=true; get(companyNumber);}
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
        if (supplier == null)throw new Exception("supplier doesn't exist");
        cacheSuppliers.put(supplier.getCompanyNumber(),supplier);
        return supplier;
        //take from the db and insert to the cache then return
    }

    public List<Supplier> getAllSuppliers() throws Exception{
        List<Supplier> list = new ArrayList();
        if (!readAll){
            //read from DB and insert into cache suppliers;
            Connection conn=null;
            List<Integer> supplierIDS = new LinkedList<>();
            try {
                conn = DatabaseManager.getInstance().connect();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select CompanyNumber from Suppliers");
                while ( rs.next() ) {
                    int companyNumber = rs.getInt("CompanyNumber");
                    supplierIDS.add(companyNumber);
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
            for (int cn:supplierIDS) {
                Supplier supplier = get(cn);
                if (!cacheSuppliers.containsKey(supplier.getCompanyNumber()))cacheSuppliers.put(supplier.getCompanyNumber(),supplier);
            }
            readAll = true;
        }
        list.addAll(cacheSuppliers.values());
        return list;
    }
    public void create(Supplier supplier) throws Exception {
        if (cacheSuppliers.containsKey(supplier.getCompanyNumber())) throw new Exception("supplier already exists");
        Connection conn=null;
        String sql = "INSERT INTO Suppliers(CompanyNumber,BankNumber,Name,Address,OrderingCP) VALUES(";
        try { //INSERT INTO Suppliers(CompanyNumber,BankNumber,Name,Address,OrderingCP) VALUES(i,'s','s','s','s');
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            //set Supplier data
            sql+="'"+supplier.getCompanyNumber()+"','"+supplier.getBankNumber()+"','"+supplier.getName()+"','"+supplier.getAddress()+
                    "','"+supplier.getOrderingCP().getName()+"');";
            rs.addBatch(sql);
            //set his CPs;
            for (ContactPerson contactPerson:supplier.getContactList().values()) {
                String sql2="\nINSERT INTO ContactPeople(FullName,Email,CellNumber,CompanyNumber) VALUES('"
                        +contactPerson.getName()+"','"
                        +contactPerson.getEmail() +"','"
                        +contactPerson.getCellNumber() +"','"
                        +supplier.getCompanyNumber()+"');";
                rs.addBatch(sql2);
            }
            //PreparedStatement rs = conn.prepareStatement(sql);
            rs.executeBatch();
            conn.commit();
            cacheSuppliers.put(supplier.getCompanyNumber(),supplier);
        } catch(Exception e) {
            if (!retry){retry=true; create(supplier);}
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
        cacheSuppliers.put(supplier.getCompanyNumber(),supplier);
    }

    public void update(Supplier supplier) throws Exception {
        //if (!cacheSuppliers.containsKey(supplier.getCompanyNumber())) throw new Exception("supplier doesn't exist");
        //update to db first
        try {
            delete(supplier.getCompanyNumber());
            create(supplier);
        }catch (Exception e){
            System.out.println("failed supplier update");
            throw new Exception(e.getMessage());
        }
        cacheSuppliers.put(supplier.getCompanyNumber(), supplier);
    }
    public void delete(int cn) throws Exception{
        Connection conn=null;
        String sql = "DELETE from Suppliers where CompanyNumber = '"+cn+"';";
        String sql2 = "DELETE from ContactPeople where CompanyNumber = '"+cn+"';";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            rs.addBatch(sql);
            rs.addBatch(sql2);
            rs.executeBatch();
            conn.commit();
            cacheSuppliers.remove(cn);
        } catch(Exception e) {
            if (!retry){retry=true; delete(cn);}
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

    public boolean exists(int cn) throws Exception {
        Supplier supplier = cacheSuppliers.get(cn);
        if (supplier != null) return true;
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from Suppliers where CompanyNumber = '"+cn+"'");
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

    public void deleteAllData() throws Exception {
            Connection conn=null;
            String sql1 = "DELETE FROM ContactPeople;";
            String sql2 = "DELETE FROM Contracts;";
            String sql3 = "DELETE FROM Orders;";
            String sql4 = "DELETE FROM SupplierProducts;";
            String sql5 = "DELETE FROM Suppliers;";
            try {
                conn = DatabaseManager.getInstance().connect();
                Statement rs = conn.createStatement();
                rs.addBatch(sql1);
                rs.addBatch(sql2);
                rs.addBatch(sql3);
                rs.addBatch(sql4);
                rs.addBatch(sql5);
                rs.executeBatch();
                conn.commit();
            } catch(Exception e) {
                if (!retry){retry=true; deleteAllData();}
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
}
