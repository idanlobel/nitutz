package src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersTransportation;

import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.ContactPerson;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.Site;
import src.TransportationsAndWorkersModule.Dal.Transportation.SitesRep;

import java.util.LinkedList;
import java.util.List;

public class SitesController {

    SitesRep sitesRep;

    public SitesController() {
        sitesRep = new SitesRep();
    }

    public void addSite(String name, String address, String contactPersonName, String contactPersonNamePhone, String region) {
        Site siteData = new Site(name, address,new ContactPerson(contactPersonName,contactPersonNamePhone),region);
        sitesRep.addSite(name, address, contactPersonName, contactPersonNamePhone, region);
        //todo insert site
    }

    public List<String> viewAllSites() {
        List<String> sitesList = new LinkedList<>();
        List<Site> sites =  sitesRep.getSites();
        for (Site site : sites)
            sitesList.add(site.toString() + "\n");
        return sitesList;
    }

}
