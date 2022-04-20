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
        //shift_manager = new Shift_Manager(worker.name,worker.id,worker.email_address,worker.bankAccount,worker.employmentConditions);
    }
    public void addWorkerToShift(Worker worker) {
        workers.add(worker);
    }
    public void removeWorkerToShift(Worker worker) {
        workers.remove(worker);
    }
    public boolean removeTransaction(int transactionID, int workerID){
        if (shift_manager.id!=workerID)return false;
        for (Transaction tran:shift_transactions) if (tran.transactionID==transactionID){shift_transactions.remove(tran); return true;}
        return false;
    }
    public void addTransactaction(Transaction transaction){
        this.shift_transactions.add(transaction);
    }
}
