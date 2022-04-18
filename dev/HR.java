import java.util.LinkedList;
import java.util.List;

public class HR extends Worker {

    public HR(String name, int id, String email_address, BankAccount bankAccount, List<String> jobs) {
        super(name, id, email_address, bankAccount, jobs);
    }

    //Adding a new worker to the database (will be deleted at the end of the current running program)
    public boolean addWorker(Worker worker) {
        for (Worker w : TempData.getInstance().workers) {
            if (w.id == worker.id) {
                return false; //There's already such worker
            }
        }
        TempData.getInstance().workers.add(worker);
        return true;
    }

    //"Read" - from workers
    public void showWorkers() {
        System.out.println("Workers: ");
        for (Worker w : TempData.getInstance().workers) {
            System.out.println(w.toString());
        }
    }

    //TODO:: For the moment we can't implement that, because we need to know what fields he wants to update...
    //Update an employee
   /* public boolean editWorker(int workerID){
        for(Worker w: TempData.getInstance().workers) {
            if (w.id == workerID) {
                return false; //There's already such worker
            }
        }
    }
    */

    public boolean deleteWorker(int workerID){
        for (Worker w : TempData.getInstance().workers) {
            if (w.id == workerID) {
                TempData.getInstance().workers.remove(w);
                return true;
            }
        }
        return false; //There's no such worker
    }

    public boolean addJob (String job){
            if(this.hasJob(job))
                return false; //Job already exists
            this.jobs.add(job); //TODO:: NOT GOOD!! WE NEED TO ADD THIS JOB TO ALL OF THE WORKERS...
                                // MAYBE CREATE A SINGLETON CLASS JOB?
            return true;
    }
}
