package org.example;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    static Connection conn = Connect.ConnectDB();
    static Statement stmt = null;
    public static void main(String[] args) {
        while(true){
            System.out.println("Welcome to the academics management portal IIT ROPAR!");
            Scanner input = new Scanner(System.in);
            String c="";

            int fs=0,fi=0;

            String query="select * from student where token='logged in';";

            try {
                stmt= conn.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                while(rs.next()){
                    student.user=true;
                    student.user_id=rs.getString(1);
                    student.batch_id=rs.getString(3);
                    student.credits=rs.getInt(7);
                    fs++;
                    c="1";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if(fs==0){
                query="select * from instructor where token='logged in';";
                try {
                    stmt= conn.createStatement();
                    ResultSet rs=stmt.executeQuery(query);
                    while(rs.next()){
                        instructor.user=true;
                        instructor.user_id=rs.getString(1);
                        fi++;
                        c="2";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }


if(fs==0 && fi==0){
    System.out.println("Enter your role");
    System.out.println("0. to exit ");
    System.out.println("1. student");
    System.out.println("2. instructor");
    System.out.println("3. admin");

    c=input.nextLine();
    if(c.equals("0")){break;}
}


            switch (c){

                case "1":
                    if(fs==0)
                    student.login();
                    while(student.user){
                        System.out.println("Press \n0. to logout \n1. to view profile\n2. to update profile\n3. to view the offered courses\n4. to add course\n5. to delete Course\n6. to view your courses\n7. to view grades\n8. to view your cgpa\n9. to check graduation  ");
                        String r="";

                        r=input.nextLine();
                        switch (r){
                            case "0":
                                student.logout();
                                break;
                            case "1":student.viewprofile();
                                break;
                            case "2": student.updateprofile();
                                break;
                            case "3": student.offeredCourses();
                                break;
                            case "4": student.addCourse();
                                break;
                            case "5": student.deleteCourse();
                                break;
                            case "6": student.mycourses();
                                break;
                            case "7":student.showGrades();
                                break;
                            case "8":double f=student.getcgpa();
                            System.out.println(f);
                                System.out.println("press any key to continue");
                                input.nextLine();
                                break;
                            case "9":student.gradcheck();
                                break;

                            default:System.out.println("please follow the instructions");
                                break;
                        }
                    }

                    break;
                case "2":
                    if(fi==0)
                    instructor.login();
                    while(instructor.user){
                        System.out.println("Press \n0. to logout \n1. to view profile\n2. to update profile\n3. to view the course catalog\n4. to add course\n5. to delete Course\n6. to view your courses\n7. to view grades of all students\n8. to approve or disapprove enrollments\n9. to submit grades  ");
                        String r="";
                        r=input.nextLine();
                        switch (r){
                            case "0": instructor.logout();
                                break;
                            case "1":instructor.viewprofile();
                                break;
                            case "2": instructor.updateprofile();
                                break;
                            case "3": instructor.offeredCourses();
                                break;
                            case "4": instructor.addCourse();
                                break;
                            case "5": instructor.deleteCourse();
                                break;
                            case "6": instructor.mycourses();
                                break;
                            case "7":instructor.showGrades();
                                break;
                            case "8":instructor.enrollmentRequests();
                                break;
                            case "9":instructor.submitgrades();
                                break;
                            default:System.out.println("please follow the instructions");
                                break;
                        }
                    }

                    break;
                case "3":
                    admin x=new admin();
                    x.login();
                    int flag=1;
                    while(flag==1){
                        System.out.println("Press \n0. to logout \n1. to add a new batch\n2. to add a new course\n3. to add or update a curriculum\n4. to start sem\n5. to end sem\n6. to add course to course_catalog\n7. to view grades of all students\n8. to add users\n9. to generate student transcripts \n10. to view transcript\n11. to view courses\n12. to view users ");
                        String r="";

                        r=input.nextLine();
                        switch (r){
                            case "0":
                                flag=0;
                                x.logout();
                                break;
                            case "1": x.addbatch();
                                break;
                            case "2": x.addcourse();
                                break;
                            case "3": x.addcurriculum();
                                break;
                            case "4": x.startsem();
                                break;
                            case "5": x.endsem();
                                break;
                            case "6": x.updatecoursecatalog();
                                break;
                            case "7":x.showGrades();
                                break;
                            case "8":x.adduser();
                                break;
                            case "9":x.submittransscript();
                                break;
                            case "10":admin.viewtranscript();
                            break;
                            case "11":admin.viewcourses();
                                break;
                            case "12":admin.viewusers();
                                break;
                            default:System.out.println("please follow the instructions");
                                break;
                        }
                    }

                    break;
                default:
                    System.out.println("invalid role");
                    break;
            }
        }
        }

}