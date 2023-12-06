package ru.spring.hw5.service;

import ru.spring.hw5.model.Professor;

public interface ProfessorService {

    Professor getProfessorWithCourses(String professorId);

    void addProfessor(Professor professor);

    void registerProfessorForCourse(String courseId, String studentId);
}
