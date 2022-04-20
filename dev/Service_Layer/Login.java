package Service_Layer;

import Domain_Layer.BusinessControllers.WorkerController;

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
        WorkerController workerController = WorkerController.getInstance();
        workerID = workerController.containsWorker(userName, password);
        if (workerID != -1) {
            return true;
        }
        return false;
    }
}
