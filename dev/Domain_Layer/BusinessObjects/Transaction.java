package Domain_Layer.BusinessObjects;

import java.util.List;

public class Transaction {
    int transactionID;
    int workerID;

    public Transaction(int transactionID, int workerID) {
        this.transactionID = transactionID;
        this.workerID = workerID;
    }
}
