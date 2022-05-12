package src.transportationsModule.Dal;

import src.transportationsModule.BusinessLogic.ContactPerson;
import src.transportationsModule.BusinessLogic.Garbage.Region;
import src.transportationsModule.BusinessLogic.Site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static src.transportationsModule.Dal.Connect.getConnection;

public class SitesRep {

    //Map<String[], String[]> sitesCache; //(region, name)-key, type, contact name, contact number....
    List<Site> sitesCache;
    //IdentityMap


    public SitesRep() {
        sitesCache = new ArrayList<>();
    }

    public List<Site> getSitesCache() {
        return sitesCache;
    }

    public void setSitesCache(List<Site> sitesCache) {
        this.sitesCache = sitesCache;
    }

    public void addSite(String name, String address, String contactPersonName, String contactPersonNamePhone, String region){
        // TODO: 05/05/2022
        //enter to DB(siteData)
        try {
            insert(name, address, contactPersonName, contactPersonNamePhone, region);
            sitesCache.add(new Site(name, address, new ContactPerson(contactPersonName, contactPersonNamePhone), Region.valueOf(region)));
            System.out.println(name + region + " added to sites cache");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String[] getSite(String name, String region){
        // TODO: 05/05/2022
        return null;
    }



    public List<Site> getSites(){
        // TODO: 05/05/2022
        //get all sites from db
        return this.sitesCache;
    }

    public void insert(String name, String address, String contactPersonName, String contactPersonNamePhone, String region) {
        String sql = "INSERT INTO sites(name, address,contactPersonName,contactPersonPhone,region) VALUES(?,?,?,?,?)";
//                + " name text PRIMARY KEY,\n"
//                + " address text NOT NULL,\n"
//                + " contactPersonName text NOT NULL,\n"
//                + " contactPersonPhone text NOT NULL,\n"
//                + " region text NOT NULL\n"
        try{
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, contactPersonName);
            pstmt.setString(4, contactPersonNamePhone);
            pstmt.setString(5, region);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
