package Domain_Layer.BusinessControllers;

import Domain_Layer.BusinessObjects.Transaction;
import Domain_Layer.BusinessObjects.Weekly_Schedule;
import Domain_Layer.BusinessObjects.Worker;
import Domain_Layer.BusinessObjects.Worker_Schedule;
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
        return workers_Schedules.get(workerID);
    }
    public Weekly_Schedule getWeeklySchedule(int weekID){
        return weekly_Schedules.get(weekID);
    }
    public boolean editWorkerSchedule(int workerID, boolean present, int day, int shiftType){
        if (day>5 || (shiftType !=0&&shiftType!=1))return false;
        getWorkerSchedule(workerID).editShiftPresence(present,day,shiftType);
        //repository.update that shit
        return true;
    }

    //TODO:: For now we can't implement this method because we don't know which shifts the Business_Layer.HR would like to add -
    // Later we can implement it by using a GUI interface.
    public boolean addWorkerToWeeklySchedule(int weekID,int day, int shift, Worker worker) {
        getWeeklySchedule(weekID).getShift(day,shift).addWorkerToShift(worker); return true;
    }
    public boolean removeWorkerToWeeklySchedule(int weekID,int day, int shift, Worker worker) {
        getWeeklySchedule(weekID).getShift(day,shift).removeWorkerToShift(worker); return true;
    }
    public boolean setShiftManagerToWeeklySchedule(int weekID,int day, int shift, Worker worker) {
        getWeeklySchedule(weekID).getShift(day,shift).setShiftManager(worker); return true;
    }
    public List<Worker> showShiftWorkers(int weekID, int day, int shift){
        return getWeeklySchedule(weekID).getShift(day,shift).getShiftWorkers();
    }
    public boolean createWeeklySchedule(int weekID) {
        weekly_Schedules.put(weekID,new Weekly_Schedule());
        return true;
    }
    public void addTransaction(int weekID, int day, int shift, int transactionID){
        Transaction transaction = new Transaction(transactionID);
        getWeeklySchedule(weekID).getShift(day,shift).addTransactaction(transaction);
    }
    public boolean removeTransaction(int weekID, int day, int shift, int transactionID, int workerID){
       return getWeeklySchedule(weekID).getShift(day,shift).removeTransaction(transactionID, workerID);
    }

}
