package com.intensive.hw7.service.unit;

import com.intensive.hw7.model.Course;
import com.intensive.hw7.model.Professor;
import com.intensive.hw7.repo.CourseRepo;
import com.intensive.hw7.repo.ProfessorRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class ProfessorServiceImplTest {
    @InjectMocks
    ProfessorServiceImpl professorService;
    @Mock
    private CourseRepo courseRepo;
    @Mock
    private ProfessorRepo professorRepo;

    @Test
    void getProfessorWithCourses() {
        Course course = new Course();
        String someID = "someId2";
        course.setTitle("test2");
        course.setId(someID);
        Course course2 = new Course();
        String someID2 = "someId2";
        course2.setTitle("test2");
        course2.setId(someID2);
        Professor professor = new Professor();
        professor.setName("test");
        String professorId = UUID.randomUUID().toString();
        professor.setId(professorId);
        List<Course> list = new ArrayList<>();
        list.add(course);
        list.add(course2);
        professor.setProfessorCourses(list);

        doReturn(Optional.of(professor)).when(professorRepo).getProfessorWithCourses(professorId);
        Professor professorResult = professorService.getProfessorWithCourses(professorId);
        assertNotNull(professorResult);
        verify(professorRepo, times(1)).getProfessorWithCourses(professorId);
    }

    @Test
    void addProfessor() {
        Professor professor = new Professor();
        professor.setName("test");
        String id = UUID.randomUUID().toString();
        professor.setId(id);

        when(professorRepo.save(professor)).thenReturn(professor);
        professorService.addProfessor(professor);
        verify(professorRepo).save(any(Professor.class));
    }

    @Test
    void registerProfessorForCourse() {
        Professor professor = new Professor();
        professor.setName("test");
        String professorId = UUID.randomUUID().toString();
        professor.setId(professorId);


        Course course = new Course();
        String courseId = "someId";
        course.setTitle("test2");
        course.setId(courseId);
        course.setProfessor(professor);

        doReturn(Optional.of(course)).when(courseRepo).findById(courseId);
        when(professorRepo.findById(professorId)).thenReturn(Optional.of(professor));

        professorService.registerProfessorForCourse(courseId, professorId);

        assertTrue(professor.getProfessorCourses().contains(course));
    }
}