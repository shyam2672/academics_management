package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
     public static void login() {
        Connection con=Connect.ConnectDB();
         String role="";
         
         if(role=="student"){
                 System.out.print(role);
         }
     }
}
