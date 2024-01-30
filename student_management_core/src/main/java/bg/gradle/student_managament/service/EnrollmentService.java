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
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class EnrollmentService {
    public String addGradeForStudentInSpecificCourse(Double grade, String studentName, String courseName)  {
        StudentRepository studentRepository = StudentRepository.getInstance();
        CourseRepository courseRepository = CourseRepository.getInstance();
        EnrollmentRepository enrollmentRepository = EnrollmentRepository.getInstance();

        if(grade >= 2.0 && grade <= 6.0){
            Optional<Student> studentOptional = studentRepository.findByName(studentName);
            Optional<Course> courseOptional = courseRepository.findByName(courseName);


            if(studentOptional.isPresent() && courseOptional.isPresent()){
                Student student = studentOptional.get();
                Course course = courseOptional.get();
                Optional<Enrollment> enrollmentOptional = enrollmentRepository.findByCourseAndStudent(course,student);
                if (enrollmentOptional.isPresent()){
                    Enrollment enrollment = enrollmentOptional.get();

                    enrollment.addGrade(grade);

                    enrollmentRepository.update(enrollment);
                    return "Grade successfully added!";
                }

                Enrollment enrollment = new Enrollment();

                enrollment.addGrade(grade);
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                student.addEnrollment(enrollment);

                enrollmentRepository.save(enrollment);
                return "Grade successfully added!";
            }
            return "The student and/or course do not exist!";
        }
        return "The grade must be between 2.0 and 6.0 (both included)!";
    }

    public String getAverageGradeOfAllStudentsInACourse(String courseName)  {
        CourseRepository courseRepository = CourseRepository.getInstance();
        EnrollmentRepository enrollmentRepository = EnrollmentRepository.getInstance();

        Optional<Course> courseOptional = courseRepository.findByName(courseName);
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            List<Enrollment> enrollmentsInCourse = enrollmentRepository.findAllByCourse(course);
            List<Double> averageGrades = new ArrayList<>();

            enrollmentsInCourse.forEach(enrollment -> averageGrades.add(enrollment.getAverageGrade()));
            double courseAverage = averageGrades.stream()
                    .mapToDouble(a -> a)
                    .average()
                    .orElse(0);
            return String.format("%s: %.1f", course.getName(), courseAverage);
        }
        return "The course does not exist!";
    }

    public String getTotalAverageGradeForAStudent(String studentName)  {
        StudentRepository studentRepository = StudentRepository.getInstance();

        Optional<Student> studentOptional = studentRepository.findByName(studentName);

        if (studentOptional.isPresent()){
            Student student = studentOptional.get();
            return String.format("%s - %.1f",student.getName(), student.getTotalAverageGrade());
        }
        return "Student does not exist!";

    }


}
