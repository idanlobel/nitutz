package src.Domain_Layer;

import src.Domain_Layer.BusinessControllers.WorkerController;
import src.Domain_Layer.BusinessObjects.Worker;

import java.util.List;

public class WorkersFacade {
    private WorkerController workerController =WorkerController.getInstance();
    public List<Worker> getAllDrivers() throws Exception {
        try {
            List<Worker> driversList = workerController.getAllDrivers();
            //either change it to list of ids, or create a DTO that transfers the data towards them like workerSL
            return driversList;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
