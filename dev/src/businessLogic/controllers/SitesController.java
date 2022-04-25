package businessLogic.controllers;

import businessLogic.ContactPerson;
import businessLogic.Region;
import businessLogic.Site;
import businessLogic.SiteType;

import java.util.ArrayList;
import java.util.List;

public class SitesController {

    List<Site> sites;

    public SitesController() {
        this.sites = new ArrayList<>();
    }

    public List<Site> getSites() {
        return sites;
    }


    public Site getSite(String source) {
        for ( Site s: sites) {
            if(s.name.equals(source))
                return s;
        }
        throw new IllegalArgumentException("no such site");

    }
    public List<Site> getSiteFromList(List<String> dests) {
        List<Site> ret =new ArrayList<>();
        for ( Site s: sites) {
            if(dests.contains(s.name))
                ret.add(s);
        }
        return ret;
    }

    public void addSites(String name, String address, String contactPersonName, String contactPersonNamePhone, String region, String siteType) {
        Site site=new Site(name,address,new ContactPerson(contactPersonName,contactPersonNamePhone), Region.valueOf(region), SiteType.valueOf(siteType));
        sites.add(site);
    }
}
