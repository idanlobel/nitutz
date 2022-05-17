package src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersTransportation;

import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.WorkerDTO;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.WorkersFacade;
import src.TransportationsAndWorkersModule.Service.ServiceTransportation.TransportSDTO;

import java.util.List;

/**
 * transportations module Functions controller
 */
public class TransportsFacade {

    TransportsController transportsController;
    TrucksController trucksController;
    SitesController sitesController;
    WorkersFacade workersFacade;
    //DriversController driversController;
    
    public TransportsFacade() {
        transportsController = new TransportsController();
        trucksController = new TrucksController();
        sitesController = new SitesController();
        // TODO: 07/05/2022  
        workersFacade = new WorkersFacade();

    }



    /**
     * create a new transportation form a transport DTO object .
     */
    public void regTransport(TransportSDTO transportSDTO){
        transportsController.createTransport(transportSDTO);
    }

    /**
     * adding a new truck to the trucks.
     */
    public void addTruck(String number, String licenseType, String licenseNumber, String model, String weight, String maxWeight){
        trucksController.addTruck(number, licenseType, licenseNumber, model, weight, maxWeight);
    }

    public void updateTruck(String TruckId, String toChangeField, String newVal){
        trucksController.updateTruck(TruckId, toChangeField, newVal);
    }

//    public Truck getTruck(String licenseNumber){
//        return trucksRep.getTruck(licenseNumber);
//    }
//    public List<TruckDto> getTrucks() {
//        return trucksController.getTrucks();
//    }

    public List<String> viewAllSites(){
        return sitesController.viewAllSites();
    }

    public void addSite(String name, String address, String contactPersonName, String contactPersonNamePhone, String region) {
        sitesController.addSite(name, address, contactPersonName, contactPersonNamePhone, region);
    }

    public String viewTrucks() {
        return trucksController.viewTrucks();
    }


    public List<String> showTransportByDate(String date) {
        return transportsController.getFormsByDate(date);
    }

    public String getTransportById(String id) {
        return transportsController.getFormsById(id).toString();
    }

    public void updateForm(String id, String toChangeField, String newVal) {
        transportsController.updateForm(id, toChangeField, newVal);

    }
    public int getTruckMaxWeightFromTransportID(String id) {
        return Integer.parseInt(trucksController.getTruckById(transportsController.getFormsById(id).getTruckLicenceNumber()).getMaxWeight());
    }

    public List<WorkerDTO> getDriversByShift(String date, String departureTime) {
        try {
            String[] time=departureTime.split(":");
            int shiftType=Integer.parseInt(time[0])>13?1:0;
            String[] dates=date.split(" ");
            List<WorkerDTO>  lst= workersFacade.getAllDrivers(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),shiftType);
            return lst;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean isLegalStieToTransportByShift(String date, String departureTime, String destinationId) {
        try {
            String[] time=departureTime.split(":");
            int shiftType=Integer.parseInt(time[0])>13?1:0;
            String[] dates=date.split(" ");
            Boolean  aBoolean= workersFacade.isTransportLegal(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),shiftType,destinationId);
            return aBoolean;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
