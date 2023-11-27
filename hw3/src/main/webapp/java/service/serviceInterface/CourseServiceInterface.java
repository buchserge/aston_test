package service.serviceInterface;

import java.sql.SQLException;

public interface CourseServiceInterface {
    boolean deleteCourseById(String courseId)throws SQLException;
}
