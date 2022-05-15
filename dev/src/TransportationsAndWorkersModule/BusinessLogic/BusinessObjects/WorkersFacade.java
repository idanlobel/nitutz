package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects;



import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.LicenseType;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.WorkerController;

import java.util.HashMap;
import java.util.List;

public class WorkersFacade {
    private WorkerController workerController =WorkerController.getInstance();
    public List<WorkerDTO> getAllDrivers() throws Exception {//todo change worker to workerDTO include license type
        try {
            List<WorkerDTO> driversList = workerController.getAllDrivers();
            //either change it to list of ids, or create a DTO that transfers the data towards them like workerSL
            return driversList;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Boolean isTransportLegal(int weekId , int Day , int shiftType ,String snif){
        return true;
    }
}
