package src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers;



import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.WorkerDTO;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.*;
import src.TransportationsAndWorkersModule.Dal.Workers.LicenseDAO;
import src.TransportationsAndWorkersModule.Dal.Workers.WeeklyScheduleDAO;
import src.TransportationsAndWorkersModule.Dal.Workers.WorkerDAO;
import src.TransportationsAndWorkersModule.Dal.Workers.WorkerScheduleDAO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShiftsController {
    private static ShiftsController shiftsController = null;
    WorkerScheduleDAO workerScheduleDAO;
    WeeklyScheduleDAO weeklyScheduleDAO;
    WorkerDAO workerDAO;
    LicenseDAO licenseDAO;
    private ShiftsController() {
        weeklyScheduleDAO = new WeeklyScheduleDAO();
        workerScheduleDAO = new WorkerScheduleDAO();
        workerDAO = new WorkerDAO();
        licenseDAO = new LicenseDAO();
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
    private Weekly_Schedule getWeeklySchedule(int weekID, String site) throws Exception {
        try {
            return weeklyScheduleDAO.get(weekID,site);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Weekly_Schedule getWeeklyScheduleSL(int weekID,int callerID,String site ) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see weekly schedule");
            return getWeeklySchedule(weekID,site);
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
    public boolean addWorkerToWeeklySchedule(int weekID,int day, int shift, String site,  int worker, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can add worker to weekly schedule");
            if(!workerDAO.get(worker).getSite().equals(site))throw new Exception("worker isnt working at the site of this schedule");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID,site);
            weekly_schedule.getShift(day, shift).addWorkerToShift(worker);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean assignWorkerToJobInShift(int weekID, int day, int shift,String site, int id, String job, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can assign worker to job in shift");
            Worker worker = workerDAO.get(id);
            if(!worker.getSite().equals(site))throw new Exception("worker isnt working at the site of this schedule");
            if (!worker.getWorkerJobs().contains(job))throw new Exception("worker is not specialized in this type of job");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID,site);
            weekly_schedule.getShift(day, shift).assignWorkerToJob(id, job);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean removeWorkerFromJobInShift(int weekID, int day, int shift,String site, int worker, String job, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can remove worker from job in shift");
            if (!workerDAO.exists(worker)) throw new Exception("worker doesn't exist");
            if(!workerDAO.get(worker).getSite().equals(site))throw new Exception("worker isnt working at the site of this schedule");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID, site);
            weekly_schedule.getShift(day, shift).removeWorkerFromJob(worker, job);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean removeWorkerFromWeeklySchedule(int weekID,int day, int shift, String site, int worker,int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can remove worker from weekly schedule");
            if (!workerDAO.exists(worker)) throw new Exception("worker doesn't exist");
            if(!workerDAO.get(worker).getSite().equals(site))throw new Exception("worker isnt working at the site of this schedule");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID,site);
            weekly_schedule.getShift(day, shift).removeWorkerFromShift(worker);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean setShiftManagerToWeeklySchedule(int weekID,int day, int shift,String site, int id, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can assign shift manager to weekly schedule");
            if (!workerDAO.exists(id)) throw new Exception("worker doesn't exist");
            if(!workerDAO.get(id).getSite().equals(site))throw new Exception("worker isnt working at the site of this schedule");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID,site);
            weekly_schedule.getShift(day, shift).setShiftManager(id);
            weeklyScheduleDAO.update(weekly_schedule);
            return true;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<Worker> showShiftWorkers(int weekID, int day, int shift,String site, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see shift workers");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID, site);
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
    public Worker getShiftManager(int weekID, int day, int shift, String site, int callerID) throws Exception {
        try {
            //todo maybe we need to change it to return shift manager instead?
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see shift manager");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID,site);
            Shift sft = weekly_schedule.getShift(day, shift);
            int id = sft.getShift_manager();
            if (id==-1)throw new Exception("shift manager does not exists");
            return workerDAO.get(id);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean createWeeklySchedule(int weekID, int callerID, String site) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can create weekly schedule");
            weeklyScheduleDAO.create(new Weekly_Schedule(weekID,site));
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean addTransaction(int weekID, int day, int shift,String site, int transactionID, int workerID) throws Exception {
        try {
            Transaction transaction = new Transaction(transactionID, workerID);
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID,site);
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
    public boolean removeTransaction(int weekID, int day, int shift,String site, int transactionID, int workerID) throws Exception {
       try {
           Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID,site);
           weekly_schedule.getShift(day, shift).removeTransaction(transactionID, workerID);
           weeklyScheduleDAO.update(weekly_schedule);
           return true;
       }
       catch(Exception e){
           throw new Exception(e.getMessage());
       }
    }
    public boolean isShiftIsReady (int weekID, int day, int shift,String site, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see if shift is ready");
            Weekly_Schedule weekly_schedule = getWeeklySchedule(weekID,site);
            Shift sft = weekly_schedule.getShift(day, shift);
            if (sft.isShiftIsReady())return true;
            throw new Exception("shift was not ready");
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean isWeeklyScheduleReady (int weekID,String site, int callerID) throws Exception {
        try {
            if (workerDAO.readHR().getId()!=callerID)throw new Exception("only HR can see if weekly schedule is ready");
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 2; j++) {
                    if (!isShiftIsReady(weekID, i, j,site, callerID)) return false;
                }
            }
            return true;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Worker> getDriversInShift(int weekID, String site, int day, int shift) throws Exception {
        try {
            List<Worker> drivers = new LinkedList<>();
            HashMap<String, List<Integer>> jobs = weeklyScheduleDAO.get(weekID,site).getShift(day, shift).getJobToWorker();
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

    public boolean isTransportationLegal(int weekID,String site, int day, int shift) throws Exception {
        try {
            HashMap<String, List<Integer>> jobs = weeklyScheduleDAO.get(weekID,site).getShift(day, shift).getJobToWorker();
            List<Integer> storeKeepersIds = jobs.get("store keeper");
            if (storeKeepersIds.isEmpty())return false;
            return true;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<WorkerDTO> getAllDrivers(int weekId , int Day , int shiftType) throws Exception {
        try {
            HashMap<String, List<Integer>> jobs = weeklyScheduleDAO.get(weekId,"driver department").getShift(Day, shiftType).getJobToWorker();
            List<Worker> driversList = new LinkedList<>();
            List<Integer> driverIDS = jobs.get("driver");
            List<String> license = new LinkedList<>();
            List<WorkerDTO> workerToRet = new LinkedList<>();
            for(int wid: driverIDS){
                driversList.add(workerDAO.get(wid));
            }
            for (Worker w: driversList){
                license.add(licenseDAO.get(w.getId()));//if even one of the workers doesnt have license data about himself it wont return you a list of the workers
            }
            for (int i =0; i<driversList.size(); i++){
                workerToRet.add(new WorkerDTO(driversList.get(i).getId(),license.get(i),driversList.get(i).getName()));
            }
            return workerToRet;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
