package com.intensive.hw7.service.unit;

import com.intensive.hw7.exception.NotFoundException;
import com.intensive.hw7.model.Course;
import com.intensive.hw7.model.Professor;
import com.intensive.hw7.model.Student;
import com.intensive.hw7.repo.CourseRepo;
import com.intensive.hw7.repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CourseServiceImplTest {
    @InjectMocks
    CourseServiceImpl courseService;
    @Mock
    private CourseRepo courseRepo;
    @Mock
    private StudentRepo studentRepo;

    @Test
    void addCourse() {
        Course course = new Course();
        course.setTitle("test");
        String id = UUID.randomUUID().toString();
        course.setId(id);
        when(courseRepo.save(course)).thenReturn(course);
        courseService.addCourse(course);
        verify(courseRepo).save(any(Course.class));
    }

    @Test
    void deleteCourseById() {

        Course course = new Course();
        course.setTitle("test");
        String someID = "someid";
        course.setId(someID);
        Student student = new Student();
        student.setId("111");
        student.setName("Johny");
        course.setStudents(Set.of(student));
        student.setStudentCourses(Set.of(course));
        when(courseRepo.findById(someID)).thenReturn(Optional.of(course));

        courseService.deleteCourseById(someID);

        verify(studentRepo, times(1)).delete(student);
        verify(courseRepo, times(1)).delete(course);
    }

    @Test
    void getById() {
        Course course = new Course();
        String someID = "someId";
        course.setTitle("test");
        course.setId(someID);
        doReturn(Optional.of(course)).when(courseRepo).getCourseWithProfessor(someID);
        courseService.getById(someID);
        verify(courseRepo, times(1)).getCourseWithProfessor(someID);

    }

    @Test
    void getByIDShouldThrowNotFoundException() {
        String someID = "someId";
        assertThrows(NotFoundException.class, () -> courseService.getById(someID));

    }

    @Test
    void fetchAllWithProfessor() {
        Professor professor = new Professor();
        professor.setId("123");
        professor.setName("Heorgey");
        Course course = new Course();
        String someID = "someId2";
        course.setTitle("test2");
        course.setId(someID);
        course.setProfessor(professor);
        Course course2 = new Course();
        String someID2 = "someId2";
        course2.setTitle("test2");
        course2.setId(someID2);
        course2.setProfessor(professor);
        List<Course> list = new ArrayList<>();
        list.add(course);
        list.add(course2);
        doReturn(list).when(courseRepo).findAllWithProfessor();
        List<Course> courses = courseService.fetchAllWithProfessor();
        assertNotNull(courses);
        verify(courseRepo, times(1)).findAllWithProfessor();

    }

    @Test
    void fetchAllByCourseId() {
        Student student = new Student();
        student.setId("111");
        student.setName("Abby");
        Student student2 = new Student();
        student2.setId("111");
        student2.setName("Johny");
        Set<Student> set = new HashSet<>();
        set.add(student);
        set.add(student2);
        Course course = new Course();
        String someID = "someId";
        course.setTitle("test2");
        course.setId(someID);
        course.setStudents(set);

        doReturn(Optional.of(course)).when(courseRepo).getCourseWithStudents(someID);
        Set<Student> students = courseService.fetchAllByCourseId(someID);
        assertNotNull(students);
        verify(courseRepo, times(1)).getCourseWithStudents(someID);
    }

    @Test
    void fetchAllByCourseIdShouldThrowNotFoundException() {
        String someID = "someId";
        assertThrows(NotFoundException.class, () -> courseService.fetchAllByCourseId(someID));

    }

}