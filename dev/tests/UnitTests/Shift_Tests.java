
import src.Domain_Layer.BusinessControllers.WorkerController;
import src.Domain_Layer.BusinessObjects.Shift;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Shift_Tests {
    Shift shift;
    WorkerController workerController;

    @BeforeEach
    void setUp() {
        shift = new Shift();
        workerController = WorkerController.getInstance();
    }

    //#1:
    @Test
    void isShiftIsReady_Success() throws Exception {
        shift.addWorkerToShift(1);
        shift.addWorkerToShift(2);
        shift.addWorkerToShift(4);
        shift.addWorkerToShift(5);
        shift.assignWorkerToJob(4,"store keeper");
        shift.assignWorkerToJob(5,"driver");
        shift.assignWorkerToJob(1,"cashier");
        shift.assignWorkerToJob(2,"steward");
        shift.setShiftManager(2);
        assertEquals(true, shift.isShiftIsReady());
    }

    @Test
    void isShiftIsReady_Failure() throws Exception {
        assertEquals(false, shift.isShiftIsReady());
        shift.addWorkerToShift(1);
        shift.addWorkerToShift(2);
        shift.addWorkerToShift(4);
        shift.addWorkerToShift(5);
        shift.assignWorkerToJob(4,"store keeper");
        shift.assignWorkerToJob(5,"driver");
        shift.assignWorkerToJob(1,"cashier");
        shift.assignWorkerToJob(2,"steward");
        assertEquals(false, shift.isShiftIsReady());
    }


    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        workerController = null;
        shift = new Shift();
    }
}
