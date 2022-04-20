package Service_Layer;

import java.util.List;

public class ShiftSL {
    List<WorkerSL> workers;

    public ShiftSL(List<WorkerSL> workers, int managerID) {
        this.workers = workers;
        this.managerID = managerID;
    }
    int managerID;

    public List<WorkerSL> getWorkers() {
        return workers;
    }

    public int getManagerID() {
        return managerID;
    }
}
