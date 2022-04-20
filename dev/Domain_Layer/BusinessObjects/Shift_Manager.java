package Domain_Layer.BusinessObjects;

import java.util.List;

public class Shift_Manager extends Worker{

    public Shift_Manager(String name, int id, String password, String email_address, BankAccount bankAccount, List<String> jobs) {
        super(name, id, password, email_address, bankAccount, jobs);
    }
}
