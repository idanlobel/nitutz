package Domain_Layer.BusinessControllers;

import Domain_Layer.BusinessObjects.*;
import Domain_Layer.Repository;

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
    public Worker_Schedule getWorkerSchedule(int workerID){
        if(!workers_Schedules.containsKey(workerID)) return null; //This worker doesn't have a worker schedule -
        // shouldn't happen at all (He should always have, but better safe rather than sorry).
        return workers_Schedules.get(workerID);
    }
    public Weekly_Schedule getWeeklySchedule(int weekID){
        if(!weekly_Schedules.containsKey(weekID)) return null;
        return weekly_Schedules.get(weekID);
    }
    public boolean editWorkerSchedule(int workerID, boolean present, int day, int shiftType){
        if ((day>5 || day<0) || (shiftType !=0&&shiftType!=1))return false;
        if(!workers_Schedules.containsKey(workerID)) return false;
        if(WorkerController.getInstance().isHR(workerID) && shiftType == 1 && present == true) return  false; //The HR can only work in
                                                                                                             // morning shifts
        getWorkerSchedule(workerID).editShiftPresence(present,day,shiftType);
        //repository: Update in the database
        return true;
    }

    //TODO:: For now we can't implement this method because we don't know which shifts the Business_Layer.HR would like to add -
    // Later we can implement it by using a GUI interface.
    public boolean addWorkerToWeeklySchedule(int weekID,int day, int shift, Worker worker) {
        return getWeeklySchedule(weekID).getShift(day,shift).addWorkerToShift(worker);
    }
    public boolean removeWorkerFromWeeklySchedule(int weekID,int day, int shift, Worker worker) {
        return getWeeklySchedule(weekID).getShift(day,shift).removeWorkerFromShift(worker);
    }
    public boolean setShiftManagerToWeeklySchedule(int weekID,int day, int shift, Worker worker) {
        getWeeklySchedule(weekID).getShift(day,shift).setShiftManager(worker); return true;
    }
    public List<Worker> showShiftWorkers(int weekID, int day, int shift){
        Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
        if(weekly_schedule != null){
            Shift sft = weekly_schedule.getShift(day,shift);
            return sft.getShiftWorkers();
        }
        return null;
    }
    public Worker getShiftManager(int weekID, int day, int shift){
        Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
        if(weekly_schedule != null){
            Shift sft = weekly_schedule.getShift(day,shift);
            return sft.getShift_manager();
        }
        return null;
    }
    public boolean createWeeklySchedule(int weekID) {
        if(weekly_Schedules.containsKey(weekID)) return false;
        weekly_Schedules.put(weekID,new Weekly_Schedule());
        return true;
    }
    public boolean addTransaction(int weekID, int day, int shift, int transactionID, int workerID){
        Transaction transaction = new Transaction(transactionID, workerID);
        return getWeeklySchedule(weekID).getShift(day,shift).addTransaction(transaction, workerID);
    }
    public boolean removeTransaction(int weekID, int day, int shift, int transactionID, int workerID){
       return getWeeklySchedule(weekID).getShift(day,shift).removeTransaction(transactionID, workerID);
    }

    public boolean isShiftIsReady (int weekID, int day, int shift){
        Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID);
        if(weekly_schedule != null){
            Shift sft = weekly_schedule.getShift(day,shift);
            return sft.isShiftIsReady();
        }
        return false;
    }

    public boolean isWeeklyScheduleReady (int weekID) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                if (!isShiftIsReady(weekID, i, j)) return false;
            }
        }
        return true;
    }
}
