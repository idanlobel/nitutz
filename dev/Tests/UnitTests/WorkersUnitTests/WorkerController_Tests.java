package Tests.UnitTests.WorkersUnitTests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.BankAccount;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.EmploymentConditions;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.WorkerController;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;
import src.TransportationsAndWorkersModule.Dal.Workers.WorkerDAO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkerController_Tests {
    WorkerController workerController;
    DatabaseManager databaseManager;

    @BeforeEach
    void setUp() {
        workerController = WorkerController.getInstance();
        try {
            DatabaseManager.ChangeURL("jdbc:sqlite:superLeeTests.db");
            databaseManager = databaseManager.getInstance();
            WorkerDAO workerDAO = new WorkerDAO();
            List<String> jobs = new LinkedList<>();
            jobs.add("HR");
            Worker HR = new Worker("ori",3,"123","gev",new BankAccount(1,1),new EmploymentConditions(1,new Date()),jobs,"human resources");
            workerDAO.create(HR);
        }
        catch(Exception e){
            //shouldn't happen...
        }

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
            System.out.println(e.getMessage());
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
            workerController.removeJob("CLEANER",3);
            assertEquals(true, workerController.addJob("CLEANER", 3));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addJob_Failure(){
        try {
            workerController.removeJob("cashier",3);
            assertEquals(true, workerController.addJob("cashier", 3));
            assertEquals(false, workerController.addJob("cashier", 3));
        }
        catch(Exception e) {
            assertEquals("job already exists",e.getMessage());
        }
    }

    //#4:
    @Test
    void edit_Worker_Success(){
        try {
            assertEquals(true,workerController.addWorker("",5,"","",1,1,1,3,""));
            assertEquals(true, workerController.editWorker("", "", "yossi9700@gmail.com",
                    0, 0, 500, 5, 3));
            assertEquals(true, workerController.editWorker("", "", "Ori77@gmail.com",
                    0, 0, 100000, 3, 3));
            workerController.deleteWorker(5,3);
        }
        catch(Exception e){
            assertEquals("", e.getMessage());
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
            assertEquals(true,workerController.removeJobFromAWorker(3, "cashier", 3));
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
        }
        try{
            assertEquals(true,workerController.addWorker("",15,"","",1,1,1,3,""));
            assertEquals(false, workerController.addJobForAWorker(15, "driver", 3));
        }
        catch(Exception e2){
            assertEquals("This job is problematic", e2.getMessage());
            try{
                assertEquals(false, workerController.addJobForAWorker(15, "pizza maker", 3));
            }
            catch(Exception e3){
                assertEquals("This job is problematic", e3.getMessage());
                try {
                    assertEquals(true, workerController.deleteWorker(15,3));
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    //#6:
    @Test
    void removeJobFromAWorker_Success(){
        try {
            workerController.addJobForAWorker(15, "driver", 3);
            assertEquals(true, workerController.removeJobFromAWorker(15, "driver", 3));
        }
        catch(Exception e){
            System.out.println(e.getMessage());//doesnt really work becasue it prints a message
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
                assertEquals("job doesn't exist", e2.getMessage());
            }
        }
    }

    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        //workerController = null;
    }
}
