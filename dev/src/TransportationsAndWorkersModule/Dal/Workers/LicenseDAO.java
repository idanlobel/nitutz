package src.TransportationsAndWorkersModule.Dal.Workers;

import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.BankAccount;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.EmploymentConditions;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.HR;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class LicenseDAO {
    private boolean readAll = false;
    private static HashMap<Integer, String> cachelicense = new HashMap<>();

    private static boolean retry = false;

    public String get(int id) throws Exception{
        String license = cachelicense.get(id);
        if (license!=null) return  license;
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from driversData where id = '"+id+"'");
            if ( rs.next() ) {
                license = rs.getString("license");
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
        if (license==null)throw new Exception("this worker has no license");
        cachelicense.put(id,license);
        return license;
        //take from the db and insert to the cache then return
    }
    public void create(int id, String license) throws Exception {
        if (cachelicense.containsKey(id)) throw new Exception("worker data already exists");
        Connection conn=null;
        String sql = "INSERT INTO driversData(id,license) VALUES(";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            //set Driver data
            sql+="'"+id+"','"+license+"');";
            rs.addBatch(sql);
            rs.executeBatch();
            conn.commit();
            cachelicense.put(id,license);
        } catch(Exception e) {
            if (!retry){retry=true; create(id,license);}
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
        //dont forget to add all the 2*5 presence needed for this worker id as false so its woker schedule will exist as well
        cachelicense.put(id,license);
    }
    public void update(int id, String license) throws Exception {
        if (!cachelicense.containsKey(id)) throw new Exception("driver data doesn't exist");
        //update to db first
        try {
            delete(id);
            create(id,license);
        }catch (Exception e){
            System.out.println("failed driver data update");
            throw new Exception(e.getMessage());
        }
        //maybe its better to actually make a delete sql instead but for now this will be it
        cachelicense.put(id,license);
    }
    public void delete(int id) throws Exception{
        Connection conn=null;
        String sql = "DELETE from driversData where id = '"+id+"';";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            rs.addBatch(sql);
            rs.executeBatch();
            conn.commit();
            cachelicense.remove(id);
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
}
