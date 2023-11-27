package service.serviceInterface;

import model.Professor;
import model.Student;

import java.sql.SQLException;
import java.util.Optional;

public interface StudentServiceInterface {

    Student getStudentWithCoursesByID(String studentId)throws SQLException;
    boolean registerStudentForCourse(String courseId,String studentName)throws SQLException;
}
