package Service_Layer;

import Domain_Layer.BusinessControllers.ShiftsController;
import Domain_Layer.BusinessControllers.WorkerController;
import Domain_Layer.BusinessObjects.*;

import java.util.LinkedList;
import java.util.List;

public class ServiceLayer {
    WorkerController workerController = WorkerController.getInstance();
    ShiftsController shiftsController = ShiftsController.getInstance();
    //all of these are HR methods
    public boolean addWorkerToWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        if (!workerController.isHR(callerID))return false;
        Worker worker = workerController.getWorker(workerID);
        return shiftsController.addWorkerToWeeklySchedule(weekID,day, shift,worker);
    }
    public boolean removeWorkerToWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        if (!workerController.isHR(callerID))return false;
        Worker worker = workerController.getWorker(workerID);
        return shiftsController.removeWorkerToWeeklySchedule(weekID,day, shift,worker);
    }
    public boolean setShiftManagerToWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        if (!workerController.isHR(callerID))return false;
        Worker worker = workerController.getWorker(workerID);
        return shiftsController.setShiftManagerToWeeklySchedule(weekID,day, shift,worker);
    }
    public WeeklyScheduleSL viewWeeklySchedule(int callerID, int weekID){
        if (!workerController.isHR(callerID))return null;//we actually need to throw exceptions or responses liek we did last year
        Weekly_Schedule weekly_schedule = shiftsController.getWeeklySchedule(weekID);
        ShiftSL[][] shiftSLS = new ShiftSL[5][2];
        Shift[][] shifts = weekly_schedule.getSchedule();
        for (int i =0;i<5;i++) {
            List<WorkerSL> workerSLS0 = new LinkedList<>();
            List<Worker> workers = shifts[i][0].getShiftWorkers();
            for (Worker worker:workers) {
                WorkerSL workerSL = new WorkerSL(worker.getName(),worker.getId(),worker.getWorkerJobs());
                workerSLS0.add(workerSL);
            }
            shiftSLS[i][0] = new ShiftSL(workerSLS0,shifts[i][0].getShift_manager().getId());
            List<WorkerSL> workerSLS1 = new LinkedList<>();
            List<Worker> workers1 = shifts[i][1].getShiftWorkers();
            for (Worker worker:workers1) {
                WorkerSL workerSL = new WorkerSL(worker.getName(),worker.getId(),worker.getWorkerJobs());
                workerSLS1.add(workerSL);
            }
            shiftSLS[i][1] = new ShiftSL(workerSLS1,shifts[i][1].getShift_manager().getId());
        }
        return new WeeklyScheduleSL(shiftSLS);
    }
    public List<WorkerSL> showShiftWorkers(int callerID,int weekID, int day, int shift){
        if (!workerController.isHR(callerID))return null;//we actually need to throw exceptions or responses liek we did last year
        List<WorkerSL> workerSLS = new LinkedList<>();
        List<Worker> workers = shiftsController.showShiftWorkers(weekID,day,shift);
        for (Worker worker:workers) {
            WorkerSL workerSL = new WorkerSL(worker.getName(),worker.getId(),worker.getWorkerJobs());
            workerSLS.add(workerSL);
        }
        return workerSLS;
    }
    public boolean createWeeklySchedule(int callerID,int weekID) {
        if (!workerController.isHR(callerID))return false;
        return shiftsController.createWeeklySchedule(weekID);
    }
    public boolean addJobs(int callerID, String job){
        if(!workerController.isHR(callerID))return false;
        return workerController.addJob(job);
    }
    //these are shiftManager methods
    public void addTransaction(int weekID, int day, int shift, int transactionID){
        shiftsController.addTransaction(weekID,day,shift,transactionID);
    }
    public boolean removeTransaction(int callerID, int weekID, int day, int shift, int transactionID){
        return shiftsController.removeTransaction(weekID,day,shift,transactionID, callerID);
    }
    //these are worker Methods
    public boolean editWorkerSchedule(int workerID, boolean present, int day, int shiftType){
        return shiftsController.editWorkerSchedule(workerID,present,day,shiftType);
    }
    public WorkerScheduleSL viewWorkerSchedule(int workerID){
        Worker_Schedule worker_schedule = shiftsController.getWorkerSchedule(workerID);
        return new WorkerScheduleSL(worker_schedule.getSchedule());
    }
}
