package com.intensive.hw7.service;



import com.intensive.hw7.model.Course;
import com.intensive.hw7.model.Student;

import java.util.List;
import java.util.Set;

public interface CourseService {

    void deleteCourseById(String courseId);

    void addCourse(Course course);

    Course getById(String id);

    List<Course> fetchAllWithProfessor();

    Set<Student> fetchAllByCourseId(String courseId);
}
