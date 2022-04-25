import Service.Service;
import Service.SiteService;
import Service.TransportFormService;
import Service.TruckService;

import java.util.Scanner;



public class Program {



    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Service service = new Service();
        service.Serve(scanner);

    }

}