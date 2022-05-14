package src.Domain_Layer.BusinessControllers;

import src.Data_Access_Layer.WeeklyScheduleDAO;
import src.Data_Access_Layer.WorkerDAO;
import src.Data_Access_Layer.WorkerScheduleDAO;
import src.Domain_Layer.BusinessObjects.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShiftsController {
    private static ShiftsController shiftsController = null;
    WorkerScheduleDAO workerScheduleDAO;
    WeeklyScheduleDAO weeklyScheduleDAO;
    WorkerDAO workerDAO;
    private ShiftsController() {
        weeklyScheduleDAO = new WeeklyScheduleDAO();
        workerScheduleDAO = new WorkerScheduleDAO();
        workerDAO = new WorkerDAO();
    }
    public static ShiftsController getInstance()
    {
        if (shiftsController == null)
            shiftsController = new ShiftsController();
        return shiftsController;
    }

    //TODO:: For now we can't implement this method because we don't know what the user would like to edit -
    // Later we can implement it by using a GUI interface.
    public Worker_Schedule getWorkerSchedule(int workerID) throws Exception {
        try {
            return workerScheduleDAO.get(workerID);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    private Weekly_Schedule getWeeklySchedule(int weekID) throws Exception {
        try {
            return weeklyScheduleDAO.get(weekID);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Weekly_Schedule getWeeklyScheduleSL(int weekID,int callerID ) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see weekly schedule");
            return getWeeklySchedule(weekID);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean editWorkerSchedule(int workerID, boolean present, int day, int shiftType) throws Exception {
        try {
            Worker_Schedule worker_schedule = workerScheduleDAO.get(workerID);
            if (WorkerController.getInstance().isHR(workerID) && shiftType == 1 && present == true)
                throw new Exception("The HR can only work in morning shifts");//The HR can only work in morning shifts
            worker_schedule.editShiftPresence(present, day, shiftType);
            workerScheduleDAO.update(worker_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    //TODO:: For now we can't implement this method because we don't know which shifts the Business_Layer.HR would like to add -
    // Later we can implement it by using a GUI interface.
    public boolean addWorkerToWeeklySchedule(int weekID,int day, int shift, int worker, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can add worker to weekly schedule");
            if (!workerDAO.exists(worker)) throw new Exception("worker doesn't exist");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).addWorkerToShift(worker);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean assignWorkerToJobInShift(int weekID, int day, int shift, int id, String job, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can assign worker to job in shift");
            Worker worker = workerDAO.get(id);
            if (!worker.getWorkerJobs().contains(job))throw new Exception("worker is not specialized in this type of job");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).assignWorkerToJob(id, job);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean removeWorkerFromJobInShift(int weekID, int day, int shift, int worker, String job, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can remove worker from job in shift");
            if (!workerDAO.exists(worker)) throw new Exception("worker doesn't exist");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).removeWorkerFromJob(worker, job);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean removeWorkerFromWeeklySchedule(int weekID,int day, int shift, int worker,int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can remove worker from weekly schedule");
            if (!workerDAO.exists(worker)) throw new Exception("worker doesn't exist");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).removeWorkerFromShift(worker);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean setShiftManagerToWeeklySchedule(int weekID,int day, int shift, int id, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can assign shift manager to weekly schedule");
            if (!workerDAO.exists(id)) throw new Exception("worker doesn't exist");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).setShiftManager(id);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<Worker> showShiftWorkers(int weekID, int day, int shift, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see shift workers");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            Shift sft = weekly_schedule.getShift(day, shift);
            List<Integer> ids =  sft.getShiftWorkers();
            List<Worker> workers = new LinkedList<>();
            for (int id:ids){workers.add(workerDAO.get(id));}
            return workers;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Worker getShiftManager(int weekID, int day, int shift, int callerID) throws Exception {
        try {
            //todo maybe we need to change it to return shift manager instead?
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see shift manager");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            Shift sft = weekly_schedule.getShift(day, shift);
            int id = sft.getShift_manager();
            if (id==-1)throw new Exception("shift manager does not exists");
            return workerDAO.get(id);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean createWeeklySchedule(int weekID, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can create weekly schedule");
            weeklyScheduleDAO.create(new Weekly_Schedule(weekID));
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean addTransaction(int weekID, int day, int shift, int transactionID, int workerID) throws Exception {
        try {
            Transaction transaction = new Transaction(transactionID, workerID);
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            //lets do the check here
            if(!workerDAO.get(workerID).getWorkerJobs().contains("cashier"))
                throw new Exception("You're not authorized to perform this action. Only the shift manager of this shift " +
                        "or the HR manager or a cashier in this shift can perform this action.");
            weekly_schedule.getShift(day, shift).addTransaction(transaction, workerID);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean removeTransaction(int weekID, int day, int shift, int transactionID, int workerID) throws Exception {
       try {
           Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
           weekly_schedule.getShift(day, shift).removeTransaction(transactionID, workerID);
           weeklyScheduleDAO.update(weekly_schedule);
           return true;
       }
       catch(Exception e){
           throw new Exception(e.getMessage());
       }
    }
    public boolean isShiftIsReady (int weekID, int day, int shift, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see if shift is ready");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            Shift sft = weekly_schedule.getShift(day, shift);
            if (sft.isShiftIsReady())return true;
            throw new Exception("shift was not ready");
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean isWeeklyScheduleReady (int weekID, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see if weekly schedule is ready");
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 2; j++) {
                    if (!isShiftIsReady(weekID, i, j, callerID)) return false;
                }
            }
            return true;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Worker> getDriversInShift(int weekID, int day, int shift) throws Exception {
        try {
            List<Worker> drivers = new LinkedList<>();
            HashMap<String, List<Integer>> jobs = weeklyScheduleDAO.get(weekID).getShift(day, shift).getJobToWorker();
            List<Integer> driverIds = jobs.get("driver");
            for(int w: driverIds){
                drivers.add(workerDAO.get(w));
            }
            return drivers;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Worker> getStoreKeepersInShift(int weekID, int day, int shift) throws Exception {
        try {
            List<Worker> drivers = new LinkedList<>();
            HashMap<String, List<Integer>> jobs = weeklyScheduleDAO.get(weekID).getShift(day, shift).getJobToWorker();
            List<Integer> storeKeepersIds = jobs.get("store keeper");
            for(int w: storeKeepersIds){
                drivers.add(workerDAO.get(w));
            }
            return drivers;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
