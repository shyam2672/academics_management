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
    public static String batch_id="";

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
                int f=0;
                while(rs.next()){
                   f++;
                    user_id=rs.getString(1);
                    batch_id=rs.getString(3);
                }

                if( f==0){
                    System.out.println("wrong credentials");
                    System.out.println("press any key to continue");
                    input.nextLine();
                }
                else{
                    user=true;

                    System.out.println("logged in successfully");
                    return;

                }

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
double cgpa_limit=0.0;
String instructor_id="";
                try {
                    ResultSet rs;


                    rs=stmt.executeQuery(query);
int f=0;
while(rs.next()){f++;cgpa_limit=rs.getInt(2);instructor_id=rs.getString(3);}
if(f==0){System.out.println("no such course is offered");continue;}

                }
                catch (SQLException e){
                    System.out.println("no such course is offered");
                    continue;
                }

                double cgpa=getcgpa();
              if(cgpa>0 && cgpa<cgpa_limit){
                  System.out.println("you cannot take this course as your cgpa is less than required " +cgpa_limit);
                  continue;
              }




                try {


                   query="select * from prereq where course_id='"+course_id+"';";
                   ResultSet rs=stmt.executeQuery(query);
                   int flag=1;
                   while (rs.next()){
                       String prereq=rs.getString(2);
                       try{
                           stmt= conn.createStatement();
                           query="select * from grades where course_id='"+prereq+"' and student_id='"+user_id+"';";
                           ResultSet rs2=stmt.executeQuery(query);
                           String grade="";
                           while (rs2.next()){
                               grade=rs2.getString(3);
                           }
                           if(grade.equals("F") || grade.equals("")){
                               System.out.println("Complete the course "+prereq+" first to register this course");
                               flag=0;
                           }
                       }catch (SQLException e){
                           System.out.println("Complete the course "+prereq+" first to register this course");
                           flag=0;

                       }
                   }


if(flag==0)
continue;
                        query="insert into registration_status(course_id,student_id,instructor_id,status) values ('"+course_id+"','"+user_id+"','"+instructor_id+"',"+"'pending instructor approval');";
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

    public static void offeredCourses()  {

        String query="\n" +
                "select course_offering.course_id,course_offering.cgpa_limit,ug_curriculum.course_type,course_offering.instructor_id\n" +
                "from ug_curriculum,course_offering\n" +
                "where '"+batch_id+"'=ug_curriculum.batch_id and ug_curriculum.course_id=course_offering.course_id;";
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
                          responseQuery += "cgpa_limit ";
                      if (i == 3)
                          responseQuery += "course_type ---> ";
                      if (i == 4)
                          responseQuery += "instructor_id" +
                                  " ---> ";
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
                 System.out.println(e);
                 System.out.println("no courses offered yet");
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
            int f=0;
            while (rs.next()) {
                f++;
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
            if(f==0){
                System.out.println("you have no courses");
                System.out.println("press any key to continue");
                input.nextLine();
                return;
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

    public static double getcgpa()
    {
         String query="select * from grades where student_id='"+user_id+"';";
         double cgpa=0.0;
        double credits=0;
        double points=0;
        int f=0;

        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            ResultSetMetaData rsmd;
            rsmd=rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (rs.next()) {
                f++;

                String cid=rs.getString(2);
                String grade=rs.getString(3);
                String q="select * from course where id='"+cid+"';";
                ResultSet rs2=stmt.executeQuery(q);

                while (rs2.next()){
//                    System.out.println("fuck");

                    credits+=rs2.getInt(7);
                }
                switch (grade){
                    case "A":points+=10;break;
                    case "A-":points+=9;break;
                    case "B":points+=8;break;
                    case "B-":points+=7;break;
                    case "C":points+=6;break;
                    case "C-":points+=5;break;
                    case "D":points+=4;break;
                    case "E":points+=2;break;
                    case "F":points+=0;break;

                }
                System.out.println(credits +" " + points);
            }



            if(f==0){
                return 0.0;
            }
            else{
                cgpa=points/credits;
            }
        } catch (SQLException e) {

            if(credits!=0.0 && f!=0){
            cgpa=points/credits;
//            System.out.println(cgpa);
            return cgpa;}
            return 0.0;
        }
        return cgpa;
    }

    public static void gradcheck(){
String query="\n" +
        "select ug_curriculum.course_id\n" +
        "from ug_curriculum\n" +
        "where course_type='core'\n" +
        "except\n" +
        "select grades.course_id\n" +
        "from grades\n" +
        "where grades.grade!='F' and grades.student_id='"+user_id+"';";
        try {
            stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            int f=0;
            while (rs.next()){
                f++;
            }
            if(f>0){
                System.out.println("not eligible for graduation");

            }
            else{
                System.out.println("eligible for graduation");

            }
            System.out.println("press any key to continue");
            input.nextLine();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            System.out.println(e);
        }
    }

}


