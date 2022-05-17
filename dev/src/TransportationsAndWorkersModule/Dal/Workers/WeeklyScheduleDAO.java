package src.TransportationsAndWorkersModule.Dal.Workers;





import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Shift;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Transaction;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Weekly_Schedule;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker_Schedule;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import java.sql.*;
import java.util.*;

public class WeeklyScheduleDAO {
    private boolean readAll = false;
    private WorkerDAO workerDAO = new WorkerDAO();
    private JobsDAO jobsDAO = new JobsDAO();
    private static HashMap<Integer,HashMap<String,Weekly_Schedule>> cacheWeeklySchedules = new HashMap<>(); //worker id to his workerSchedule
    public Weekly_Schedule get(int id, String site) throws Exception{
        HashMap<String, Weekly_Schedule> hashMap = cacheWeeklySchedules.get(id);
        if (hashMap!=null && hashMap.get(site)!=null) return hashMap.get(site);
        Weekly_Schedule worker_schedule = null;
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
            conn.close();
            conn = DatabaseManager.getInstance().connect();
            Statement statement3 = conn.createStatement();
            ResultSet rs3 = statement3.executeQuery("select * from transactions where week_id = '" + id + "' and site = '" + site + "'");
            while (rs3.next()) {
                int shift_type = rs3.getInt("shift");
                int day = rs3.getInt("day");
                int trans_id = rs3.getInt("id");
                int wid = rs3.getInt("worker_id");
                String data = rs3.getString("data");
                if (shifts[day][shift_type] == null) shifts[day][shift_type] = new Shift();
                if (shifts[day][shift_type].getShift_transactions() == null) shifts[day][shift_type].addTransactionData(new Transaction(trans_id,wid));
            }
            worker_schedule = new Weekly_Schedule(shifts,id,site);
            if (cacheWeeklySchedules.get(id)==null)cacheWeeklySchedules.put(id,new HashMap<>());
            cacheWeeklySchedules.get(id).put(site,worker_schedule);
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
        HashMap<String, Weekly_Schedule> hashMap = cacheWeeklySchedules.get(weekly_schedule.getId());
        if (hashMap!=null && hashMap.get(weekly_schedule.getSite())!=null) throw new Exception("week already exists");
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
                    for (int l =0; l<weekly_schedule.getSchedule()[i][j].getShift_transactions().size(); l++){
                        Transaction transaction = weekly_schedule.getSchedule()[i][j].getShift_transactions().get(l);
                        String sql2 = "INSERT INTO transactions(week_id,site,id,data,day,shift,worker_id) VALUES('";
                        sql2 += weekly_schedule.getId() + "','" + weekly_schedule.getSite() + "','" + transaction.getTransactionID() + "','";
                        sql2 += transaction.getData() + "','" + i + "','"+j+"','"+transaction.getWorkerID()+"');";
                        rs.addBatch(sql2);
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
        if (cacheWeeklySchedules.get(weekly_schedule.getId())==null)cacheWeeklySchedules.put(weekly_schedule.getId(), new HashMap<>());
        cacheWeeklySchedules.get(weekly_schedule.getId()).put(weekly_schedule.getSite(),weekly_schedule);
    }
    public void update(Weekly_Schedule weekly_schedule) throws Exception {
        HashMap<String, Weekly_Schedule> hashMap = cacheWeeklySchedules.get(weekly_schedule.getId());
        if (hashMap==null || hashMap.get(weekly_schedule.getSite())==null) throw new Exception("week doesnt exist");
        //update to db first
        try {
            delete(weekly_schedule.getId(), weekly_schedule.getSite());
            create(weekly_schedule);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if (cacheWeeklySchedules.get(weekly_schedule.getId())==null)cacheWeeklySchedules.put(weekly_schedule.getId(), new HashMap<>());
        cacheWeeklySchedules.get(weekly_schedule.getId()).put(weekly_schedule.getSite(),weekly_schedule);
    }
    public void delete(int id, String site) throws Exception {
        HashMap<String, Weekly_Schedule> hashMap = cacheWeeklySchedules.get(id);
        if (hashMap==null || hashMap.get(site)==null) throw new Exception("week doesnt exist");
        //delete from db first
        Connection conn=null;
        String sql = "DELETE from shiftWorkerBook where week_id = '"+id+"' and site = '"+site+"'";
        String sql2 = "DELETE from weeklySchedule where id = '"+id+"' and site = '"+site+"'";
        String sql3 = "DELETE from transactions where week_id = '"+id+"' and site = '"+site+"'";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            rs.addBatch(sql);
            rs.addBatch(sql2);
            rs.addBatch(sql3);
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
        if (cacheWeeklySchedules.get(id)!=null)cacheWeeklySchedules.get(id).remove(site);
    }
}
