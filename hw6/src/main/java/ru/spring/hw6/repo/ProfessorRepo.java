package ru.spring.hw6.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.spring.hw6.model.Professor;

import java.util.Optional;

@Repository
public interface ProfessorRepo extends JpaRepository<Professor, String> {

    @Query("select p from Professor p left join fetch p.professorCourses where p.id =:id ")
    Optional<Professor> getProfessorWithCourses(@Param("id") String id);
}
