package Service_Layer;

import java.util.Scanner;
import Domain_Layer.*;

public class Login {
    String userName;
    String password;
    int workerID;

    public Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
        workerID = -1;
    }

    public boolean isAUser() {
        TempData tmpData = TempData.getInstance();
        workerID = tmpData.containsWorker(userName, password);
        if (workerID != -1) {
            return true;
        }
        return false;
    }
}
