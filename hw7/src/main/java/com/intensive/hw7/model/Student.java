package com.intensive.hw7.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.intensive.hw7.util.CourseViews;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends AbstractEntity {

    @JsonView(CourseViews.StudentAndProfessorView.class)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonView(CourseViews.StudentAndProfessorView.class)
    @JoinTable(name = "course_student", joinColumns = {@JoinColumn(name = "student_id")}, inverseJoinColumns = {@JoinColumn(name = "course_id")})
    private Set<Course> studentCourses = new HashSet<>();

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student student = (Student) obj;
        return super.getId() != null && super.getId().equals(student.getId());
    }
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
    public void removeCourse(Course course) {
        studentCourses.remove(course);
        course.getStudents().remove(this);
    }

    public void addCourse(Course course) {
        studentCourses.add(course);
        course.getStudents().add(this);
    }

}