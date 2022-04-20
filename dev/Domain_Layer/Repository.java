package Domain_Layer;

import Domain_Layer.BusinessObjects.HR;
import Domain_Layer.BusinessObjects.Weekly_Schedule;
import Domain_Layer.BusinessObjects.Worker;
import Domain_Layer.BusinessObjects.Worker_Schedule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Repository {
    //this class will work infront of the data layer. everything that has to do with the data layer goes through this class
    private static Repository repository = null;
    private Repository(){

    }
    public static Repository getInstance(){
        if (repository==null)repository = new Repository();
        return repository;
    }
    public List<Worker> readWorkers(){
        //here fill the workers to your own satisfaction
        //you will probably need to create a bank account for each and all that shit
        return new LinkedList<>();
        //for now this is the implementation
    }
    public HR readHR(){
        return null;//create HR to your own satisfaction
    }
    public boolean addWorker(Worker workerToAdd){
        //implement when the time comes
        return true;
    }
    public boolean register(int workerID,String userName, String password){
        return true;
    }
    public boolean deleteWorker(Worker w) {
        return true;
    }

    public HashMap<Integer, Worker_Schedule> readWorkerSchedules() {
        return new HashMap<>(); //you can fill initial data here as well to your own satisfaction
    }

    public HashMap<Integer, Weekly_Schedule> readWeeklySchedules() {
        return new HashMap<>();//you can fill initial data here as well to your own satisfaction
    }
}
