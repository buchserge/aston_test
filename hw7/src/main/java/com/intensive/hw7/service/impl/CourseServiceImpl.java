package com.intensive.hw7.service.unit;

import com.intensive.hw7.exception.NotFoundException;
import com.intensive.hw7.model.Course;
import com.intensive.hw7.model.Student;
import com.intensive.hw7.repo.CourseRepo;
import com.intensive.hw7.repo.StudentRepo;
import com.intensive.hw7.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl  implements CourseService {

    private final CourseRepo courseRepo;
    private final StudentRepo studentRepo;


    @Override
    public void deleteCourseById(String courseId) {
       Course course= courseRepo.findById(courseId).orElseThrow(NotFoundException::new);
        for (Student s : course.getStudents()) {
            if (s.getStudentCourses().size() == 1) {
                studentRepo.delete(s);
            } else {
                s.getStudentCourses().remove(course);
            }
        }
        courseRepo.delete(course);
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
