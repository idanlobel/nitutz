package Tests.UnitTests.IntegrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.WorkersFacade;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;

import static org.junit.jupiter.api.Assertions.*;

class WorkersFacadeTest {

    WorkersFacade workersFacade;
    DatabaseManager databaseManager;
    @BeforeEach
    void setUp() {
        try {
            databaseManager.ChangeURL("jdbc:sqlite:superLeeTests.db");
            databaseManager=DatabaseManager.getInstance();
            workersFacade=new WorkersFacade();
        }
        catch (Exception e)
        {
            fail(e.getMessage());

        }

    }

    @AfterEach
    void tearDown() {
    }



    @Test
    void getAllDrivers() {
        try {
            workersFacade.getAllDrivers(2,2,2);
        } catch (Exception e) {
            assertEquals("weekly schedule does not exists",e.getMessage());

        }
    }

    @Test
    void isTransportLegal() {
        try {
            workersFacade.isTransportLegal(2,2,2,"super1");
        } catch (Exception e) {
            assertEquals("weekly schedule does not exists",e.getMessage());
        }
    }
}