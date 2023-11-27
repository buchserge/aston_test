package dao;

import dao.daoInterface.StudentDaoInterface;
import model.Course;
import model.Professor;
import model.Student;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDao implements StudentDaoInterface {

    @Override
    public Optional<Student> getStudentById(String studentId) {

        Student student = new Student();
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from student s where s.id=?")
        ) {
            preparedStatement.setInt(1, Integer.parseInt(studentId));
            try (
                    ResultSet rs = preparedStatement.executeQuery();
            ) {
                while (rs.next()) {
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(student);
    }
    @Override
    public List<Course> getStudentCourses(String studentId) throws SQLException {
        List<Course> studentCourses = new ArrayList<>();
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from course c left join course_student cs on c.id_course=?");
        ) {
            preparedStatement.setInt(1, Integer.parseInt(studentId));
            try (
                    ResultSet rs = preparedStatement.executeQuery();
            ) {
                while (rs.next()) {

                    Course course = new Course();
                    course.setId(rs.getInt("id_course"));
                    course.setTitle(rs.getString("title"));
                    studentCourses.add(course);

                }

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return studentCourses;
    }
    @Override
    public boolean registerStudentForCourse(String courseId, String studentName) throws SQLException {

        int studentId = 0;
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select id from student s where s.name=?");
                PreparedStatement preparedStatement2 = connection.
                        prepareStatement("insert into course_student (course_id, student_id) values(?,?)")
        ) {
            preparedStatement.setString(1, studentName);
            try (
                    ResultSet rs = preparedStatement.executeQuery();
            ) {
                if (rs.next()) {
                    studentId = rs.getInt("id");
                }
            }
            preparedStatement2.setInt(1, Integer.parseInt(courseId));
            if (studentId != 0) {
                preparedStatement2.setInt(2, studentId);
            }
            return preparedStatement2.executeUpdate()==1;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}