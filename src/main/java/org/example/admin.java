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
public class admin {
    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
   static Scanner input = new Scanner(System.in);


    public static void login(){
        String username="",password="";
        while(true){
            System.out.println("enter username");
            username=input.nextLine();
            System.out.println("enter password");
            password=input.nextLine();
            System.out.println(username + " "+ password);
            if(username.equals("admin") && password.equals("iitropar")){
                System.out.println("logged in successfully");
                break;
            }
            else{
                System.out.println("wrong credentials");
            }
        }
    }

    public static void addbatch(){
        while(true){
            String batch_id="",year="",dep_id="";

            System.out.println("enter batch id");
            batch_id=input.nextLine();
            System.out.println("enter year");
            year=input.nextLine();
            System.out.println("enter department id");
            dep_id=input.nextLine();

            try {
                stmt=conn.createStatement();
                String query = "INSERT INTO batch(id,year,dep_id) VALUES('" + batch_id + "'," + "'"+year+"'," + "'"+dep_id+ "');";
                System.out.println(query);
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                   System.out.println(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("press 0 for exit and 1 to continue");
            if(input.nextLine().equals("0")){
                break;
            }
        }
    }

    public static void addcourse(){
        while(true){
            String course_id="",course_name="",dep_id="",l,t,p,c;
            System.out.println("Enter course id");
            course_id=input.nextLine();
            System.out.println("Enter course name");
            course_name=input.nextLine();
            System.out.println("Enter dep_id");
            dep_id=input.nextLine();
            System.out.println("Enter number of lectures per week");
            l=input.nextLine();
            System.out.println("Enter number of tutorials per week");
            t=input.nextLine();
            System.out.println("Enter course practicals per week");
            p=input.nextLine();
            System.out.println("Enter course credits");
            c=input.nextLine();
            try {
                stmt=conn.createStatement();
                String query = "INSERT INTO course(id,name,dep_id,l,t,p,c) VALUES('" + course_id + "'," + "'"+course_name+"'," + "'"+dep_id+ "',"+l+","+t+","+p+","+c+");";
                System.out.println(query);
                try {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("press 0 for exit and 1 to continue");
            if(input.nextLine().equals("0")){
                break;
            }
        }
    }

    public static void addcurriculum(){
        while(true){
            String course_id="",course_type,batch_id;
            System.out.println("enter the course id or enter 0 to quit");
            course_id=input.nextLine();
            if(course_id.equals("0")){
                return;
            }
            System.out.println("enter the batch_id ");
            batch_id=input.nextLine();
            System.out.println("enter the course type");
            course_type=input.nextLine();
            try {
                stmt=conn.createStatement();
                String query = "INSERT INTO ug_curriculum(course_id,batch_id,course_type) VALUES('" + course_id + "'," + "'"+batch_id+"'," + "'"+course_type+"');";
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
