package Tests.UnitTests.IntegrationTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.BankAccount;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.EmploymentConditions;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.WorkersFacade;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.ShiftsController;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.WorkerController;
import src.TransportationsAndWorkersModule.Dal.DatabaseManager;
import src.TransportationsAndWorkersModule.Dal.Workers.WorkerDAO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkersFacadeTest {

    WorkersFacade workersFacade;
    DatabaseManager databaseManager;
    WorkerController workerController;
    ShiftsController shiftsController;
    @BeforeEach
    void setUp() {
        workersFacade = new WorkersFacade();
        workerController = WorkerController.getInstance();
        shiftsController = ShiftsController.getInstance();
        try {
            DatabaseManager.ChangeURL("jdbc:sqlite:superLeeTests.db");
            databaseManager = databaseManager.getInstance();
            WorkerDAO workerDAO = new WorkerDAO();
            List<String> jobs = new LinkedList<>();
            jobs.add("HR");
            Worker HR = new Worker("ori",3,"123","gev",new BankAccount(1,1),new EmploymentConditions(1,new Date()),jobs,"human resources");
            workerDAO.create(HR);
        }
        catch (Exception e)
        {
            //shouldn't happen...
        }
    }

    //#1:
    @Test
    void getAllDrivers_Success(){
        try {
            workerController.addWorker("paprika",5,"bbb","bb",1,1,1,3,"driver department");
            workerController.addJob("driver", 3);
            workerController.addJobForAWorker(5, "driver", 3);
            shiftsController.createWeeklySchedule(1, 3, "driver department");
            shiftsController.addWorkerToWeeklySchedule(1, 1,1, "driver department", 5, 3);
            shiftsController.assignWorkerToJobInShift(1, 1, 1, "driver department", 5, "driver", 3);
            assertEquals(5, (workersFacade.getAllDrivers(1, 1, 1)).get(0).getId());
            shiftsController.removeWorkerFromJobInShift(1, 1, 1, "driver department", 5, "driver", 3);
            shiftsController.removeWorkerFromWeeklySchedule(1, 1, 1, "driver department", 5, 3);
            workerController.removeJobFromAWorker(5, "driver", 3);
            workerController.deleteWorker(5, 3);
            workerController.removeJob("driver", 3);
            shiftsController.removeWeeklySchedule(1, 3, "driver department");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



    @AfterEach
    void tearDown() {
    }





}