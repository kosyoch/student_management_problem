package bg.gradle.student_managament.repository;

import bg.gradle.student_managament.entity.Course;
import bg.gradle.student_managament.entity.Teacher;
import jakarta.persistence.EntityManager;

import java.util.Optional;


public class TeacherRepository {

    private static TeacherRepository single_instance = null;

    private TeacherRepository (){

    }
    public static TeacherRepository getInstance(){
        if(single_instance == null){
            single_instance = new TeacherRepository();
        }
        return single_instance;
    }
    public Optional<Teacher> findByName(String name){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        Optional<Teacher> teacher = entityManager
                .createQuery("SELECT t FROM Teacher t WHERE t.name =:name", Teacher.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
        entityManager.close();
        return teacher;
    }
    public void save(Teacher teacher){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(teacher);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void update(Teacher teacher){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(teacher);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
