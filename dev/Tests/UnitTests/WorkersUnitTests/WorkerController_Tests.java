package Tests.UnitTests.WorkersUnitTests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.WorkerController;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WorkerController_Tests {
    WorkerController workerController;
    DatabaseManager databaseManager;

    @BeforeEach
    void setUp() {
        workerController = WorkerController.getInstance();
        try {
            databaseManager = databaseManager.getInstance();
        }
        catch(Exception e){
            //shouldn't happen...
        }
        databaseManager.ChangeURL("jdbc:sqlite:superLeeTests.db");
    }


    //#1:
    @Test
    void add_Worker_Success() {
        try {
            assertEquals(true, workerController.addWorker("Amihai", 6, "AB13", "amihai@gmail.com",
                    306721, 067, 50000, 3, "sniff33"));
            assertEquals(true, workerController.addWorker("Ori", 27, "Orkal57", "Ori@gmail.com",
                    387562, 22, 50000, 3, "sniff33"));
            try {
                workerController.deleteWorker(6, 3);
                workerController.deleteWorker(27, 3);
            }
            catch(Exception e2){
                System.out.println(e2.getMessage());
            }
        } catch (Exception e) {
            System.out.println("remove the workers from the database and try again.");
        }
    }

    @Test
    void add_Worker_Fail() {
        try {
            workerController.addWorker("Amihai", 6, "AB13", "amihai@gmail.com",
                    306721, 067, 50000, 3, "sniff33");
            assertEquals(false, workerController.addWorker("Amihai", 6, "AB13", "amihai@gmail.com",
                    306721, 067, 50000, 3, "sniff33"));
        } catch (Exception e) {
            assertEquals("worker already exists", e.getMessage());
            try{
                workerController.deleteWorker(6, 3);
            }
            catch(Exception e2){
                System.out.println(e2.getMessage());
            }
        }
    }

    //#2:
    @Test
    void delete_Worker_Success(){
        try {
            workerController.addWorker("Amihai", 6, "AB13", "amihai@gmail.com",
                    306721, 067, 50000, 3, "sniff33");
            assertEquals(true, workerController.deleteWorker(6, 3));
        }
        catch(Exception e){
            System.out.println("shouldn't have failed........");
        }
    }

    @Test
    void delete_Worker_Failure() {
        try {
            assertEquals(false, workerController.deleteWorker(300, 3));
        }
        catch(Exception e){
            assertEquals("worker doesn't exist", e.getMessage());
        }
    }

    //#3:
    @Test
    void addJob_Success(){
        try {
            assertEquals(true, workerController.addJob("CLEANER", 3));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addJob_Failure(){
        try {
            assertEquals(false, workerController.addJob("cashier", 3));
        }
        catch(Exception e) {
            
        }
    }

    //#4:
    @Test
    void edit_Worker_Success(){
        try {
            assertEquals(true, workerController.editWorker("", "", "yossi9700@gmail.com",
                    0, 0, 500, 5, 3));
            assertEquals(true, workerController.editWorker("", "", "Ori77@gmail.com",
                    0, 0, 100000, 3, 3));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void edit_Worker_Fail(){
        try {
            assertEquals(false, workerController.editWorker("Amihai", "", "",
                    0, 0, 5000, 900, 3));
        }
        catch(Exception e){
            assertEquals("worker doesn't exist", e.getMessage());
        }
    }

    //#5:
    @Test
    void addJobForAWorker_Success(){
        try {
            assertEquals(true, workerController.addJobForAWorker(3, "cashier", 3));
            workerController.removeJobFromAWorker(3, "cashier", 3);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addJobForAWorker_Failure(){
        try {
            assertEquals(false, workerController.addJobForAWorker(99, "driver", 3));
        }
        catch(Exception e){
            assertEquals("worker doesn't exist", e.getMessage());
            try{
                assertEquals(false, workerController.addJobForAWorker(15, "driver", 3));
            }
            catch(Exception e2){
                assertEquals("This job is problematic", e2.getMessage());
            }

            try{
                assertEquals(false, workerController.addJobForAWorker(15, "pizza maker", 3));
            }
            catch(Exception e3){
                assertEquals("This job is problematic", e3.getMessage());
            }
        }
    }

    //#6:
    @Test
    void removeJobFromAWorker_Success(){
        try {
            assertEquals(true, workerController.removeJobFromAWorker(15, "driver", 3));
            workerController.addJobForAWorker(15, "driver", 3);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void removeJobFromAWorker_Failure(){
        try {
            assertEquals(false, workerController.removeJobFromAWorker(99, "cashier", 3));
        }
        catch(Exception e){
            assertEquals("worker doesn't exist", e.getMessage());
            try{
                assertEquals(false, workerController.removeJobFromAWorker(15, "cashier", 3));
            }
            catch(Exception e2){
                assertEquals("", e2.getMessage());
            }
        }
    }

    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        //workerController = null;
    }
}
