package src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers;



import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.WorkerDTO;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.BankAccount;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.EmploymentConditions;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker_Schedule;
import src.TransportationsAndWorkersModule.Dal.Workers.JobsDAO;
import src.TransportationsAndWorkersModule.Dal.Workers.LicenseDAO;
import src.TransportationsAndWorkersModule.Dal.Workers.WorkerDAO;
import src.TransportationsAndWorkersModule.Dal.Workers.WorkerScheduleDAO;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class WorkerController {
    private static WorkerController workerController = null;
    WorkerDAO workers;
    WorkerScheduleDAO workerScheduleDAO;

    JobsDAO jobsDAO;
    LicenseDAO licenseDAO;

    private WorkerController() {
        //TODO:: צריך לשנות פה שיקרא מקובץ טקסט במקום ויכניס לתוך הרשימה...
        workers = new WorkerDAO();
        workerScheduleDAO = new WorkerScheduleDAO();
        jobsDAO = new JobsDAO();
        licenseDAO = new LicenseDAO();
    }

    public static WorkerController getInstance() {
        if (workerController == null)
            workerController = new WorkerController();
        return workerController;
    }

    public int idPassMatch(int id, String password) {
        try {
            Worker worker = getWorker(id);
            if (worker.getPassword().equals(password))return id;
            return -1;//login info incorrect
        }catch (Exception e){
            return -1;//worker doesnt exist (might need to send an exception instead)
        }
    }

    public List<Worker> getWorkers(int callerID) throws Exception {
        if (workers.readHR().getId()!=callerID)throw new Exception("only HR can see all worker");
        return workers.getAllWorkers();
    }

    public boolean addWorker(String name, int id, String password, String email_address,
                             int bankID, int branch, int salary, int callerID, String site) throws Exception {
        try {
            if (workers.readHR().getId()!=callerID)throw new Exception("only HR can add a new worker to the system");
            workers.create(new Worker(name, id, password, email_address, new BankAccount(bankID, branch),
                            new EmploymentConditions(salary, new Date()), new LinkedList<>(),site));
            workerScheduleDAO.create(new Worker_Schedule(id));
            return true;
        }catch (Exception e){
            //delete the worker and workerSchedule data if accidently created.
            throw  new Exception(e.getMessage());
        }
    }

    public boolean deleteWorker(int workerID, int callerID) throws Exception{
        if(workers.readHR().getId() == workerID) throw new Exception("Can't remove HR!");
        try {
            workers.get(workerID);
            workers.delete(workerID);
            workerScheduleDAO.delete(workerID);
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Worker getWorker(int workerID) throws Exception {
        try {
            if(workers.readHR().getId() == workerID)
                return workers.readHR();
            return workers.get(workerID);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public int isHR(int id, String password) throws Exception {
        try {
            if (workers.readHR().getId() == id && workers.readHR().passwordsMatch(password))
                return id;
            return -1; //Not an hr;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean isHR(int id) throws Exception {
        return workers.readHR().getId() == id;
    }

    public boolean addJob(String job, int callerID) throws Exception {
        try {
            if (workers.readHR().getId()!=callerID)throw new Exception("only HR can add jobs");
            jobsDAO.add(job);
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public String getWorkerName(int id) throws Exception {
        try {
        if(workers.readHR().getId() == id)
            return workers.readHR().getName();
        return getWorker(id).getName();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean editWorker(String name, String password, String email_address, int bankID, int branch, int salary, int workerID, int callerID) throws Exception {
        try {
            if (workers.readHR().getId()!=callerID)throw new Exception("only HR can edit worker");
            Worker worker = getWorker(workerID);
            if (!name.equals("")) worker.setName(name);
            if (!password.equals("")) worker.setPassword(password);
            if (!email_address.equals("")) worker.setEmail_address(email_address);
            if (bankID != 0) worker.getBankAccount().setBankID(bankID);
            if (branch != 0) worker.getBankAccount().setBranch(branch);
            if (salary != 0) worker.getEmploymentConditions().setSalary(salary);
            workers.update(worker);
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean addJobForAWorker(int workerID, String job, int callerID) throws Exception {
        try {
            Worker worker = getWorker(workerID);
            if (workers.readHR().getId()!=callerID)throw new Exception("only HR can add a job to a worker");
            if(jobsDAO.exists(job.toLowerCase()) && !worker.getWorkerJobs().contains(job.toLowerCase())){
                worker.getWorkerJobs().add(job.toLowerCase());
                workers.update(worker);
                return true;
            }
            throw new Exception("This job is problematic");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean removeJobFromAWorker(int workerID, String job, int callerID) throws Exception {
        if (workers.readHR().getId()!=callerID)throw new Exception("only HR can remove a job to a worker");
        Worker worker = getWorker(workerID);
        try {
            if (worker.getWorkerJobs().contains(job.toLowerCase())){
                worker.getWorkerJobs().remove(job.toLowerCase());
                workers.update(worker);
                return true;
            }
            return false;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean Login(int ID, String password) throws Exception {
        try {
            Worker worker = workers.get(ID);
            if (worker.getPassword().equals(password))return true;
            return false;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public boolean addLicnese(int callerID,int ID, String name) throws Exception{
        try {
            if (!isHR(callerID)) throw new Exception("ONLY HR CAN ADD LICENSE");
            if (!workers.get(ID).getWorkerJobs().contains("driver"))throw new Exception("worker isnt a driver");
            licenseDAO.create(ID,name);
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean removeLicense(int callerID, int ID, String name)throws Exception{
        try {
            if (!isHR(callerID)) throw new Exception("ONLY HR CAN REMOVE LICENSE");

            if (!workers.get(ID).getWorkerJobs().contains("driver"))throw new Exception("worker isnt a driver");
            licenseDAO.delete(ID);
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
