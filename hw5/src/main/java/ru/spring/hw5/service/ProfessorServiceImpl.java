package ru.spring.hw5.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.hw5.model.Course;
import ru.spring.hw5.model.Professor;
import ru.spring.hw5.repo.CourseRepo;
import ru.spring.hw5.repo.ProfessorRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl {

    private final ProfessorRepo professorRepo;
    private final CourseRepo courseRepo;


    public Professor getProfessorWithCourses(String professorId) {

        return professorRepo.getProfessorWithCourses(professorId).orElseThrow();
    }

    public void addProfessor(Professor professor) {
        professorRepo.save(professor);
    }

    @Transactional
    public void registerProfessorForCourse(String courseId, String studentId) {
        Optional<Course> course = courseRepo.findById(courseId);
        Optional<Professor> professor = professorRepo.findById(studentId);
        course.ifPresent(c -> c.setProfessor(professor.orElseThrow()));
        professor.ifPresent(p -> p.addCourse(course.orElseThrow()));
        professorRepo.save(professor.orElseThrow());

    }
}
