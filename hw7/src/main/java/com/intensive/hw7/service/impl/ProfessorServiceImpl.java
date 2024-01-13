package com.intensive.hw7.service.unit;

import com.intensive.hw7.exception.NotFoundException;
import com.intensive.hw7.model.Course;
import com.intensive.hw7.model.Professor;
import com.intensive.hw7.repo.CourseRepo;
import com.intensive.hw7.repo.ProfessorRepo;
import com.intensive.hw7.service.ProfessorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
    public void registerProfessorForCourse(String courseId, String professorId) {
        Optional<Course> course = courseRepo.findById(courseId);
        Optional<Professor> professor = professorRepo.findById(professorId);
        course.ifPresent(c -> c.setProfessor(professor.orElseThrow(NotFoundException::new)));
        professor.ifPresent(p -> p.addCourse(course.orElseThrow(NotFoundException::new)));
        professorRepo.save(professor.orElseThrow(NotFoundException::new));

    }
}