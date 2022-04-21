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
        //                    11 - addWorkerToWeeklySchedule (There's no register for users!
        //                         Only the HR can add new workers to the company).
        //                    99 - exit the program: hence log-out

        int choise = 0;
        Scanner sc;
        while(choise != 99) {
            System.out.println("Please enter your desired function (according to the ones available in the instructions list "
                    + "that has been provided to you): ");
            sc = new Scanner(System.in);
            choise = sc.nextInt();
            switch (choise) {
                //'regular worker' methods:
                case 1: //editWorkerSchedule:
                    if(loginInfo.isHr()){
                        System.out.println("You don't have a working Schedule as an HR");
                    }
                    else {
                        List<Object> list = editWorkerSchedule();
                        if (list == null) break;
                        boolean ans = serviceLayer.editWorkerSchedule(loginInfo.getWorkerID(),
                                (boolean) list.get(0), (int) list.get(1), (int) list.get(2));
                        if (ans) System.out.println("The shift information has been updated successfully");
                        else System.out.println("There has been an issue, please try again.");
                    }
                    break;
                case 2: //viewWorkerSchedule
                    if(!loginInfo.isHr()) {
                        WorkerScheduleSL workerSchedule = serviceLayer.viewWorkerSchedule(loginInfo.getWorkerID());
                        if (workerSchedule == null) {
                            System.out.println("There's no working schedule for you, please contact the HR.");
                            break;
                        }
                        System.out.println("Your working Schedule: " +
                                Arrays.deepToString(workerSchedule.getSchedule()));
                    }

                    else{
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
                case 3: //addTransaction - a 'shift Manager' or a 'Cashier' can do this method
                    if(loginInfo.isHr()){
                        System.out.println("You don't have access to transactions as an HR.");
                    }
                    else {
                        List<Object> list = addOrRemoveTransaction();
                        if (list == null) break;
                        boolean ans = serviceLayer.addTransaction((int)list.get(0),
                                (int) list.get(1), (int) list.get(2), (int) list.get(3), loginInfo.getWorkerID());
                        if (ans) System.out.println("The transaction has been made successfully");
                        else System.out.println("You're not authorized to make a transaction.");
                    }
                    break;
                case 4: //removeTransaction
                    if(loginInfo.isHr()){
                        System.out.println("You don't have access to transactions as an HR.");
                    }
                    else {
                        List<Object> list = addOrRemoveTransaction();
                        if (list == null) break;
                        boolean ans = serviceLayer.removeTransaction(loginInfo.getWorkerID(),(int)list.get(0),
                                (int) list.get(1), (int) list.get(2), (int) list.get(3));
                        if (ans) System.out.println("The transaction has been removed successfully");
                        else System.out.println("You're not authorized to remove a transaction.");
                    }
                    break;

                //'HR' worker method:
                case 5: //addJobs
                    if(!loginInfo.isHr()) {
                        System.out.println("You're not authorized to perform this action.");
                        break;
                    }
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the Job's name that you'd like to add: ");
                    String job = sc.nextLine();
                    boolean ans = serviceLayer.addJobs(loginInfo.getWorkerID(), job);
                    if(ans) System.out.println("The Job has been added successfully");
                    else System.out.println("The Job already exists in the System, therefore it hasn't been added.");
                    break;

                case 6: //createWeeklySchedule
                    if(!loginInfo.isHr()) {
                        System.out.println("You're not authorized to perform this action.");
                        break;
                    }
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the id of the week for the weekly schedule: ");
                    int weekID = sc.nextInt();
                    if(serviceLayer.createWeeklySchedule(loginInfo.getWorkerID(), weekID))
                       System.out.println("The schedule has been created successfully");
                    else
                        System.out.println("There's already a Weekly Schedule with the provided Week ID" +
                                " in the System, therefore it hasn't been created, please try again.");
                    break;
                case 7: //showShiftWorkers
                    if(!loginInfo.isHr()) {
                        System.out.println("You're not authorized to perform this action.");
                        break;
                    }
                    List<Object> list = showShiftWorkers();
                    if (list == null) break;
                    List<WorkerSL> workersInShift = serviceLayer.showShiftWorkers(loginInfo.getWorkerID(),(int)list.get(0),
                            (int) list.get(1), (int) list.get(2));
                    if(workersInShift == null)
                        System.out.println("You've entered a wrong week ID (meaning this Weekly Schedule doesn't exist)");
                    else if(workersInShift.isEmpty()) System.out.println("There are no workers in the specified shift");
                    else System.out.println("The workers in the desired shift are: " + workersInShift.toString());
                    break;
                case 8: //viewWeeklySchedule TODO:: For now, if the Weekly Schedule is 'empty', it will show it to the HR as null
                                                    //(We want to change that later on)
                    if(!loginInfo.isHr()) {
                        System.out.println("You're not authorized to perform this action.");
                        break;
                    }
                    sc = new Scanner(System.in);
                    System.out.println("Please enter the id of the week for the weekly schedule: ");
                    int weeklyID = sc.nextInt();
                    WeeklyScheduleSL weeklySchedule = serviceLayer.viewWeeklySchedule(loginInfo.getWorkerID(), weeklyID);
                    if(weeklySchedule == null)
                        System.out.println("You've entered a wrong week ID (meaning this Weekly Schedule doesn't exist)");
                    else System.out.println("The workers in the desired shift are: " +
                            Arrays.deepToString(weeklySchedule.getShiftSLS()));
                    break;
                case 9: //setShiftManagerToWeeklySchedule
                    if(!loginInfo.isHr()) {
                        System.out.println("You're not authorized to perform this action.");
                        break;
                    }
                    List<Object> lst = addOrRemoveInWeeklySchedule();
                    if (lst == null) break;

                    if(serviceLayer.setShiftManagerToWeeklySchedule(loginInfo.getWorkerID(), (int)lst.get(0),
                            (int) lst.get(1), (int) lst.get(2), (int) lst.get(3)))
                        System.out.println("The workers has been added to this shift as a 'Shift Manager' successfully");
                    else System.out.println("There has been an issue, please try again and check that you've" +
                            " entered everything correctly.");
                    break;
                case 10: //removeWorkerFromWeeklySchedule
                    if(!loginInfo.isHr()) {
                        System.out.println("You're not authorized to perform this action.");
                        break;
                    }
                    List<Object> ls = addOrRemoveInWeeklySchedule();
                    if (ls == null) break;

                    if(serviceLayer.removeWorkerFromWeeklySchedule(loginInfo.getWorkerID(), (int)ls.get(0),
                            (int) ls.get(1), (int) ls.get(2), (int) ls.get(3)))
                        System.out.println("The worker has been remove from the desires shift in the " +
                                "specified weekly schedule successfully");
                    else System.out.println("There has been an issue, please try again and check that you've" +
                            " entered everything correctly.");
                    break;
                case 11: //addWorkerToWeeklySchedule
                    if(!loginInfo.isHr()) {
                        System.out.println("You're not authorized to perform this action.");
                        break;
                    }
                    List<Object> list1 = addOrRemoveInWeeklySchedule();
                    if (list1 == null) break;

                    if(serviceLayer.addWorkerToWeeklySchedule(loginInfo.getWorkerID(), (int)list1.get(0),
                            (int) list1.get(1), (int) list1.get(2), (int) list1.get(3)))
                        System.out.println("The worker has been added to the desires shit in the " +
                                "specified weekly schedule successfully");
                    else System.out.println("There has been an issue, please try again and check that you've" +
                            " entered everything correctly.");
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

    private static List<Object> editWorkerSchedule(){
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
        if(shiftType !=0 && shiftType != 1) {
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
        return Arrays.asList(present, shiftDay, shiftType);
    }
    private static List<Object> addOrRemoveTransaction(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the id the week: ");
        int weekID = sc.nextInt();
        if(weekID <0){
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
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
        return Arrays.asList(weekID, shiftDay, shiftType, transactionID);
    }

    private static List<Object> showShiftWorkers(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the week of the desired shift: ");
        int shiftWeek = sc.nextInt();
        if(shiftWeek < 0)  {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
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
        if(shiftType !=0 && shiftType != 1) {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
        return Arrays.asList(shiftWeek, shiftDay, shiftType);
    }

    private static List<Object> addOrRemoveInWeeklySchedule(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the week of the desired shift: ");
        int shiftWeek = sc.nextInt();
        if(shiftWeek < 0)  {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }
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
        if(shiftType !=0 && shiftType != 1) {
            System.out.println("You've entered an illegal option, please start the last process again.");
            return null;
        }

        System.out.println("Please enter the worker's ID: ");
        int wID = sc.nextInt();

        return Arrays.asList(shiftWeek, shiftDay, shiftType, wID);
    }
}
