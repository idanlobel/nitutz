package src.Domain_Layer.BusinessControllers;

import src.Data_Access_Layer.WeeklyScheduleDAO;
import src.Data_Access_Layer.WorkerDAO;
import src.Data_Access_Layer.WorkerScheduleDAO;
import src.Domain_Layer.BusinessObjects.*;
import src.Domain_Layer.Repository;

import java.util.HashMap;
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
            throw new Exception(e);
        }
    }
    public Weekly_Schedule getWeeklyScheduleSL(int weekID,int callerID ) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see weekly schedule");
            return weeklyScheduleDAO.get(weekID);
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }
    public boolean editWorkerSchedule(int workerID, boolean present, int day, int shiftType) throws Exception {
        try {
            Worker_Schedule worker_schedule = workerScheduleDAO.get(workerID);
            if (WorkerController.getInstance().isHR(workerID) && shiftType == 1 && present == true)
                throw new Exception("The HR can only work in morning shifts");//The HR can only work in morning shifts
            worker_schedule.editShiftPresence(present, day, shiftType);
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    //TODO:: For now we can't implement this method because we don't know which shifts the Business_Layer.HR would like to add -
    // Later we can implement it by using a GUI interface.
    public boolean addWorkerToWeeklySchedule(int weekID,int day, int shift, Worker worker, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can add worker to weekly schedule");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).addWorkerToShift(worker);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public boolean assignWorkerToJobInShift(int weekID, int day, int shift, Worker worker, String job, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can assign worker to job in shift");
            if (!worker.getWorkerJobs().contains(job))throw new Exception("worker is not specialized in this type of job");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).assignWorkerToJob(worker, job);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public boolean removeWorkerFromJobInShift(int weekID, int day, int shift, Worker worker, String job, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can remove worker from job in shift");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).removeWorkerFromJob(worker, job);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public boolean removeWorkerFromWeeklySchedule(int weekID,int day, int shift, Worker worker,int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can remove worker from weekly schedule");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).removeWorkerFromShift(worker);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public boolean setShiftManagerToWeeklySchedule(int weekID,int day, int shift, Worker worker, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can assign shift manager to weekly schedule");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            weekly_schedule.getShift(day, shift).setShiftManager(worker);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public List<Worker> showShiftWorkers(int weekID, int day, int shift, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see shift workers");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            Shift sft = weekly_schedule.getShift(day, shift);
            return sft.getShiftWorkers();
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public Worker getShiftManager(int weekID, int day, int shift, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see shift manager");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            Shift sft = weekly_schedule.getShift(day, shift);
            return sft.getShift_manager();
        }
        catch(Exception e){
            throw new Exception(e);
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
            weekly_schedule.getShift(day, shift).addTransaction(transaction, workerID);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch(Exception e){
            throw new Exception(e);
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
           throw new Exception(e);
       }
    }
    public boolean isShiftIsReady (int weekID, int day, int shift, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see if shift is ready");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            Shift sft = weekly_schedule.getShift(day, shift);
            return sft.isShiftIsReady();
        }
        catch(Exception e){
            throw new Exception(e);
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
            throw new Exception(e);
        }
    }
}
