package src.Data_Access_Layer;


import src.Domain_Layer.BusinessObjects.BankAccount;
import src.Domain_Layer.BusinessObjects.EmploymentConditions;
import src.Domain_Layer.BusinessObjects.HR;
import src.Domain_Layer.BusinessObjects.Worker;
import src.Domain_Layer.Repository;

import java.util.*;

public class WorkerDAO {
    private boolean readAll = false;
    private static HashMap<Integer, Worker> cacheWorkers = new HashMap<>();
    private static HR hr;
    public Worker get(int id) throws Exception{
        Worker worker = cacheWorkers.get(id);
        if (worker!=null) return  worker;
        worker=Repository.getInstance().readWorker(id);
        if (worker==null)throw new Exception("worker doesn't exist");
        return worker;
        //take from the db and insert to the cache then return
    }
    public List<Worker> getAllWorkers() throws Exception{
        if (readAll)return (List<Worker>) cacheWorkers.values();
        //read from DB and insert into cache workers;
        List<Worker> workers = Repository.getInstance().readWorkers();
        for (Worker w:workers)if (!cacheWorkers.containsKey(w.getId()))cacheWorkers.put(w.getId(),w);
        readAll = true;
        return (List<Worker>) cacheWorkers.values();
    }
    public void create(Worker worker) throws Exception {
        if (cacheWorkers.containsKey(worker.getId())) throw new Exception("worker already exists");
        //insert to db first
        //dont forget to add all the 2*5 presence needed for this worker id as false so its woker schedule will exist as well
        cacheWorkers.put(worker.getId(),worker);
    }
    public void update(Worker worker) throws Exception {
        if (!cacheWorkers.containsKey(worker.getId())) throw new Exception("worker doesn't exist");
        //update to db first
        cacheWorkers.put(worker.getId(), worker);
    }
    public void delete(int id) throws Exception{
        //throw exception only if db throws it a second time or somesht.
        //delete from db first
        cacheWorkers.remove(id);
    }

    public HR readHR() {
        if (hr==null) {hr = new HR("Ori", 3, "OriK3000", "OriK@gmail.com",
                new BankAccount(202562, 015), new EmploymentConditions(1, new Date()),
                new LinkedList(Arrays.asList()));}//read from db;
        return hr;
    }
}
