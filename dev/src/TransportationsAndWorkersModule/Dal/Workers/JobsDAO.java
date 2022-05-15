package src.TransportationsAndWorkersModule.Dal.Workers;


import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JobsDAO {
    private static boolean readAll = false;
    private static List<String> cacheJobs = new LinkedList<>();
    public JobsDAO(){
    }
    public boolean exists(String job) throws Exception {
        boolean toret = true;
        if (!cacheJobs.contains(job)){
            //read from db
            Connection conn=null;
            try {
                conn = DatabaseManager.getInstance().connect();
                Statement statement = conn.createStatement();
                String sql = "select * from jobs where job = '"+job+"'";
                ResultSet rs = statement.executeQuery(sql);
                if ( rs.next() ) {
                    cacheJobs.add(job);
                    toret = true;
                }
                else toret = false;
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
        return toret;
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
