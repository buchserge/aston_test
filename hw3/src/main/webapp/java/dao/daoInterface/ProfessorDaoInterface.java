package dao.daoInterface;

import model.Professor;

import java.sql.SQLException;
import java.util.Optional;

public interface ProfessorDaoInterface {
    Optional<Professor> getProfessorByCourseId(String courseId);
    Optional<Professor>  fetchProfessorWithCourses(String professorId)throws SQLException;
}
