package Domain_Layer.BusinessObjects;

import Domain_Layer.BusinessControllers.ShiftsController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Worker {
    String name;
    int id;
    private String password;
    String email_address;
    BankAccount bankAccount;

    EmploymentConditions employmentConditions;
    static List<String> jobs = new ArrayList<String>(Arrays.asList("cashier", "trucking", "store keeper", "steward", "director of procurement and logistics",
            "driver"));
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

    public String getPassword() {
        return password;
    }

    public boolean passwordsMatch(String password){
        return this.password.equals(password);
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
        job = job.toLowerCase();
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
