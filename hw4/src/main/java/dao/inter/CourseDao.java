package dao.inter;

import model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    Optional<Course> getById(String id);
    List<Course> getAllCourses();
    boolean deleteAll();
}
