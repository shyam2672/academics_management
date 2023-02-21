package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class adminTest {

    admin x=new admin();

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
         x.username="admin";
         x.password="iitropar";
         assertTrue(x.login());
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
    }

    @Test
    void adduser() {
    }

    @Test
    void startsem() {
    }

    @Test
    void endsem() {
    }

    @Test
    void updatecoursecatalog() {
    }

    @Test
    void submittransscript() {
    }

    @Test
    void viewtranscript() {
    }

    @Test
    void viewcourses() {
    }

    @Test
    void viewusers() {
    }
}