package businessLogic.controllers;

import businessLogic.Driver;
import businessLogic.LicenseType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static businessLogic.LicenseType.A;

public class DriversController {

    List<Driver> drivers;

    public DriversController(List<Driver> drivers) {
        this.drivers = drivers;
    }


    public DriversController() {
        drivers = new LinkedList<>();

    }

    public List<Driver> getDrivers() {
        return drivers;
    }
    public List<String> getDriversName() {
        List<String> ret =new ArrayList<>();
        for (Driver driver : drivers){
            ret.add(driver.toString());
        }
        return ret;
    }
    public void addDriver(String id, String name, String phonenumber, String licenseTypeEnum) {
        Driver driver = new Driver(id,name,phonenumber, LicenseType.valueOf(licenseTypeEnum));
        this.drivers.add(driver);
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Driver getDriverById(String driverId){
        for (Driver driver : drivers){
            if (driver.getId().equals(driverId))
                return driver;
        }
        throw new IllegalArgumentException("no such driver");
    }
    public String getDriversLicenceTypeByDriveId(String driverId){
        for (Driver driver : drivers){
            if (driver.getId().equals(driverId))
                return driver.getLicenseTypes().toString();
        }
        throw new IllegalArgumentException("no such driver");
    }



}
