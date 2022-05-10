package src.Data_Access_Layer;


import src.Domain_Layer.BusinessObjects.Worker_Schedule;

import java.util.HashMap;

public class WorkerScheduleDAO {
    private boolean readAll = false;
    private static HashMap<Integer, Worker_Schedule> cacheWorkersSchedules = new HashMap<>(); //worker id to his workerSchedule
    public Worker_Schedule get(int id) throws Exception{
        Worker_Schedule worker_schedule = cacheWorkersSchedules.get(id);
        if (worker_schedule!=null) return  worker_schedule;
        //take from the db and insert to the cache then return
        throw new Exception("worker doesn't exist");
    }
    public void create(Worker_Schedule worker_schedule) throws Exception {
        if (cacheWorkersSchedules.containsKey(worker_schedule.getId())) throw new Exception("worker already exists");
        //insert to db first
        cacheWorkersSchedules.put(worker_schedule.getId(),worker_schedule);

    }
    public void update(Worker_Schedule worker_schedule) throws Exception {
        if (!cacheWorkersSchedules.containsKey(worker_schedule.getId())) throw new Exception("worker doesn't exist");
        //update to db first
        cacheWorkersSchedules.put(worker_schedule.getId(), worker_schedule);
    }
    public void delete(int id) throws Exception {
        if (!cacheWorkersSchedules.containsKey(id)) throw new Exception("worker doesn't exist");
        //delete from db first
        cacheWorkersSchedules.remove(id);
    }
}
