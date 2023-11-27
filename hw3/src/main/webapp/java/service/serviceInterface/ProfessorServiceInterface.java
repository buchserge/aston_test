package service.serviceInterface;

import model.Professor;

import java.sql.SQLException;
import java.util.Optional;

public interface ProfessorServiceInterface {
    Professor getProfessorWithCourses(String professorId)throws SQLException;
}
