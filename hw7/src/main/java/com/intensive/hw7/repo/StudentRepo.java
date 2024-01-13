package com.intensive.hw7.repo;

import com.intensive.hw7.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, String> {

    @Query("select s from Student s left  join fetch s.studentCourses where s.id =:studentId ")
    Optional<Student> getStudentWithCourses(@Param("studentId") String studentId);


}
