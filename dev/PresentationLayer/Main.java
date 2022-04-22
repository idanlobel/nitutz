package PresentationLayer;

import Service_Layer.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
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
                    boolean ans = serviceLayer.editWorkerSchedule(loginInfo.getWorkerID(),
                            (boolean) list.get(0), (int) list.get(1), (int) list.get(2));
                    if (ans) System.out.println("The shift information has been updated successfully");
                    else System.out.println("There has been an issue, please try again.");

                    break;
                case 2: //viewWorkerSchedule
                    if (!loginInfo.isHr()) {
                        WorkerScheduleSL workerSchedule = serviceLayer.viewWorkerSchedule(loginInfo.getWorkerID());
                        if (workerSchedule == null) {
                            System.out.println("There's no working schedule for you, please contact the HR.");
                            break;
                        }
                        System.out.println("Your working Schedule: " +
                                Arrays.deepToString(workerSchedule.getSchedule()));
                    } else {
                        System.out.println("Please enter the Worker's ID of the specified worker: ");
                        sc = new Scanner(System.in);
                        int workerID = sc.nextInt();
                        WorkerScheduleSL workerSchedule = serviceLayer.viewWorkerSchedule(workerID);
                        if (workerSchedule == null) {
                            System.out.println("There's no working schedule for the specified worker.");
                            break;
                        }
                        System.out.println("The specified worker's working Schedule: " +
                                Arrays.deepToString(workerSchedule.getSchedule()));
                    }
                    break;

                //'Shift Manager' methods:
                case 3: //addTransaction - a 'shift Manager' or a 'Cashier' or an 'HR' can do this method
                    List<Object> l1 = addOrRemoveTransaction();
                    if (l1 == null) break;
                    boolean ans1 = serviceLayer.addTransaction((int) l1.get(0),
                            (int) l1.get(1), (int) l1.get(2), (int) l1.get(3), loginInfo.getWorkerID());
                    if (ans1) System.out.println("The transaction has been made successfully");
                    else System.out.println("There has been an issue: The transaction already exists, or you're not authorized to perform" +
                            " this action.");

                    break;
                case 4: //removeTransaction - a 'shift manager' or an 'HR' can perform this method
                    List<Object> l2 = addOrRemoveTransaction();
                    if (l2 == null) break;
                    boolean ans2 = serviceLayer.removeTransaction(loginInfo.getWorkerID(), (int) l2.get(0),
                            (int) l2.get(1), (int) l2.get(2), (int) l2.get(3));
                    if (ans2) System.out.println("The transaction has been removed successfully");
                    else System.out.println("You're either not authorized to remove a transaction" +
                            " Or there's no such transaction.");

                    break;

                //'HR' worker methods:
                case 5: //addJobs
                    if(!isHR(loginInfo.isHr()))
                        break;
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the Job's name that you'd like to add: ");
                    String job = sc.nextLine();
                    boolean ans3 = serviceLayer.addJobs(loginInfo.getWorkerID(), job);
                    if (ans3) System.out.println("The Job has been added successfully");
                    else System.out.println("The Job already exists in the System, therefore it hasn't been added.");
                    break;

                case 6: //createWeeklySchedule
                    if(!isHR(loginInfo.isHr()))
                        break;
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the id of the week for the weekly schedule: ");
                    int weekID = sc.nextInt();
                    if (serviceLayer.createWeeklySchedule(loginInfo.getWorkerID(), weekID))
                        System.out.println("The schedule has been created successfully");
                    else
                        System.out.println("There's already a Weekly Schedule with the provided Week ID" +
                                " in the System, therefore it hasn't been created, please try again.");
                    break;
                case 7: //showShiftWorkers
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> list1 = showShiftWorkers();
                    if (list1 == null) break;
                    List<WorkerSL> workersInShift = serviceLayer.showShiftWorkers(loginInfo.getWorkerID(), (int) list1.get(0),
                            (int) list1.get(1), (int) list1.get(2));
                    if (workersInShift == null)
                        System.out.println("You've entered a wrong week ID (meaning this Weekly Schedule doesn't exist)");
                    else if (workersInShift.isEmpty())
                        System.out.println("There are no workers in the specified shift");
                    else System.out.println("The workers in the desired shift are: " + workersInShift.toString() + "\n" +
                                "And the shift manager is: " + serviceLayer.getShiftManagerInfo(loginInfo.getWorkerID(), (int) list1.get(0),
                                (int) list1.get(1), (int) list1.get(2)));
                    break;
                case 8: //viewWeeklySchedule TODO:: For now, if the Weekly Schedule is 'empty', it will show it to the HR as null
                    //TODO: FIX THIS - A LOT OF NULL POINTER EXCEPTIONS
                    //(We want to change that later on)
                    if(!isHR(loginInfo.isHr()))
                        break;
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the id of the week for the weekly schedule: ");
                    int weeklyID = sc.nextInt();
                    WeeklyScheduleSL weeklySchedule = serviceLayer.viewWeeklySchedule(loginInfo.getWorkerID(), weeklyID);
                    if (weeklySchedule == null)
                        System.out.println("You've entered a wrong week ID (meaning this Weekly Schedule doesn't exist)");
                    else System.out.println("The workers in the desired weekly schedule are: " +
                            Arrays.deepToString(weeklySchedule.getShiftSLS()));
                    break;
                case 9: //setShiftManagerToWeeklySchedule
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> lst = addOrRemoveInWeeklySchedule(loginInfo.getWorkerID());
                    if (lst == null) break;

                    if (serviceLayer.setShiftManagerToWeeklySchedule(loginInfo.getWorkerID(), (int) lst.get(0),
                            (int) lst.get(1), (int) lst.get(2), (int) lst.get(3)))
                        System.out.println("The workers has been added to this shift as a 'Shift Manager' successfully");
                    else System.out.println("There has been an issue, please try again and check that you've" +
                            " entered everything correctly.");
                    break;
                case 10: //removeWorkerFromWeeklySchedule
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> ls = addOrRemoveInWeeklySchedule(loginInfo.getWorkerID());
                    if (ls == null) break;

                    if (serviceLayer.removeWorkerFromWeeklySchedule(loginInfo.getWorkerID(), (int) ls.get(0),
                            (int) ls.get(1), (int) ls.get(2), (int) ls.get(3)))
                        System.out.println("The worker has been remove from the desired shift in the " +
                                "specified weekly schedule successfully");
                    else System.out.println("There has been an issue, please try again and check that you've" +
                            " entered everything correctly.");
                    break;
                case 11: //addWorkerToWeeklySchedule
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> list2 = addOrRemoveInWeeklySchedule(loginInfo.getWorkerID());
                    if (list2 == null) break;

                    if (serviceLayer.addWorkerToWeeklySchedule(loginInfo.getWorkerID(), (int) list2.get(0),
                            (int) list2.get(1), (int) list2.get(2), (int) list2.get(3)))
                        System.out.println("The worker has been added to the desired shift in the " +
                                "specified weekly schedule successfully");
                    else System.out.println("There has been an issue, please try again and check that you've" +
                            " entered everything correctly.");
                    break;
                case 12: //isShiftReady
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> list3 = showShiftWorkers();
                    if(serviceLayer.isShiftReady(loginInfo.getWorkerID(), (int)list3.get(0), (int)list3.get(1),
                            (int)list3.get(2)))
                        System.out.println("The shift is ready");

                    else System.out.println("The shift isn't ready or doesn't exist, please make sure you've entered the " +
                            "information correctly.");
                    break;
                case 13: //isWeeklyScheduleReady
                    if(!isHR(loginInfo.isHr()))
                        break;
                    System.out.println("Please enter the id of the week for the weekly schedule: ");
                    int weekly_id_number = sc.nextInt();

                    if(serviceLayer.isWeeklyScheduleReady(loginInfo.getWorkerID(), weekly_id_number))
                        System.out.println("The Weekly Schedule is ready");

                    else System.out.println("The Weekly Schedule isn't ready or doesn't exist, please make sure you've entered the " +
                            "information correctly.");
                    break;
                case 14: //showAllWorkers
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<WorkerSL> allWorkers = serviceLayer.showAllWorkers(loginInfo.getID());
                    if(allWorkers == null) System.out.println("There are no currently workers in the System.");
                    else System.out.println(allWorkers.toString());
                    break;
                case 15: //registerAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> wD = registerAWorker();
                    if(serviceLayer.registerAWorker(loginInfo.getWorkerID(), wD.get(0) + "", (int) wD.get(1),
                            wD.get(2) + "", wD.get(3) + "", (int) wD.get(4), (int) wD.get(5), (int) wD.get(6)))
                        System.out.println("The worker has been registered successfully");
                    else System.out.println("A worker with the same ID already exists in the System. If you wish to update his details " +
                            "please use the 'edit' method.");
                    break;
                case 16: //removeAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    sc= new Scanner(System.in);
                    System.out.println("Please enter the worker's ID: ");
                    int wID = sc.nextInt();
                    if(serviceLayer.removeAWorker(loginInfo.getID(), wID))
                        System.out.println("The worker has been removed successfully");
                    else System.out.println("The worker doesn't exist in the system, please make sure that you've " +
                            "entered everything correctly and try again.");
                    break;
                case 17: //editAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    System.out.println("Please enter information that you'd like to update, " +
                            "if you don't want to update something, just hit enter (without typing anything) or " +
                            "'0' if you're required to put in a number: ");
                    List<Object> ewD = editAWorker();
                    if(serviceLayer.editAWorker(ewD.get(0) + "", ewD.get(1) + "",
                            ewD.get(2) + "", (int) ewD.get(3), (int) ewD.get(4), (int) ewD.get(5), (int) ewD.get(6)))
                        System.out.println("The worker has been updated successfully");
                    else System.out.println("The worker doesn't exist in the system, please make sure that you've " +
                            "entered everything correctly and try again.");
                    break;
                case 18: //addJobForAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> workerJob = addOrRemoveAJob_Worker();
                    if(serviceLayer.addJobForAWorker(loginInfo.getID(), (int) workerJob.get(0), workerJob.get(1) + ""))
                        System.out.println("The job has been added successfully");
                    else System.out.println("The worker doesn't exist in the system or the worker already has this job" +
                            " or the job isn't from the provided list of jobs," +
                            " please make sure that you've entered everything correctly and try again.");
                    break;
                case 19: //removeJobFromAWorker
                    if(!isHR(loginInfo.isHr()))
                        break;
                    List<Object> workerJobR = addOrRemoveAJob_Worker();
                    if(serviceLayer.removeJobFromAWorker(loginInfo.getID(), (int) workerJobR.get(0), workerJobR.get(1) + ""))
                        System.out.println("The job has been removed successfully");
                    else System.out.println("The worker doesn't exist in the system or the worker doesn't have this job or the job isn't " +
                            "on the provided list of jobs" +
                            ", please make sure that you've entered everything correctly and try again.");
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
        if(!(shiftDay >= 0 && shiftDay <= 5))  {
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
        return Arrays.asList(weekID, shiftDay - 1, shiftType, transactionID);
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
        return Arrays.asList(shiftWeek, shiftDay - 1, shiftType);
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

        return Arrays.asList(shiftWeek, shiftDay - 1, shiftType, wID);
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

        return Arrays.asList(name, wID, password, email_address, bankID, branch, salary);
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
