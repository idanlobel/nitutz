package Service;

import businessLogic.controllers.DriversController;
import businessLogic.controllers.TransportsController;
import businessLogic.controllers.TrucksController;

import java.util.Scanner;

public class TransportFormService {

    SiteService siteService;
    DriverService driverService;
    TruckService truckService;
    TransportsController transportsController;

    Scanner scanner;
    IdGenerator idgenarator;

    public TransportFormService(Scanner scanner , SiteService siteService, TruckService truckService , DriverService driverService) {
        this.siteService = siteService;
        this.truckService = truckService;
        this.driverService = driverService;
        this.transportsController = new TransportsController(driverService.driversController, siteService.sitesController,truckService.trucksController);

        this.scanner = scanner;
        this.idgenarator = new IdGenerator();
    }

    @Override
    public String toString() {
        return "TransportForm: " + this.hashCode();
    }

    public void createForm(){
        TransportForm transportForm = new TransportForm();
        transportForm.id = idgenarator.getId();
        transportForm.date = askInput("enter the transport date . for example 02/05/2022 ");
        transportForm.departureTime = askInput("enter the departure time. for example 13:20 ");
        transportForm.driverid = askInput("choose the transport driver id from your drivers crew: " + driverService.getDriversName().toString() );
        transportForm.truckLicensePlateId = askInput("choose the truck from your truck: " + truckService.getTrucksByLicenseType(driverService.getDriversLicenceTypeByDriveId(transportForm.driverid) ));
        transportForm.source = askInput("choose the source name from site list: " + siteService.getSitesNames());
        int numberOfDest=Integer.parseInt( askInput("choose number of destenations: "));
        for (int i = 0; i < numberOfDest; i++) {
            TransportProductsDocument tfD=new TransportProductsDocument();

            tfD.destination=askInput("choose the destanation name from site list: " + siteService.getSitesNames());
            transportForm.destinations.add(tfD.destination);
            tfD.id=idgenarator.getId();
            int numberOfProduct=Integer.parseInt( askInput("choose number of products to add for this destanation: "));
            for (int j = 0; j < numberOfProduct; j++) {
                tfD.products.put(askInput("name of product: "),Integer.parseInt(askInput("quantity: ")));
            }
            transportForm.transportProductsDocuments.add(tfD);


        }

        //transportForm.truck????
        //\no good. needs Service objects???? tranportForm.source = siteService. askInput("choose the transport source from following: " + siteService.getSitesNames().toString())
        transportsController.createForm(transportForm);

    }

    public void updateForm(){
        String formtoupdate = askInput("choose what form to update from list:" +transportsController.getForms().toString());
        transportsController.updateForm(formtoupdate);
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

}
