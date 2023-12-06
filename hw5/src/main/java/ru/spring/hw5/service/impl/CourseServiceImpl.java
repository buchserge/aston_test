package ru.spring.hw5.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.hw5.model.Course;
import ru.spring.hw5.model.Student;
import ru.spring.hw5.repo.CourseRepo;
import ru.spring.hw5.service.CourseService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;

    @Override
    public void deleteCourseById(String courseId) {
        courseRepo.deleteById(courseId);
    }
    @Override
    public void addCourse(Course course) {
        courseRepo.save(course);
    }

    @Override
    public Course getById(String id) {
        return courseRepo.getCourseWithProfessor(id).orElseThrow();
    }

    @Override
    public List<Course> fetchAllWithProfessor() {
        return courseRepo.findAllWithProfessor();
    }

    @Override
    public Set<Student> fetchAllByCourseId(String courseId) {
        return courseRepo.getCourseWithStudents(courseId).orElseThrow().getStudents();
    }
}
