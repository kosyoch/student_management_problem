package bg.gradle.student_managament.repository;

import bg.gradle.student_managament.entity.Course;
import bg.gradle.student_managament.entity.Enrollment;
import bg.gradle.student_managament.entity.Student;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;


public class EnrollmentRepository {
    private static EnrollmentRepository single_instance = null;

    private EnrollmentRepository (){

    }
    public static EnrollmentRepository getInstance(){
        if(single_instance == null){
            single_instance = new EnrollmentRepository();
        }
        return single_instance;
    }



    public Optional<Enrollment> findByCourseAndStudent(Course course, Student student){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        Optional<Enrollment> enrollment = entityManager
                .createQuery("SELECT e FROM Enrollment e WHERE e.course = :course AND e.student = :student", Enrollment.class)
                .setParameter("course", course)
                .setParameter("student", student)
                .getResultList()
                .stream()
                .findFirst();
        entityManager.close();
        return enrollment;
    }
    public List<Enrollment> findAllByCourse (Course course){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        List<Enrollment> enrollments = entityManager
                .createQuery("SELECT e FROM Enrollment e WHERE e.course = :course", Enrollment.class)
                .setParameter("course", course)
                .getResultList();
        entityManager.close();
        return enrollments;
    }
    public Enrollment findByStudent(Student student){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        Enrollment enrollment = entityManager
                .createQuery("SELECT e FROM Enrollment e WHERE e.student = :student", Enrollment.class)
                .setParameter("student", student)
                .getSingleResult();
        entityManager.close();
        return enrollment;
    }
    public void save(Enrollment enrollment){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(enrollment);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public void update(Enrollment enrollment){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(enrollment);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
