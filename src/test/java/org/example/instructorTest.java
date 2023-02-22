package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class instructorTest {
instructor x=new instructor();
    @BeforeAll
    @Test
    void login() {
        assertTrue(x.login("HS0@iitrpr.ac.in","1234"));
    }
    @AfterAll
    @Test
    void logout() {
        x.logout();
        assertFalse(x.user);
    }

    @Test
    void viewprofile() {
        assertNotEquals(x.viewprofile(),"error");
    }

    @Test
    void updateprofile() {
        assertTrue(x.updateprofile("dummy","1234","9428730301"));
    }

    @Test
    void addCourse() {
     if(x.offeredCourses()){

       assertTrue( x.addCourse("DM111","3"));
         //testing when adding course added by other instructor
         instructor y=new instructor();
         assertTrue(y.login("CS1@iitrpr.ac.in","1234"));
         assertFalse(y.addCourse("DM111","3"));
         assertTrue(y.logout());
         assertTrue(x.deleteCourse("DM111"));
     }
     else{
     assertFalse(x.addCourse("DM111","3"));
     }
    }

    @Test
    void offeredCourses() {
        String f=admin.viewsemester();
        if(f.equals("no sem is running"))
        assertFalse(x.offeredCourses());
        else assertTrue(x.offeredCourses());
    }

    @Test
    void mycourses() {
        if(admin.viewsemester().equals("no sem is running")){
            assertEquals(x.mycourses(),"error");
        }
        else{
            // when user has no offered courses
            assertEquals(x.mycourses(),"you have no offered courses");
            assertTrue(x.addCourse("DM111","3"));
            assertNotEquals(x.mycourses(),"you have no offered courses");
            assertTrue(x.deleteCourse("DM111"));
        }

    }

    @Test
    void deleteCourse() {
        if(admin.viewsemester().equals("no sem is running")){
            assertFalse(x.deleteCourse("DM111"));
        }
        else{
            assertTrue(x.addCourse("DM111","3"));
            assertTrue(x.deleteCourse("DM111"));
        }
    }

    @Test
    void showGrades() {
        assertTrue(x.showGrades());
    }

    @Test
    void enrollmentRequests() {
        if(admin.viewsemester().equals("no sem is running")){
            assertEquals(x.enrollmentRequests(),"error");
        }
        else{

            x.addCourse("DM111","3");
            assertEquals(x.enrollmentRequests(),"no enrollment requests yet");
            student y=new student();
            assertTrue(y.login("2020csb2","iitropar"));
            y.addCourse("DM111");
            assertNotEquals(x.enrollmentRequests(),"no enrollment requests yet");
            assertTrue(y.logout());
            assertTrue(x.deleteCourse("DM111"));
            }
    }

    @Test
    void acceptorreject(){
        if(admin.viewsemester().equals("no sem is running")){
            assertFalse(x.approveordissaprove("DM111","2020csb2","1"));
            assertFalse(x.approveordissaprove("DM111","2020csb2","2"));
        }
        else{

            x.addCourse("DM111","3");
            student y=new student();
            assertTrue(y.login("2020csb2","iitropar"));
            y.addCourse("DM111");
            assertTrue(x.approveordissaprove("DM111","2020csb2","1"));
            assertTrue(x.approveordissaprove("DM111","2020csb2","2"));
            assertTrue(y.logout());
            assertTrue(x.deleteCourse("DM111"));
        }
    }

    @Test
    void submitgrades() {
        if(admin.viewsemester().equals("no sem is running")){
            assertFalse(x.submitgrades());
        }
        else{

            x.addCourse("DM111","0");
            student y=new student();
            assertTrue(y.login("2020csb2","iitropar"));
            y.addCourse("DM111");
            assertTrue(x.approveordissaprove("DM111","2020csb2","1"));
            assertTrue(y.logout());
            assertTrue(x.submitgrades());
            assertTrue(x.deleteCourse("DM111"));
        }
    }
}