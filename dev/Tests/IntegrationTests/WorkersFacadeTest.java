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
            shiftsController.createWeeklySchedule(3, "05-06-2022", "driver department");
            workerController.addJob("driver", 3);
            workerController.addJobForAWorker(3, "driver", 3);
            workerController.addLicense(3, 3, "C");
            shiftsController.addWorkerToWeeklySchedule("05-06-2022", 1, 0, "driver department", 3, 3);
            shiftsController.assignWorkerToJobInShift("05-06-2022", 1, 0, "driver department", 3, "driver", 3);
        }
        catch (Exception e)
        {
            try {
                shiftsController.createWeeklySchedule(3, "05-06-2022", "driver department");
                workerController.addJob("driver", 3);
                workerController.addJobForAWorker(3, "driver", 3);
                workerController.addLicense(3, 3, "C");
                shiftsController.addWorkerToWeeklySchedule("05-06-2022", 1, 0, "driver department", 3, 3);
                shiftsController.assignWorkerToJobInShift("05-06-2022", 1, 0, "driver department", 3, "driver", 3);
            }
            catch(Exception e2){
                try {
                    workerController.addJob("driver", 3);
                    workerController.addJobForAWorker(3, "driver", 3);
                    workerController.addLicense(3, 3, "C");
                    shiftsController.addWorkerToWeeklySchedule("05-06-2022", 1, 0, "driver department", 3, 3);
                    shiftsController.assignWorkerToJobInShift("05-06-2022", 1, 0, "driver department", 3, "driver", 3);
                }
                catch(Exception e3){
                   try {
                       workerController.addJobForAWorker(3, "driver", 3);
                       workerController.addLicense(3, 3, "C");
                       shiftsController.addWorkerToWeeklySchedule("05-06-2022", 1, 0, "driver department", 3, 3);
                       shiftsController.assignWorkerToJobInShift("05-06-2022", 1, 0, "driver department", 3, "driver", 3);
                   }catch (Exception e4){
                       try {
                           workerController.addLicense(3, 3, "C");
                           shiftsController.addWorkerToWeeklySchedule("05-06-2022", 1, 0, "driver department", 3, 3);
                           shiftsController.assignWorkerToJobInShift("05-06-2022", 1, 0, "driver department", 3, "driver", 3);
                       }catch (Exception e5){
                           try {
                               shiftsController.addWorkerToWeeklySchedule("05-06-2022", 1, 0, "driver department", 3, 3);
                               shiftsController.assignWorkerToJobInShift("05-06-2022", 1, 0, "driver department", 3, "driver", 3);
                           }catch (Exception e6){
                               try {
                                   shiftsController.assignWorkerToJobInShift("05-06-2022", 1, 0, "driver department", 3, "driver", 3);
                               }catch (Exception e7){

                               }
                           }
                       }
                   }
                }
            }
        }
    }

    //#1:
    @Test
    void getAllDrivers_Success(){
        try {
            workerController.addWorker("paprika",12,"bbb","bb",1,1,1,3,"driver department");
            workerController.addJobForAWorker(12, "driver", 3);
            workerController.addLicense(3, 12, "A");
            shiftsController.addWorkerToWeeklySchedule("05-06-2022", 1,0, "driver department", 12, 3);
            shiftsController.assignWorkerToJobInShift("05-06-2022", 1, 0, "driver department", 12, "driver", 3);
            assertEquals(12, (workersFacade.getAllDrivers("05-06-2022", 2, 0)).get(1).getId());
            assertEquals(3, (workersFacade.getAllDrivers("05-06-2022", 2, 0)).get(0).getId());
            shiftsController.removeWorkerFromJobInShift("05-06-2022", 1, 0, "driver department", 12, "driver", 3);
            shiftsController.removeWorkerFromWeeklySchedule("05-06-2022", 1, 0, "driver department", 12, 3);
            workerController.removeLicense(3, 12, "A");
            workerController.removeJobFromAWorker(12, "driver", 3);
            workerController.deleteWorker(12, 3);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getAllDrivers_Fail() {
        try {
            workersFacade.getAllDrivers("12-06-2022", 2, 0);
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
                shiftsController.createWeeklySchedule(3, "12-06-2022", "sniff3");
                workerController.addJob("store keeper", 3);
                workerController.addJobForAWorker(7, "store keeper", 3);
                shiftsController.addWorkerToWeeklySchedule("12-06-2022", 1, 0, "sniff3", 7, 3);
                shiftsController.assignWorkerToJobInShift("12-06-2022", 1, 0, "sniff3", 7, "store keeper", 3);
                assertEquals(true, workersFacade.isTransportLegal("12-06-2022", 2, 0, "sniff3"));
                workerController.removeJobFromAWorker(7, "driver", 3);
                workerController.removeJob("store keeper",3);
                shiftsController.removeWeeklySchedule("12-06-2022",3,"sniff3");
                workerController.deleteWorker(7, 3);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void isTransportLegal_Fail(){
        try{
            workersFacade.isTransportLegal("12-06-2022", 2, 0, "driver department");
        }
        catch(Exception e){
            assertEquals("weekly schedule does not exists", e.getMessage());
        }
    }



    @AfterEach
    void tearDown() {
    }





}