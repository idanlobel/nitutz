package SuppliersModule.SupplierDataAccessLayer.DataAccessObjects;

import SuppliersModule.SupplierDataAccessLayer.DatabaseManager;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Contracts.PeriodicContract;
import SuppliersModule.SuppliersBusinessLayer.Products.SupplierProduct;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ContractDAO {
    private boolean readAll = false;
    private static HashMap<Integer, Contract> cacheContracts = new HashMap<>();
    private static boolean retry = false;
    public Contract get(int companyNumber) throws Exception{
        Contract contract = cacheContracts.get(companyNumber);
        if (contract != null) return contract;
        Connection conn = null;
        try {
            List<SupplierProduct> supplierProductList = new ArrayList<>();
            HashMap<Integer, List<int[]>> discountsMap = new HashMap<>();
            List<int[]> generalDiscounts = new ArrayList<>();
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs3 = statement.executeQuery("select * from SupplierProducts where CompanyNumber = '"+companyNumber+"'");
            while (rs3.next()){
                int id = rs3.getInt("CatalogNumber");
                int supplierId = rs3.getInt("SupplierCatalogNumber");
                int price = rs3.getInt("Price");
                String discounts = rs3.getString("Discounts");
                List<int[]> productDiscounts = new ArrayList<>();
                if(!Objects.equals(discounts, "") && discounts != null){
                    for (String part:discounts.split("l")) {
                        String[] pair = part.split("v");
                        productDiscounts.add(new int[]{Integer.parseInt(pair[0]), Integer.parseInt(pair[1])});
                    }
                }
                discountsMap.put(id, productDiscounts);
                supplierProductList.add(new SupplierProduct(supplierId, price, id));
            }
            conn.close();
            conn = DatabaseManager.getInstance().connect();
            ResultSet rs = conn.createStatement().executeQuery("select * from Contracts where CompanyNumber = '"+companyNumber+"'");
            if (rs.next()){
                String deliveryDays = rs.getString("DeliveryDays");
                int contractType = rs.getInt("ContractType");
                int deliveryType = rs.getInt("DeliveredBySupplier");
                String orderingCP=rs.getString("OrderingCP");
                String[] arrDeliveryDays = deliveryDays.split(",");
                boolean[] boolDeliveryDay = new boolean[7];
                for(int i = 0; i < arrDeliveryDays.length; i++)
                    boolDeliveryDay[i] = Boolean.parseBoolean(arrDeliveryDays[i].substring(1));
                if(contractType == 0)
                    contract = new Contract(companyNumber,orderingCP, supplierProductList, discountsMap, generalDiscounts,deliveryType==1);
                else
                    contract = new PeriodicContract(companyNumber,orderingCP, supplierProductList, discountsMap, generalDiscounts, boolDeliveryDay,deliveryType==1);

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
        if (contract == null)throw new Exception("contract doesn't exist");
        cacheContracts.put(contract.getCompanyNumber(),contract);
        return contract;
        //take from the db and insert to the cache then return
    }

    public List<Contract> getAllContracts() throws Exception{
        List<Contract> list = new ArrayList();
        if (!readAll){
            //read from DB and insert into cache contracts;
            Connection conn=null;
            List<Integer> contractIDS = new LinkedList<>();
            try {
                conn = DatabaseManager.getInstance().connect();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select CompanyNumber from Contracts");
                while ( rs.next() ) {
                    int companyNumber = rs.getInt("CompanyNumber");
                    contractIDS.add(companyNumber);
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
            for (int cn:contractIDS) {
                Contract contract = get(cn);
                if (!cacheContracts.containsKey(contract.getCompanyNumber())) cacheContracts.put(contract.getCompanyNumber(),contract);
            }
            //readAll = true;
        }
        list.addAll(cacheContracts.values());
        return list;
    }
    public void create(Contract contract) throws Exception {
        if (cacheContracts.containsKey(contract.getCompanyNumber())) throw new Exception("contract already exists");
        Connection conn = null;
        String sql = "INSERT INTO Contracts(CompanyNumber,DeliveryDays,ContractType,OrderingCP,DeliveredBySupplier) VALUES(";
        try { //VALUES(i,'s','i','i');
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            //set Contract data
            String deliveryDays = Arrays.toString(contract.getDeliveryDays());
            sql+="'"+contract.getCompanyNumber()+"','"+deliveryDays+"','"+contract.getType()+"','"+ contract.getOrderingCP()+"','"+contract.getType()+
                    "');";
            rs.addBatch(sql);
            //set his productss;
            for (SupplierProduct supplierProduct:contract.getAllProducts().values()) {
                StringBuilder discountsString = new StringBuilder();
                List<int[]> productDiscounts = contract.getDiscounts().get(supplierProduct.getId());
                int count = 0;
                for(int[] pair: productDiscounts){
                    discountsString.append("l");
                    discountsString.append(pair[0]);
                    discountsString.append("v");
                    discountsString.append(pair[1]);
                    count++;
                }
                if (count > 0)
                    discountsString.replace(0, 1, "");
                String discountsStringFinal = discountsString.toString();
                String sql2="\nINSERT INTO SupplierProducts(CompanyNumber,CatalogNumber,SupplierCatalogNumber,Discounts,Price) VALUES('"
                        +contract.getCompanyNumber()+"','"
                        +supplierProduct.getId()+"','"
                        +supplierProduct.getSupplierId()+"','"
                        +discountsStringFinal+"','"
                        +supplierProduct.getPrice()+"');";
                rs.addBatch(sql2);
            }
            //PreparedStatement rs = conn.prepareStatement(sql);
            rs.executeBatch();
            conn.commit();
            cacheContracts.put(contract.getCompanyNumber(),contract);
        } catch(Exception e) {
            if (!retry){retry=true; create(contract);}
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
        cacheContracts.put(contract.getCompanyNumber(),contract);
    }

    public void update(Contract contract) {
        //if (!cacheSuppliers.containsKey(supplier.getCompanyNumber())) throw new Exception("supplier doesn't exist");
        //update to db first
        try {
            delete(contract.getCompanyNumber());
            create(contract);
        }catch (Exception e){
            System.out.println("failed contract update");
            throw new RuntimeException(e.getMessage());
        }
        cacheContracts.put(contract.getCompanyNumber(), contract);
    }
    public void delete(int cn) throws Exception{
        Connection conn=null;
        String sql = "DELETE from Contracts where CompanyNumber = '"+cn+"';";
        String sql2 = "DELETE from SupplierProducts where CompanyNumber = '"+cn+"';";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            rs.addBatch(sql);
            rs.addBatch(sql2);
            rs.executeBatch();
            conn.commit();
            cacheContracts.remove(cn);
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
        Contract contract = cacheContracts.get(cn);
        if (contract != null) return true;
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from Contracts where CompanyNumber = '"+cn+"'");
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
