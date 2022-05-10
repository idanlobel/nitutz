package src.Service_Layer;

import src.Domain_Layer.BusinessControllers.ShiftsController;
import src.Domain_Layer.BusinessControllers.WorkerController;
import src.Domain_Layer.BusinessObjects.Shift;
import src.Domain_Layer.BusinessObjects.Weekly_Schedule;
import src.Domain_Layer.BusinessObjects.Worker;
import src.Domain_Layer.BusinessObjects.Worker_Schedule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ServiceLayer {
    WorkerController workerController;
    ShiftsController shiftsController;

    public ServiceLayer() {
        this.workerController = WorkerController.getInstance();
        this.shiftsController = ShiftsController.getInstance();
    }

    //all of these are HR methods
    public boolean addWorkerToWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        try {
            Worker worker = workerController.getWorker(workerID);
            return shiftsController.addWorkerToWeeklySchedule(weekID, day, shift, worker, callerID);
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean removeWorkerFromWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        try {
            Worker worker = workerController.getWorker(workerID);
            return shiftsController.removeWorkerFromWeeklySchedule(weekID, day, shift, worker,callerID);
            //TODO:: change it later - deal with Response here.
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean assignWorkerToJobInShift(int callerID,int weekID,int day, int shift, int workerID, String job) {
        try {
            Worker worker = workerController.getWorker(workerID);
            return shiftsController.assignWorkerToJobInShift(weekID, day, shift, worker, job,callerID);
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean removeWorkerFromJobInShift(int callerID,int weekID,int day, int shift, int workerID,String job) {
        try {
            Worker worker = workerController.getWorker(workerID);
            return shiftsController.removeWorkerFromJobInShift(weekID, day, shift, worker, job,callerID);
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean setShiftManagerToWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        try {
            Worker worker = workerController.getWorker(workerID);
            return shiftsController.setShiftManagerToWeeklySchedule(weekID, day, shift, worker,callerID);
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public WeeklyScheduleSL viewWeeklySchedule(int callerID, int weekID){
        //TODO: FIX THIS - A LOT OF NULL POINTER EXCEPTIONS
       try {
           Weekly_Schedule weekly_schedule = shiftsController.getWeeklyScheduleSL(weekID, callerID);
           if (weekly_schedule != null) {//if it was actually created at all
               ShiftSL[][] shiftSLS = new ShiftSL[5][2];
               Shift[][] shifts = weekly_schedule.getSchedule();
               if (shifts != null) {//should not be null anyway
                   for (int i = 0; i < 5; i++) {
                       List<WorkerSL> workerSLS0 = new LinkedList<>();
                       int managerID = -1;
                       HashMap<String, List<Integer>> jobToWorkers = new HashMap<>();
                       if (shifts[i][0] != null) {
                           List<Worker> workers = shifts[i][0].getShiftWorkers();
                           jobToWorkers = shifts[i][0].getJobToWorker();
                           if (workers != null) {
                               for (Worker worker : workers) {
                                   WorkerSL workerSL = new WorkerSL(worker.getName(), worker.getId(), worker.getWorkerJobs());
                                   workerSLS0.add(workerSL);
                               }
                               if (shifts[i][0].getShift_manager() != null) {
                                   managerID = shifts[i][0].getShift_manager().getId();
                               }
                           }
                       }
                       shiftSLS[i][0] = new ShiftSL(workerSLS0, managerID, jobToWorkers);
                       List<WorkerSL> workerSLS1 = new LinkedList<>();
                       managerID = -1;
                       HashMap<String, List<Integer>> jobToWorkers2 = new HashMap<>();
                       if (shifts[i][1] != null) {
                           List<Worker> workers1 = shifts[i][1].getShiftWorkers();
                           jobToWorkers2 = shifts[i][0].getJobToWorker();
                           if (workers1 != null) {
                               for (Worker worker : workers1) {
                                   WorkerSL workerSL = new WorkerSL(worker.getName(), worker.getId(), worker.getWorkerJobs());
                                   workerSLS1.add(workerSL);
                               }
                               if (shifts[i][1].getShift_manager() != null) {
                                   managerID = shifts[i][1].getShift_manager().getId();
                               }
                           }
                       }
                       shiftSLS[i][1] = new ShiftSL(workerSLS1, managerID, jobToWorkers2);
                   }
                   return new WeeklyScheduleSL(shiftSLS);
               }
           }
           return null; //TODO:: change it later - deal with Response here.
       }
       catch (Exception e){
           return null; //TODO:: change it later - deal with Response here.
       }
    }
    public List<WorkerSL> showShiftWorkers(int callerID,int weekID, int day, int shift){
        try {
            List<WorkerSL> workerSLS = new LinkedList<>();
            List<Worker> workers = shiftsController.showShiftWorkers(weekID, day, shift,callerID);//todo check if it may return null list//should be an empty list instead
            for (Worker worker : workers) {
                WorkerSL workerSL = new WorkerSL(worker.getName(), worker.getId(), worker.getWorkerJobs());
                workerSLS.add(workerSL);
            }
            return workerSLS;
        }
        catch (Exception e){
            return null; //TODO:: change it later - deal with Response here.
        }
    }
    public String getShiftManagerInfo (int callerID,int weekID, int day, int shift){
        try {
            Worker worker = shiftsController.getShiftManager(weekID, day, shift,callerID);
            return worker.getName() + ": " + worker.getId();
        }
        catch(Exception e){
            return e.getMessage(); //TODO:: change it later - deal with Response here.
        }
    }
    public boolean createWeeklySchedule(int callerID,int weekID) {
        try {
            return shiftsController.createWeeklySchedule(weekID,callerID);
        }catch (Exception e){
            return false;
        }
    }
    public boolean addJobs(int callerID, String job){
        try {
            return workerController.addJob(job,callerID);
        }catch (Exception e){
            return false;
        }
    }

    public List<WorkerSL> showAllWorkers(int callerID){
        try {
            List<WorkerSL> workerSLS = new LinkedList<>();
            List<Worker> workers = workerController.getWorkers(callerID);
            for (Worker worker : workers) {
                WorkerSL workerSL = new WorkerSL(worker.getName(), worker.getId(), worker.getWorkerJobs());
                workerSLS.add(workerSL);
            }
            return workerSLS;
        }catch (Exception e){
            return null;
        }

    }

    public boolean registerAWorker(int callerID, String name, int id, String password, String email_address, int bankID,
                                   int branch, int salary){
        try{
            return workerController.addWorker(name, id, password, email_address, bankID, branch, salary, callerID);
        }catch (Exception e){
            return false;
        }

    }

    public boolean removeAWorker(int callerID, int workerID){
        try{
            return workerController.deleteWorker(workerID, callerID);
        }catch (Exception e){
            return false;
        }

    }

    public boolean editAWorker(/*int callerID, */ String name, String password, String email_address, int bankID,
                                   int branch, int salary, int workerID){
        try{
            return workerController.editWorker(name, password, email_address, bankID, branch, salary, workerID);

        }catch (Exception e){
            return false;
        }
        //if (!workerController.isHR(callerID)) throw Exception(); //TODO:: we actually need to throw exceptions or responses like we did last year
    }

    public boolean addJobForAWorker(int callerID, int workerID, String job){
        try{
            return workerController.addJobForAWorker(workerID, job, callerID);
        }catch (Exception e){
            return false;
        }

    }

    public boolean removeJobFromAWorker(int callerID, int workerID, String job){
        try{
            return workerController.removeJobFromAWorker(workerID, job, callerID);
        }catch (Exception e){
            return false;
        }

    }

    //these are shiftManager methods
    public boolean addTransaction(int weekID, int day, int shift, int transactionID, int workerID){
        try {
            return shiftsController.addTransaction(weekID, day, shift, transactionID, workerID);
        }
        catch(Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean removeTransaction(int callerID, int weekID, int day, int shift, int transactionID){
        try {
            return shiftsController.removeTransaction(weekID, day, shift, transactionID, callerID);
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }

    //these are worker Methods
    public boolean editWorkerSchedule(int workerID, boolean present, int day, int shiftType){
        try {
            return shiftsController.editWorkerSchedule(workerID, present, day, shiftType);
        }
        catch(Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    //the following method can be used by the HR as well:
    public WorkerScheduleSL viewWorkerSchedule(int workerID){
        try {
            Worker_Schedule worker_schedule = shiftsController.getWorkerSchedule(workerID);//todo see if it could return null, needs to be an exception if non existent
            return new WorkerScheduleSL(worker_schedule.getSchedule());
        }catch (Exception e){
            return null;
        }
    }

    public boolean isShiftReady(int callerID, int weekID, int day, int shift){
        try {
            return shiftsController.isShiftIsReady(weekID, day, shift,callerID);
        }
        catch(Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }

    public boolean isWeeklyScheduleReady(int callerID, int weekID){
        try {
            return shiftsController.isWeeklyScheduleReady(weekID,callerID);
        }
        catch(Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
}
