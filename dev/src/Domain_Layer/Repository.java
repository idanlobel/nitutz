package src.Domain_Layer;

import src.Domain_Layer.BusinessObjects.*;

import java.util.*;

public class Repository {
    //this class will work infront of the data layer. everything that has to do with the data layer goes through this class
    private static Repository repository = null;
    private static List<Worker> workers = new LinkedList<Worker>(Arrays.asList(new Worker("Yossi", 1, "BBBbbb97",
                                                                                           "popkins@walla.co.il", new BankAccount(306752, 188),
                        new EmploymentConditions(1, new Date()),
            new LinkedList(Arrays.asList("cashier", "trucking"))),
            new Worker("Bob", 2, "Bobinka97",
                               "pinkas@walla.co.il", new BankAccount(765732, 190),
                        new EmploymentConditions(1, new Date()),
            new LinkedList<String>(Arrays.asList("driver", "store keeper"))),
            new Worker("Papka", 4, "Papka97",
                               "pupik@walla.co.il", new BankAccount(321312, 22),
                        new EmploymentConditions(2, new Date()),
            new LinkedList<String>(Arrays.asList("steward", "store keeper"))),
            new Worker("gorilla", 5, "gorrila97",
                               "pupik@walla.co.il", new BankAccount(321313, 12),
                        new EmploymentConditions(2, new Date()),
            new LinkedList<String>(Arrays.asList("driver", "store keeper")))));


    private Repository(){

    }
    public static Repository getInstance(){
        if (repository==null)repository = new Repository();
        return repository;
    }
    public List<Worker> readWorkers(){
        //TODO:: DELETE THIS AFTER WE HAVE A DATABASE!!! - WE need it only for the first milestone to load some data...
        return workers;
        //for now this is the implementation
    }

    public Worker readWorker(int id) {
        return workers.get(id);
    }
}
