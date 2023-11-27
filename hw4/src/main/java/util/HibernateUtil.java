package util;

import model.Course;
import model.Professor;
import model.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;


    public static void init() {
        Configuration configuration = new Configuration();
        sessionFactory = configuration.configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Professor.class)
                .buildSessionFactory();

    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
