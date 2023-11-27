package ru.spring.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.hw6.exception.NotFoundException;
import ru.spring.hw6.model.Course;
import ru.spring.hw6.model.Student;
import ru.spring.hw6.repo.CourseRepo;
import ru.spring.hw6.service.CourseService;

import java.util.List;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class CourseServiceImpl  implements CourseService {

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
        return courseRepo.getCourseWithProfessor(id).orElseThrow(NotFoundException::new);
    }
    @Override
    public List<Course> fetchAllWithProfessor() {
        return courseRepo.findAllWithProfessor();
    }

    @Override
    public Set<Student> fetchAllByCourseId(String courseId) {
        return courseRepo.getCourseWithStudents(courseId).orElseThrow(NotFoundException::new).getStudents();
    }
}
