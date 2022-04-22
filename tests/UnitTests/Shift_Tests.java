
import Domain_Layer.BusinessControllers.WorkerController;
import Domain_Layer.BusinessObjects.Shift;
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
        shift.addWorkerToShift(workerController.getWorker(1));
        shift.addWorkerToShift(workerController.getWorker(2));
        shift.addWorkerToShift(workerController.getWorker(4));
        assertEquals(true, shift.isShiftIsReady());
    }

    @Test
    void isShiftIsReady_Failure() throws Exception {
        assertEquals(false, shift.isShiftIsReady());
        shift.addWorkerToShift(workerController.getWorker(2));
        shift.addWorkerToShift(workerController.getWorker(4));
        assertEquals(false, shift.isShiftIsReady());
    }


    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        workerController = null;
        shift = new Shift();
    }
}
