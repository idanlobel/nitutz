package src.Presentation.Workers;




import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.WorkerDTO;
import src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersWorkers.ShiftsController;
import src.TransportationsAndWorkersModule.Service.ServiceWorkers.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void mainWorkers() throws Exception {
        ServiceLayer serviceLayer = new ServiceLayer();
        System.out.println("Hello! welcome to our system. Firstly, please enter your login information: ");
        Login loginInfo = Login();
        while (!loginInfo.isAUser()) {
            System.out.println("The information you've entered is incorrect, please try again: ");
            loginInfo = Login();
        }
        System.out.println("Hello " + loginInfo.getName() + "! You've successfully logged-in.");
        if (loginInfo.isHR()) {
            System.out.println("Your role in the system is: HR Worker");
        }

        //after logged-in successfully:

        //TODO::Available methods::
        // AS a 'regular' worker: 1- editWorkerSchedule, 2- viewWorkerSchedule
        // As a 'Shift Manager' worker: 3 - addTransaction, 4 - removeTransaction
        // As an 'HR' worker: 5 - addJobs, 6 - createWeeklySchedule, 7 - showShiftWorkers, 8 - viewWeeklySchedule,
        //                    9 - setShiftManagerToWeeklySchedule, 10 - removeWorkerFromWeeklySchedule,
        //                    11 - addWorkerToWeeklySchedule, 12 - isShiftReady, 13 - isWeeklyScheduleReady, 14 - showAllWorkers,
        //                    15 - registerAWorker (There's no register for users! Only the HR can add new workers to the company),
        //                    16 - removeAWorker, 17 - editAWorker,
        //                    18 - addJobForAWorker, 19 - removeJobFromAWorker
        //                    99 - exit the program: hence log-out
        int choice = 0;
        Scanner sc;
        while(choice != 99) {
            System.out.println("Please enter your desired function (according to the ones available in the instructions list "
                    + "that has been provided to you): ");
            sc = new Scanner(System.in);
            choice = sc.nextInt();
            switch (choice) {
                //'regular worker' methods:
                case 1: //editWorkerSchedule:
                    List<Object> list = editWorkerSchedule(loginInfo.isHr());
                    if (list == null) break;
                    Response<Boolean> ans = serviceLayer.editWorkerSchedule(loginInfo.getWorkerID(),
                            (boolean) list.get(0), (int) list.get(1), (int) list.get(2));
                    if (ans.ErrorOccured())System.out.println(ans.ErrorMessage);
                    else System.out.println("The shift information has been updated successfully");
                    break;
                case 2: //viewWorkerSchedule
                    if (!loginInfo.isHr()) {
                        Response<WorkerScheduleSL> workerSchedule = serviceLayer.viewWorkerSchedule(loginInfo.getWorkerID());
                        if (workerSchedule.ErrorOccured()) {
                            System.out.println(workerSchedule.ErrorMessage);
                            break;
                        }
                        System.out.println("Your working Schedule: " + workerSchedule.value.toString());
                    } else {
                        System.out.println("Please enter the Worker's ID of the specified worker: ");
                        sc = new Scanner(System.in);
                        int workerID = sc.nextInt();
                        Response<WorkerScheduleSL> workerSchedule = serviceLayer.viewWorkerSchedule(workerID);
                        if (workerSchedule.ErrorOccured()) {
                            System.out.println(workerSchedule.ErrorMessage);
                            break;
                        }
                        System.out.println("The specified worker's working Schedule: " + workerSchedule.value.toString());
                    }
                    break;

                //'Shift Manager' methods:
                case 3: //addTransaction - a 'shift Manager' or a 'Cashier' or an 'HR' can do this method
                    List<Object> l1 = addOrRemoveTransaction();
                    if (l1 == null) break;
                    Response<Boolean> ans1 = serviceLayer.addTransaction((int) l1.get(0),
                            (int) l1.get(1), (int) l1.get(2),(String) l1.get(4), (int) l1.get(3), loginInfo.getWorkerID());
                    if (ans1.ErrorOccured())System.out.println(ans1.ErrorMessage);
                    else System.out.println("The transaction has been made successfully");
                    break;
                case 4: //removeTransaction - a 'shift manager' or an 'HR' can perform this method
                    List<Object> l2 = addOrRemoveTransaction();
                    if (l2 == null) break;
                    Response<Boolean> ans2 = serviceLayer.removeTransaction(loginInfo.getWorkerID(), (int) l2.get(0),
                            (int) l2.get(1), (int) l2.get(2),(String) l2.get(4), (int) l2.get(3));
                    if (ans2.ErrorOccured()) System.out.println(ans2.ErrorMessage);
                    else System.out.println("The transaction has been removed successfully");
                    break;

                //'HR' worker methods:
                case 5: //addJobs
                    if(!isHR(loginInfo.isHr()))
                        break;
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the Job's name that you'd like to add: ");
                    String job = sc.nextLine();
                    Response<Boolean> ans3 = serviceLayer.addJobs(loginInfo.getWorkerID(), job);
                    if (ans3.ErrorOccured()) System.out.println(ans3.ErrorMessage);
                    else System.out.println("The Job has been added successfully");
                    break;

                case 6: //createWeeklySchedule
                    if(!isHR(loginInfo.isHr()))
                        break;
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the id of the week for the weekly schedule: ");
                    int weekID = sc.nextInt();
                    System.out.println("Please enter the site of the weekly schedule: ");
                    String site = new Scanner(System.in).nextLine();
                    Response<Boolean> res = serviceLayer.createWeeklySchedule(loginInfo.getWorkerID(), weekID, site);
                    if (res.ErrorOccured())
                        System.out.println(res.ErrorMessage);
                    else
                        System.out.println("The schedule has been created successfully");
                    break;
                case 7: //showShiftWorkers
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> list1 = showShiftWorkers();
                    if (list1 == null) break;
                    Response<List<WorkerSL>> workersInShift = serviceLayer.showShiftWorkers(loginInfo.getWorkerID(), (int) list1.get(0),
                            (int) list1.get(1), (int) list1.get(2),(String)list1.get(3));
                    if (workersInShift.ErrorOccured())
                        System.out.println(workersInShift.ErrorMessage);
                    else if (workersInShift.value.isEmpty())
                        System.out.println("There are no workers in the specified shift");
                    else System.out.println("The workers in the desired shift are: " + workersInShift.value.toString() + "\n" +
                                "And the shift manager is: " + serviceLayer.getShiftManagerInfo(loginInfo.getWorkerID(), (int) list1.get(0),
                                (int) list1.get(1), (int) list1.get(2),(String) list1.get(3)).value.toString());
                    break;
                case 8: //viewWeeklySchedule TODO:: For now, if the Weekly Schedule will be shown only if being filled completely.
                    //TODO: FIX THIS - A LOT OF NULL POINTER EXCEPTIONS
                    //(We want to change that later on)
                    if(!isHR(loginInfo.isHr()))
                        break;
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the id of the week for the weekly schedule: ");
                    int weeklyID = sc.nextInt();
                    System.out.println("Please enter the site of the weekly schedule: ");
                    String site1 = new Scanner(System.in).nextLine();
                    Response<WeeklyScheduleSL> weeklySchedule = serviceLayer.viewWeeklySchedule(loginInfo.getWorkerID(), weeklyID,site1);
                    if (weeklySchedule.ErrorOccured())
                        System.out.println(weeklySchedule.ErrorMessage);
                    else System.out.println(weeklySchedule.value.toString());
                    break;
                case 9: //setShiftManagerToWeeklySchedule
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> lst = addOrRemoveInWeeklySchedule(loginInfo.getWorkerID());
                    if (lst == null) break;
                    Response<Boolean> res2 = serviceLayer.setShiftManagerToWeeklySchedule(loginInfo.getWorkerID(), (int) lst.get(0),
                            (int) lst.get(1), (int) lst.get(2),(String)lst.get(4),(int) lst.get(3));
                    if (res2.ErrorOccured())
                        System.out.println(res2.ErrorMessage);
                    else System.out.println("The workers has been added to this shift as a 'Shift Manager' successfully");
                    break;
                case 10: //removeWorkerFromWeeklySchedule
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> ls = addOrRemoveInWeeklySchedule(loginInfo.getWorkerID());
                    if (ls == null) break;
                    Response<Boolean> res3 = serviceLayer.removeWorkerFromWeeklySchedule(loginInfo.getWorkerID(), (int) ls.get(0),
                            (int) ls.get(1), (int) ls.get(2),(String)ls.get(4),(int) ls.get(3));
                    if (res3.ErrorOccured())
                        System.out.println(res3.ErrorMessage);
                    else System.out.println("The worker has been remove from the desired shift in the " +
                        "specified weekly schedule successfully");
                    break;
                case 11: //addWorkerToWeeklySchedule
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> list2 = addOrRemoveInWeeklySchedule(loginInfo.getWorkerID());
                    if (list2 == null) break;
                    Response<Boolean> res4= serviceLayer.addWorkerToWeeklySchedule(loginInfo.getWorkerID(), (int) list2.get(0),
                            (int) list2.get(1), (int) list2.get(2),(String)list2.get(4),(int) list2.get(3));
                    if (res4.ErrorOccured())
                        System.out.println(res4.ErrorMessage);
                    else System.out.println("The worker has been added to the desired shift in the " +
                            "specified weekly schedule successfully");
                    break;
                case 12: //isShiftReady
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> list3 = showShiftWorkers();
                    Response<Boolean> res5= serviceLayer.isShiftReady(loginInfo.getWorkerID(), (int)list3.get(0), (int)list3.get(1),
                            (int)list3.get(2),(String) list3.get(3));
                    if(res5.ErrorOccured())
                        System.out.println(res5.ErrorMessage);
                    else System.out.println("The shift is ready");
                    break;
                case 13: //isWeeklyScheduleReady
                    if(!isHR(loginInfo.isHr()))
                        break;
                    System.out.println("Please enter the id of the week for the weekly schedule: ");
                    int weekly_id_number = sc.nextInt();
                    System.out.println("Please enter the name of the site of the weekly schedule: ");
                    String site2 = new Scanner(System.in).nextLine();
                    Response<Boolean> res6= serviceLayer.isWeeklyScheduleReady(loginInfo.getWorkerID(), weekly_id_number,site2);
                    if(res6.ErrorOccured())
                        System.out.println(res6.ErrorMessage);
                    else System.out.println("The Weekly Schedule is ready");
                    break;
                case 14: //showAllWorkers
                    if(!isHR(loginInfo.isHr()))
                        break;
                    Response<List<WorkerSL>> allWorkers = serviceLayer.showAllWorkers(loginInfo.getID());
                    if(allWorkers.ErrorOccured()) System.out.println(allWorkers.ErrorMessage);
                    else System.out.println(allWorkers.value.toString());
                    break;
                case 15: //registerAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> wD = registerAWorker();
                    Response<Boolean> res7= serviceLayer.registerAWorker(loginInfo.getWorkerID(), wD.get(0) + "", (int) wD.get(1),
                            wD.get(2) + "", wD.get(3) + "", (int) wD.get(4), (int) wD.get(5), (int) wD.get(6),(String)wD.get(7));
                    if(res7.ErrorOccured())
                        System.out.println(res7.ErrorMessage);
                    else System.out.println("The worker has been registered successfully");
                    break;
                case 16: //removeAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    sc= new Scanner(System.in);
                    System.out.println("Please enter the worker's ID: ");
                    int wID = sc.nextInt();
                    Response<Boolean> res8= serviceLayer.removeAWorker(loginInfo.getID(), wID);
                    if(res8.ErrorOccured())
                        System.out.println(res8.ErrorMessage);
                    else System.out.println("The worker has been removed successfully");
                    break;
                case 17: //editAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    System.out.println("Please enter information that you'd like to update, " +
                            "if you don't want to update something, just hit enter (without typing anything) or " +
                            "'0' if you're required to put in a number: ");
                    List<Object> ewD = editAWorker();
                    Response<Boolean> res9= serviceLayer.editAWorker(3,ewD.get(0) + "", ewD.get(1) + "",
                            ewD.get(2) + "", (int) ewD.get(3), (int) ewD.get(4), (int) ewD.get(5), (int) ewD.get(6));
                    if(res9.ErrorOccured())
                        System.out.println(res9.ErrorMessage);
                    else System.out.println("The worker has been updated successfully");
                    break;
                case 18: //addJobForAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> workerJob = addOrRemoveAJob_Worker();
                    Response<Boolean> res10= serviceLayer.addJobForAWorker(loginInfo.getID(), (int) workerJob.get(0), workerJob.get(1) + "");
                    if(res10.ErrorOccured())
                        System.out.println(res10.ErrorMessage);
                    else System.out.println("The job has been added successfully");
                    break;
                case 19: //removeJobFromAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> workerJobR = addOrRemoveAJob_Worker();
                    Response<Boolean> res11= serviceLayer.removeJobFromAWorker(loginInfo.getID(), (int) workerJobR.get(0), workerJobR.get(1) + "");
                    if(res11.ErrorOccured())
                        System.out.println(res11.ErrorMessage);
                    else System.out.println("The job has been removed successfully");
                    break;
                case 20://assign job for a worker in week
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> listassign = assignOrRemoveInWeeklySchedule(loginInfo.getWorkerID());
                    if (listassign == null) break;
                    Response<Boolean> res12= serviceLayer.assignWorkerToJobInShift(loginInfo.getWorkerID(), (int) listassign.get(0),
                            (int) listassign.get(1), (int) listassign.get(2),(String) listassign.get(4) ,(int) listassign.get(3),(String)listassign.get(5));
                    if (res12.ErrorOccured())
                        System.out.println(res12.ErrorMessage);
                    else System.out.println("The worker has been added to the desired job in the " +
                            "specified weekly schedule successfully");
                    break;
                case 21://remove job for a worker in week
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> listremove = assignOrRemoveInWeeklySchedule(loginInfo.getWorkerID());
                    if (listremove == null) break;
                    Response<Boolean> res13= serviceLayer.removeWorkerFromJobInShift(loginInfo.getWorkerID(), (int) listremove.get(0),
                            (int) listremove.get(1), (int) listremove.get(2),(String)listremove.get(4), (int) listremove.get(3),(String) listremove.get(5));
                    if (res13.ErrorOccured())
                        System.out.println(res13.ErrorMessage);
                    else System.out.println("The worker has been removed from the desired job in the " +
                            "specified weekly schedule successfully");
                    break;
                case 22://add license
                    if (!isHR(loginInfo.isHr()))
                        break;
                    List<Object> listLicense = addremoveLicense();
                    Response<Boolean> res14 = serviceLayer.addLicenseToWorker(loginInfo.getWorkerID(),(int)listLicense.get(0),(String) listLicense.get(1));
                    if (res14.ErrorOccured())
                        System.out.println(res14.ErrorMessage);
                    else System.out.println("the license has been added successfully");
                    break;
                case 23://remove license
                    if (!isHR(loginInfo.isHr()))
                        break;
                    List<Object> listLicense2 = addremoveLicense();
                    Response<Boolean> res15 = serviceLayer.removeLicenseFromWorker(loginInfo.getWorkerID(),(int)listLicense2.get(0),(String) listLicense2.get(1));
                    if (res15.ErrorOccured())
                        System.out.println(res15.ErrorMessage);
                    else System.out.println("the license has been removed successfully");
                    break;
                case 24://remove license
                    if (!isHR(loginInfo.isHr()))
                        break;
                    List<WorkerDTO> res16 = ShiftsController.getInstance().getAllDrivers(1,0,0);
                    for (int i =0; i<res16.size(); i++){
                        System.out.println(res16.get(i).getName());
                    }
                    break;
                case 99:
                    System.out.println("You've been successfully logged-out.");
                    break;
                default:
                    System.out.println("You've entered an illegal option, please try again.");
                    break;
            }
        }
    }


    private static List<Object> addremoveLicense(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the worker id: ");
        int workerID = sc.nextInt();
        System.out.println("Please enter the day of the License name : ");
        String licenseName = new Scanner(System.in).nextLine();
        return Arrays.asList(workerID,licenseName);
    }
    private static Login Login(){
        Scanner scLogin1= new Scanner(System.in);
        System.out.println("Please enter your ID, and hit enter afterwards: ");
        int id= scLogin1.nextInt();
        Scanner scLogin2= new Scanner(System.in);
        System.out.println("Please enter the password, and hit enter afterwards: ");
        String password= scLogin2.nextLine();
        Login login = new Login(id, password);
        return login;
    }

    private static boolean isHR(boolean isHr){
        if (!isHr) {
            System.out.println("You're not authorized to perform this action.");
            return false;
        }
        return true;
    }

    private static List<Object> editWorkerSchedule(boolean isHr){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the day of the week in which the shift is on: " +
                "1 to 5");
        int shiftDay = sc.nextInt();
        if(!(shiftDay >= 1 && shiftDay <= 5))  {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the shift Type of the week in which the shift is on: " +
                "0 - Morning shift, 1 - Evening Shift");
        int shiftType = sc.nextInt();
        if(isHr && shiftType == 1){
            System.out.println("An HR worker can only work morning shifts.");
            return null;
        }
        else if (shiftType !=0 && shiftType != 1) {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter '1' if you want to be present on this shift, or '0' otherwise: ");
        int presentNum = sc.nextInt();
        boolean present = false;
        if(presentNum == 1) present = true;
        else if(presentNum !=0 ){
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        return Arrays.asList(present, shiftDay - 1, shiftType);
    }
    private static List<Object> addOrRemoveTransaction(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the id the week: ");
        int weekID = sc.nextInt();
        if(weekID <1){
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the day of the week in which the shift is on: " +
                "1 to 5");
        int shiftDay = sc.nextInt();
        if(!(shiftDay >= 1 && shiftDay <= 5))  {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the shift Type of the week in which the shift is on: " +
                "0 - Morning shift, 1 - Evening Shift");
        int shiftType = sc.nextInt();
        if(shiftType !=0 && shiftType != 1) {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the transaction ID: ");
        int transactionID = sc.nextInt();
        if(transactionID <0 ){
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the name of the site : ");
        String siteName = new Scanner(System.in).nextLine();
        return Arrays.asList(weekID, shiftDay - 1, shiftType, transactionID,siteName);
    }

    private static List<Object> showShiftWorkers(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the week of the desired shift: ");
        int shiftWeek = sc.nextInt();
        if(shiftWeek < 1)  {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the day of the week in which the shift is on: " +
                "1 to 5");
        int shiftDay = sc.nextInt();
        if(!(shiftDay >= 1 && shiftDay <= 5))  {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the shift Type of the week in which the shift is on: " +
                "0 - Morning shift, 1 - Evening Shift");
        int shiftType = sc.nextInt();
        if(shiftType !=0 && shiftType != 1) {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the name of the site : ");
        String site = new Scanner(System.in).nextLine();
        return Arrays.asList(shiftWeek, shiftDay - 1, shiftType,site);
    }

    private static List<Object> addOrRemoveInWeeklySchedule(int hrID){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the week of the desired shift: ");
        int shiftWeek = sc.nextInt();
        if(shiftWeek < 1)  {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the day of the week in which the shift is on: " +
                "1 to 5");
        int shiftDay = sc.nextInt();
        if(!(shiftDay >= 1 && shiftDay <= 5))  {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        System.out.println("Please enter the shift Type of the week in which the shift is on: " +
                "0 - Morning shift, 1 - Evening Shift");
        int shiftType = sc.nextInt();
        if(shiftType !=0 && shiftType != 1) {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }

        System.out.println("Please enter the worker's ID: ");
        int wID = sc.nextInt();
        if(hrID == wID && shiftType == 1){
            System.out.println("An HR worker can only work morning shifts.");
            return null;
        }
        System.out.println("Please enter the name of the site: ");
        String site = new Scanner(System.in).nextLine();
        return Arrays.asList(shiftWeek, shiftDay - 1, shiftType, wID,site);
    }
    private static List<Object> assignOrRemoveInWeeklySchedule(int hrID) {
        List<Object> objects = new LinkedList<>();
        objects.addAll(addOrRemoveInWeeklySchedule(hrID));
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the job name: ");
        String job = sc.nextLine();
        objects.add(job);
        return objects;
    }

    private static List<Object> registerAWorker() {
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        System.out.println("Please enter the name of the worker: ");
        String name = sc1.nextLine();
        System.out.println("Please enter the worker's ID: ");
        int wID = sc2.nextInt();
        System.out.println("Please enter the password of the worker: ");
        String password= sc1.nextLine();
        System.out.println("Please enter the email address of the worker: ");
        String email_address = sc1.nextLine();
        System.out.println("Please enter the worker's Bank ID: ");
        int bankID = sc2.nextInt();
        System.out.println("Please enter the worker's branch number: ");
        int branch = sc2.nextInt();
        System.out.println("Please enter the worker's salary: ");
        int salary = sc2.nextInt();
        System.out.println("Please enter the name of the site this worker works at: ");
        String site = new Scanner(System.in).nextLine();
        return Arrays.asList(name, wID, password, email_address, bankID, branch, salary,site);
    }

    private static List<Object> editAWorker() {
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        System.out.println("Please enter the name of the worker or hit enter: ");
        String name = sc1.nextLine();
        System.out.println("Please enter the password of the worker or hit enter: ");
        String password= sc1.nextLine();
        System.out.println("Please enter the email address of the worker or hit enter: ");
        String email_address = sc1.nextLine();
        System.out.println("Please enter the worker's Bank ID or hit '0': ");
        int bankID = sc2.nextInt();
        System.out.println("Please enter the worker's branch number or hit '0': ");
        int branch = sc2.nextInt();
        System.out.println("Please enter the worker's salary or hit '0': ");
        int salary = sc2.nextInt();
        System.out.println("Please enter the worker's ID: ");
        int wID = sc2.nextInt();
        return Arrays.asList(name, password, email_address, bankID, branch, salary, wID);
    }

    private static List<Object> addOrRemoveAJob_Worker() {
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        System.out.println("Please enter the worker's ID: ");
        int wID = sc2.nextInt();
        System.out.println("Please enter the name of the job (from the provided list of jobs): ");
        String job = sc1.nextLine();
        return Arrays.asList(wID, job);
    }
}
