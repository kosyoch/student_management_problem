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
public class Course extends BaseEntity{

    private String name;

    @Column(name = "total_hours")
    private Integer totalHours;

    @OneToOne
    private Teacher teacher;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Student> students;

    public Course(){
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student){
        students.add(student);
    }
}
