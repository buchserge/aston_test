package ru.spring.hw5.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.hw5.model.Course;
import ru.spring.hw5.model.Student;
import ru.spring.hw5.repo.CourseRepo;
import ru.spring.hw5.repo.StudentRepo;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class StudentServiceImpl {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;


    public Student getStudentWithCoursesByID(String studentId) {
        Optional<Student> student = studentRepo.getStudentWithCourses(studentId);
        return student.orElseThrow();
    }

    @Transactional
    public void registerStudentForCourse(String courseId, String studentId) {

        Optional<Course> course = courseRepo.getCourseWithStudents(courseId);
        Optional<Student> student = studentRepo.getStudentWithCourses(studentId);
        student.ifPresent(stu -> stu.addCourse(course.orElseThrow()));
        studentRepo.save(student.orElseThrow());

    }

    @Transactional
    public void removeStudentFromCourse(String studentId, String courseId) {

        Optional<Student> student = studentRepo.getStudentWithCourses(studentId);
        Set<Course> studentCourses = student.orElseThrow().getStudentCourses();
        Course course = studentCourses.stream().filter(c -> c.getId().equals(courseId)).findFirst().orElseThrow();
        student.ifPresent(s -> s.getStudentCourses().remove(course));
        studentRepo.save(student.orElseThrow());
    }


    public void addStudent(Student student) {
        studentRepo.save(student);
    }

    public void deleteStudent(String studentId) {
        studentRepo.deleteById(studentId);
    }

}
