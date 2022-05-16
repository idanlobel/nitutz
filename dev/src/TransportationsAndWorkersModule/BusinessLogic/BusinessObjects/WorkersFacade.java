package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects;



import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.LicenseType;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.ShiftsController;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.WorkerController;

import java.util.HashMap;
import java.util.List;

public class WorkersFacade {
    private WorkerController workerController =WorkerController.getInstance();
    private ShiftsController shiftsController = ShiftsController.getInstance();
    //private the dal that gets data of the licensedrivers
    public List<WorkerDTO> getAllDrivers(String site) throws Exception {//todo change worker to workerDTO include license type
        try {
            List<WorkerDTO> driversList = workerController.getAllDrivers(site);
            //either change it to list of ids, or create a DTO that transfers the data towards them like workerSL
            return driversList;
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public Boolean isTransportLegal(int weekId , int Day , int shiftType ,String snif) throws Exception {
        try {
            return shiftsController.isTransportationLegal(weekId,snif,Day,shiftType);
            //ori: we currently do nothing with sniff, we will add this possibility on the next part of this project because we havent yet added sniffs to the ERD
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
