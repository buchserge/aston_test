package dao.daoInterface;

import java.sql.SQLException;

public interface CourseDaoInterface {
    boolean deleteCourseById(String courseId)throws SQLException;
}
