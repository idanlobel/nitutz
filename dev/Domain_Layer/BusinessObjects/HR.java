package Domain_Layer.BusinessObjects;

import Domain_Layer.BusinessControllers.WorkerController;

import java.util.List;

public class HR extends Worker {

    static int weekID;
    public HR(String name, int id, String password, String email_address, BankAccount bankAccount, List<String> jobs) {
        super(name, id, password, email_address, bankAccount, jobs);
        weekID = 1;
        // or read from database
    }
    //Adding a new worker to the database (will be deleted at the end of the current running program)
    public boolean addWorker(Worker worker) {
        for (Worker w : WorkerController.getInstance().getWorkers()) {
            if (w.id == worker.id) {
                return false; //There's already such worker
            }
        }
        return WorkerController.getInstance().addWorker(worker);
    }

    public boolean deleteWorker(int workerID){
        for (Worker w : WorkerController.getInstance().getWorkers()) {
            if (w.id == workerID) {
                return WorkerController.getInstance().deleteWorker(workerID);
            }
        }
        return false; //There's no such worker
    }

    public boolean addJob (String job){
            if(hasJob(job))
                return false; //Job already exists
            jobs.add(job);
            return true;
    }
}
