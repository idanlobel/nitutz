package Domain_Layer.BusinessObjects;

import Domain_Layer.BusinessControllers.WorkerController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Shift {
    enum Type {Morning, Evening};
    private boolean shiftIsReady;
    int[] mustJobs_Counter;
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
        shiftIsReady = false;
    }

    public Shift() { //for now, since we have no database...
        this.workers = new LinkedList<>();
        this.shift_transactions = new LinkedList<>();
        mustJobs_Counter = new int [4]; //initialized by default
        shiftIsReady = false;
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
        if(!workers.contains(worker)) workers.add(worker);
    }

    public boolean isShiftIsReady() {
        return shiftIsReady;
    }

    private void addToMustJobs_Counter(Worker worker){
            for(String job : worker.workerJobs){
                switch(job){
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
        }
        updateShiftIsReady();
    }

    private void removeFromMustJobs_Counter(Worker worker){
        for(String job : worker.workerJobs){
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
        shiftIsReady = true;
    }

    public boolean addWorkerToShift(Worker worker) {
        if(!workers.contains(worker)) {
            workers.add(worker);
            addToMustJobs_Counter(worker);
            return true;
        }
        return false;
    }
    public boolean removeWorkerFromShift(Worker worker) {
        if(workers.contains(worker)) {
            workers.remove(worker);
            removeFromMustJobs_Counter(worker);
            return true;
        }
        else return false;
    }
    public boolean removeTransaction(int transactionID, int workerID){
        if ((shift_manager == null || shift_manager.getId()!=workerID) && !WorkerController.getInstance().isHR(workerID)) return false;
        if(shift_transactions != null) {
            for (Transaction tran : shift_transactions)
                if (tran.transactionID == transactionID) {
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

        if((shift_manager != null && shift_manager.getId() == workerID) || WorkerController.getInstance().isHR(workerID)) {
            this.shift_transactions.add(transaction);
            return true;
        }
        else {
            for (Worker w : workers) {
                if (w.getId() == workerID) {
                    if (!w.workerJobs.contains("cashier")) return false;
                    else {
                       this.shift_transactions.add(transaction);
                       return true;
                    }
                }
            }
            return false; //The worker isn't in this shift.
        }
    }
}
