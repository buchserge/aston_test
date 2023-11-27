package ru.spring.hw6.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spring.hw6.exception.NotFoundException;
import ru.spring.hw6.model.Course;
import ru.spring.hw6.model.Professor;
import ru.spring.hw6.repo.CourseRepo;
import ru.spring.hw6.repo.ProfessorRepo;
import ru.spring.hw6.service.ProfessorService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepo professorRepo;
    private final CourseRepo courseRepo;

    @Override
    public Professor getProfessorWithCourses(String professorId) {
        return professorRepo.getProfessorWithCourses(professorId).orElseThrow(NotFoundException::new);
    }
    @Override
    public void addProfessor(Professor professor) {
        professorRepo.save(professor);
    }

    @Transactional
    @Override
    public void registerProfessorForCourse(String courseId, String studentId) {
        Optional<Course> course = courseRepo.findById(courseId);
        Optional<Professor> professor = professorRepo.findById(studentId);
        course.ifPresent(c -> c.setProfessor(professor.orElseThrow(NotFoundException::new)));
        professor.ifPresent(p -> p.addCourse(course.orElseThrow(NotFoundException::new)));
        professorRepo.save(professor.orElseThrow(NotFoundException::new));

    }
}