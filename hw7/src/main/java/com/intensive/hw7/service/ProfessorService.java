package com.intensive.hw7.service;

import com.intensive.hw7.model.Professor;


public interface ProfessorService {

    Professor getProfessorWithCourses(String professorId);

    void addProfessor(Professor professor);

    void registerProfessorForCourse(String courseId, String professorId);
}
