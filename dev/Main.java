
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.Service.ServiceTransportation.Service;

import java.util.Scanner;

import static src.Presentation.Transportation.Main.mainTransport;
import static src.Presentation.Workers.Main.mainWorkers;
import static src.TransportationsAndWorkersModule.Dal.Transportation.Create.createNewDatabase;
import static src.TransportationsAndWorkersModule.Dal.Transportation.CreateTable.*;

public class Main {

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String s = "start";
    while(!s.equals("quit")) {
        System.out.println("Choose Workers Or Transport:");
        s = scanner.nextLine();
        if (s.equals("Workers")) {
            try {
                mainWorkers();
            } catch (Exception e) {
                System.out.println("run program again");
            }
        } else if (s.equals("Transport")) {
            mainTransport();
        }
    }

        System.out.println("end program");


    }



}
