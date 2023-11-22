package service;

import dao.CourseDaoImpl;
import dao.StudentDaoImpl;
import model.Course;
import model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class StudentService {
    private final StudentDaoImpl studentDao;
    private final CourseDaoImpl courseDao;

    public StudentService() {
        this.courseDao = new CourseDaoImpl(Course.class);
        this.studentDao = new StudentDaoImpl(Student.class);
    }


    public Student getStudentWithCoursesByID(String studentId) throws SQLException {
        Optional<Student> student = studentDao.getById(studentId);
        return student.orElseThrow();
    }


    public boolean registerStudentForCourse(String courseId, String studentId) throws SQLException {
        Optional<Course> course = courseDao.getById(courseId);
        Optional<Student> student = studentDao.getById(studentId);
        student.ifPresent(stu -> stu.addCourse(course.orElseThrow()));
        return studentDao.update(student.orElseThrow());

    }
// Контекст : данный метод ранее был реализован через обращение к StudentDao и CourseDao в рамках двух транзакций
// LazyInitializationException  происходит, если попытаться пройтись по коллекции студентов через стрим
// Решение - создание одной транзакции в рамках метода removeStudentFromCourse()
    public boolean removeStudentFromCourse(String studentId, String courseId) {
        HibernateUtil.init();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Student student;
        try (session) {
            Transaction tr = session.beginTransaction();
            student = session.createQuery("select s from Student s left join fetch s.studentCourses where s.id =:id ", Student.class)
                    .setParameter("id", studentId).getSingleResult();
            Set<Course> studentCourses = student.getStudentCourses();
            Course course = studentCourses.stream().filter(c -> c.getId().equals(courseId)).findFirst().orElseThrow();
            student.removeCourse(course);
            session.merge(student);
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
                return false;
            }

        }
        return  true;
    }

    public boolean addStudent(Student student) {
       return studentDao.save(student);
    }

    public boolean deleteStudent(String studentId) {
        return studentDao.deleteById(studentId);
    }
}
