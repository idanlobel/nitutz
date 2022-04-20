package Domain_Layer.BusinessObjects;

import Domain_Layer.BusinessControllers.ShiftsController;

import java.util.Arrays;
import java.util.List;

public class Worker {
    String name;
    int id;
    private String password;
    String email_address;
    BankAccount bankAccount;

    EmploymentConditions employmentConditions;
    static List<String> jobs = Arrays.asList("Cashier", "Trucking", "Store Keeper", "Steward", "Director of Procurement and Logistics",
            "Driver");
    List<String> workerJobs;

    //Create a new worker
    public Worker(String name, int id, String password, String email_address, BankAccount bankAccount, List<String> workerJobs) {
        this.name = name;
        this.id = id;
        this.email_address = email_address;
        this.bankAccount = bankAccount;
        this.workerJobs = workerJobs;
        this.password = password;
    }
    public boolean passwordsMatch(String password){
        return this.password == password;
    }
    public String toString(){
        String s =  "Name " + name + ", ID: " + id + ", Jobs: ";
        for (String job : jobs){
            s+= job + " ,";
        }
        return (s.substring(0, s.length()) + "."); //removing the last ',' and adding a '.'
    }
    //check if the job already exists
    protected boolean hasJob(String job){
        return jobs.contains(job);
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public List<String> getWorkerJobs() {
        return workerJobs;
    }
}
