package bg.gradle.student_managament.repository;

import bg.gradle.student_managament.entity.Course;
import bg.gradle.student_managament.entity.Student;
import jakarta.persistence.EntityManager;

import java.util.Optional;


public class StudentRepository {


    private static StudentRepository single_instance = null;

    private StudentRepository (){

    }
    public static StudentRepository getInstance(){
        if(single_instance == null){
            single_instance = new StudentRepository();
        }
        return single_instance;
    }
    public Optional<Student> findByName(String name){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        Optional<Student> student = entityManager
                .createQuery("SELECT s FROM Student s WHERE s.name = :name", Student.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
        entityManager.close();
        return student;
    }
    public void save(Student student){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(student);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void update(Student student){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(student);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
