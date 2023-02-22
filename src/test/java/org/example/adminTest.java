package org.example;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class adminTest {

    admin x=new admin();
   String dummystudentid="",dummyinstructorid;
    @AfterAll
    @Test
    void deleteuserdummies(){

        assertTrue(x.deleteuser("1",dummystudentid));
        assertTrue(x.deleteuser("2",dummyinstructorid));
    }
    @AfterAll
    @Test
    void deletebatchdummies(){
        assertTrue(x.deletebatch("2010csb"));
    }
    @AfterAll
    @Test
    void deletecoursedummies(){
        assertTrue(x.deletecourse("DM100"));
        assertTrue(x.deletecourse("DM101"));
    }



    @AfterAll
    @Test
    void deletecoursecurriculumdummies(){
        assertTrue(x.deletefromcurriculum("CS200","2020mcb"));
    }
    @BeforeAll
    @Test
    void login() {

         assertTrue(x.login("admin","iitropar"));
    }
    @AfterAll
    @Test
    void logout() {
        x.logout();
        assertFalse(x.user);
    }

    @Test
    void addbatch() {
        boolean f=x.addbatch("2010csb","2010","CS");
        assertTrue(f);
    }

    @Test
    void addcourse() {
        List<String> prereq=new ArrayList<String>();
        boolean f=x.addcourse("DM100","dummy_course","CS","3","3","3","3",prereq);

        prereq.add("DM100");
        boolean f2=x.addcourse("DM101","dummy_course","CS","3","3","3","3",prereq);
        assertTrue(f);
        assertTrue(f2);

    }

    @Test
    void addcurriculum() {
        boolean f= x.addcurriculum("CS200","core","2020mcb");
        assertTrue(f);
    }

    @Test
    void showGrades() {
//          String grades=x.showGrades();
        boolean f=x.showGrades("2020csb0");
    }
//
    @Test
    void adduser() {
        List<String> data=new ArrayList<String>();
        data.add("dummy");
        data.add("2020csb");
        data.add("9327223367");
          String f=x.adduser("1",data);
        List<String> data2=new ArrayList<String>();
        data2.add("dummy");
        data2.add("CS");
        data2.add("9327223367");
        String f2=x.adduser("2",data2);
        assertNotEquals(f,"failed");
        assertNotEquals(f2,"failed");
        dummystudentid=f;
        dummyinstructorid=f2;
    }

    @Test
    void startsem(){
        String resp=x.viewsemester();
         String f=x.startsem("2026","monsoon");
//         System.out.println(resp);
         if(resp.equals("no sem is running")){
             assertNotEquals(f,"a sem is already running");
             boolean b=x.endsem();
             assertEquals(b,true);
         }
         else{
             assertEquals(f,"a sem is already running");
         }
    }

    @Test
    void endsem() {
        String resp=x.viewsemester();
        boolean f=x.endsem();
//         System.out.println(resp);
        if(resp.equals("no sem is running")){
         assertEquals(f,false);
        }
        else{
            assertEquals(f,true);

        }
    }

    @Test
    void updatecoursecatalog() {
        String resp=x.viewsemester();
        boolean f=x.updatecoursecatalog("DM111");
        if(resp.equals("no sem is running")){
assertEquals(f,false);
        }
        else{
            assertEquals(f,true);
           f= x.deletefromcoursecatalog("DM111");
            assertEquals(f,true);

        }
    }

    @Test
    void submitransscript() {
boolean f=x.submittranscript("2020csb0");
assertEquals(f,true);
assertEquals(x.deletetranscript("2020csb0"),true);
    }

    @Test
    void viewtranscript() {
        boolean f=x.submittranscript("2020csb0");
        assertEquals(f,true);
          f=x.viewtranscript("2020csb0");
        assertEquals(f,true);

        assertEquals(x.deletetranscript("2020csb0"),true);

    }

    @Test
    void viewcourses() {
         assertEquals(x.viewcourses(),true);
    }

    @Test
    void viewusers() {
        assertEquals(x.viewusers("1"),true);
        assertEquals(x.viewusers("2"),true);
    }
}