package src.transportationsModule.BusinessLogic;

import src.transportationsModule.BusinessLogic.LicenseType;

public class Truck {

    String number;
    LicenseType licenseType;
    String licenseNumber;
    String model;
    String weight;
    String maxWeight;

    public Truck(String number, LicenseType licenseType, String licenseNumber, String model, String weight, String maxWeight) {
        this.number = number;
        this.licenseType = licenseType;
        this.licenseNumber = licenseNumber;
        this.model = model;
        this.weight = weight;
        this.maxWeight = maxWeight;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "number=" + number +
                ", licenseType='" + licenseType + '\'' +
                ", licenseNumber=" + licenseNumber +
                ", model='" + model + '\'' +
                ", weight=" + weight +
                ", maxWeight=" + maxWeight +
                '}';
    }

    public void update(String toChangeField, String newVal) {
        switch (toChangeField) {

            case "number": {
                number = newVal;
                break;
            }
            case "licenseType": {
                licenseType = LicenseType.valueOf(newVal);
                break;
            }
            case "model": {
                model = newVal;
                break;
            }
            case "weight": {
                weight = newVal;
                break;
            }
            case "maxWeight": {
                maxWeight = newVal;
                break;
            }
        }
        //updating DB?
    }

    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public String getWeight() {
        return weight;
    }

    public String getMaxWeight() {
        return maxWeight;
    }
}
