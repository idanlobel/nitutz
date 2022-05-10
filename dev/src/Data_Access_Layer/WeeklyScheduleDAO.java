package src.Data_Access_Layer;



import src.Domain_Layer.BusinessObjects.Weekly_Schedule;

import java.util.HashMap;

public class WeeklyScheduleDAO {
    private boolean readAll = false;
    private static HashMap<Integer, Weekly_Schedule> cacheWeeklySchedules = new HashMap<>(); //worker id to his workerSchedule
    public Weekly_Schedule get(int id) throws Exception{
        Weekly_Schedule worker_schedule = cacheWeeklySchedules.get(id);
        if (worker_schedule!=null) return  worker_schedule;
        //take from the db and insert to the cache then return
        throw new Exception("weekly schedule doesn't exist");
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
