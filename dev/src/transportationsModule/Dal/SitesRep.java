package src.transportationsModule.Dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static src.transportationsModule.Dal.Connect.getConnection;

public class SitesRep {

    Map<String[], String[]> sitesCache; //(region, name)-key, type, contact name, contact number....
    //IdentityMap


    public SitesRep() {
        sitesCache = new HashMap<>();
    }

    public Map<String[], String[]> getSitesCache() {
        return sitesCache;
    }

    public void setSitesCache(Map<String[], String[]> sitesCache) {
        this.sitesCache = sitesCache;
    }

    public void addSite(String siteName, String siteRegion, String[] siteData){
        String[] siteKey = {siteName, siteRegion};
        // TODO: 05/05/2022
        //enter to DB(siteData)
        sitesCache.put(siteKey, siteData);
        System.out.println(siteName + siteRegion + " added to sites cache");

    }

    public String[] getSite(String name, String region){
        // TODO: 05/05/2022
        return null;
    }

    public Map<String[], String[]> getSitesByRegion(String region){
        // TODO: 05/05/2022
        //get all by region from db
        return null;
    }


    public Map<String[], String[]> getSites(){
        // TODO: 05/05/2022
        //get all sites from db
        return this.sitesCache;
    }

    public void insert( String name, double capacity) {
        String sql = "INSERT INTO employees(name, capacity) VALUES(?,?)";

        try{
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
