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
        } else {
            System.out.print("ultrafuck");

        }

        String role = "";
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your role");
        System.out.println("1. student \n 2. instructor \n 3. admin ");

        role = input.nextLine();
        switch (role) {
            case "1":
                String name = "", batch = "", department = "", entry_no = "", password = "";
                System.out.println("Enter your name");
                name = input.nextLine();
                System.out.println("Enter your batch");
                batch = input.nextLine();
                System.out.println("Enter your department");
                department = input.nextLine();
                System.out.println("Enter your password");
                password = input.nextLine();
                String count="";
               String  cquery="select count(*) from student";
                ResultSet rs;
                ResultSetMetaData rsmd;
                try {
                    stmt= conn.createStatement();
                    rs=stmt.executeQuery(cquery);
                    rsmd=rs.getMetaData();
                    String s1 = "";
                    while (rs.next()) {
                        for (int i = 1; i <= rsmd.getColumnCount(); i++) {


                            String columnValue = rs.getString(i);
                            // System.out.print(columnValue + " " + rsmd.getColumnName(i));
                            s1 += columnValue;
                        }

                    }
                    System.out.println(s1);
                    entry_no=s1;
                    System.out.println(name +" "+ entry_no);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            String query = "INSERT INTO student(name,id,batch,department,password) VALUES('" + name + "'," + "'"+entry_no+"'," + "'"+batch+"',"+"'"+department+"',"+ "'" + password  + "');";
System.out.println(query);
                try {
                    stmt.executeQuery(query);
                } catch (SQLException e) {

                }
                break;

        }

    }
}

