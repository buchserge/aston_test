package ru.spring.hw6.service;

import ru.spring.hw6.model.Professor;

public interface ProfessorService {

    Professor getProfessorWithCourses(String professorId);

    void addProfessor(Professor professor);

    void registerProfessorForCourse(String courseId, String studentId);
}
