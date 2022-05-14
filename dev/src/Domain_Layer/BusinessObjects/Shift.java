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
    int shift_manager;
    List<Integer> workers;

    public List<Integer> getWorkers() {
        return workers;
    }

    static List<String> mustJobs = new ArrayList<String>(Arrays.asList("store keeper", "steward", "cashier", "driver"));
    List<Transaction> shift_transactions;
    public Shift(int shift_manager, List<Integer> workers, List<Transaction> transactions) {
        //For when we have a database to read from...
        this.shift_manager = shift_manager;
        this.workers = workers;
        this.shift_transactions = transactions;
        mustJobs_Counter = new int [4]; //initialized by default
        jobToWorker = new HashMap<>();
        updateShiftIsReady();
    }

    public Shift() { //for now, since we have no database...
        this.workers = new LinkedList<>();
        this.shift_transactions = new LinkedList<>();
        mustJobs_Counter = new int [4]; //initialized by default
        shiftIsReady = false;
        jobToWorker = new HashMap<>();
        shift_manager = -1;
    }

    public List<Integer> getShiftWorkers() throws Exception {
        if(workers!= null) {
            return workers;
        }
        throw new Exception("There's currently no workers in this shift");
    }
    public int getShift_manager() throws Exception {
        return shift_manager;
    }
    public void setShiftManager(int worker){
        shift_manager = worker;
        if (!jobToWorker.containsKey("shift manager"))jobToWorker.put("shift manager",new ArrayList<>());
        jobToWorker.get("shift manager").add(worker);
        updateShiftIsReady();
        if(!workers.contains(worker)) workers.add(worker);
    }

    public boolean isShiftIsReady() {
        return shiftIsReady;
    }

    private void addToMustJobs_Counter(String job){
        for (int i =0 ;i<mustJobs.size(); i++){
            if (job.equals(mustJobs.get(i)))mustJobs_Counter[i]++;
        }
        updateShiftIsReady();
    }

    private void removeFromMustJobs_Counter(String job){
        for (int i =0 ;i<mustJobs.size(); i++){
            if (job.equals(mustJobs.get(i)))mustJobs_Counter[i]--;
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
        if (shift_manager!=-1)
            shiftIsReady = true;
        else shiftIsReady = false;
    }

    public boolean addWorkerToShift(Integer worker) throws Exception {
        if(!workers.contains(worker)) {
            workers.add(worker);
            return true;
        }
       throw new Exception("The worker is already in this shift");
    }

    public boolean assignWorkerToJob(Integer worker, String job) throws Exception {
        try {
            if (!workers.contains(worker)) throw new Exception("There's no such worker in this shift");
            for (String job2 : jobToWorker.keySet()) {
                if (jobToWorker.get(job2).contains(worker)) {
                    for (int i = 0; i < jobToWorker.get(job2).size(); i++) {
                        if (jobToWorker.get(job2).get(i) == worker) jobToWorker.get(job2).remove(i);
                    }
                    removeFromMustJobs_Counter(job2);
                    if (jobToWorker.get(job2).isEmpty()) jobToWorker.remove(job2);
                }
            }
            if (!jobToWorker.containsKey(job)) jobToWorker.put(job, new LinkedList<>());
            jobToWorker.get(job).add(worker);
            addToMustJobs_Counter(job);
            return true;
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }

    public boolean removeWorkerFromJob(Integer worker, String job) throws Exception {
        try {
            if (!workers.contains(worker)) throw new Exception("There's no such worker in this shift");
            if (!jobToWorker.containsKey(job)) throw new Exception("There's no such job in this shift");
            if (!jobToWorker.get(job).contains(worker)) throw new Exception("There's no such job for this worker");
            if ((job.equals("driver")||job.equals("store keeper"))&&jobToWorker.get(job).size()==1)
                throw new Exception("need to add another worker to this job before removing");
            for (int i = 0; i < jobToWorker.get(job).size(); i++) {
                if (jobToWorker.get(job).get(i) == worker) jobToWorker.get(job).remove(i);
            }
            if (jobToWorker.get(job).isEmpty()) jobToWorker.remove(job);
            removeFromMustJobs_Counter(job);
            return true;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public boolean removeWorkerFromShift(Integer worker) throws Exception {
        try {
            if (workers.contains(worker)) {
                workers.remove(worker);
                for (String job : jobToWorker.keySet()) {
                    if (jobToWorker.get(job).contains(worker)) {
                        if ((job.equals("driver")||job.equals("store keeper"))&&jobToWorker.get(job).size()==1)
                            throw new Exception("need to add another worker to this job before removing");
                        jobToWorker.get(job).remove(worker);
                        if (jobToWorker.get(job).isEmpty()) jobToWorker.remove(job);
                        removeFromMustJobs_Counter(job);
                        break;
                    }
                }
                if (shift_manager != -1 && shift_manager == worker)
                    shift_manager = -1;
                return true;
            }
            else throw new Exception("There's no such worker in this shift");
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }
    public boolean removeTransaction(int transactionID, int workerID) throws Exception {
        try {
            if ((shift_manager == -1 || shift_manager != workerID) && !WorkerController.getInstance().isHR(workerID))
                throw new Exception("You're not authorized to perform this action. Only the shift manager of this shift " +
                        "or the HR manager can perform this action.");
            if (shift_transactions != null) {
                for (Transaction tran : shift_transactions)
                    if (tran.getTransactionID() == transactionID && tran.getWorkerID() == workerID) {
                        shift_transactions.remove(tran);
                        return true;
                    }
                throw new Exception("The entered details are incorrect, there's no such transaction. Please make sure that you've " +
                        "entered everything correctly.");
            }
            throw new Exception("There are no transactions in this shift.");
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }
    public boolean addTransaction(Transaction transaction, int workerID) throws Exception {
        try {
            if (shift_transactions == null) {
                shift_transactions = new LinkedList<>();
            }
            if (((shift_manager != -1 && shift_manager == workerID) || WorkerController.getInstance().isHR(workerID)) &&
                    !hasTransaction(transaction.getTransactionID())) {
                this.shift_transactions.add(transaction);
                return true;
            } else {
                for (Integer w : workers) {
                    if (w == workerID) {
                        if (hasTransaction(transaction.getTransactionID())) {
                            throw new Exception("There's already such transaction with that transaction id.");
                        } else {
                            this.shift_transactions.add(transaction);
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        catch(Exception e){
            throw new Exception(e);
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
