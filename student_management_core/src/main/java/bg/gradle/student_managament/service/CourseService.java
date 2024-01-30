package bg.gradle.student_managament.service;

import bg.gradle.student_managament.entity.Course;
import bg.gradle.student_managament.entity.Student;
import bg.gradle.student_managament.entity.Teacher;
import bg.gradle.student_managament.repository.CourseRepository;
import bg.gradle.student_managament.repository.StudentRepository;
import bg.gradle.student_managament.repository.TeacherRepository;
import lombok.NoArgsConstructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class CourseService {

    public String addCourse(String name, int hours) {
        CourseRepository courseRepository = CourseRepository.getInstance();
        if(hours <= 0){
            return "Course hours must be greater than 0!";
        }
        if(courseRepository.findByName(name).isEmpty()){
            Course course = new Course();
            course.setName(name);
            course.setTotalHours(hours);
            courseRepository.save(course);
            return "Course successfully added!";
        }
        return "Course already exists!";
    }

    public String addTeacherToCourse(String teacherName, String courseName)  {
        CourseRepository courseRepository = CourseRepository.getInstance();
        TeacherRepository teacherRepository = TeacherRepository.getInstance();

       Optional<Course> courseOptional = courseRepository.findByName(courseName);

       Optional<Teacher> teacherOptional = teacherRepository.findByName(teacherName);

        if (teacherOptional.isPresent() && courseOptional.isPresent()){
           if(courseOptional.get().getTeacher() == null){
               Course course = courseOptional.get();
               Teacher teacher = teacherOptional.get();


               course.setTeacher(teacher);

               courseRepository.update(course);
               return "Successfully added teacher to course!";
           }
            return "The course already has a teacher!";
        }
        return "The course and/or teacher do not exist!";

    }

    public String addStudentToCourse(String studentName, String courseName) {
        StudentRepository studentRepository = StudentRepository.getInstance();
        CourseRepository courseRepository = CourseRepository.getInstance();
        Optional<Student> studentOptional = studentRepository.findByName(studentName);
        Optional<Course> courseOptional = courseRepository.findByName(courseName);

        if(studentOptional.isPresent() && courseOptional.isPresent()){
            Student student = studentOptional.get();

            Course course = courseOptional.get();
            student.addCourse(course);
            course.addStudent(student);
            courseRepository.update(course);
            return "Student successfully added to course!";
        }
        return "The course and/or student do not exist!";
    }

    public String returnAllCoursesWithTeachersAndStudents() {
        CourseRepository courseRepository = CourseRepository.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        List<Course> allCourses = courseRepository.findAll();
        for (Course course : allCourses) {
            stringBuilder.append(String.format("%s:%n", course.getName()));
            stringBuilder.append(String.format(String.format("  Teacher: %s%n",course.getTeacher().getName())));
            stringBuilder.append(String.format("    Students:%n"));
            for (Student student : course.getStudents()) {
                stringBuilder.append(String.format("        %s%n",student.getName()));
            }
        }
        return stringBuilder.toString();
    }


}
