package org.example;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Register {
    public static void register() {
        Connection conn = Connect.ConnectDB();
        Statement stmt = null;
        if (conn == null) {
            System.out.print("megafuck");
        }
        else{
            System.out.print("ultrafuck");

        }
         String role = "", name = "", department = "", year = "";
         Scanner input = new Scanner(System.in);
         System.out.println("Enter your role");
         role = input.nextLine();
         System.out.println("Enter your name");
         name = input.nextLine();

         System.out.println("Enter your department");
         department = input.nextLine();

         System.out.println("Enter your year");
         year = input.nextLine();
         System.out.println(role + " " + name + " " + department + " " + year);
        try {
            stmt = conn.createStatement();
            String query="INSERT INTO student(name,id,password) VALUES('"+name+"',"+ "'2020csb1111'"+","+"'ashish'" +")";
            try {

                stmt.executeQuery(query);


            } catch (Exception e) {

                // TODO: handle exception
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

