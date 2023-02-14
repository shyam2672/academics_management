package org.example;

import javax.swing.*;
import java.util.Scanner;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
public class instructor {
    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
    static Scanner input = new Scanner(System.in);
    public static String user_id="";
    public static boolean user=false;
    public static void login(){
        String email="",password="";
        while(true){
            System.out.println("enter your email");
            email=input.nextLine();
            System.out.println("enter your password");
            password=input.nextLine();

            String query="select * from instructor where email='"+email+"' and password='"+password+"';";
            try {
                stmt=conn.createStatement();
                ResultSet rs;

                rs=stmt.executeQuery(query);
                System.out.println("logged in successfully");
                user=true;
                while(rs.next()){
                    user_id=rs.getString(1);
                }
                return;

            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("wrong credentials");
                System.out.println("press any key to continue");
                input.nextLine();
            }

        }
    }

    public static void viewprofile()
    {
        if(!user){
            System.out.println("please log in!");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }

        String query="select * from instructor where id='"+user_id+"';";

        try {
            stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            ResultSetMetaData rsmd;
            rsmd=rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            String responseQuery="";
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {

                    if (i == 1)
                        responseQuery += "id ---> ";
                    if (i == 2)
                        responseQuery += "      name ---> ";
                    if (i == 3)
                        responseQuery += "      email ---> ";
                    if (i == 4)
                        responseQuery += "      dep_id ---> ";

                    if (i == 6)
                        responseQuery += "      phone_number ---> ";

                    if (i > 1 && i!=5 && i!=7)
                        responseQuery = responseQuery + " ";
                    if(i!=5 && i!=7) {
                        String columnValue = rs.getString(i);
                        responseQuery += columnValue;

                    }
                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }

            }
            System.out.println(responseQuery);
            System.out.println("press any key to continue");
            input.nextLine();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateprofile(){
        if(!user){
            System.out.println("please log in!");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }
         String name="",password="",phone_number;
        System.out.println("enter name to update");
        name=input.nextLine();
        System.out.println("enter phone number to update");
        phone_number=input.nextLine();
        System.out.println("enter password to update");
        password=input.nextLine();

        String query =" update instructor set name='"+name+"',phone_number='"+phone_number+"',password='"+password+"' where id='"+user_id+"';";

        try {
            stmt=conn.createStatement();
            stmt.executeUpdate(query);
            System.out.println("profile updated successfully");
            System.out.println("press any key to continue");
            input.nextLine();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void addCourse(){
        if(!user){
            System.out.println("please log in!");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }

        while(true){
            String course_id;
            System.out.println("enter the course_id or 0 to exit");
            course_id=input.nextLine();
if(course_id.equals("0")){
    return;
}
            try {
                stmt= conn.createStatement();
                String query="select * from course_catalog where course_id='"+course_id+"';";

                try {
                    ResultSet rs;


                    rs=stmt.executeQuery(query);


                }
                catch (SQLException e){
                    System.out.println("no such course in the course catalog");
                }

                String query2="select * from course_offering where course_id='"+course_id+"';";
                try {
                    ResultSet rs;
                    rs=stmt.executeQuery(query2);
                    System.out.println("Course already offered by someone else");
                }
                catch (SQLException e){
                    String cgpa_limit;
                    System.out.println("set the cgpa limit for this course");
                    cgpa_limit=input.nextLine();

                    query="insert into course_offering(course_id,cgpa_limit,instructor_id) values ('"+course_id+"',"+cgpa_limit+",'"+user_id+"');";
                    stmt.executeUpdate(query);
                    System.out.println("Added course successfully");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    }

    public static void offeredCourses(){
        if(!user){
            System.out.println("please log in!");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }

        String query="select * from course_catalog;";

        try {
            stmt= conn.createStatement();
            try {
                ResultSet rs=stmt.executeQuery(query);
                ResultSetMetaData rsmd;
                rsmd=rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String responseQuery="";
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {

                        if (i == 1)
                            responseQuery += "course_id ---> ";
                            String columnValue = rs.getString(i);
                            responseQuery += columnValue;
                        // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
responseQuery+="\n";
                }
                System.out.println(responseQuery);
                System.out.println("press any key to continue");
                input.nextLine();
            }
            catch (SQLException ee){
                System.out.println("semester has not started yet");
                System.out.println("press any key to continue");
                input.nextLine();

                return;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
public static void mycourses(){

        String query="select * from course_offering where instructor_id='"+user_id+"';";

    try {
        stmt= conn.createStatement();
        ResultSet rs=stmt.executeQuery(query);
        ResultSetMetaData rsmd;
        rsmd=rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        String responseQuery="";
        while (rs.next()) {
            for (int i = 1; i <= columnsNumber; i++) {

                if (i == 1)
                    responseQuery += "course_id ---> ";
                if (i == 2)
                    responseQuery += "cgpa_limit ---> ";
                if (i == 3)
                    responseQuery += "instructor_id ---> ";

                String columnValue = rs.getString(i);
                responseQuery += columnValue;
                if(i>1)responseQuery+=" ";

                // System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            responseQuery+="\n";
        }
        System.out.println(responseQuery);
        System.out.println("press any key to continue");
        input.nextLine();
    } catch (SQLException e) {
        System.out.println("you have no offered courses");
    }
}
    public static void deleteCourse()
    {
        if(!user){
            System.out.println("please log in!");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }

        while(true){
            String course_id;
            System.out.println("enter course_id to delete or 0 to exit");
            course_id=input.nextLine();
            String query="delete from course_offering where course_id='"+course_id+"' and instructor_id='"+user_id+"';";
            try {
                stmt=conn.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println("you have not offered this course yet");
//                throw new RuntimeException(e);
            }
        }


    }


    public static void showGrades()
    {
        if(!user){
            System.out.println("please log in!");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }

        try {
            stmt=conn.createStatement();
            String query = "select * from grades;";
            System.out.println(query);
            try {
                ResultSet rs;
                ResultSetMetaData rsmd;
                rs= stmt.executeQuery(query);
                rsmd=rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String responseQuery="";
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {

                        if (i == 1)
                            responseQuery += "student_id ---> ";
                        if (i == 2)
                            responseQuery += "      course_id ---> ";
                        if (i == 3)
                            responseQuery += "      Grade ---> ";
                        if (i == 4)
                            responseQuery += "      semester ---> ";
                        if (i == 5)
                            responseQuery += "      academic year ---> ";

                        if (i > 1)
                            responseQuery = responseQuery + " ";
                        String columnValue = rs.getString(i);
                        // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                        responseQuery += columnValue;
                    }
                    responseQuery = responseQuery + "\n";

                }
                System.out.println(responseQuery);
                System.out.println("press any key to continue");
                input.nextLine();
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void enrollmentRequests()
    {
        if(!user){
            System.out.println("please log in!");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }
String query="select * from registration_status where isntructor_id='"+user_id+"',status='pending instructor approval';";

        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            ResultSetMetaData rsmd=rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            String responseQuery="";
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {

                    if (i == 1)
                        responseQuery += "course_id ---> ";
                    if (i == 2)
                        responseQuery += "      student_id ---> ";


                    if (i > 1)
                        responseQuery = responseQuery + " ";
                    String columnValue = rs.getString(i);
                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    responseQuery += columnValue;
                }
                responseQuery = responseQuery + "\n";

            }
            System.out.println(responseQuery);
            System.out.println("press any key to continue");
            input.nextLine();

            System.out.println("Approve or disapprove");
            while(true){
                String course_id,student_id;
                System.out.println("enter course_id or 0 to exit");
                course_id=input.nextLine();
                if(course_id.equals("0")){
                    return;
                }
                System.out.println("enter Student_is ");
                student_id=input.nextLine();
                String resp;
                System.out.println("press 1 to approve and 2 to disapprove");
                resp=input.nextLine();
                if(resp.equals("1")){
                     query="update registration_status set status='approved by the instructor' where course_id='"+course_id+" and student_id='"+student_id+"';";
                    stmt.executeUpdate(query);
                }
                else{
                    query="update registration_status set status='rejected by the instructor' where course_id='"+course_id+" and student_id='"+student_id+"';";
                    stmt.executeUpdate(query);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void submitgrades(){

    }



}
