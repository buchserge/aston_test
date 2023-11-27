package dao;

import dao.daoInterface.CourseDaoInterface;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseDao implements CourseDaoInterface {
    @Override
    public boolean deleteCourseById(String courseId) throws SQLException {
        int res = 0;
        try (
                Connection connection = DBUtil.getConnection()) {
            connection.setAutoCommit(false);

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement("delete from course_student where course_id=?")
            ) {
                preparedStatement.setInt(1, Integer.parseInt(courseId));
                preparedStatement.executeUpdate();

                PreparedStatement preparedStatement2 = connection.prepareStatement("delete from course where id_course=?");

                preparedStatement2.setInt(1, Integer.parseInt(courseId));
                res = preparedStatement2.executeUpdate();
                connection.commit();
                return res == 1;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

