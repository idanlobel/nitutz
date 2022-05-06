package src.transportationsModule.BusinessLogic.controllers;

import src.EmpModule.EMPLOYEEFACADE;
import src.transportationsModule.Service.TransportSDTO;

import java.util.List;

/**
 * transportations module Functions controller
 */
public class TransportsFacade {

    TransportsController transportsController;
    TrucksController trucksController;
    SitesController sitesController;
    //DriversController driversController;
    
    public TransportsFacade() {
        transportsController = new TransportsController();
        trucksController = new TrucksController();
        sitesController = new SitesController();


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

    public void addSite(String name, String address, String contactPersonName, String contactPersonNamePhone, String region, String siteType) {
        sitesController.addSite(name, address, contactPersonName, contactPersonNamePhone, region, siteType);
    }

    public String viewTrucks() {
        return trucksController.viewTrucks();
    }

    /**
     * serves the emp' module to schedule storeKeeper workers and driver workers
     * @param dateOfSunday
     * @return all shifts dates and time that a transportations gonna be in doing.
     */
    public String viewNextWeekTransportationArrivings(String dateOfSunday){
        return null;
    }


}
