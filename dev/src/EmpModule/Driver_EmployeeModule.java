package src.EmpModule;

import src.transportationsModule.BusinessLogic.LicenseType;

public class Driver_EmployeeModule {

    String id;
    String name;
    String phoneNumber;
    LicenseType licenseTypes;


    public Driver_EmployeeModule(String id , String name, String phoneNumber, LicenseType licenseTypes) {
        this.id =id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseTypes = licenseTypes;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LicenseType getLicenseTypes() {
        return licenseTypes;
    }

    public void setLicenseTypes(LicenseType licenseTypes) {
        this.licenseTypes = licenseTypes;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", licenseTypes=" + licenseTypes +
                '}';
    }


}
