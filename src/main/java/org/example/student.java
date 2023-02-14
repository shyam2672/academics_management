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
public class student {
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

            String query="select * from student where email='"+email+"' and password='"+password+"';";
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

        String query="select * from student where id='"+user_id+"';";

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
                        responseQuery += "      batch_id ---> ";
                    if (i == 4)
                        responseQuery += "      email ---> ";
                    if (i == 5)
                        continue;
                    if (i == 6)
                        responseQuery += "      phone_number ---> ";
                    if (i == 7)
                        responseQuery += "      credits ---> ";

                        String columnValue = rs.getString(i);
                        responseQuery += columnValue+" ";

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

        String query =" update student set name='"+name+"',phone_number='"+phone_number+"',password='"+password+"' where id='"+user_id+"';";

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
                String query="select * from course_offering where course_id='"+course_id+"';";

                try {
                    ResultSet rs;


                    rs=stmt.executeQuery(query);


                }
                catch (SQLException e){
                    System.out.println("no such course is offered");
                }

                try {
                   String instructor_id="";

                   query="select * from course_offering where course_id='"+course_id+"';";
                   ResultSet rs=stmt.executeQuery(query);
                   while (rs.next()){
                       instructor_id=rs.getString(3);
                   }

                   if(instructor_id.equals("")){
                       System.out.println("no such course is offered");
                       return;
                   }

                        query="insert into registration_status(course_id,student_id,instructor_id,status) values ('"+course_id+"',"+user_id+",'"+instructor_id+"',"+"'pending instructor approval');";
                        stmt.executeUpdate(query);
                        System.out.println("Added course successfully");

                }
                catch (SQLException e){
                    System.out.println(e);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void offeredCourses() throws SQLException {

        String query="\n" +
                "select course_offering.course_id,ug_curriculum.course_type\n" +
                "from student,ug_curriculum,course_offering\n" +
                "where student.batch_id=ug_curriculum.batch_id and ug_curriculum.course_id=course_offering.course_id;";
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
                          responseQuery += "course_type ---> ";
                      if (i == 3)
                          responseQuery += "instructor_id ---> ";
                      String columnValue = rs.getString(i);
                      responseQuery += columnValue+" ";
                      // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                  }
                  responseQuery+="\n";
              }
              System.out.println(responseQuery);
              System.out.println("press any key to continue");
              input.nextLine();

          }
          catch (SQLException e){

          }

    }
    public static void mycourses(){

        String query="select * from registration_status where student_id='"+user_id+"';";

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
                        continue;
                    if (i == 3)
                        responseQuery += "instructor_id ---> ";
                    if (i == 4)
                        responseQuery += "status ---> ";

                    String columnValue = rs.getString(i);
                    responseQuery += columnValue+" ";
                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                responseQuery+="\n";
            }
            System.out.println(responseQuery);
            System.out.println("press any key to continue");
            input.nextLine();
        } catch (SQLException e) {
            System.out.println("you have no courses");
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
            if(course_id.equals("0")){
                return;
            }
            String query="delete from registration_status where course_id='"+course_id+"' and student_id='"+user_id+"';";
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

    }

   





}
