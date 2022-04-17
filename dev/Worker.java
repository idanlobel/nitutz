import java.util.List;

public class Worker {
    String name;
    int id;
    String email_address;
    BankAccount bankAccount;
    EmploymentConditions employmentConditions;
    enum Job {Cashier, Trucking, Store_Keeper};
    List<Job> jobs;
}
