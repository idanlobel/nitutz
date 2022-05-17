package Tests.UnitTests.WorkersUnitTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Shift;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.WorkerController;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Shift_Tests {
    Shift shift;
    WorkerController workerController;
    DatabaseManager databaseManager;

    @BeforeEach
    void setUp() {
        shift = new Shift();
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
    void isShiftIsReady_Success() throws Exception {
        shift.addWorkerToShift(15);
        shift.addWorkerToShift(19);
        shift.addWorkerToShift(123);
        shift.addWorkerToShift(5);
        shift.addWorkerToShift(313356701);
        shift.assignWorkerToJob(15,"store keeper");
        shift.assignWorkerToJob(19,"driver");
        shift.assignWorkerToJob(123,"cashier");
        shift.assignWorkerToJob(5,"steward");
        shift.assignWorkerToJob(313356701,"cashier");
        shift.setShiftManager(19);
        assertEquals(true, shift.isShiftIsReady());
    }

    @Test
    void isShiftIsReady_Failure() throws Exception {
        assertEquals(false, shift.isShiftIsReady());
        shift.addWorkerToShift(15);
        shift.addWorkerToShift(19);
        shift.addWorkerToShift(123);
        shift.addWorkerToShift(5);
        shift.addWorkerToShift(313356701);
        shift.assignWorkerToJob(15,"store keeper");
        shift.assignWorkerToJob(19,"driver");
        shift.assignWorkerToJob(123,"cashier");
        shift.assignWorkerToJob(5,"steward");
        shift.assignWorkerToJob(313356701,"cashier");
        assertEquals(false, shift.isShiftIsReady());
    }

    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        //workerController = null;
        shift = new Shift();
    }
}
