package Service;

import businessLogic.controllers.DriversController;
import businessLogic.controllers.TrucksController;

import java.util.Scanner;

public class TruckService {

    TrucksController trucksController;
    Scanner scanner;

    public TruckService(Scanner scanner) {
        this.scanner=scanner;
        trucksController = new TrucksController();
    }

    public String getTrucksByLicenseType(String driversLicenceType) {
        return trucksController.getTrucksMatchDriverLicence(driversLicenceType).toString();
    }

    public void addTruck() {


        String number = askInput("enter the truck number:");
        String licenseType = askInput("enter the truck licenseType A B:");
        String licenseNumber = askInput("enter the truck licenseNumber:");
        String model = askInput("enter the truck model:" );
        String weight = askInput("enter the truck weight:" );
        String maxWeight = askInput("enter the truck maxWeight:" );
        //transportForm.truckLicensePlateId = askInput("choose the transport truck license plate from your Truces match your driver: " + trucksController.getTrucksMatchDriverLicence(L).toString() );

        trucksController.addTruck(number,licenseType,licenseNumber,model,weight,maxWeight);
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
    }
}
