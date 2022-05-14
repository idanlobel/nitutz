package src.Data_Access_Layer;


import src.Domain_Layer.BusinessObjects.BankAccount;
import src.Domain_Layer.BusinessObjects.EmploymentConditions;
import src.Domain_Layer.BusinessObjects.Worker;
import src.Domain_Layer.BusinessObjects.Worker_Schedule;

import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class WorkerScheduleDAO {
    private boolean readAll = false;
    private static HashMap<Integer, Worker_Schedule> cacheWorkersSchedules = new HashMap<>(); //worker id to his workerSchedule
    public Worker_Schedule get(int id) throws Exception{
        Worker_Schedule worker_schedule = cacheWorkersSchedules.get(id);
        if (worker_schedule!=null) return  worker_schedule;
        //take from the db and insert to the cache then return
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from workerPresence where worker_id = '"+id+"'");
            //need to change to final values like table name etc
            boolean[][] presence = new boolean[5][2];
            boolean exists = false;
            while ( rs.next() ) {
                exists=true;
                int day = rs.getInt("day");
                int shift = rs.getInt("shift_type");
                int presentInt = rs.getInt("present");
                boolean present = false;
                if (presentInt!=0) present = true;
                presence[day][shift] = present;
            }
            if (!exists) throw new Exception("Worker schedule does not exist");
            worker_schedule = new Worker_Schedule(presence,id);
            cacheWorkersSchedules.put(id,worker_schedule);
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
        return worker_schedule;
    }
    public void create(Worker_Schedule worker_schedule) throws Exception {
        if (cacheWorkersSchedules.containsKey(worker_schedule.getId())) throw new Exception("worker already exists");
        //insert to db first
        Connection conn=null;
        String sql = "INSERT INTO workerPresence(present,day,shift_type,worker_id) VALUES(?,?,?,?)";
        try {
            conn = DatabaseManager.getInstance().connect();
            PreparedStatement rs;
            for (int i =0; i<5; i++){
                for (int j =0; j<2; j++){
                    rs=conn.prepareStatement(sql);
                    boolean present = worker_schedule.getSchedule()[i][j];
                    int presentInt = 0;
                    if (present) presentInt = 1;
                    rs.setInt(1,presentInt);
                    rs.setInt(2,i);
                    rs.setInt(3,j);
                    rs.setInt(4,worker_schedule.getId());
                    rs.execute();
                }
            }
            conn.commit();
        } catch(Exception e) {
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
        cacheWorkersSchedules.put(worker_schedule.getId(),worker_schedule);
    }
    public void update(Worker_Schedule worker_schedule) throws Exception {
        if (!cacheWorkersSchedules.containsKey(worker_schedule.getId())) throw new Exception("worker doesn't exist");
        //update to db first
        delete(worker_schedule.getId());
        create(worker_schedule);
        cacheWorkersSchedules.put(worker_schedule.getId(), worker_schedule);
    }
    public void delete(int id) throws Exception {
        if (!cacheWorkersSchedules.containsKey(id)) throw new Exception("worker doesn't exist");
        //delete from db first
        Connection conn=null;
        String sql = "DELETE from workerPresence where worker_id = '"+id+"'";
        try {
            conn = DatabaseManager.getInstance().connect();
            PreparedStatement rs = conn.prepareStatement(sql);
            rs.execute();
            conn.commit();
        }catch(Exception e) {
            throw new Exception(e.getMessage());
        }
         finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        cacheWorkersSchedules.remove(id);
    }
}
