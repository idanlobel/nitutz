package src.Service_Layer;

import src.Domain_Layer.BusinessControllers.WorkerController;

public class Login {
    int id;
    String password; //TODO:: Delete this once we have a database!
    String name;
    int workerID;
    boolean isHr;

    public boolean isHr() {
        return isHr;
    }

    public Login(int id, String password) {
        this.id = id;
        this.password = password;
        name = "";
        workerID = -1;
        isHr = false;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public int getWorkerID() {
        return workerID;
    }

    public boolean isAUser() {
        WorkerController workerController = WorkerController.getInstance();
        workerID = workerController.idPassMatch(id, password);
        if (workerID == -1) {
            workerID = workerController.isHR(id, password);
        }
        if(workerID == -1)
            return false;
        try {
            name = workerController.getWorkerName(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean isHR() {
        WorkerController workerController = WorkerController.getInstance();
        isHr = workerController.isHR(workerID);
        return isHr;
    }
}
