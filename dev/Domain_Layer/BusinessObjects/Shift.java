package Domain_Layer.BusinessObjects;

import java.util.LinkedList;
import java.util.List;

public class Shift {
    enum Type {Morning, Evening};
    Shift_Manager shift_manager;
    List<Worker> workers;
    List<Transaction> shift_transactions;
    public Shift(Shift_Manager shift_manager, List<Worker> workers, List<Transaction> transactions) {
        this.shift_manager = shift_manager;
        this.workers = workers;
        this.shift_transactions = transactions;
    }
    public Shift(){
        this.workers = new LinkedList<>();
    }
    public List<Worker> getShiftWorkers(){
        return workers;
    }
    public Shift_Manager getShift_manager(){
        return  shift_manager;
    }
    public void setShiftManager(Worker worker){
        shift_manager = new Shift_Manager(worker.name, worker.id, worker.getPassword(), worker.email_address, worker.bankAccount,
                worker.workerJobs);
    }
    public boolean addWorkerToShift(Worker worker) {
        if(!workers.contains(worker)) {
            workers.add(worker);
            return true;
        }
        return false;
    }
    public boolean removeWorkerFromShift(Worker worker) {
        if(workers.contains(worker)) {
            workers.remove(worker);
            return true;
        }
        else return false;
    }
    public boolean removeTransaction(int transactionID, int workerID){
        if (shift_manager == null || shift_manager.getId()!=workerID)return false;
        for (Transaction tran:shift_transactions)
            if (tran.transactionID==transactionID){shift_transactions.remove(tran); return true;}
        return false;
    }
    public boolean addTransaction(Transaction transaction, int workerID){
        if(shift_manager != null && shift_manager.getId() == workerID) {
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
