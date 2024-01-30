package bg.gradle.student_managament.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryHolder {
    private static EntityManagerFactory instance = null;
    private EntityManagerFactoryHolder(){
    }
    public static EntityManager createEntityManager(){
        if(instance == null){
            instance = Persistence.createEntityManagerFactory("student_manager");
        }
        return instance.createEntityManager();
    }
    public static void close(){
        instance.close();
    }
}
