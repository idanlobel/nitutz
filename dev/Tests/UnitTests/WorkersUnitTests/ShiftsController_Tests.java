package Tests.UnitTests.WorkersUnitTests;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.*;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.ShiftsController;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.WorkerController;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;
import src.TransportationsAndWorkersModule.Dal.Workers.WorkerDAO;
import sun.awt.geom.AreaOp;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShiftsController_Tests {
    ShiftsController shiftsController;
    DatabaseManager databaseManager;
    WorkerController workerController;

    @BeforeEach
    void setUp() {
        shiftsController = ShiftsController.getInstance();
        workerController = WorkerController.getInstance();
        try {
            shiftsController.createWeeklySchedule(1, 3, "North");
        }
        catch(Exception e){
            //shouldn't happen
        }
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
    void editWorkerSchedule_Success() throws Exception {
        try {
            workerController.addWorker("Amihai", 6, "AB13", "amihai@gmail.com",
                    306721, 067, 50000, 3, "sniff33");
            assertEquals(true, shiftsController.editWorkerSchedule(6, true, 1, 0));
            assertEquals(true, shiftsController.editWorkerSchedule(6, false, 1, 1));
            workerController.deleteWorker(6, 3);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    void editWorkerSchedule_Failure(){
        try {
           shiftsController.editWorkerSchedule(500, false, 1, 0);
        } catch (Exception e) {
            assertEquals("Worker schedule does not exist", e.getMessage());
        }
    }

    //#2:
    @Test
    void createWeeklySchedule_Success(){
        try {
            assertEquals(true, shiftsController.createWeeklySchedule(5000, 3, "South"));
            shiftsController.removeWeeklySchedule(5000, 3, "South");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void createWeeklySchedule_Failure(){
       try {
           shiftsController.createWeeklySchedule(5000, 3, "North");
       }
       catch(Exception e){
           assertEquals("batch entry 0: [SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed (UNIQUE constraint failed: weeklySchedule.id, weeklySchedule.site)", e.getMessage());
       }
    }

    //#3:
    @Test
    void addTransaction_Success(){
        try {
            assertEquals(true, shiftsController.addTransaction(1, 1, 0, "North", 3, 3));
            shiftsController.removeTransaction(1, 1, 0, "North", 3, 3);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addTransaction_Failure(){
        try {
            assertEquals(false, shiftsController.addTransaction(1, 1, 0, "South", 576, 3));
        }
        catch(Exception e){
            assertEquals("weekly schedule does not exists", e.getMessage());
        }
    }

    //#4:
    @Test
    void removeTransaction_Success(){
        try {
            shiftsController.addTransaction(1, 1, 0, "North", 576, 3);
            assertEquals(true, shiftsController.removeTransaction(1, 1, 0, "North", 576, 3));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void removeTransaction_Failure(){
        try {
            assertEquals(false, shiftsController.removeTransaction(1, 1, 0, "South", 579, 3));
        }
        catch(Exception e){
            assertEquals("weekly schedule does not exists", e.getMessage());
        }
    }



    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        //shiftsController = null;
    }
}
