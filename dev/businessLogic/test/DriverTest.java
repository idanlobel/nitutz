package businessLogic.test;

import businessLogic.Driver;
import businessLogic.LicenseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    Driver driverToTest;
    String name;
    String phoneNumber;
    List<LicenseType>  lType;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        name="Itay";
        phoneNumber="0522521366";

        driverToTest=new Driver("0",name,phoneNumber,LicenseType.A);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {

    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals(driverToTest.getName(),name);
    }

    @org.junit.jupiter.api.Test
    void setName() {
        name="difName";
        driverToTest.setName(name);
        assertEquals(driverToTest.getName(),name);
    }

    @org.junit.jupiter.api.Test
    void getPhoneNumber() {
        assertEquals(driverToTest.getPhoneNumber(),phoneNumber);
    }

    @org.junit.jupiter.api.Test
    void setPhoneNumber() {
        phoneNumber="difPhone";
        driverToTest.setPhoneNumber(phoneNumber);
        assertEquals(driverToTest.getPhoneNumber(),phoneNumber);
    }

    @Test
    void getLicenseTypes() {

        assertEquals(driverToTest.getLicenseTypes(),lType);

    }

    @Test
    void setLicenseTypes() {
        lType.add(LicenseType.B);
        assertEquals(driverToTest.getLicenseTypes(),lType);

    }
}