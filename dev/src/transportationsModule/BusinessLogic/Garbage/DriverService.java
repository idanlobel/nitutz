//package BusinessLogic.Garbage;
//
//import BusinessLogic.controllers.DriversController;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class DriverService {
//
//    DriversController driversController;
//    Scanner scanner;
//    public DriverService(Scanner scanner) {
//        this.scanner=scanner;
//        driversController = new DriversController();
//    }
//
//    public List<String> getDriversName() {
//        return driversController.getDriversName();
//    }
//
//
//    public String getDriversLicenceTypeByDriveId(String id) {
//        return driversController.getDriversLicenceTypeByDriveId(id);
//    }
//
//    public void addDriver() {
//
//
//        String id = askInput("enter the driver id:");
//        String name = askInput("enter the driver name:");
//        String phonenumber = askInput("enter the driver phone:");
//        String licensetype = askInput("enter the driver type A or B:" );
//        //transportForm.truckLicensePlateId = askInput("choose the transport truck license plate from your Truces match your driver: " + trucksController.getTrucksMatchDriverLicence(L).toString() );
//
//
//        driversController.addDriver(id,name,phonenumber,licensetype);
//    }
//
//    /**
//     * @param question that the user will gonna asked to answer
//     * @return the answer of the user as a string
//     */
//    private String askInput(String question){
//        System.out.println(question);
//        String answer = scanner.next();
//        return answer;
//    }
//
//    public DriversController getDriversController() {
//        return driversController;
//    }
//}
