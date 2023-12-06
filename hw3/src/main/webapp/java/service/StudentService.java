package service;

import dao.StudentDao;
import model.Course;
import model.Professor;
import model.Student;
import service.serviceInterface.StudentServiceInterface;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentService implements StudentServiceInterface {
    private final StudentDao studentDao;

    public StudentService() {

        this.studentDao = new StudentDao();
    }

    @Override
    public Student getStudentWithCoursesByID(String studentId) throws SQLException {
        Optional<Student> student = studentDao.getStudentById(studentId);
        List<Course> studentCourses = studentDao.getStudentCourses(studentId);
        student.ifPresent(stu->stu.setStudentCourses(studentCourses));

        return student.orElseThrow();
    }

    @Override
    public boolean registerStudentForCourse(String courseId,String studentName) throws SQLException {
      return   studentDao.registerStudentForCourse(courseId,studentName);
    }
}
