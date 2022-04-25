package businessLogic.test;

import businessLogic.Driver;
import businessLogic.controllers.DriversController;
import businessLogic.LicenseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DriverControllerTest {

    Driver driver;
    String name;
    String phoneNumber;
    List<LicenseType> lType;
    List<Driver> lDrivers;
    DriversController driverControllerToTest;

    @BeforeEach
    void setUp() {
        name="Itay";
        phoneNumber="0522521366";

        driver=new Driver("0",name,phoneNumber,LicenseType.A);
        lDrivers=new ArrayList<>();
        lDrivers.add(driver);
        driverControllerToTest= new DriversController(lDrivers);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDrivers() {
        assertEquals(driverControllerToTest.getDrivers(),lDrivers);
    }

    @Test
    void setDrivers() {
        Driver driver2=new Driver("0",name,phoneNumber,LicenseType.A);
        lDrivers.add(driver2);
        driverControllerToTest.setDrivers(lDrivers);
        assertEquals(driverControllerToTest.getDrivers(),lDrivers);

    }

    @Test
    void getDriver() {
        assertEquals(driverControllerToTest.getDriverById("0"),driver);
    }

    @Test
    void addDriver() {
        Driver driver2=new Driver("0",name+"1",phoneNumber+"1",LicenseType.A);
        lDrivers.add(driver2);
        driverControllerToTest.addDriver("0",name+"1",phoneNumber+"1","A");
        assertEquals(driverControllerToTest.getDrivers(),lDrivers);

    }
}