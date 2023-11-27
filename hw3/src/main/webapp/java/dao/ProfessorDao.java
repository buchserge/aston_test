package dao;

import dao.daoInterface.ProfessorDaoInterface;
import model.Course;
import model.Professor;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorDao implements ProfessorDaoInterface {

    @Override
    public   Optional<Professor>  getProfessorByCourseId(String courseId) {
        Professor professor = new Professor();
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.
                        prepareStatement("SELECT * from professor  WHERE course_id = ?")
        ) {
            preparedStatement.setInt(1, Integer.parseInt(courseId));
            try (
                    ResultSet rs = preparedStatement.executeQuery();
            ) {
                while (rs.next()) {
                    professor.setId(rs.getInt("id"));
                    professor.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(professor);
    }
    @Override
    public Optional<Professor> fetchProfessorWithCourses(String professorId) throws SQLException {

        List<Course> professorCourses = new ArrayList<>();
        Professor professor = new Professor();
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.
                        prepareStatement("select * from professor left join course on course.professor_id = professor.id where professor.id=?")
        ) {

            preparedStatement.setInt(1, Integer.parseInt(professorId));
            try (
                    ResultSet rs = preparedStatement.executeQuery();
            ) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setId(rs.getInt("id_course"));
                    course.setTitle(rs.getString("title"));
                    professor.setId(rs.getInt("professor_id"));
                    professor.setName(rs.getString("name"));
                    professorCourses.add(course);
                    professor.setProfessorCourses(professorCourses);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        professor.setProfessorCourses(professorCourses);
        return Optional.ofNullable(professor);
    }


}