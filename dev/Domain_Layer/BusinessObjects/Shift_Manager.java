package Domain_Layer.BusinessObjects;

import java.util.LinkedList;
import java.util.List;

public class Shift_Manager extends Worker{

    public Shift_Manager(String name, int id, String password, String email_address, BankAccount bankAccount,
                         EmploymentConditions employmentConditions, LinkedList<String> jobs) {
        super(name, id, password, email_address, bankAccount, employmentConditions, jobs);
    }
}
