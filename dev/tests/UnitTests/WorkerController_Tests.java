import src.Domain_Layer.BusinessControllers.WorkerController;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WorkerController_Tests {
    WorkerController workerController;

    @BeforeEach
    void setUp() {
        workerController = WorkerController.getInstance();
    }

    //#1:
    @Test
    void add_Worker_Success() throws Exception {
        assertEquals(true, workerController.addWorker("Amihai", 6, "AB13", "amihai@gmail.com",
                306721, 067, 50000,3));
        assertEquals(true, workerController.addWorker("Ori", 27, "Orkal57", "Ori@gmail.com",
                387562, 22, 50000,3));
    }

    @Test
    void add_Worker_Fail() throws Exception {
        assertEquals(false, workerController.addWorker("Amihai", 1, "AB13", "amihai@gmail.com",
                306721, 067, 50000,3));
        assertEquals(false, workerController.addWorker("Ori", 2, "Orkal57", "Ori@gmail.com",
                387562, 22, 50000,3));
    }

    //#2:
    @Test
    void delete_Worker_Success() throws Exception {
        assertEquals(true, workerController.deleteWorker(1,3));
    }

    @Test
    void delete_Worker_Failure() throws Exception {
        assertEquals(false, workerController.deleteWorker(300,3));
    }

    //#3:
    @Test
    void addJob_Success() throws Exception {
        assertEquals(true, workerController.addJob("CLEANER",3));
    }

    @Test
    void addJob_Failure() throws Exception {
        assertEquals(false, workerController.addJob("cashier",3));
    }

    //#4:
    @Test
    void edit_Worker_Success() throws Exception {
        assertEquals(true, workerController.editWorker("", "", "yossi9700@gmail.com",
                0, 0, 500, 2));
        assertEquals(true, workerController.editWorker("", "", "Ori77@gmail.com",
                0, 0, 100000, 3));
    }

    @Test
    void edit_Worker_Fail() throws Exception {
        assertEquals(false, workerController.editWorker("Amihai", "", "",
                0, 0, 5000, 900));
        assertEquals(false, workerController.editWorker("Ori", "paprika59", "",
                0, 2222, 50, 99));
    }

    //#5:
    @Test
    void addJobForAWorker_Success() throws Exception {
        assertEquals(true, workerController.addJobForAWorker(3, "cashier",3));
    }

    @Test
    void addJobForAWorker_Failure() throws Exception {
        assertEquals(false, workerController.addJobForAWorker(2, "driver",3));
        assertEquals(false, workerController.addJobForAWorker(99, "driver",3));
        assertEquals(false, workerController.addJobForAWorker(2, "pizza maker",3));
    }

    //#6:
    @Test
    void removeJobFromAWorker_Success() throws Exception {
        assertEquals(true, workerController.removeJobFromAWorker(2, "driver",3));
    }

    @Test
    void removeJobFromAWorker_Failure() throws Exception {
        assertEquals(false, workerController.removeJobFromAWorker(2, "cashier",3));
        assertEquals(false, workerController.removeJobFromAWorker(99, "driver",3));
        assertEquals(false, workerController.removeJobFromAWorker(2, "pizza maker",3));
    }

    @AfterEach
    void tearDown() { //TODO:: doesn't work properly (because it's a singleton) - NEED TO FIX THIS!
        workerController = null;
    }
}
