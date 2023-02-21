package org.example;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class studentTest {

    public String student_name="dummy_student";
    public String student_id="2010csb0";

    public String email="2010csb0@iitrpr.ac.in";
    public int credits=0;

    public String password="iitropar";

    public String batch_id="2010csb";
    public String phone_number="9327223367";

    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
    static Scanner input = new Scanner(System.in);

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void viewprofile() {
    }

    @Test
    void updateprofile() {
    }

    @Test
    void addCourse() {
    }

    @Test
    void offeredCourses() {
    }

    @Test
    void mycourses() {
    }

    @Test
    void deleteCourse() {
    }

    @Test
    void showGrades() {
    }

    @Test
    void getcgpa() {
    }

    @Test
    void gradcheck() {
    }

    @Test
    void islessthantwo() {
    }
}