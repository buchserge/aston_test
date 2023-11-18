package service;

import dao.ProfessorDao;
import model.Professor;
import service.serviceInterface.ProfessorServiceInterface;

import java.sql.SQLException;
import java.util.Optional;

public class ProfessorService implements ProfessorServiceInterface {
    private final ProfessorDao professorDao;

    public ProfessorService() {
        this.professorDao = new ProfessorDao();
    }

    @Override
    public Professor getProfessorWithCourses(String professorId) throws SQLException {
        Optional<Professor> professor = professorDao.fetchProfessorWithCourses(professorId);
       return  professor.orElseThrow();

    }
}
