package com.intensive.hw7.repo;

import com.intensive.hw7.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepo extends JpaRepository<Professor, String> {

    @Query("select p from Professor p left join fetch p.professorCourses where p.id =:id ")
    Optional<Professor> getProfessorWithCourses(@Param("id") String id);
}
