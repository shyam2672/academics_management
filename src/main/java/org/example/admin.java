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
                    while (true){
                        String pre;
                        System.out.println("enter the course code of the prerequisite course of the course "+course_id+" or 0 to exit");
                        pre=input.nextLine();
                        if(pre.equals("0"))break;
                        query="insert into prereq(course_id,prereq_id) values('"+course_id+"','"+pre+"');";
                        stmt.executeUpdate(query);
                    }
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

    public static void showGrades(){
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

    public static void adduser(){
        while(true){
            System.out.println("press \n0 to exit\n1 to add student\n2 to add instructor");
            String role=input.nextLine();
            switch (role){
                case "0": {return;}
                case "1":
                {String id,name,batch_id,phone_number,password;
              System.out.println("enter name of the student");
              name=input.nextLine();
                    System.out.println("enter batch_id of the student");
                    batch_id=input.nextLine();
                    System.out.println("enter phone number of the student");
                    phone_number=input.nextLine();
                    try {
                        stmt=conn.createStatement();
                    try {
                        ResultSet rs;
                        ResultSetMetaData rsmd;
                        String query="select count(*) from student where batch_id='"+batch_id+"';";
                        rs= stmt.executeQuery(query);
                        rsmd=rs.getMetaData();
                        int columnsNumber = rsmd.getColumnCount();
                        String responseQuery="";
                        while (rs.next()) {
                            for (int i = 1; i <= columnsNumber; i++) {


                                String columnValue = rs.getString(i);
                                // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                                responseQuery += columnValue;
                            }

                        }
                      id=batch_id+responseQuery;
                        query="insert into student(id,name,batch_id,email,password,phone_number,credits,token) values('"+id+"','"+name+"','"+batch_id+"','"+id+"@iitrpr.ac.in','"+ "iitropar','"+phone_number+"',0,'');";
                        System.out.println(query);
                         stmt.executeUpdate(query);

                    } catch (SQLException e) {
                        System.out.println(e);
                    }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
                    break;}
                case "2": {
                    String id, name,dep_id, phone_number;
                    System.out.println("enter name of the instructor");
                    name = input.nextLine();
                    System.out.println("enter dep_id of the instructor");
                    dep_id = input.nextLine();
                    System.out.println("enter phone number of the instructor");
                    phone_number = input.nextLine();
                    try {
                        stmt = conn.createStatement();
                        try {
                            ResultSet rs;
                            ResultSetMetaData rsmd;
                            String query = "select count(*) from instructor where dep_id='"+dep_id+"';";
                            rs = stmt.executeQuery(query);
                            rsmd = rs.getMetaData();
                            int columnsNumber = rsmd.getColumnCount();
                            String responseQuery = "";
                            while (rs.next()) {
                                for (int i = 1; i <= columnsNumber; i++) {


                                    String columnValue = rs.getString(i);
                                    // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                                    responseQuery += columnValue;
                                }

                            }
                            id = dep_id + responseQuery;
                            query = "insert into instructor(id,name,dep_id,email,password,phone_number,token) values('" + id + "','" + name + "','" + dep_id + "','" + id + "@iitrpr.ac.in','" + "iitropar','" + phone_number + "','');";
                            System.out.println(query);
                            stmt.executeUpdate(query);
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                default:System.out.println("please follow the instructions");

            }
        }
    }

    public static void startsem(){
        try {
            ResultSet rs;
            stmt=conn.createStatement();

            rs=stmt.executeQuery("select *from semester;");
            System.out.println("A sem is already running");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        } catch (SQLException e) {

        }

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
        String s5="CREATE TABLE semester(\n" +
                "academic_year VARCHAR(10),\n" +
                "semester VARCHAR(10)\n" +
                ");";
        String s7="CREATE TABLE registration_status(\n" +
                "course_id VARCHAR(10),\n" +
                "student_id VARCHAR(10),\n" +
                "instructor_id VARCHAR(10),\n" +
                "status VARCHAR(100),\n" +
                "FOREIGN KEY (course_id) references course_offering (course_id),\n" +
                "FOREIGN KEY (student_id) references student (id),\n" +
                "FOREIGN KEY (instructor_id) references instructor (id)\n" +
                ");";

        String academic_year,semester;
        System.out.println("enter the academic year");
        academic_year=input.nextLine();
        System.out.println("enter the semester monsoon/winter");
        semester=input.nextLine();

        try {
            stmt=conn.createStatement();
            try {

                stmt.execute(s3);
                stmt.execute(s4);
                stmt.execute(s5);
                stmt.execute(s7);

String query="insert into semester(academic_year,semester) values('"+academic_year+"','"+semester+"');";
               stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        System.out.println( academic_year+"-"+semester+" started successfully");
        System.out.println("please add course to the current sem's course catalog");
        System.out.println("press any key to continue");
        input.nextLine();
    }

    public static void endsem(){
        String academic_year="",sem="";
        try {
            ResultSet rs;
            stmt=conn.createStatement();

            rs=stmt.executeQuery("select *from semester;");
            while(rs.next()){
                academic_year=rs.getString(1);
                sem=rs.getString(2);
            }

        } catch (SQLException e) {
            System.out.println("No sem is running");
            System.out.println("press any key to continue");
            input.nextLine();
            return;
        }
        try {
            stmt.execute("drop table semester;");
            String s1="drop table course_catalog;";
            String s2="drop table course_offering;";
            String s6="drop table registration_status;";
            stmt.execute(s6);
            stmt.execute(s2);
            stmt.execute(s1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println( academic_year+"-"+sem+" ended successfully");
        System.out.println("press any key to continue");
        input.nextLine();
    }

    public static void updatecoursecatalog(){
        try {
            ResultSet rs;
            stmt=conn.createStatement();

            rs=stmt.executeQuery("select *from semester");


        } catch (SQLException e) {
            System.out.println("sem is not yet started");
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
    public static void submittransscript(){

    }
}
