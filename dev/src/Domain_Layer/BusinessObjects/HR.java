package src.Domain_Layer.BusinessObjects;

import java.util.LinkedList;
import java.util.List;

public class HR extends Worker {
    public HR(String name, int id, String password, String email_address, BankAccount bankAccount,
              EmploymentConditions employmentConditions, List<String> jobs) {
        super(name, id, password, email_address, bankAccount, employmentConditions, jobs);
        // or read from database
    }
}