package src.transportationsModule.Service;

import src.transportationsModule.BusinessLogic.controllers.TransportsFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SiteService {

    Scanner scanner;
    TransportsFacade transportsFacade;
    //IdGenerator idgenarator;

    public SiteService(Scanner scanner, TransportsFacade transportsFacade) {
        this.scanner = scanner;
        this.transportsFacade =transportsFacade;
    }

    public List<String> viewAllSites(){
        return transportsFacade.viewAllSites();
    }

    public void addSite() {

        String name = askInput("enter the site name:");
        String address = askInput("enter the site address:");
        String contactPersonName = askInput("enter the site contact Person Name:");
        String contactPersonNamePhone = askInput("enter the site contact Person Name Phone:" );
        String region = askInput("enter the site region South or North:" );
        String siteType = askInput("enter the site siteType Supplier Or Branch:" );

        transportsFacade.addSite(name, address, contactPersonName,contactPersonNamePhone, region);

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

    public void updateSite() {
    }

    public void addSite_init(String name, String address, String contactPersonName, String contactPersonNamePhone, String region) {
        transportsFacade.addSite(name, address, contactPersonName, contactPersonNamePhone, region);
    }
}
