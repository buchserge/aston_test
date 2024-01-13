package com.intensive.hw7.repo;

import com.intensive.hw7.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course, String> {

    @Query("select c from Course c  left join fetch c.students where c.id =:courseId  ")
    Optional<Course> getCourseWithStudents(@Param("courseId") String courseID);

    @Query("select c from Course c  left join fetch c.professor where c.id=:courseId ")
    Optional<Course> getCourseWithProfessor(@Param("courseId") String courseID);

    @Query("select c from Course c  left join fetch c.professor")
    List<Course> findAllWithProfessor();
    List<Course> findAllByUniversityName(String name);
}
