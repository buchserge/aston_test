package ru.spring.hw6.service.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.hw6.exception.NotFoundException;
import ru.spring.hw6.model.Course;
import ru.spring.hw6.model.Student;
import ru.spring.hw6.repo.CourseRepo;
import ru.spring.hw6.repo.StudentRepo;
import ru.spring.hw6.service.StudentService;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    @Override
    public Student getStudentWithCoursesByID(String studentId) {
        Optional<Student> student = studentRepo.getStudentWithCourses(studentId);
        return student.orElseThrow(NotFoundException::new);
    }

    @Transactional
    @Override
    public void registerStudentForCourse(String courseId, String studentId) {

        Optional<Course> course = courseRepo.getCourseWithStudents(courseId);
        Optional<Student> student = studentRepo.getStudentWithCourses(studentId);
        student.ifPresent(stu -> stu.addCourse(course.orElseThrow(NotFoundException::new)));
        studentRepo.save(student.orElseThrow(NotFoundException::new));

    }

    @Transactional
    @Override
    public void removeStudentFromCourse(String studentId, String courseId) {

        Optional<Student> student = studentRepo.getStudentWithCourses(studentId);
        Set<Course> studentCourses = student.orElseThrow().getStudentCourses();
        Course course = studentCourses.stream().filter(c -> c.getId().equals(courseId)).findFirst().orElseThrow(NotFoundException::new);
        student.ifPresent(s -> s.getStudentCourses().remove(course));
        studentRepo.save(student.orElseThrow(NotFoundException::new));
    }

    @Override
    public void addStudent(Student student) {
        studentRepo.save(student);
    }

    @Override
    public void deleteStudent(String studentId) {
        studentRepo.deleteById(studentId);
    }

}
