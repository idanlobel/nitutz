package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers;

import java.util.List;

public class HR extends Worker {
    public HR(String name, int id, String password, String email_address, BankAccount bankAccount,
              EmploymentConditions employmentConditions, List<String> jobs,String site) {
        super(name, id, password, email_address, bankAccount, employmentConditions, jobs,site);
        // or read from database
    }
}