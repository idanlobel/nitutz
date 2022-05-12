package src.transportationsModule.BusinessLogic.controllers;

import src.transportationsModule.BusinessLogic.ContactPerson;
import src.transportationsModule.BusinessLogic.Garbage.Region;
import src.transportationsModule.BusinessLogic.Site;
import src.transportationsModule.Dal.SitesRep;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SitesController {

    SitesRep sitesRep;

    public SitesController() {
        sitesRep = new SitesRep();
    }

    public void addSite(String name, String address, String contactPersonName, String contactPersonNamePhone, String region) {
        Site siteData = new Site(name, address,new ContactPerson(contactPersonName,contactPersonNamePhone), Region.valueOf(region));
        sitesRep.addSite(name, address, contactPersonName, contactPersonNamePhone, region);
    }

    public List<String> viewAllSites() {
        List<String> sitesList = new LinkedList<>();
        List<Site> sites =  sitesRep.getSites();
        for (Site site : sites)
            sitesList.add(site.toString() + "\n");
        return sitesList;
    }

}
