package src.TransportationsAndWorkersModule.Service.ServiceWorkers;

import java.util.List;


//this class is the service layer worker. basically this class has only whats relevant for the service layer
public class WorkerSL {
    String name;
    int id;
    //BankAccount bankAccount;
    //EmploymentConditions employmentConditions;
    List<String> workerJobs;
    //Create a new workerSL
    public WorkerSL(String name, int id, List<String> workerJobs) {
        this.name = name;
        this.id = id;
        this.workerJobs = workerJobs;
    }
    public String toString(){
        String s =  "Name: " + name + ", ID: " + id + ", Jobs: ";
        for (String job : workerJobs){
            s+= job + " ,";
        }
        return (s.substring(0, s.length() -1 ) + "."); //removing the last ','
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public List<String> getWorkerJobs(){
        return workerJobs;
    }

}
