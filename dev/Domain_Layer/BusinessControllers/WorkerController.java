package Domain_Layer.BusinessControllers;

import Domain_Layer.BusinessObjects.BankAccount;
import Domain_Layer.BusinessObjects.EmploymentConditions;
import Domain_Layer.BusinessObjects.HR;
import Domain_Layer.BusinessObjects.Worker;
import Domain_Layer.Repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class WorkerController {
    private static WorkerController workerController = null;
    private Repository repository = Repository.getInstance();
    LinkedList<Worker> workers;
    HR hr;

    private WorkerController() {
        //TODO:: צריך לשנות פה שיקרא מקובץ טקסט במקום ויכניס לתוך הרשימה...
        workers = new LinkedList<>();
        readWorkersFromData();
        readHRFromData();
    }

    public static WorkerController getInstance() {
        if (workerController == null)
            workerController = new WorkerController();
        return workerController;
    }

    private void readWorkersFromData() {
        workers = repository.readWorkers();
    }

    private void readHRFromData() {
        hr = repository.readHR();
    }

    public int containsWorker(int id, String password) {
        for (Worker w : workers) {
            if (w.getId() == id && w.passwordsMatch(password)) {
                return w.getId();
            }
        }
        return -1; //The login info is incorrect.
    }

    public List<Worker> getWorkers() {
        if (workers == null) readWorkersFromData();
        return workers;
    }

    public boolean addWorker(String name, int id, String password, String email_address, int bankID, int branch, int salary) {
       /* if (repository.addWorker(worker)) {
            workers.add(worker);
            return true;
        }
        return false; */
        Worker worker = getWorker(id);
        if(worker == null) {
            workers.add(new Worker(name, id, password, email_address, new BankAccount(bankID, branch),
                    new EmploymentConditions(salary, new Date()), new LinkedList<>()));
            return true;
        }
        return false;
    }

    public boolean deleteWorker(int workerID) {
       /* for (Worker w : workers) {
            if (w.getId() == workerID) {
                return repository.deleteWorker(w);
            }
        }
        return false; */
        if(hr.getId() == workerID) return false; //Can't remove an HR from the system!
        for (Worker w : WorkerController.getInstance().getWorkers()) {
            if (w.getId() == workerID) {
                workers.remove(w);
                return true;
            }
        }
        return false; //worker doesn't exist in the system
    }

    public Worker getWorker(int workerID) {
        for (Worker w : workers) {
            if (w.getId() == workerID) {
                return w;
            }
        }

        if(hr.getId() == workerID)
            return hr;
        return null;
    }

    public int isHR(int id, String password) {
        if (this.hr.getId() == id && this.hr.passwordsMatch(password))
            return this.hr.getId();
        return -1; //Not an hr;
    }

    public boolean isHR(int id) {
        return this.hr.getId() == id;
    }

    public boolean addJob(String job) {
        return hr.addJob(job);
    }


    public String getWorkerName(int id) {
        for (Worker w : workers) {
            if (w.getId() == id) {
                return w.getName();
            }
        }
        if(this.hr.getId() == id)
            return this.hr.getName();
        return null;
    }

    public boolean editWorker(String name, String password, String email_address, int bankID, int branch, int salary, int workerID) {
        Worker worker = getWorker(workerID);
        if(worker != null) {
            if (!name.equals("")) worker.setName(name);
            if (!password.equals("")) worker.setPassword(password);
            if (!email_address.equals("")) worker.setEmail_address(email_address);
            if (bankID != 0) worker.getBankAccount().setBankID(bankID);
            if (branch != 0) worker.getBankAccount().setBranch(branch);
            if (salary != 0) worker.getEmploymentConditions().setSalary(salary);
            return true;
        }
        return false;
    }

    public boolean addJobForAWorker(int workerID, String job) {
        Worker worker = getWorker(workerID);
        if(worker != null && Worker.getJobs().contains(job.toLowerCase()) && !worker.getWorkerJobs().contains(job.toLowerCase())) {
            worker.getWorkerJobs().add(job.toLowerCase());
            return true;
        }
        return false;
    }

    public boolean removeJobFromAWorker(int workerID, String job) {
        Worker worker = getWorker(workerID);
        if(worker != null && worker.getWorkerJobs().contains(job.toLowerCase())) {
            worker.getWorkerJobs().remove(job.toLowerCase());
            return true;
        }
        return false;
    }
}
