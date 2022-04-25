package businessLogic.controllers;

import businessLogic.LicenseType;
import businessLogic.Truck;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrucksController {

    List<Truck> trucks;

    public TrucksController(){
        trucks = new LinkedList<>();
    }

    public TrucksController(List<Truck> trucks) {
        this.trucks = trucks;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }
    public Truck getTruckByLicensePlate(String lPlate) {
        for (Truck t: trucks
             ) {
            if(t.getLicenseNumber().equals(lPlate))
                return t;
        }
        throw new IllegalArgumentException("no such truck");

    }

    public List<String> getTrucksMatchDriverLicence(String licenseType) {
        List<String> ret=new ArrayList<>();
        for (Truck t:trucks) {
            if(t.getLicenseType().equals(LicenseType.valueOf(licenseType)))
                ret.add(t.getLicenseNumber());
        }
        return ret;
    }

    public void addTruck(Truck truck){
        trucks.add(truck);
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }


    public void addTruck(String number, String licenseType, String licenseNumber, String modle, String weight, String maxWeight) {
    Truck truck = new Truck(Integer.parseInt(number),LicenseType.valueOf(licenseType),licenseNumber,modle,Integer.parseInt(weight),Integer.parseInt(maxWeight));
    this.trucks.add(truck);
    }

}
