
import src.transportationsModule.Service.Service;

import java.util.Arrays;
import java.util.Scanner;

import static src.transportationsModule.Dal.Create.createNewDatabase;
import static src.transportationsModule.Dal.CreateTable.*;

public class Main {

    public static void main(String[] args) {
	System.out.println("Connecting to DB...");
	createNewDatabase("SuperDuper.db");
    createNewTrucksTable();
    createNewTransportFormsTable();
    createNewTransportDocumentTable();
    createNewDocumentProductsTable();



    Scanner var1 = new Scanner(System.in);
    Service var2 = new Service();

    var2.Serve(var1);
            }



}
