package src.TransportationsAndWorkersModule.Service.ServiceTransportation;

import src.EmpModule.EMPLOYEEFACADE;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.IdGenerator;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.TransportStatus;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersTransportation.TransportsFacade;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * responsible creating/updating transport or viewing transports
 */
public class TransportService {

    Scanner scanner;
    EMPLOYEEFACADE employeefacade;
    TransportsFacade transportsFacade;
    IdGenerator idgenarator;

    public TransportService(Scanner scanner, TransportsFacade transportsFacade) {
        ; //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        this.scanner = scanner;
        // TODO: 06/05/2022   get employee facade throught emp' module
        employeefacade = new EMPLOYEEFACADE();
        this.transportsFacade = transportsFacade;
        idgenarator = new IdGenerator();

    }

    public void createTransport(){
        TransportSDTO transportSDTO = new TransportSDTO();
        transportSDTO.id = idgenarator.getId();
        transportSDTO.date = askInput("enter the transport date . for example 02/05/2022 ");
        transportSDTO.departureTime = askInput("enter the departure time. for example 13:20 ");
        //get מחסנאים שעובדים באותו משמרת
        transportSDTO.driverId = askInput("choose the transport driver id from your drivers crew: " + transportsFacade.showDriversDetailsAvailable(transportSDTO.departureTime, transportSDTO.date));
        transportSDTO.truckLicensePlateId = askInput("choose the truck from your truck: " + transportsFacade.viewTrucks());//getTrucksByLicenseType(); //driverService.getDriversLicenceTypeByDriveId(transportDTO.driverid) )
        transportSDTO.source = askInput( "choose the source name and region from site list: " + transportsFacade.viewAllSites());
        int numberOfDest=Integer.parseInt( askInput("choose number of destinations: "));
        for (int i = 0; i < numberOfDest; i++) {
            ProductsDocumentSDTO tfD=new ProductsDocumentSDTO();

            tfD.destinationId = askInput("choose the destination name and region from site list: " + transportsFacade.viewAllSites());
            transportSDTO.destinations.add(tfD.destinationId);
            tfD.id= idgenarator.getId();
            int numberOfProduct=Integer.parseInt( askInput("choose number of products to add for this destination: "));
            for (int j = 0; j < numberOfProduct; j++) {
                tfD.products.put(askInput("name of product: "),Integer.parseInt(askInput("quantity: ")));
            }
            transportSDTO.productsDocumentSDTOS.add(tfD);
        }

        //transportForm.truck????
        //\no good. needs Service objects???? tranportForm.source = siteService. askInput("choose the transport source from following: " + siteService.getSitesNames().toString())
        transportsFacade.regTransport(transportSDTO);

    }

//    public void updateTransport(){
//        String TransportToUpdate = askInput("choose what form to update from list:" +transportsController.getForms().toString());
//        transportsController.updateForm(TransportToUpdate);
//    }

    @Override
    public String toString() {
        return "TransportForm: " + this.hashCode();
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




    public void addTransport_init(String id, String date, String departureTime, String driverId,
                                  String truckLicensePlateId, String source, List<String> destinations,
                                  List<Object[]> documents){

        List<ProductsDocumentSDTO> docs = new LinkedList<>();
        for (Object[] o : documents){
            Object[][] items  = { {"milk", 20} , {"water" , 10} , {"queso" , 25 }};
            docs.add(new ProductsDocumentSDTO((String)o[0],(String)o[1], items) );
        }
        transportsFacade.regTransport(new TransportSDTO(id, date, departureTime, driverId, truckLicensePlateId, source, destinations, docs, TransportStatus.PreTransported,""));
    }


    public void showTransportFormByDate() {

        String date = askInput("enter date to pull all Transport Form:");


        //transportForm.truck????
        //\no good. needs Service objects???? tranportForm.source = siteService. askInput("choose the transport source from following: " + siteService.getSitesNames().toString())
        List<String> transports = transportsFacade.showTransportByDate(date);

        System.out.println(transports.toString());

    }

    public void updateTransport() {
        String toMore = "Y";
        String id = askInput("enter Transport Form Id to update:");
        String transport = transportsFacade.getTransportById(id);

        System.out.println(transport);

        //transportForm.truck????
        //\no good. needs Service objects???? tranportForm.source = siteService. askInput("choose the transport source from following: " + siteService.getSitesNames().toString())
        while (toMore.equals("Y")) {
            String toChangeField = askInput("enter the field you want to change:");
            String newVal = askInput("enter the new val:");

            transportsFacade.updateForm(id, toChangeField, newVal);

            toMore = askInput("do you want to change more? Y/N");
        }
        transport = transportsFacade.getTransportById(id);
        System.out.println("Updated:");
        System.out.println(transport);


    }

    public void updateWeight() {
        String formIdToChangeWeight = askInput("enter the form id:");
        int maxweight = transportsFacade.getTruckMaxWeightFromTransportID(formIdToChangeWeight);
        int weightToUpdate = Integer.parseInt(askInput("enter the weight:"));
        transportsFacade.updateForm(formIdToChangeWeight, "transportWeight", weightToUpdate+"");

        if(maxweight<weightToUpdate){
            transportsFacade.updateForm(formIdToChangeWeight, "transportStatus", TransportStatus.ReOrganize.toString());
            System.out.println("please Contact transport manager to reorganize this transportation");
        }

    }
}
