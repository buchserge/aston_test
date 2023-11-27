package dao.daoInterface;

import model.Course;
import model.Professor;
import model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StudentDaoInterface {
    Optional<Student> getStudentById(String studentId);
    List<Course> getStudentCourses(String studentId)throws SQLException;
    boolean registerStudentForCourse(String courseId, String studentName)throws SQLException;
}
