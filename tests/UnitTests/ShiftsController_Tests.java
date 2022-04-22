import Domain_Layer.BusinessControllers.ShiftsController;
import org.junit.jupiter.api.AfterEach;
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


    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        shiftsController = null;
    }
}
