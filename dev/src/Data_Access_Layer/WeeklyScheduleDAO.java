package src.Data_Access_Layer;



import src.Domain_Layer.BusinessObjects.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class WeeklyScheduleDAO {
    private boolean readAll = false;
    private WorkerDAO workerDAO = new WorkerDAO();
    private static HashMap<Integer, Weekly_Schedule> cacheWeeklySchedules = new HashMap<>(); //worker id to his workerSchedule
    public Weekly_Schedule get(int id) throws Exception{
        Weekly_Schedule worker_schedule = cacheWeeklySchedules.get(id);
        if (worker_schedule!=null) return  worker_schedule;
        //take from the db and insert to the cache then return
        List<Integer> worker_ids = new LinkedList<>();
        Shift[][] shifts = new Shift[5][2];
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("select * from shiftWorkerBook where week_id = "+id);
            while ( rs.next() ) {
                int shift_type = rs.getInt("shift_type");
                int day = rs.getInt("shift_day");
                int worker_id = rs.getInt("worker_id");
                String job = rs.getString("job");
                if (shifts[day][shift_type]==null)shifts[day][shift_type] = new Shift();
                if (job!=""){
                    if(shifts[day][shift_type].getJobToWorker().get(job)==null)shifts[day][shift_type].getJobToWorker().put(job,new ArrayList<>());
                    shifts[day][shift_type].getJobToWorker().get(job).add(worker_id);
                }
                worker_ids.add(worker_id);
            }
        } catch (Exception e) {

            throw new Exception(e.getMessage());
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        for (int w:worker_ids) {
            Worker worker = workerDAO.get(w);// dont need to save workers as objects at all in shift need to change that.

        }
        return null;
    }
    public void create(Weekly_Schedule weekly_schedule) throws Exception {
        if (cacheWeeklySchedules.containsKey(weekly_schedule.getId())) throw new Exception("worker already exists");
        //insert to db first
        cacheWeeklySchedules.put(weekly_schedule.getId(),weekly_schedule);

    }
    public void update(Weekly_Schedule weekly_schedule) throws Exception {
        if (!cacheWeeklySchedules.containsKey(weekly_schedule.getId())) throw new Exception("worker doesn't exist");
        //update to db first
        cacheWeeklySchedules.put(weekly_schedule.getId(), weekly_schedule);
    }
    public void delete(int id) throws Exception {
        if (!cacheWeeklySchedules.containsKey(id)) throw new Exception("worker doesn't exist");
        //delete from db first
        cacheWeeklySchedules.remove(id);
    }
}
