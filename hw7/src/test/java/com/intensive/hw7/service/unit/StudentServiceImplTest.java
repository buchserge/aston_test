package com.intensive.hw7.service.unit;

import com.intensive.hw7.exception.NotFoundException;
import com.intensive.hw7.model.Course;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    StudentServiceImpl studentService;
    @Mock
    private CourseRepo courseRepo;
    @Mock
    private StudentRepo studentRepo;


    @Test
    void getStudentWithCoursesByID() {

        Course course = new Course();
        String someID = "someId2";
        course.setTitle("test2");
        course.setId(someID);
        Course course2 = new Course();
        String someID2 = "someId2";
        course2.setTitle("test2");
        course2.setId(someID2);
        Student student = new Student();
        String studentId="111";
        student.setId(studentId);
        student.setName("Abby");
        Set<Course> set = new HashSet<>();
        set.add(course);
        set.add(course2);
        student.setStudentCourses(set);

        doReturn(Optional.of(student)).when(studentRepo).getStudentWithCourses(studentId);
        Student studentResult = studentService.getStudentWithCoursesByID(studentId);
        assertNotNull(studentResult);
        verify(studentRepo,times(1)).getStudentWithCourses(studentId);
    }

    @Test
    void getStudentWithCoursesByIDShouldThrowNotFoundException() {
        String someID = "someId";

        assertThrows(NotFoundException.class, () -> studentService.getStudentWithCoursesByID(someID));
    }
    @Test
    void registerStudentForCourse() {
        Student student = new Student();
        student.setName("test");
        String studentId = UUID.randomUUID().toString();
        student.setId(studentId);


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
        when(studentRepo.getStudentWithCourses(studentId)).thenReturn(Optional.of(student));

        studentService.registerStudentForCourse(someID,studentId);

        assertTrue(student.getStudentCourses().contains(course));
    }

    @Test
    void registerStudentForCourseShouldThrowNotFoundException() {
        String someID = "someId";
        String someID2 = "someId2";

        assertThrows(NotFoundException.class, () -> studentService.registerStudentForCourse(someID,someID2));
    }
    @Test
    void removeStudentFromCourse() {

        Course course = new Course();
        String someID = "someId";
        course.setTitle("test2");
        course.setId(someID);

        Set<Course> set = new HashSet<>();
        set.add(course);

        Student student = new Student();
        student.setName("test");
        String studentId = UUID.randomUUID().toString();
        student.setId(studentId);
        student.setStudentCourses(set);

        when(studentRepo.getStudentWithCourses(studentId)).thenReturn(Optional.of(student));
        studentService.removeStudentFromCourse(studentId,someID);

        assertFalse(student.getStudentCourses().contains(course));
    }

    @Test
    void addStudent() {
        Student student = new Student();
        student.setName("test");
        String id = UUID.randomUUID().toString();
        student.setId(id);

        when(studentRepo.save(student)).thenReturn(student);
        studentService.addStudent(student);
        verify(studentRepo).save(any(Student.class));
    }

    @Test
    void deleteStudent() {
        String someID = "someId";

        doNothing().when(studentRepo).deleteById(someID);
        studentService.deleteStudent(someID);
        verify(studentRepo, times(1)).deleteById(someID);
    }
}