package src.transportationsModule.BusinessLogic.controllers;

import src.transportationsModule.BusinessLogic.LicenseType;
import src.transportationsModule.BusinessLogic.Truck;
import src.transportationsModule.Dal.TrucksRep;

import java.util.List;

/**
 * responsible to make/create/get truck/trucks
 */
public class TrucksController {

    TrucksRep trucksRep;

    public TrucksController(){
        trucksRep = new TrucksRep();
    }


    public List<Truck> getTrucks() {
        return trucksRep.getAllTrucks();
    }

    public Truck getTruckById(String truckId){
        return trucksRep.getTruck(truckId);
    }

//    public Truck getTruckByLicensePlate(String lPlate) {
//        for (Truck t: trucks
//             ) {
//            if(t.getLicenseNumber().equals(lPlate))
//                return t;
//        }
//        throw new IllegalArgumentException("no such truck");
//
//    }


//    public List<String> getTrucksMatchDriverLicence(String licenseType) {
//        List<String> ret=new ArrayList<>();
//        for (Truck t:trucks) {
//            if(t.getLicenseType().equals(LicenseType.valueOf(licenseType)))
//                ret.add(t.getLicenseNumber());
//        }
//        return ret;
//    }

//    public void addTruck(Truck truck){
//        trucksRep.addTruck(truck);
//    }

    public void addTruck(String number, String licenseType, String licenseNumber, String modle, String weight, String maxWeight) {
    Truck truck = new Truck(number, LicenseType.valueOf(licenseType),licenseNumber,modle,weight,maxWeight);
    trucksRep.addTruck(truck);
    }


    public void updateTruck(String truckId, String toChangeField, String newVal) {
        trucksRep.getTruck(truckId).update(toChangeField, newVal);
    }

    public String viewTrucks() {
        String ans = "";
        for (Truck t : trucksRep.getAllTrucks()){
            ans = ans + t.toString() + "\n";
        }
        return ans;
    }
}
