package bg.gradle;



import bg.gradle.student_managament.entity.enums.Degree;
import bg.gradle.student_managament.repository.StudentRepository;
import bg.gradle.student_managament.service.CourseService;
import bg.gradle.student_managament.service.EnrollmentService;
import bg.gradle.student_managament.service.StudentService;
import bg.gradle.student_managament.service.TeacherService;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args)  {

        StudentService studentService = new StudentService();
        TeacherService teacherService = new TeacherService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the student management system!");
        System.out.println("The following commands are available to you:");
        System.out.println("ADD NEW COURSE, ADD NEW STUDENT, ADD NEW TEACHER, ADD TEACHER TO COURSE, ADD STUDENT TO COURSE, ADD GRADE");
        System.out.println("SHOW ALL STUDENTS, SHOW ALL COURSES, SHOW ALL AVERAGE GRADES IN COURSE, GET TOTAL AVERAGE FOR A STUDENT");
        System.out.println("If you wish to stop using the system type CLOSE.");
        String command = scanner.nextLine();
        while (!command.equals("CLOSE")){
            String[] input;
        switch (command){
            case "ADD NEW COURSE":
                System.out.println("Please provide the course name and its total hours:");
                input = scanner.nextLine().split(" ");
                if(input.length == 2  && isValidInteger(input[1])){
                    System.out.println(courseService
                            .addCourse(input[0], Integer.parseInt(input[1])));
                    break;
                }
                System.out.println("Invalid input!");
                break;
            case "ADD NEW STUDENT":
                System.out.println("Please provide the student's name and age:");
                input = scanner.nextLine().split(" ");
                if(input.length == 2  && isValidInteger(input[1])){
                    System.out.println(studentService
                            .addStudent(input[0], Integer.parseInt(input[1])));
                    break;
                }
                System.out.println("Invalid input!");
                break;
            case "ADD NEW TEACHER":
                System.out.println("Please provide the teacher's name and degree:");
                input = scanner.nextLine().split(" ");
                if(input.length == 2){
                    System.out.println(teacherService
                            .addTeacher(input[0], input[1]));
                    break;
                }
                System.out.println("Invalid input!");
                break;
            case "ADD TEACHER TO COURSE":
                System.out.println("Please provide the teacher's name and the course they are being added to:");
                input = scanner.nextLine().split(" ");
                if(input.length == 2){
                    System.out.println(courseService
                            .addTeacherToCourse(input[0], input[1]));
                    break;
                }
                System.out.println("Invalid input!");
                break;
            case "ADD STUDENT TO COURSE":
                System.out.println("Please provide the student's name and the course they are being added to:");
                input = scanner.nextLine().split(" ");
                if(input.length == 2){
                    System.out.println(courseService
                            .addStudentToCourse(input[0], input[1]));
                    break;
                }
                System.out.println("Invalid input!");
                break;
            case "ADD GRADE":
                System.out.println("Please provide the grade, student's name and the course:");
                input = scanner.nextLine().split(" ");
                if(input.length == 3 && isValidDouble(input[0])){
                    System.out.println(enrollmentService
                            .addGradeForStudentInSpecificCourse(Double.parseDouble(input[0]), input[1], input[2]));
                    break;
                }
                System.out.println("Invalid input!");
                break;
            case "SHOW ALL STUDENTS":
                System.out.println(studentService.returnAllStudentsByCourseAndAverageGrade());
                break;
            case "SHOW ALL COURSES":
                System.out.println(courseService.returnAllCoursesWithTeachersAndStudents());
                break;
            case "SHOW ALL AVERAGE GRADES IN COURSE":
                System.out.println("Please provide the course name:");
                input = scanner.nextLine().split(" ");
                if(input.length ==1){
                    System.out.println(enrollmentService.getAverageGradeOfAllStudentsInACourse(input[0]));
                    break;
                }
                System.out.println("Invalid input!");
                break;
            case "GET TOTAL AVERAGE FOR A STUDENT":
                System.out.println("Please provide the student's name:");
                input = scanner.nextLine().split(" ");
                if(input.length ==1){
                    System.out.println( enrollmentService.getTotalAverageGradeForAStudent(input[0]));
                    break;
                }
                System.out.println("Invalid input!");
                break;
            default:
                System.out.println("Invalid command!");
        }

            System.out.println("Please input a new command or type CLOSE to end the session:");
            command = scanner.nextLine();
        }

        System.out.println("Thank you for using the student management system!");
    }

    private static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}