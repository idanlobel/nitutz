package src.transportationsModule.Service;

import src.transportationsModule.BusinessLogic.controllers.TransportsFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Service {

    Scanner scanner;
    TransportsFacade transportsFacade;

    TruckService truckService;
    SiteService siteService;
    TransportService transportService;


    public Service() {
        transportsFacade = new TransportsFacade();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * serves using scanner
     * @param scanner
     */
    public void Serve(Scanner scanner){
        setScanner(scanner);

         truckService = new TruckService(scanner, transportsFacade);
         siteService = new SiteService(scanner, transportsFacade);
         transportService = new TransportService(scanner, transportsFacade);


        System.out.println("please enter your name and press enter");
        String userName = scanner.nextLine();

        //data init
        System.out.println("hello " + userName + " initiating initial values...");
        dataInit();
        System.out.println();

        while(true){
            String action = askInput("hello " + userName + ", please choose action from the following options and press enter:\n"
                    + "createForm , updateForm , addSite ,addDriver ,addTruck , updateSite , updateTruck , update truck , showFormByDate , updateWeight");

            switch (action){

                case "addTruck": { truckService.addTruck(); break;}

                case "updateTruck": { truckService.updateTruck(); break;}

                case "addSite": {  siteService.addSite(); break;}

                //case "updateSite": { siteService.updateSite(); break;}

                case "createForm": { transportService.createTransport(); break;}

                case "UpdateForm": { transportService.updateTransport(); break;}

                case "showFormByDate": { transportService.showTransportFormByDate(); break;}

                case "updateForm": {  transportService.updateTransport(); break;}

                case "updateWeight": {  transportService.updateWeight(); break;}

            }
        }
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

    private void dataInit(){
//        driverService.driversController.addDriver("3133","itay","024231","A");
        truckService.addTruck_init("1","A","1234","mazda","10","100");
        truckService.addTruck_init("2","B","4321","mazda2","10","100");
        siteService.addSite_init("super1","sham","yosi","000","South");
        siteService.addSite_init("branch2","po","yosf","001","North");
        transportService.addTransport_init("0000", "01/01/0000", "00:00","driverID","licenceId"
                                            , "super1",new LinkedList<String>(),new LinkedList<Object[]>());
    }

}
