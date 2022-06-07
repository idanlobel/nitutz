package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers;

public class BankAccount {
    int bankID;
    int branch;

    public BankAccount(int bankID, int branch) {
        this.bankID = bankID;
        this.branch = branch;
    }

    public int getBankID() {
        return bankID;
    }

    public int getBranch() {
        return branch;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }
}
