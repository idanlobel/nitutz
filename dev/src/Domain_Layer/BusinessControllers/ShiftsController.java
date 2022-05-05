package src.Domain_Layer.BusinessControllers;

import src.Domain_Layer.BusinessObjects.*;
import src.Domain_Layer.Repository;

import java.util.HashMap;
import java.util.List;

public class ShiftsController {

    private static ShiftsController shiftsController = null;
    int currentWeekID;
    HashMap<Integer, Worker_Schedule> workers_Schedules; //worker id to his workerSchedule
    HashMap<Integer, Weekly_Schedule> weekly_Schedules; //week id to weekly schedule
    private Repository repository = Repository.getInstance();
    private ShiftsController() {
        readWorkersSchedules();
        readWeeklySchedules();
    }
    private void readWorkersSchedules(){
        this.workers_Schedules =repository.readWorkerSchedules();
    }
    private void readWeeklySchedules(){
        this.weekly_Schedules = repository.readWeeklySchedules();
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
        if(!workers_Schedules.containsKey(workerID)) throw new Exception("This worker doesn't have a worker schedule"); //This worker doesn't have a worker schedule -
        // TODO:: shouldn't happen at all (He should always have!) - (but better safe rather than sorry).
        return workers_Schedules.get(workerID);
    }
    public boolean addWorkerSchedule(int workerID){
        if (workers_Schedules.containsKey(workerID))return false;
        workers_Schedules.put(workerID,new Worker_Schedule());
        return true;
    }
    public boolean removeWorkerSchedule(int workerID){
        if (!workers_Schedules.containsKey(workerID))return false;
        workers_Schedules.remove(workerID);
        return true;
    }
    public Weekly_Schedule getWeeklySchedule(int weekID) throws Exception {
        try {
            if (!weekly_Schedules.containsKey(weekID)) throw new Exception("There's no such weekly schedule");
            else return weekly_Schedules.get(weekID);
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }
    public boolean editWorkerSchedule(int workerID, boolean present, int day, int shiftType) throws Exception {
        try {
            if ((day > 5 || day < 0) || (shiftType != 0 && shiftType != 1))
                throw new Exception("Please make sure you've entered legal values for day and shift type");
            if (!workers_Schedules.containsKey(workerID))
                throw new Exception("There's no such working schedule for the specified worker");
            if (WorkerController.getInstance().isHR(workerID) && shiftType == 1 && present == true)
                throw new Exception("The HR can only work in morning shifts");//The HR can only work in
            // morning shifts
            getWorkerSchedule(workerID).editShiftPresence(present, day, shiftType);
            //repository: Update in the database
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    //TODO:: For now we can't implement this method because we don't know which shifts the Business_Layer.HR would like to add -
    // Later we can implement it by using a GUI interface.
    public boolean addWorkerToWeeklySchedule(int weekID,int day, int shift, Worker worker) throws Exception {
        try {
            return getWeeklySchedule(weekID).getShift(day, shift).addWorkerToShift(worker);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public boolean assignWorkerToJobInShift(int weekID, int day, int shift, Worker worker, String job) throws Exception {
        try {
            return getWeeklySchedule(weekID).getShift(day, shift).assignWorkerToJob(worker, job);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public boolean removeWorkerFromJobInShift(int weekID, int day, int shift, Worker worker, String job) throws Exception {
        try {
            return getWeeklySchedule(weekID).getShift(day, shift).removeWorkerFromJob(worker, job);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public boolean removeWorkerFromWeeklySchedule(int weekID,int day, int shift, Worker worker) throws Exception {
        try {
            return getWeeklySchedule(weekID).getShift(day, shift).removeWorkerFromShift(worker);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public boolean setShiftManagerToWeeklySchedule(int weekID,int day, int shift, Worker worker) throws Exception {
        try {
            getWeeklySchedule(weekID).getShift(day, shift).setShiftManager(worker);
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public List<Worker> showShiftWorkers(int weekID, int day, int shift) throws Exception {
        try {
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            if (weekly_schedule != null) {
                Shift sft = weekly_schedule.getShift(day, shift);
                return sft.getShiftWorkers();
            }
            return null;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
    public Worker getShiftManager(int weekID, int day, int shift) throws Exception {
        try {
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            if (weekly_schedule != null) {
                Shift sft = weekly_schedule.getShift(day, shift);
                return sft.getShift_manager();
            }
            throw new Exception("There's No such shift");
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }
    public boolean createWeeklySchedule(int weekID) throws Exception {
        if(weekly_Schedules.containsKey(weekID)) throw new Exception("this week already exists");
        weekly_Schedules.put(weekID,new Weekly_Schedule());
        return true;
    }
    public boolean addTransaction(int weekID, int day, int shift, int transactionID, int workerID) throws Exception {
        try {
            Transaction transaction = new Transaction(transactionID, workerID);
            return getWeeklySchedule(weekID).getShift(day, shift).addTransaction(transaction, workerID);
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }

    public boolean removeTransaction(int weekID, int day, int shift, int transactionID, int workerID) throws Exception {
       try {
           return getWeeklySchedule(weekID).getShift(day, shift).removeTransaction(transactionID, workerID);
       }
       catch(Exception e){
           throw new Exception(e);
       }
    }

    public boolean isShiftIsReady (int weekID, int day, int shift) throws Exception {
        try {
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
            if (weekly_schedule != null) {
                Shift sft = weekly_schedule.getShift(day, shift);
                return sft.isShiftIsReady();
            }
            return false;
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }

    public boolean isWeeklyScheduleReady (int weekID) throws Exception {
        try {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 2; j++) {
                    if (!isShiftIsReady(weekID, i, j)) return false;
                }
            }
            return true;
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }
}
