package PresentationLayer;

import Service_Layer.Login;
import Service_Layer.ServiceLayer;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        ServiceLayer serviceLayer;
        Scanner sc= new Scanner(System.in);
        System.out.println("Hello! welcome to our system. Firstly, please enter your login information: ");
        String decision = sc.nextLine();
        if(decision == "Login"){
            Login loginInfo = Login();
            while(!loginInfo.isAUser()){

                System.out.println("The information you've entered is incorrect, please try again: ");
            }

            //TODO:: after logged-in successfully

        }
        //TODO:: There's no register! Only HR can add new workers to the company.
        else{
            System.out.println("Something went wrong, please re-run the program again.");
        }
    }

    private static Login Login(){
        Scanner scLogin= new Scanner(System.in);
        System.out.println("Please enter the username, and hit enter afterwards: ");
        String userName= scLogin.nextLine();
        System.out.println("Please enter the password, and hit enter afterwards: ");
        String password= scLogin.nextLine();
        Login login = new Login(userName, password);
        return login;
    }
}
