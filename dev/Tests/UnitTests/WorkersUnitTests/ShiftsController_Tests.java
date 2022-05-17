package Tests.UnitTests.WorkersUnitTests;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Weekly_Schedule;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker_Schedule;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.ShiftsController;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;
import sun.awt.geom.AreaOp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShiftsController_Tests {
    ShiftsController shiftsController;
    DatabaseManager databaseManager;
    Weekly_Schedule weekly_schedule;

    @BeforeEach
    void setUp() {
        shiftsController = ShiftsController.getInstance();
        weekly_schedule = new Weekly_Schedule(1, "North");
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
    void editWorkerSchedule_Success() throws Exception {
        try {
            assertEquals(true, shiftsController.editWorkerSchedule(5, true, 1, 0));
            assertEquals(true, shiftsController.editWorkerSchedule(5, false, 1, 1));
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
            try {
                assertEquals("Worker schedule does not exist", e.getMessage());
                shiftsController.editWorkerSchedule(3, true, 1, 1);
            }
            catch(Exception e2){
                assertEquals("The HR can only work in morning shifts", e2.getMessage());
            }
        }
    }

    //#2:
    @Test
    void createWeeklySchedule_Success(){
        try {
            assertEquals(true, shiftsController.createWeeklySchedule(5000, 3, "South"));
        }
        catch(Exception e){
            System.out.println("Worker Schedule already exists in the database, delete him first");
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
            assertEquals(true, shiftsController.addTransaction(1, 1, 0, "site2", 3, 3));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addTransaction_Failure(){
        try {
            assertEquals(false, shiftsController.addTransaction(1, 1, 0, "North", 576, 3));
        }
        catch(Exception e){
            assertEquals("weekly schedule does not exists", e.getMessage());
        }
    }

    //#4:
    @Test
    void removeTransaction_Success(){
        try {
            shiftsController.addTransaction(5000, 1, 0, "South", 576, 3);
            assertEquals(true, shiftsController.removeTransaction(5000, 1, 0, "South", 576, 3));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void removeTransaction_Failure(){
        try {
            assertEquals(false, shiftsController.removeTransaction(1, 1, 0, "North", 579, 3));
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
