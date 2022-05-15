package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers;

import java.util.*;

public class Worker {
    String name;
    int id;
    private String password;
    public String getEmail_address() {
        return email_address;
    }
    String email_address;
    BankAccount bankAccount;
    EmploymentConditions employmentConditions;
    List<String> workerJobs;

    //Create a new worker
    public Worker(String name, int id, String password, String email_address, BankAccount bankAccount,
                  EmploymentConditions employmentConditions, List<String> workerJobs) {
        this.name = name;
        this.id = id;
        this.email_address = email_address;
        this.bankAccount = bankAccount;
        this.workerJobs = workerJobs;
        this.password = password;
        this.employmentConditions = employmentConditions;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public EmploymentConditions getEmploymentConditions() {
        return employmentConditions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setEmploymentConditions(EmploymentConditions employmentConditions) {
        this.employmentConditions = employmentConditions;
    }
    public String getPassword() {
        return password;
    }
    public boolean passwordsMatch(String password){
        return this.password.equals(password);
    }
    public String toString(){
        String s =  "Name " + name + ", ID: " + id + ", Jobs: ";
        for (String job : workerJobs){
            s+= job + " ,";
        }
        return (s.substring(0, s.length()-1) + "."); //removing the last ',' and adding a '.'
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
