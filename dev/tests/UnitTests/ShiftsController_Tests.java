import org.junit.jupiter.api.AfterEach;
import src.Domain_Layer.BusinessControllers.ShiftsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShiftsController_Tests {
    ShiftsController shiftsController;

    @BeforeEach
    void setUp() {
        shiftsController = ShiftsController.getInstance();
    }

    //#1:
    @Test
    void editWorkerSchedule_Success() throws Exception {
        assertEquals(true, shiftsController.editWorkerSchedule(3, true, 1, 0));
        assertEquals(true, shiftsController.editWorkerSchedule(3, false, 1, 1));
    }

    @Test
    void editWorkerSchedule_Failure() throws Exception {
        assertEquals(false, shiftsController.editWorkerSchedule(3, true, 2, 1));
        assertEquals(false, shiftsController.editWorkerSchedule(500, false, 1, 0));
    }

    //#2:
    @Test
    void createWeeklySchedule_Success() throws Exception {
        assertEquals(true, shiftsController.createWeeklySchedule(2,3));
        assertEquals(true, shiftsController.createWeeklySchedule(3,3));
    }

    @Test
    void createWeeklySchedule_Failure() throws Exception {
        assertEquals(false, shiftsController.createWeeklySchedule(1,3));
    }

    //#3:
    @Test
    void addTransaction_Success() throws Exception {
        assertEquals(true, shiftsController.addTransaction(1, 1, 0, 576, 3));
    }

    @Test
    void addTransaction_Failure() throws Exception {
        shiftsController.addTransaction(1, 1, 0, 576, 3);
        assertEquals(false, shiftsController.addTransaction(1, 1, 0, 576, 3));
        assertEquals(false, shiftsController.addTransaction(1, 1, 0, 56, 4));
        assertEquals(false, shiftsController.addTransaction(1, 1, 0, 11, 58));
        assertEquals(false, shiftsController.addTransaction(1, 1, 0, 11, 1)); //He is a cashier,
                                                                                                                         //but not in this shift
    }

    //#4:
    @Test
    void removeTransaction_Success() throws Exception {
        shiftsController.addTransaction(1, 1, 0, 576, 3);
        assertEquals(true, shiftsController.removeTransaction(1, 1, 0, 576, 3));
    }

    @Test
    void removeTransaction_Failure() throws Exception {
        shiftsController.addTransaction(1, 1, 0, 576, 3);

        assertEquals(false, shiftsController.removeTransaction(1, 1, 0, 576, 4));
        assertEquals(false, shiftsController.removeTransaction(1, 1, 0, 1, 4));
        assertEquals(false, shiftsController.removeTransaction(1, 1, 0, 11, 500));
        assertEquals(false, shiftsController.removeTransaction(1, 1, 0, 11, 1));
    }

    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        shiftsController = null;
    }
}
