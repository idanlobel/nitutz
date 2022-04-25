package businessLogic;

public class Truck {

    int number;
    LicenseType licenseType;
    String licenseNumber;
    String model;
    int weight;
    int maxWeight;

    public Truck(int number, LicenseType licenseType, String licenseNumber, String model, int weight, int maxWeight) {
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
}
