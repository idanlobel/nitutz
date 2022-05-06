package src.transportationsModule.Dal;

import src.transportationsModule.BusinessLogic.Truck;


import java.util.LinkedList;
import java.util.List;

public class TrucksRep {

    List<Truck> trucksCache;
    //connection sqlite connection
    //Map<,> trucksIdentityMap

    public TrucksRep() {
        trucksCache = new LinkedList<Truck>();
        //new map and get connection.
    }

    public void addTruck(Truck truck){
        //add truck also to DB
        trucksCache.add(truck);
        System.out.println(truck.getLicenseNumber() + " added to trucks cache");
    }

    public Truck getTruck(String licenseNumber){
        return null;
    }

    public List<Truck> getAllTrucks() {
        // TODO: 06/05/2022
        //get all trucks also from DB
        return trucksCache;
    }

    //getByLicenseType

}
