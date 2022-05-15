package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers;

import java.util.LinkedList;

public class Shift_Manager extends Worker{

    public Shift_Manager(String name, int id, String password, String email_address, BankAccount bankAccount,
                         EmploymentConditions employmentConditions, LinkedList<String> jobs) {
        super(name, id, password, email_address, bankAccount, employmentConditions, jobs);
    }
}
