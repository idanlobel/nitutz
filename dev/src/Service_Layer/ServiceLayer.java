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
    public Response<Boolean> addWorkerToWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        Response<Boolean> response;
        try {
            Worker worker = workerController.getWorker(workerID);
            response = Response.FromValue(shiftsController.addWorkerToWeeklySchedule(weekID, day, shift, workerID, callerID));
        }
        catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<Boolean> removeWorkerFromWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        Response<Boolean> response;
        try {
            Worker worker = workerController.getWorker(workerID);
            response = Response.FromValue(shiftsController.removeWorkerFromWeeklySchedule(weekID, day, shift, workerID,callerID));
            //TODO:: change it later - deal with Response here.
        }
        catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<Boolean> assignWorkerToJobInShift(int callerID,int weekID,int day, int shift, int workerID, String job) {
        Response<Boolean> response;
        try {
            Worker worker = workerController.getWorker(workerID);
            response = Response.FromValue(shiftsController.assignWorkerToJobInShift(weekID, day, shift, weekID, job,callerID));
        }
        catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<Boolean> removeWorkerFromJobInShift(int callerID,int weekID,int day, int shift, int workerID,String job) {
        Response<Boolean> response;
        try {
            Worker worker = workerController.getWorker(workerID);
            response = Response.FromValue(shiftsController.removeWorkerFromJobInShift(weekID, day, shift, workerID, job,callerID));
        }
        catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<Boolean> setShiftManagerToWeeklySchedule(int callerID,int weekID,int day, int shift, int workerID) {
        Response<Boolean> response;
        try {
            Worker worker = workerController.getWorker(workerID);
            response = Response.FromValue(shiftsController.setShiftManagerToWeeklySchedule(weekID, day, shift, workerID,callerID));
        }
        catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<WeeklyScheduleSL> viewWeeklySchedule(int callerID, int weekID){
        Response<WeeklyScheduleSL> response;
       try {
           Weekly_Schedule weekly_schedule = shiftsController.getWeeklyScheduleSL(weekID, callerID);
           ShiftSL[][] shiftSLS = new ShiftSL[5][2];
           Shift[][] shifts = weekly_schedule.getSchedule();
           for (int i = 0; i < 5; i++) {
               for (int j = 0; j<2; j++){
                   Response<List<WorkerSL>> response1 = showShiftWorkers(callerID, weekID, i, j);
                   if (response1.ErrorOccured())throw new Exception(response1.ErrorMessage);
                   List<WorkerSL> workerSLS = response1.value;
                   int managerID = shifts[i][j].getShift_manager();
                   HashMap<String, List<Integer>> jobToWorkers = shifts[i][j].getJobToWorker();
                   shiftSLS[i][j] = new ShiftSL(workerSLS, managerID, jobToWorkers);
               }
           }
               response = Response.FromValue(new WeeklyScheduleSL(shiftSLS));
       }
       catch (Exception e){
           response = Response.FromError(e.getMessage());
       }
        return response;
    }
    public Response<List<WorkerSL>> showShiftWorkers(int callerID,int weekID, int day, int shift){
        Response<List<WorkerSL>> response;
        try {
            List<WorkerSL> workerSLS = new LinkedList<>();
            List<Worker> workers = shiftsController.showShiftWorkers(weekID, day, shift,callerID);//todo check if it may return null list//should be an empty list instead
            for (Worker worker : workers) {
                WorkerSL workerSL = new WorkerSL(worker.getName(), worker.getId(), worker.getWorkerJobs());
                workerSLS.add(workerSL);
            }
            response = Response.FromValue(workerSLS);
        }
        catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<String> getShiftManagerInfo (int callerID,int weekID, int day, int shift){
        Response<String> response;
        try {
            Worker worker = shiftsController.getShiftManager(weekID, day, shift,callerID);
            response = Response.FromValue(worker.getName() + ": " + worker.getId());
        }
        catch(Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<Boolean> createWeeklySchedule(int callerID,int weekID) {
        Response<Boolean> response;
        try {
            response = Response.FromValue(shiftsController.createWeeklySchedule(weekID,callerID));
        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<Boolean> addJobs(int callerID, String job){
        Response<Boolean> response;
        try {
            response = Response.FromValue(workerController.addJob(job,callerID));
        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    public Response<List<WorkerSL>> showAllWorkers(int callerID){
        Response<List<WorkerSL>> response;
        try {
            List<WorkerSL> workerSLS = new LinkedList<>();
            List<Worker> workers = workerController.getWorkers(callerID);
            for (Worker worker : workers) {
                WorkerSL workerSL = new WorkerSL(worker.getName(), worker.getId(), worker.getWorkerJobs());
                workerSLS.add(workerSL);
            }
            response = Response.FromValue(workerSLS);
        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    public Response<Boolean> registerAWorker(int callerID, String name, int id, String password, String email_address, int bankID,
                                   int branch, int salary){
        Response<Boolean> response;
        try{
           response = Response.FromValue(workerController.addWorker(name, id, password, email_address, bankID, branch, salary, callerID));
        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    public Response<Boolean> removeAWorker(int callerID, int workerID){
        Response<Boolean> response;
        try{
            response = Response.FromValue(workerController.deleteWorker(workerID, callerID));
        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    public Response<Boolean> editAWorker(int callerID, String name, String password, String email_address, int bankID,
                                   int branch, int salary, int workerID){
        Response<Boolean> response;
        try{
            response = Response.FromValue(workerController.editWorker(name, password, email_address, bankID, branch, salary, workerID,callerID));

        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    public Response<Boolean> addJobForAWorker(int callerID, int workerID, String job){
        Response<Boolean> response;
        try{
            response = Response.FromValue(workerController.addJobForAWorker(workerID, job, callerID));
        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    public Response<Boolean> removeJobFromAWorker(int callerID, int workerID, String job){
        Response<Boolean> response;
        try{
            response = Response.FromValue(workerController.removeJobFromAWorker(workerID, job, callerID));
        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    //these are shiftManager methods
    public Response<Boolean> addTransaction(int weekID, int day, int shift, int transactionID, int workerID){
        Response<Boolean> response;
        try {
            response = Response.FromValue(shiftsController.addTransaction(weekID, day, shift, transactionID, workerID));
        }
        catch(Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    public Response<Boolean> removeTransaction(int callerID, int weekID, int day, int shift, int transactionID){
        Response<Boolean> response;
        try {
            response = Response.FromValue(shiftsController.removeTransaction(weekID, day, shift, transactionID, callerID));
        }
        catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    //these are worker Methods
    public Response<Boolean> editWorkerSchedule(int workerID, boolean present, int day, int shiftType){
        Response<Boolean> response;
        try {
            response = Response.FromValue(shiftsController.editWorkerSchedule(workerID, present, day, shiftType));
        }
        catch(Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
    //the following method can be used by the HR as well:
    public Response<WorkerScheduleSL> viewWorkerSchedule(int workerID){
        Response<WorkerScheduleSL> response;
        try {
            Worker_Schedule worker_schedule = shiftsController.getWorkerSchedule(workerID);//todo see if it could return null, needs to be an exception if non existent
           response = Response.FromValue(new WorkerScheduleSL(worker_schedule.getSchedule()));
        }catch (Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    public Response<Boolean> isShiftReady(int callerID, int weekID, int day, int shift){
        Response<Boolean> response;
        try {
            response = Response.FromValue(shiftsController.isShiftIsReady(weekID, day, shift,callerID));
        }
        catch(Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }

    public Response<Boolean> isWeeklyScheduleReady(int callerID, int weekID){
        Response<Boolean> response;
        try {
            response = Response.FromValue(shiftsController.isWeeklyScheduleReady(weekID,callerID));
        }
        catch(Exception e){
            response = Response.FromError(e.getMessage());
        }
        return response;
    }
}
