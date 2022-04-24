package src.Domain_Layer.BusinessObjects;

import src.Domain_Layer.BusinessControllers.WorkerController;

import java.util.*;

public class Shift {
    enum Type {Morning, Evening};
    private boolean shiftIsReady;
    int[] mustJobs_Counter;
    HashMap<String, List<Integer>> jobToWorker;

    public HashMap<String, List<Integer>> getJobToWorker() {
        return jobToWorker;
    }

    //TODO:: צריך לוודא שאנחנו ממלאים את המערך למעלה בכל פעם שאנחנו מוסיפים עובדים או מוחקים עובדים מהרשימה!!!
    Shift_Manager shift_manager;
    List<Worker> workers;
    static List<String> mustJobs = new ArrayList<String>(Arrays.asList("store keeper", "steward", "cashier", "driver"));
    List<Transaction> shift_transactions;
    public Shift(Shift_Manager shift_manager, List<Worker> workers, List<Transaction> transactions) {
        //For when we have a database to read from...
        this.shift_manager = shift_manager;
        this.workers = workers;
        this.shift_transactions = transactions;
        mustJobs_Counter = new int [4]; //initialized by default
        jobToWorker = new HashMap<>();
        shiftIsReady = false;
    }

    public Shift() { //for now, since we have no database...
        this.workers = new LinkedList<>();
        this.shift_transactions = new LinkedList<>();
        mustJobs_Counter = new int [4]; //initialized by default
        shiftIsReady = false;
        jobToWorker = new HashMap<>();
    }

    public List<Worker> getShiftWorkers(){
        return workers;
    }
    public Shift_Manager getShift_manager(){
        return  shift_manager;
    }
    public void setShiftManager(Worker worker){
        shift_manager = new Shift_Manager(worker.name, worker.id, worker.getPassword(), worker.email_address, worker.bankAccount,
                worker.employmentConditions, worker.workerJobs);
        updateShiftIsReady();
        if(!workers.contains(worker)) workers.add(worker);
    }

    public boolean isShiftIsReady() {
        return shiftIsReady;
    }

    private void addToMustJobs_Counter(String job){
                switch(job) {
                    case "store keeper":
                        mustJobs_Counter[0]++;
                        break;
                    case "steward":
                        mustJobs_Counter[1]++;
                        break;
                    case "cashier":
                        mustJobs_Counter[2]++;
                        break;
                    case "driver":
                        mustJobs_Counter[3]++;
                        break;
                    default:
                        //do nothing
                        break;
                }
        updateShiftIsReady();
    }

    private void removeFromMustJobs_Counter(String job){
            switch(job){
                case "store keeper":
                    mustJobs_Counter[0]--;
                    break;
                case "steward":
                    mustJobs_Counter[1]--;
                    break;
                case "cashier":
                    mustJobs_Counter[2]--;
                    break;
                case "driver":
                    mustJobs_Counter[3]--;
                    break;
                default:
                    //do nothing
                    break;
            }
        updateShiftIsReady();
    }

    private void updateShiftIsReady() {
        for(int i =0; i<mustJobs_Counter.length; i++) {
            if (mustJobs_Counter[i] == 0) {
                shiftIsReady = false;
                return;
            }
        }
        if (shift_manager!=null)
            shiftIsReady = true;
        else shiftIsReady = false;
    }

    public boolean addWorkerToShift(Worker worker) {
        if(!workers.contains(worker)) {
            workers.add(worker);
            return true;
        }
        return false;
    }

    public boolean assignWorkerToJob(Worker worker, String job){
        if (!workers.contains(worker))return false;
        if (!worker.hasJob(job)) return false;
        for (String job2:jobToWorker.keySet()) {
            if (jobToWorker.get(job2).contains(worker.getId())) {
                for (int i =0; i<jobToWorker.get(job2).size(); i++){
                    if (jobToWorker.get(job2).get(i)== worker.getId())jobToWorker.get(job2).remove(i);
                }
                removeFromMustJobs_Counter(job2);
                if (jobToWorker.get(job2).isEmpty())jobToWorker.remove(job2);
            }
        }
        if (!jobToWorker.containsKey(job))jobToWorker.put(job,new LinkedList<>());
        jobToWorker.get(job).add(worker.getId());
        addToMustJobs_Counter(job);
        return true;
    }

    public boolean removeWorkerFromJob(Worker worker, String job){
        if (!workers.contains(worker))return false;
        if (!jobToWorker.containsKey(job))return false;
        if(!jobToWorker.get(job).contains(worker.getId()))return false;
        for (int i =0; i<jobToWorker.get(job).size(); i++){
            if (jobToWorker.get(job).get(i)== worker.getId())jobToWorker.get(job).remove(i);
        }
        if (jobToWorker.get(job).isEmpty())jobToWorker.remove(job);
        removeFromMustJobs_Counter(job);
        return true;
    }

    public boolean removeWorkerFromShift(Worker worker) {
        if(workers.contains(worker)) {
            workers.remove(worker);
            for (String job:jobToWorker.keySet()) {
                if (jobToWorker.get(job).contains(worker.getId())){
                    jobToWorker.get(job).remove(worker.getId());
                    if (jobToWorker.get(job).isEmpty())jobToWorker.remove(job);
                    removeFromMustJobs_Counter(job);
                    break;
                }
            }
            if(shift_manager != null && shift_manager.getId() == worker.getId())
                shift_manager = null;
            return true;
        }
        else return false;
    }
    public boolean removeTransaction(int transactionID, int workerID){
        if ((shift_manager == null || shift_manager.getId()!=workerID) && !WorkerController.getInstance().isHR(workerID)) return false;
        if(shift_transactions != null) {
            for (Transaction tran : shift_transactions)
                if (tran.getTransactionID() == transactionID && tran.getWorkerID() == workerID) {
                    shift_transactions.remove(tran);
                    return true;
                }
        }
        return false;
    }
    public boolean addTransaction(Transaction transaction, int workerID){
        if(shift_transactions == null){
            shift_transactions = new LinkedList<>();
        }

        if(((shift_manager != null && shift_manager.getId() == workerID) || WorkerController.getInstance().isHR(workerID)) &&
                !hasTransaction(transaction.getTransactionID())) {
            this.shift_transactions.add(transaction);
            return true;
        }
        else {
            for (Worker w : workers) {
                if (w.getId() == workerID) {
                    if (!w.workerJobs.contains("cashier") || hasTransaction(transaction.getTransactionID())) return false;
                    else {
                       this.shift_transactions.add(transaction);
                       return true;
                    }
                }
            }
            return false; //The worker isn't in this shift.
        }
    }

    private boolean hasTransaction(int transactionID){
        if(shift_transactions != null) {
            for (Transaction tran : shift_transactions)
                if (tran.transactionID == transactionID) return true;
        }
        return false;
    }
}
