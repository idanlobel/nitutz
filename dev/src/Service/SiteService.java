package Service;

import businessLogic.ContactPerson;
import businessLogic.Region;
import businessLogic.Site;
import businessLogic.SiteType;
import businessLogic.controllers.SitesController;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SiteService {

    SitesController sitesController;
    Scanner scanner;
    IdGenerator idgenarator;

    public SiteService(Scanner scanner) {
        sitesController = new SitesController();
        this.scanner = scanner;
        idgenarator = new IdGenerator();
    }

    public List<String> getSitesNames(){
        List<String> sitesNames = new LinkedList<>();
        for (Site site : sitesController.getSites()){
            sitesNames.add(site.name);
        }
        return sitesNames;
    }

    public void addSite() {


        String name = askInput("enter the site name:");
        String address = askInput("enter the site address:");
        String contactPersonName = askInput("enter the site contact Person Name:");
        String contactPersonNamePhone = askInput("enter the site contact Person Name Phone:" );
        String region = askInput("enter the site region South or North:" );
        String siteType = askInput("enter the site siteType Supplier Or Branch:" );
        //transportForm.truckLicensePlateId = askInput("choose the transport truck license plate from your Truces match your driver: " + trucksController.getTrucksMatchDriverLicence(L).toString() );

        sitesController.addSites( name,  address, contactPersonName,contactPersonNamePhone,  region,  siteType);

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
}
