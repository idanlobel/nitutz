package Tests.UnitTests.WorkersUnitTests;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Weekly_Schedule;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker_Schedule;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.ShiftsController;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShiftsController_Tests {
    ShiftsController shiftsController;
    DatabaseManager databaseManager;
    Worker_Schedule workerSchedule;
    Weekly_Schedule weekly_schedule;

   /* @BeforeEach
    void setUp() {
        shiftsController = ShiftsController.getInstance();
        workerSchedule = new Worker_Schedule(3);
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
        assertEquals(true, shiftsController.editWorkerSchedule(3, true, 1, 0));
        assertEquals(true, shiftsController.editWorkerSchedule(3, false, 1, 1));
    }

    @Test
    void editWorkerSchedule_Failure() throws Exception {
        assertEquals(false, shiftsController.editWorkerSchedule(500, false, 1, 0));
    }

    //#2:
    @Test
    void createWeeklySchedule_Success() throws Exception {
        assertEquals(true, shiftsController.createWeeklySchedule(2,3, "South"));
        assertEquals(true, shiftsController.createWeeklySchedule(3,3, "North"));
    }

    @Test
    void createWeeklySchedule_Failure() throws Exception {
        assertEquals(false, shiftsController.createWeeklySchedule(1,3, "North"));
    }

    //#3:
    @Test
    void addTransaction_Success() throws Exception {
        assertEquals(true, shiftsController.addTransaction(1, 1, 0, "North", 3, 3));
    }

    @Test
    void addTransaction_Failure() throws Exception {
        shiftsController.addTransaction(1, 1, 0, "North", 576, 3);
        assertEquals(false, shiftsController.addTransaction(1, 1, 0, "North", 576, 3));
    }

    //#4:
    @Test
    void removeTransaction_Success() throws Exception {
        shiftsController.addTransaction(1, 1, 0, "North", 576, 3);
        assertEquals(true, shiftsController.removeTransaction(1, 1, 0, "North", 576, 3));
    }

    @Test
    void removeTransaction_Failure() throws Exception {
        assertEquals(false, shiftsController.removeTransaction(1, 1, 0, "North", 579, 3));
    }

    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        //shiftsController = null;
    }*/
}
