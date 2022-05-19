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
            Worker HR = new Worker("ori",3,"123","gev",new BankAccount(1,1),new EmploymentConditions(1,new Date()),jobs,"driver department");
            workerDAO.create(HR);
            workerController.addJob("driver", 3);
        }
        catch(Exception e){
            try{
                workerController.addJob("driver", 3);
            }
            catch(Exception e2) {
                //do nothing..
            }
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
            System.out.println(e.getMessage());
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
            assertEquals(true, workerController.addJob("CLEANER", 3));
            workerController.removeJob("CLEANER", 3);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addJob_Failure(){
        try {
            assertEquals(true, workerController.addJob("cashier", 3));
            assertEquals(false, workerController.addJob("cashier", 3));
            assertEquals(false, workerController.addJob("cashier", 5));
        }
        catch(Exception e) {
            assertEquals("job already exists",e.getMessage());
            try {
                workerController.removeJob("cashier", 3);
            }
            catch(Exception e2){
                System.out.println(e2.getMessage());
            }
        }
    }

    //#4:
    @Test
    void edit_Worker_Success(){
        try {
            assertEquals(true,workerController.addWorker("",77,"","",1,1,1,3,""));
            assertEquals(true, workerController.editWorker("", "", "yossi9700@gmail.com",
                    0, 0, 500, 77, 3));
            assertEquals(true, workerController.editWorker("", "", "Ori77@gmail.com",
                    0, 0, 100000, 3, 3));
            workerController.deleteWorker(77,3);
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
            workerController.addJob("cashier", 3);
            assertEquals(true, workerController.addJobForAWorker(3, "cashier", 3));
            workerController.removeJobFromAWorker(3, "cashier", 3);
            workerController.removeJob("cashier", 3);

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
    }

    //#6:
    @Test
    void removeJobFromAWorker_Success(){
        try {
            workerController.addWorker("pap",66,"bbb","bb",1,1,1,3,"North");
            workerController.addJob("cashier", 3);
            workerController.addJobForAWorker(66, "cashier", 3);
            assertEquals(true, workerController.removeJobFromAWorker(66, "cashier", 3));
            workerController.deleteWorker(66, 3);
            workerController.removeJob("cashier", 3);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void removeJobFromAWorker_Failure(){
        try {
            assertEquals(false, workerController.removeJobFromAWorker(9999, "cashier", 3));
        }
        catch(Exception e){
            assertEquals("worker doesn't exist", e.getMessage());
            try{
                assertEquals(false, workerController.removeJobFromAWorker(3, "cashier", 3));
            }
            catch(Exception e2){
                assertEquals("job doesn't exist", e2.getMessage());
            }
        }
    }

    //#7:
    @Test
    void Login_Success() {
        try {
            assertEquals(true, workerController.Login(3,"123"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void Login_Failure() {
        try {
            workerController.Login(5899,"123");
        }
        catch (Exception e) {
            assertEquals("worker doesn't exist", e.getMessage());
        }
    }

    //#8:
    @Test
    void addLicense_Success() {
        try {
            workerController.addWorker("",57,"bbb","bb",1,1,1,3,"North");
            workerController.addJobForAWorker(57, "driver", 3);
            assertEquals(true, workerController.addLicense(3,57, "A"));
            workerController.removeLicense(3, 57, "A");
            workerController.removeJobFromAWorker(57, "driver", 3);
            workerController.deleteWorker(57, 3);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addLicense_Failure() {
        try {
            workerController.addWorker("",507,"bbb","bb",1,1,1,3,"North");
            workerController.addLicense(3,507, "A");
        }
        catch (Exception e) {
            assertEquals("worker isn't a driver", e.getMessage());
            try {
                workerController.deleteWorker(507, 3);
            }
            catch(Exception e2){
                System.out.println(e2.getMessage());
            }
        }
    }

    //#9:
    @Test
    void removeLicense_Success() {
        try {
            workerController.addWorker("",9,"bbb","bb",1,1,1,3,"North");
            workerController.addJobForAWorker(9, "driver", 3);
            workerController.addLicense(3,9, "A");
            assertEquals(true, workerController.removeLicense(3,9, "A"));
            workerController.removeJobFromAWorker(9, "driver", 3);
            workerController.deleteWorker(9, 3);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void removeLicense_Failure() {
        try {
            workerController.addWorker("",50000,"bbb","bb",1,1,1,3,"North");
            workerController.removeLicense(3,50000, "A");
        }
        catch (Exception e) {
            assertEquals("worker isnt a driver", e.getMessage());
            try {
                workerController.deleteWorker(50000, 3);
            }
            catch(Exception e2){
                //do nothing
            }
        }
    }


    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        //workerController = null;
    }
}
