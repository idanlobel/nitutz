package src.transportationsModule.BusinessLogic.controllers;

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

    public void addSite(String name, String address, String contactPersonName, String contactPersonNamePhone, String region, String siteType) {
        String[] siteData = {name, region, address, contactPersonName, contactPersonNamePhone, siteType};
        sitesRep.addSite(name, region, siteData);
    }

    public List<String> viewAllSites() {
        List<String> sitesList = new LinkedList<>();
        Map<String[], String[]> sites =  sitesRep.getSites();
        for (String[] site : sites.values())
            sitesList.add(Arrays.toString(site) + "\n");
        return sitesList;
    }

    public String viewSitesNamesAndRegion() {
        String ans = "";
        Map<String[], String[]> sites =  sitesRep.getSites();
        for (String[] site : sites.values())
            ans = ans + "\n" + site[0] + " " + site[1];
        return ans;
    }
}
