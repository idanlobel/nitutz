package Domain_Layer;

import Domain_Layer.BusinessObjects.*;

import java.util.*;

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
        List<Worker> workers = Arrays.asList(new Worker("Yossi", 1, "BBBbbb97",
                "popkins@walla.co.il", new BankAccount(306752, 188),
                        new ArrayList<String>(Arrays.asList("cashier", "trucking"))),
                new Worker("Bob", 2, "Bobinka97",
                        "pinkas@walla.co.il", new BankAccount(765732, 190),
                        new ArrayList<String>(Arrays.asList("driver", "store keeper"))),
                new Worker("Papka", 4, "Papka97",
                        "pupik@walla.co.il", new BankAccount(321312, 22),
                        new ArrayList<String>(Arrays.asList("steward", "store keeper"))));
        return workers;
        //for now this is the implementation
    }
    public HR readHR(){
        HR hr = new HR("Ori", 3, "OriK3000", "OriK@gmail.com",
                new BankAccount(202562, 015), new ArrayList<String>(Arrays.asList()));
        return hr;
        //create HR to your own satisfaction - Loads at the beginning of the program.
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
        HashMap<Integer, Worker_Schedule> workersSchedule = new HashMap<>();
        workersSchedule.put(1, new Worker_Schedule());
        workersSchedule.put(2,new Worker_Schedule());
        workersSchedule.put(3,new Worker_Schedule()); //HRs working schedule
        return workersSchedule; //you can fill initial data here as well to your own satisfaction
    }

    public HashMap<Integer, Weekly_Schedule> readWeeklySchedules() {
        HashMap<Integer, Weekly_Schedule> weeklySchedule = new HashMap<>();
        weeklySchedule.put(1, new Weekly_Schedule());
        return weeklySchedule;//you can fill initial data here as well to your own satisfaction
    }
}
