package src.Presentation.Transportation;


import src.TransportationsAndWorkersModule.Service.ServiceTransportation.Service;

import java.util.Scanner;

import static src.TransportationsAndWorkersModule.Dal.Transportation.Create.createNewDatabase;
import static src.TransportationsAndWorkersModule.Dal.Transportation.CreateTable.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Connecting to DB...");
        //createNewDatabase("superLee.db");
        createNewTrucksTable();
        createNewTransportFormsTable();
        createNewTransportDocumentTable();
        createNewDocumentProductsTable();
        createSitesTable();
        createSitesTable();

        Scanner var1 = new Scanner(System.in);
        Service var2 = new Service();

        var2.Serve(var1);
    }
    public static void mainTransport() {
        System.out.println("Connecting to DB...");
        //createNewDatabase("superLee.db");
        createNewTrucksTable();
        createNewTransportFormsTable();
        createNewTransportDocumentTable();
        createNewDocumentProductsTable();
        createSitesTable();
        createSitesTable();

        Scanner var1 = new Scanner(System.in);
        Service var2 = new Service();

        var2.Serve(var1);
    }


}
