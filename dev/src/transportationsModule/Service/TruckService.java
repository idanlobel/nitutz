package src.transportationsModule.Service;

import src.transportationsModule.BusinessLogic.controllers.TransportsFacade;

import java.util.Scanner;

/**
 * responsible adding a Truck or viewing trucks
 */
public class TruckService {

    Scanner scanner;
    TransportsFacade transportsFacade;

    public TruckService(Scanner scanner, TransportsFacade transportsFacade) {
        this.scanner=scanner;
        this.transportsFacade = transportsFacade;
    }

//    public String getTrucksByLicenseType(String driversLicenceType) {
//        return trucksController.getTrucksMatchDriverLicence(driversLicenceType).toString();
//    }

    public void addTruck() {

        String number = askInput("enter the truck number:");
        String licenseType = askInput("enter the truck licenseType A B:");
        String licenseNumber = askInput("enter the truck licenseNumber:");
        String model = askInput("enter the truck model:" );
        String weight = askInput("enter the truck weight:" );
        String maxWeight = askInput("enter the truck maxWeight:" );

        transportsFacade.addTruck(number,licenseType,licenseNumber,model,weight,maxWeight);
    }

    /**
     * @param question that the user will gonna asked to answer
     * @return the answer of the user as a string
     */
    private String askInput(String question){
        System.out.println(question);
        String answer = scanner.next();
        return answer;
    }



    public void updateTruck() {
        String id = askInput("enter the truck licenseNumber:" );
        String toChangeField = askInput("enter the field you want to change:");
        String newVal = askInput("enter the new val:");

        transportsFacade.updateTruck(id,toChangeField, newVal );
    }

    public void addTruck_init(String name, String licenceType, String licenseNumber, String model, String weight, String maxWeight) {
        transportsFacade.addTruck(name, licenceType, licenseNumber, model, weight, maxWeight);
    }

//    public void updateTruck() {
//        transportsFacade.updateTruck(String toChangeFeild, String newVal);
//    }

}
