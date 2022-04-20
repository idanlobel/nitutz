package Domain_Layer.BusinessControllers;

import Domain_Layer.BusinessObjects.HR;
import Domain_Layer.BusinessObjects.Worker;
import Domain_Layer.Repository;

import java.util.List;

public class WorkerController {
    private static WorkerController workerController = null;
    private Repository repository = Repository.getInstance();
    List<Worker> workers;
    HR hr;
    private WorkerController() {
        //TODO:: צריך לשנות פה שיקרא מקובץ טקסט במקום ויכניס לתוך הרשימה...
        readWorkersFromData();
        readHRFromData();
    }
    public static WorkerController getInstance()
    {
        if (workerController == null)
            workerController = new WorkerController();
        return workerController;
    }
    private void readWorkersFromData(){
        workers = repository.readWorkers();
    }
    private void readHRFromData(){
        hr = repository.readHR();
    }
    public int containsWorker(String userName, String password){
        for(Worker w : workers){
            if(w.getName() == userName && w.passwordsMatch(password)){
                return w.getId();
            }
        }
        return -1; //The login info is incorrect.
    }

    public List<Worker> getWorkers() {
        if (workers==null)readWorkersFromData();
        return workers;
    }
    public boolean addWorker(Worker worker) {

        if(repository.addWorker(worker)){workers.add(worker);return true;}
        return false;
    }
    public boolean deleteWorker(int workerID) {
        for(Worker w : workers){
            if(w.getId() == workerID){
                return repository.deleteWorker(w);
            }
        }
        return false;
    }
    public Worker getWorker(int workerID) {
        for(Worker w : workers){
            if(w.getId()== workerID){
                return w;
            }
        }
        return null;
    }
    public boolean isHR(int id){
        return hr.getId() == id;
    }

    public boolean addJob(String job) {
        hr.addJob(job);
        return true;
    }
}
