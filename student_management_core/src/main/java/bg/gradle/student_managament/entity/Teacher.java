package bg.gradle.student_managament.entity;

import bg.gradle.student_managament.entity.enums.Degree;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Teacher extends BaseEntity{

    private String name;

    @Enumerated(EnumType.STRING)
    private Degree degree;
}
