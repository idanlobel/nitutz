package Service;

import java.util.Scanner;

public class Service {

    Scanner scanner;
    SiteService siteService;
    TruckService truckService;
    DriverService driverService;
    TransportFormService transportFormService;

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

         siteService = new SiteService(scanner);
         truckService = new TruckService(scanner);
         driverService = new DriverService(scanner);
         transportFormService = new TransportFormService(scanner, siteService,truckService,driverService);


        System.out.println("please enter your name and press enter");
        String userName = scanner.nextLine();


        //data init
        dataInit();
//        System.out.println();
//        String action = scanner.next();
//        System.out.println("for " + action + ", follow the next instructions:");

        while(true){
            String action = askInput("hello " + userName + ", please choose action from the following options and press enter: "
                    + "createForm , updateForm , addSite ,addDriver ,addTruck , updateSite , updateTruck , update truck");

            switch (action){
                case "createForm": { transportFormService.createForm(); break;}
                case "updateForm": {  transportFormService.updateForm(); break;}
                case "addSite": {  siteService.addSite(); break;}
                case "updateSite": { siteService.updateSite(); break;}
                case "updateTruck": { truckService.updateTruck(); break;}
                case "addDriver": { driverService.addDriver(); break;}
                case "addTruck": { truckService.addTruck(); break;}


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
        driverService.driversController.addDriver("3133","itay","024231","A");
        driverService.driversController.addDriver("3132","yotam","042312","A");
        truckService.trucksController.addTruck("1","A","1234","mazda","10","100");
        truckService.trucksController.addTruck("2","B","4321","mazda2","10","100");
        siteService.sitesController.addSites("super1","sham","yosi","000","South","Supplier");
        siteService.sitesController.addSites("branch2","po","yosf","001","North","Branch");
    }

}
