package dao;

import dao.inter.StudentDao;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Optional;

public class StudentDaoImpl extends GenericDaoImpl<Student, String> implements StudentDao {
    public StudentDaoImpl(Class<Student> type) {
        super(type);
    }

    @Override
    public boolean save(Student student) {
        return super.save(student);
    }

    @Override
    public Optional<Student> getById(String studentId) {
        HibernateUtil.init();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Student student;
        try(session) {
            Transaction tr = session.beginTransaction();
             student = session.createQuery("select s from Student s left join fetch s.studentCourses where s.id =:id ", Student.class)
                    .setParameter("id", studentId).getSingleResult();
            tr.commit();
        }catch(Exception e){
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return Optional.empty();
        }
        return Optional.of(student);


    }

    @Override
    public boolean deleteById(String id) {
        return super.deleteById(id);
    }

    @Override
    public boolean delete(Student student) {
        return super.delete(student);
    }
}
