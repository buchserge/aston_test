package ru.spring.hw5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.hw5.model.Course;
import ru.spring.hw5.model.Student;
import ru.spring.hw5.repo.CourseRepo;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl {

    private final CourseRepo courseRepo;


    public void deleteCourseById(String courseId) {
        courseRepo.deleteById(courseId);

    }

    public void addCourse(Course course) {
        courseRepo.save(course);

    }

//    @Transactional
    public Course getById(String id) {
        return courseRepo.getCourseWithProfessor(id).orElseThrow();
    }

    public List<Course> fetchAllWithProfessor() {
        return courseRepo.findAllWithProfessor();
    }

    public Set<Student> fetchAllByCourseId(String courseId) {
        return courseRepo.getCourseWithStudents(courseId).orElseThrow().getStudents();
    }
}
