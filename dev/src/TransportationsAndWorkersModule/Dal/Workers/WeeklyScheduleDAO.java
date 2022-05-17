package src.TransportationsAndWorkersModule.Dal.Workers;





import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Shift;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Weekly_Schedule;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import java.sql.*;
import java.util.*;

public class WeeklyScheduleDAO {
    private boolean readAll = false;
    private WorkerDAO workerDAO = new WorkerDAO();
    private JobsDAO jobsDAO = new JobsDAO();
    private static HashMap<Integer, Weekly_Schedule> cacheWeeklySchedules = new HashMap<>(); //worker id to his workerSchedule
    public Weekly_Schedule get(int id, String site) throws Exception{
        Weekly_Schedule worker_schedule = cacheWeeklySchedules.get(id);
        if (worker_schedule!=null) return  worker_schedule;
        //take from the db and insert to the cache then return
        List<Integer> worker_ids = new LinkedList<>();
        Shift[][] shifts = new Shift[5][2];
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs2 = statement.executeQuery("select * from weeklySchedule where id = '"+id+"' and site = '"+site+"'");
            if (!rs2.next())throw new Exception("weekly schedule does not exists");
            conn.close();
            conn = DatabaseManager.getInstance().connect();
            Statement statement2 = conn.createStatement();
            ResultSet rs = statement2.executeQuery("select * from shiftWorkerBook where week_id = '"+id+"' and site = '"+site+"'");
            while ( rs.next() ) {
                int shift_type = rs.getInt("shift_type");
                int day = rs.getInt("shift_day");
                int worker_id = rs.getInt("worker_id");
                String job = rs.getString("job");
                if (shifts[day][shift_type]==null)shifts[day][shift_type] = new Shift();
                if (!shifts[day][shift_type].getWorkers().contains(worker_id))shifts[day][shift_type].addWorkerToShift(worker_id);
                if (!job.equals("")){
                    shifts[day][shift_type].assignWorkerToJob(worker_id,job);
                    if (job.equals("shift manager"))shifts[day][shift_type].setShiftManager(worker_id);
                }
                if (!shifts[day][shift_type].getShiftWorkers().contains(worker_id))shifts[day][shift_type].addWorkerToShift(worker_id);
            }
            worker_schedule = new Weekly_Schedule(shifts,id,site);
            cacheWeeklySchedules.put(id,worker_schedule);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
        return worker_schedule;
    }
    public void create(Weekly_Schedule weekly_schedule) throws Exception {
        if (cacheWeeklySchedules.containsKey(weekly_schedule.getId())) throw new Exception("week already exists");
        //insert to db first
        Connection conn=null;
        String sqlCreate = "INSERT INTO weeklySchedule(id, site) VALUES('"+weekly_schedule.getId()+"','"+weekly_schedule.getSite()+"');";
        List<String> jobs = jobsDAO.getAllJobs();
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            rs.addBatch(sqlCreate);
            for (int i =0; i<5; i++){
                for (int j =0; j<2; j++){
                    for (int wid : weekly_schedule.getSchedule()[i][j].getWorkers()){
                        String sql = "INSERT INTO shiftWorkerBook(week_id,shift_type,shift_day,worker_id,job,site) VALUES('";
                        boolean exists = false;
                        for (String job : jobs){
                            if (weekly_schedule.getSchedule()[i][j].getJobToWorker().containsKey(job)&&
                            weekly_schedule.getSchedule()[i][j].getJobToWorker().get(job).contains(wid)){
                                sql+=weekly_schedule.getId()+"','"+j+"','"+i+"','";
                                sql+=wid+"','"+job+"','"+weekly_schedule.getSite()+"');";
                                rs.addBatch(sql);
                                exists=true;
                            }
                        }
                        if (!exists) {
                            sql += weekly_schedule.getId() + "','" + j + "','" + i + "','";
                            sql += wid + "','" + "" + "','"+weekly_schedule.getSite()+"');";
                            rs.addBatch(sql);
                        }
                    }
                }
            }
            rs.executeBatch();
            conn.commit();
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
        cacheWeeklySchedules.put(weekly_schedule.getId(),weekly_schedule);
    }
    public void update(Weekly_Schedule weekly_schedule) throws Exception {
        if (!cacheWeeklySchedules.containsKey(weekly_schedule.getId())) throw new Exception("week doesn't exist");
        //update to db first
        try {
            delete(weekly_schedule.getId(), weekly_schedule.getSite());
            create(weekly_schedule);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        cacheWeeklySchedules.put(weekly_schedule.getId(), weekly_schedule);
    }
    public void delete(int id, String site) throws Exception {
        if (!cacheWeeklySchedules.containsKey(id)) throw new Exception("week doesn't exist");
        //delete from db first
        Connection conn=null;
        String sql = "DELETE from shiftWorkerBook where week_id = '"+id+"' and site = '"+site+"'";
        String sql2 = "DELETE from weeklySchedule where id = '"+id+"' and site = '"+site+"'";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            rs.addBatch(sql);
            rs.addBatch(sql2);
            rs.executeBatch();
            conn.commit();
        }catch(Exception e) {
            throw new Exception(e.getMessage());
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                throw new Exception(ex.getMessage());
            }
        }
        cacheWeeklySchedules.remove(id);
    }
}
