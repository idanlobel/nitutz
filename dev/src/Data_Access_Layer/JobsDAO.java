package src.Data_Access_Layer;

import src.Domain_Layer.BusinessObjects.BankAccount;
import src.Domain_Layer.BusinessObjects.EmploymentConditions;
import src.Domain_Layer.BusinessObjects.Worker;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class JobsDAO {
    private static boolean readAll = false;
    private static List<String> cacheJobs = new LinkedList<>();
    public JobsDAO(){
    }
    public boolean exists(String job) throws Exception {
        if (!cacheJobs.contains(job)){
            //read from db
            Connection conn=null;
            try {
                conn = DatabaseManager.getInstance().connect();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select * from jobs where job = "+job);
                if ( rs.next() ) {
                    cacheJobs.add(job);
                    return true;
                }
                return false;
            } catch (Exception e) {
                return false;
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
        return true;
    }
    public List<String> getAllJobs() throws Exception{
        if (readAll)return cacheJobs;
        //read from DB and insert into cache workers;
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from jobs");
            while ( rs.next() ) {
                cacheJobs.add(rs.getString("job"));
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
        readAll = true;
        return cacheJobs;
    }
    public void add(String job) throws Exception {
        if (cacheJobs.contains(job)) throw new Exception("worker already exists");
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            PreparedStatement rs = conn.prepareStatement("INSERT INTO jobs(job) VALUES(?)");
            rs.setString(1,job);
            rs.execute();
            conn.commit();
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
        //insert to db first
        //dont forget to add all the 2*5 presence needed for this worker id as false so its woker schedule will exist as well
        cacheJobs.add(job);
    }
    public void delete(String job)throws Exception{
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            PreparedStatement rs = conn.prepareStatement("delete from jobs where job ="+job);
            rs.execute();
            conn.commit();
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
        cacheJobs.remove(job);
    }
}
