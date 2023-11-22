package service;

import dao.CourseDaoImpl;
import model.Course;

import java.sql.SQLException;
import java.util.List;


public class CourseService {
    private final CourseDaoImpl courseDao;

    public CourseService() {
        this.courseDao = new CourseDaoImpl(Course.class);

    }

    public boolean deleteCourseById(String courseId) throws SQLException {
        return courseDao.deleteById(courseId);

    }

    public boolean addCourse(Course course)  {
        return courseDao.save(course);

    }


}
