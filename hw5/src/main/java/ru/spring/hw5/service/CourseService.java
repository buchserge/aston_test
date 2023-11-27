package ru.spring.hw5.service;

import ru.spring.hw5.model.Course;
import ru.spring.hw5.model.Student;

import java.util.List;
import java.util.Set;

public interface CourseService {

    void deleteCourseById(String courseId);

    void addCourse(Course course);

    Course getById(String id);

    List<Course> fetchAllWithProfessor();

    Set<Student> fetchAllByCourseId(String courseId);
}
