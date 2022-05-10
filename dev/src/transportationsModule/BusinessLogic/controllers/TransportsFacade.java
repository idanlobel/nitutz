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
    EMPLOYEEFACADE employeefacade;
    //DriversController driversController;
    
    public TransportsFacade() {
        transportsController = new TransportsController();
        trucksController = new TrucksController();
        sitesController = new SitesController();
        // TODO: 07/05/2022  
        employeefacade = new EMPLOYEEFACADE();

    }

    public void setEmployeefacade(EMPLOYEEFACADE employeefacade) {
        this.employeefacade = employeefacade;
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

    public String showDriversDetailsAvailable(String departureTime, String date) {
        return employeefacade.showDriversDetails();//get all drivers לפי המשמרת
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
}
