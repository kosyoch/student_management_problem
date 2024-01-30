package bg.gradle.student_managament.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
@Getter
@Setter
public class Student extends BaseEntity{

    private String name;

    private Integer age;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Course> courses;

    public double getTotalAverageGrade(){
        List<Double> averageGrades = enrollments.stream().map(Enrollment::getAverageGrade).toList();

        return averageGrades.stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(0);
    }
    public void addCourse(Course course){
        courses.add(course);
    }
    public void addEnrollment(Enrollment enrollment){
        enrollments.add(enrollment);
    }
}
