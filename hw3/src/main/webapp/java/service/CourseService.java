package service;

import dao.CourseDao;
import dao.ProfessorDao;
import service.serviceInterface.CourseServiceInterface;

import java.sql.SQLException;


public class CourseService implements CourseServiceInterface {
    private final CourseDao courseDao;
    private final ProfessorDao professorDao;

    public CourseService() {
        this.courseDao = new CourseDao();
        this.professorDao = new ProfessorDao();
    }

    @Override
    public boolean deleteCourseById(String courseId) throws SQLException {
       return courseDao.deleteCourseById(courseId);

    }
}
