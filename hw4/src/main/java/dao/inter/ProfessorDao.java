package dao.inter;

import model.Professor;

import java.util.Optional;

public interface ProfessorDao {

    Optional<Professor> getById(String id);
}
