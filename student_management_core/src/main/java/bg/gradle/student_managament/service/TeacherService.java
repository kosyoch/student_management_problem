package bg.gradle.student_managament.service;

import bg.gradle.student_managament.entity.Teacher;
import bg.gradle.student_managament.entity.enums.Degree;
import bg.gradle.student_managament.repository.TeacherRepository;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.sql.SQLException;

@NoArgsConstructor
public class TeacherService {
    public String addTeacher(String name, String degree)  {
        TeacherRepository teacherRepository = TeacherRepository.getInstance();

        if (!degreeExists(degree)){
            return "That is not a valid degree!";
        }
        if(teacherRepository.findByName(name).isEmpty()){
            Teacher teacher = new Teacher();
            teacher.setName(name);
            teacher.setDegree(Degree.valueOf(degree));
            teacherRepository.save(teacher);
            return "Teacher successfully added!";
        }
        return "Teacher already exists!";
    }

    private static boolean degreeExists(String degreeToCheck){
        for (Degree degree : Degree.values()) {
            if(degree.name().equals(degreeToCheck)){
                return true;
            }
        }
        return false;
    }
}
