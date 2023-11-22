package dao.inter;

import model.Student;

import java.util.Optional;

public interface StudentDao {

    Optional<Student> getById(String id);
}
