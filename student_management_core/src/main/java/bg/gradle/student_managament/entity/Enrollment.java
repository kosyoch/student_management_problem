package bg.gradle.student_managament.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Enrollment extends BaseEntity{
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private List<Double> grades;
    
    @ManyToOne
    private Course course;

    @ManyToOne
    private Student student;
    public Enrollment(){
        this.grades = new ArrayList<>();
    }

    public double getAverageGrade(){
        return grades.stream()
                .mapToDouble(a -> a)
                .average()
                .orElse(0);
    }
    public void addGrade(Double grade){
        grades.add(grade);
    }
}
