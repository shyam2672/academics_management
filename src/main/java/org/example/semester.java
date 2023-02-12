package org.example;
import java.util.Scanner;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class semester {
    public static String semester;
    public static String academic_year;

    public static boolean sem_running=false;
    public static boolean sem_end=false;
    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
    static Scanner input = new Scanner(System.in);
    public static void startsem(){
        if(sem_running) {
            System.out.println("A sem is already running please end it before starting a new one");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }
            String s1="drop table course_catalog";
            String s2="drop table course_offering";
            String s3="CREATE TABLE course_catalog(\n" +
                    "course_id VARCHAR(10),\n" +
                    "PRIMARY KEY(course_id),\n" +
                    "FOREIGN KEY (course_id) references course (id)\n" +
                    ");";
            String s4="CREATE TABLE course_offering(\n" +
                    "course_id VARCHAR(10),\n" +
                    "cgpa_limit INTEGER,\n" +
                    "instructor_id VARCHAR(10),\n" +
                    "PRIMARY KEY(course_id),\n" +
                    "FOREIGN KEY (course_id) references course_catalog (course_id),\n" +
                    "FOREIGN KEY (instructor_id) references instructor (id)\n" +
                    "\n" +
                    ");";


            try {
                stmt=conn.createStatement();
                try {
                    stmt.executeUpdate(s2);
                    stmt.executeUpdate(s1);
                    stmt.executeUpdate(s3);
                    stmt.executeUpdate(s4);
                    System.out.println("please add course to the current sem's course catalog");
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        System.out.println("enter the academic year");
            academic_year=input.nextLine();
            System.out.println("enter the semester monsoon/winter");
        semester=input.nextLine();
        sem_running=true;
        sem_end=false;
        System.out.println( academic_year+"-"+semester+" started successfully");

        System.out.println("press any key to continue");
        input.nextLine();
    }

    public static void endsem(){
        if(sem_running==false){
            System.out.println("no semester is running!");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }
        sem_end=true;
        sem_running=false;
        System.out.println( academic_year+"-"+semester+" ended successfully");
        System.out.println("press any key to continue");
        input.nextLine();
    }

    public static void updatecoursecatalog(){
        if(sem_running==false ){
            System.out.println("Semester is not yet started");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }
        while (true){
            String course_id;
            System.out.println("enter course code or enter 0 to exit");
            course_id=input.nextLine();
            if(course_id.equals("0")){
                return;
            }
            try {
                stmt=conn.createStatement();
                String query = "INSERT INTO course_catalog(course_id) VALUES('" + course_id + "');" ;
                System.out.println(query);
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }




}
