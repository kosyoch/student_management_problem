package bg.gradle.student_managament.service;

import bg.gradle.student_managament.entity.Course;
import bg.gradle.student_managament.entity.Enrollment;
import bg.gradle.student_managament.entity.Student;
import bg.gradle.student_managament.repository.CourseRepository;
import bg.gradle.student_managament.repository.EnrollmentRepository;
import bg.gradle.student_managament.repository.StudentRepository;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@NoArgsConstructor
public class StudentService {
    public String addStudent(String name, Integer age)  {
        StudentRepository studentRepository = StudentRepository.getInstance();

        if(age <= 5){
            return "Students must be at least 6 years old!";
        }
        if(studentRepository.findByName(name).isEmpty()){
            Student student = new Student();
            student.setName(name);
            student.setAge(age);
            studentRepository.save(student);
            return "Student added successfully!";
        }
        return "Student already exists!";
    }

    public String returnAllStudentsByCourseAndAverageGrade()  {
        EnrollmentRepository enrollmentRepository = EnrollmentRepository.getInstance();

        StringBuilder stringBuilder = new StringBuilder();

        List<Course> allCourses = sortCoursesAlphabetically();

        for (Course course : allCourses) {
            stringBuilder.append(String.format("%s: %n", course.getName()));
            List<Student> students = sortStudentsInCourseByAverageGrade(course);
            for (Student student : students) {
                stringBuilder.append(String.format("    %s - %.1f%n",student.getName(), enrollmentRepository.findByStudent(student).getAverageGrade()));
            }
        }
        return stringBuilder.toString();
    }


    private List<Course> sortCoursesAlphabetically()  {
        CourseRepository courseRepository = CourseRepository.getInstance();
        Comparator<Course> courseNameComparator = Comparator.comparing(Course::getName);

       return courseRepository.findAll().stream().sorted(courseNameComparator).toList();
    }

    private List<Student> sortStudentsInCourseByAverageGrade(Course course)  {
        EnrollmentRepository enrollmentRepository = EnrollmentRepository.getInstance();

        List<Enrollment> enrollments = enrollmentRepository.findAllByCourse(course);
        enrollments.sort(Comparator.comparing(Enrollment::getAverageGrade));
        List<Student> students = enrollments.stream()
                .map(Enrollment::getStudent)
                .toList();
        return students;

    }

}
