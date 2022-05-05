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
            if (!workerController.isHR(callerID)) return false; //TODO:: change it later - deal with Response here.
            Worker worker = workerController.getWorker(workerID);
            if (worker != null) {
                return shiftsController.addWorkerToWeeklySchedule(weekID, day, shift, worker);
            }
            return false; //TODO:: change it later - deal with Response here.
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean removeWorkerFromWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        try {
            if (!workerController.isHR(callerID)) return false; //TODO:: change it later - deal with Response here.
            Worker worker = workerController.getWorker(workerID);
            if (worker != null) {
                return shiftsController.removeWorkerFromWeeklySchedule(weekID, day, shift, worker);
            } else return false; //TODO:: change it later - deal with Response here.
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean assignWorkerToJobInShift(int callerID,int weekID,int day, int shift, int workerID, String job) {
        try {
            if (!workerController.isHR(callerID)) return false; //TODO:: change it later - deal with Response here.
            Worker worker = workerController.getWorker(workerID);
            if (worker != null) {
                return shiftsController.assignWorkerToJobInShift(weekID, day, shift, worker, job);
            }
            return false; //TODO:: change it later - deal with Response here.
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean removeWorkerFromJobInShift(int callerID,int weekID,int day, int shift, int workerID,String job) {
        try {
            if (!workerController.isHR(callerID)) return false; //TODO:: change it later - deal with Response here.
            Worker worker = workerController.getWorker(workerID);
            if (worker != null) {
                return shiftsController.removeWorkerFromJobInShift(weekID, day, shift, worker, job);
            } else return false; //TODO:: change it later - deal with Response here.
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public boolean setShiftManagerToWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        try {
            if (!workerController.isHR(callerID)) return false; //TODO:: change it later - deal with Response here.
            Worker worker = workerController.getWorker(workerID);
            if (worker != null) {
                return shiftsController.setShiftManagerToWeeklySchedule(weekID, day, shift, worker);
            } else return false; //TODO:: change it later - deal with Response here.
        }
        catch (Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
    public WeeklyScheduleSL viewWeeklySchedule(int callerID, int weekID){
        //TODO: FIX THIS - A LOT OF NULL POINTER EXCEPTIONS
       try {
           if (!workerController.isHR(callerID))
               return null;//TODO:: we actually need to throw exceptions or responses like we did last year
           Weekly_Schedule weekly_schedule = shiftsController.getWeeklySchedule(weekID);
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
            if (!workerController.isHR(callerID))
                return null;//TODO:: change it later - deal with Response here.
            List<WorkerSL> workerSLS = new LinkedList<>();
            List<Worker> workers = shiftsController.showShiftWorkers(weekID, day, shift);
            if (workers != null) {
                for (Worker worker : workers) {
                    WorkerSL workerSL = new WorkerSL(worker.getName(), worker.getId(), worker.getWorkerJobs());
                    workerSLS.add(workerSL);
                }
            }
            return workerSLS;
        }
        catch (Exception e){
            return null; //TODO:: change it later - deal with Response here.
        }
    }
    public String getShiftManagerInfo (int callerID,int weekID, int day, int shift){
        try {
            if (!workerController.isHR(callerID))
                return "Only an HR can perform this action.";//TODO:: we actually need to throw exceptions or responses like we did last year
            Worker worker = shiftsController.getShiftManager(weekID, day, shift);
            if (worker != null) {
                return worker.getName() + ": " + worker.getId();
            }
            return "There's currently no shift manager.";
        }
        catch(Exception e){
            return e.getMessage(); //TODO:: change it later - deal with Response here.
        }
    }
    public boolean createWeeklySchedule(int callerID,int weekID) {
        try {
            if (!workerController.isHR(callerID))return false;
            return shiftsController.createWeeklySchedule(weekID);
        }catch (Exception e){
            return false;
        }

    }
    public boolean addJobs(int callerID, String job){
        try {
            if(!workerController.isHR(callerID))return false;
            return workerController.addJob(job);
        }catch (Exception e){
            return false;
        }
    }

    public List<WorkerSL> showAllWorkers(int callerID){
        try {
            if (!workerController.isHR(callerID))return null; //TODO:: we actually need to throw exceptions or responses like we did last year
            List<WorkerSL> workerSLS = new LinkedList<>();
            List<Worker> workers = workerController.getWorkers();
            if(workers != null) {
                for (Worker worker : workers) {
                    WorkerSL workerSL = new WorkerSL(worker.getName(), worker.getId(), worker.getWorkerJobs());
                    workerSLS.add(workerSL);
                }
                return workerSLS;
            }
            return null;
        }catch (Exception e){
            return null;
        }

    }

    public boolean registerAWorker(int callerID, String name, int id, String password, String email_address, int bankID,
                                   int branch, int salary){
        try{
            if (!workerController.isHR(callerID))return false; //TODO:: we actually need to throw exceptions or responses like we did last year
            return workerController.addWorker(name, id, password, email_address, bankID, branch, salary);
        }catch (Exception e){
            return false;
        }

    }

    public boolean removeAWorker(int callerID, int workerID){
        try{
            if (!workerController.isHR(callerID))return false; //TODO:: we actually need to throw exceptions or responses like we did last year
            return workerController.deleteWorker(workerID);
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
            if (!workerController.isHR(callerID))return false; //TODO:: we actually need to throw exceptions or responses like we did last year
            return workerController.addJobForAWorker(workerID, job);
        }catch (Exception e){
            return false;
        }

    }

    public boolean removeJobFromAWorker(int callerID, int workerID, String job){
        try{
            if (!workerController.isHR(callerID))return false; //TODO:: we actually need to throw exceptions or responses like we did last year
            return workerController.removeJobFromAWorker(workerID, job);
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
            Worker_Schedule worker_schedule = shiftsController.getWorkerSchedule(workerID);
            if(worker_schedule != null) {
                return new WorkerScheduleSL(worker_schedule.getSchedule());
            }
            return null;//TODO:: change it later - deal with Response here.
        }catch (Exception e){
            return null;
        }

    }

    public boolean isShiftReady(int callerID, int weekID, int day, int shift){
        try {
            if (!workerController.isHR(callerID)) return false; //TODO:: change it later - deal with Response here.
            return shiftsController.isShiftIsReady(weekID, day, shift);
        }
        catch(Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }

    public boolean isWeeklyScheduleReady(int callerID, int weekID){
        try {
            if (!workerController.isHR(callerID)) return false; //TODO:: change it later - deal with Response here.
            return shiftsController.isWeeklyScheduleReady(weekID);
        }
        catch(Exception e){
            return false; //TODO:: change it later - deal with Response here.
        }
    }
}
