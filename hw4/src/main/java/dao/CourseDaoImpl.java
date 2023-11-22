package dao;

import dao.inter.CourseDao;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class CourseDaoImpl extends GenericDaoImpl<Course, String> implements CourseDao {
    public CourseDaoImpl(Class<Course> type) {
        super(type);
    }

    // n+1 solved using left join
    @Override
    public List<Course> getAllCourses() {
        HibernateUtil.init();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Course> courses;
        try (session) {
            Transaction tr = session.beginTransaction();
            courses = session.createQuery("select c from  Course  c left join fetch c.professor", Course.class)
                    .getResultList();
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return null;
        }
        return courses;
    }


    @Override
    public Set<Course> getAll() {
        return super.getAll();
    }
    @Override
    public Optional<Course> getById(String id) {
        HibernateUtil.init();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Course course;
        try (session) {

            Transaction tr = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Course> q = cb.createQuery(Course.class);
            Root<Course> o = q.from(Course.class);
            o.fetch("students", JoinType.LEFT);
            q.select(o);
            q.where(cb.equal(o.get("id"), id));
            course =  session.createQuery(q).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return Optional.empty();
        }
        return Optional.ofNullable(course);
    }

    @Override
    public boolean delete(Course course) {
        return super.delete(course);
    }

    @Override
    public boolean deleteAll() {
        HibernateUtil.init();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try (session) {
            Transaction tr = session.beginTransaction();
            session.createMutationQuery("DELETE FROM Course").executeUpdate();
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return false;
        }
        return true;
    }


// EXTRA HOME TASK
//    public List<Course> getAllCoursesTestCost() {
//        HibernateUtil.init();
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        List<Course> courses;
//        try (session) {
//
//            Transaction tr = session.beginTransaction();
//            courses = session.createQuery("Select c FROM Course c where c.testCost > 100000 and  c.universityName='oxford'", Course.class).getResultList();
//            tr.commit();
//        } catch (Exception e) {
//            if (session.getTransaction().getStatus().canRollback()) {
//                session.getTransaction().rollback();
//            }
//            return null;
//        }
//
//        return courses;
//    }
//
//    public String generateRandomString() {
//        int leftLimit = 97;
//        int rightLimit = 122;
//        int targetStringLength = 10;
//        Random random = new Random();
//        StringBuilder buffer = new StringBuilder(targetStringLength);
//        for (int i = 0; i < targetStringLength; i++) {
//            int randomLimitedInt = leftLimit + (int)
//                    (random.nextFloat() * (rightLimit - leftLimit + 1));
//            buffer.append((char) randomLimitedInt);
//        }
//        return buffer.toString();
//    }
}
