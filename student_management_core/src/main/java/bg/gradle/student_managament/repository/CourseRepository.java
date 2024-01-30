package bg.gradle.student_managament.repository;

import bg.gradle.student_managament.entity.Course;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;


public class CourseRepository{

    private static CourseRepository single_instance = null;

    private CourseRepository(){

    }
    public static CourseRepository getInstance(){
        if(single_instance == null){
            single_instance = new CourseRepository();
        }
        return single_instance;
    }

    public Optional<Course> findByName(String name){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        Optional<Course> course = entityManager
                .createQuery("SELECT c FROM Course c WHERE c.name =:name", Course.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findFirst();
        entityManager.close();
        return course;
    }
    public Course findById(Long courseId) {
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        Course course = entityManager.find(Course.class, courseId);
        entityManager.close();
        return course;
    }
    public void save(Course course){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(course);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void update(Course course){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(course);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public List<Course> findAll(){
        EntityManager entityManager = EntityManagerFactoryHolder.createEntityManager();
        List<Course> courses = entityManager
                .createQuery("SELECT c FROM Course c ", Course.class)
                .getResultList();
        entityManager.close();
        return courses;
    }

}
