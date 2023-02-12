package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the academics management portal IIT ROPAR!");
        Scanner input = new Scanner(System.in);

        System.out.println("Enter your role");
        System.out.println("1. student");
        System.out.println("2. instructor");
        System.out.println("3. admin");

        String c="";
       c=input.nextLine();
     switch (c){
         case "1":
             break;
         case "2":
             break;
         case "3":
             admin x=new admin();
             x.login();
             int flag=1;
             while(flag==1){
                 System.out.println("Press \n0. to logout \n1. to add a new batch\n2. to add a new course\n3. to add or update a curriculum\n4. to start sem\n5. to end sem\n6. to add course to course_catalog\n7. to view grades of all students\n8. to add users ");
                 String r="";

                 r=input.nextLine();
                 switch (r){
                     case "0":
                     flag=0;
                     break;
                     case "1": x.addbatch();
                         break;
                     case "2": x.addcourse();
                     break;
                     case "3": x.addcurriculum();
                     break;
                     case "4": x.startsem();
                     break;
                     case "5": x.endsem();
                     break;
                     case "6": x.updatecoursecatalog();
                     break;
                     case "7":x.showGrades();
                     break;
                     case "8":x.adduser();
                     break;
                     default:System.out.println("please follow the instructions");
                     break;
                 }
             }

             break;
         default:
             System.out.println("invalid role");
             break;
     }
    }
}