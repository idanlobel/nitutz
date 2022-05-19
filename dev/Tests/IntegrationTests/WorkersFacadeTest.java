package Tests.IntegrationTests;

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
            Worker HR = new Worker("ori",3,"123","gev",new BankAccount(1,1),new EmploymentConditions(1,new Date()),jobs,"driver department");
            workerDAO.create(HR);
        }
        catch (Exception e)
        {
            try {
                shiftsController.createWeeklySchedule(1, 3, "driver department");
                workerController.addJob("driver", 3);
                workerController.addJobForAWorker(3, "driver", 3);
                workerController.addLicense(3, 3, "C");
                shiftsController.addWorkerToWeeklySchedule(1, 1, 0, "driver department", 3, 3);
                shiftsController.assignWorkerToJobInShift(1, 1, 0, "driver department", 3, "driver", 3);
            }
            catch(Exception e2){
                try {
                    workerController.addJob("driver", 3);
                    workerController.addJobForAWorker(3, "driver", 3);
                    workerController.addLicense(3, 3, "C");
                    shiftsController.addWorkerToWeeklySchedule(1, 1, 0, "driver department", 3, 3);
                    shiftsController.assignWorkerToJobInShift(1, 1, 0, "driver department", 3, "driver", 3);
                }
                catch(Exception e3){
                    //do nothing
                }
            }
        }
    }

    //#1:
    @Test
    void getAllDrivers_Success(){
        try {
            workerController.addWorker("paprika",5,"bbb","bb",1,1,1,3,"driver department");
            workerController.addJobForAWorker(5, "driver", 3);
            workerController.addLicense(3, 5, "A");
            shiftsController.addWorkerToWeeklySchedule(1, 1,0, "driver department", 5, 3);
            shiftsController.assignWorkerToJobInShift(1, 1, 0, "driver department", 5, "driver", 3);
            assertEquals(5, (workersFacade.getAllDrivers(1, 2, 0)).get(1).getId());
            assertEquals(3, (workersFacade.getAllDrivers(1, 2, 0)).get(0).getId());
            shiftsController.removeWorkerFromJobInShift(1, 1, 0, "driver department", 5, "driver", 3);
            shiftsController.removeWorkerFromWeeklySchedule(1, 1, 0, "driver department", 5, 3);
            workerController.removeLicense(3, 5, "A");
            workerController.removeJobFromAWorker(5, "driver", 3);
            workerController.deleteWorker(5, 3);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getAllDrivers_Fail() {
        try {
            workersFacade.getAllDrivers(2000, 2, 0);
        }
        catch(Exception e){
            assertEquals("weekly schedule does not exists", e.getMessage());
        }
    }

    //#2:
    @Test
    void isTransportLegal_Success(){
        try{
            try {
                workerController.addWorker("paprika", 7, "bbb", "bb", 1, 1, 1, 3, "sniff3");
                shiftsController.createWeeklySchedule(2, 3, "sniff3");
                workerController.addJob("store keeper", 3);
                workerController.addJobForAWorker(7, "store keeper", 3);
                shiftsController.addWorkerToWeeklySchedule(2, 1, 0, "sniff3", 7, 3);
                shiftsController.assignWorkerToJobInShift(2, 1, 0, "sniff3", 7, "store keeper", 3);
            }
            catch(Exception e){
                assertEquals(true, workersFacade.isTransportLegal(2, 2, 0, "sniff3"));
                return;
            }
            assertEquals(true, workersFacade.isTransportLegal(2, 2, 0, "sniff3"));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void isTransportLegal_Fail(){
        try{
            workersFacade.isTransportLegal(1, 2, 0, "driver department");
        }
        catch(Exception e){
            assertEquals("", e.getMessage());
        }
    }



    @AfterEach
    void tearDown() {
    }





}