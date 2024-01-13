package com.intensive.hw7.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.intensive.hw7.util.CourseViews;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.intensive.customspringbootstarter.MustStartWithCapitalLetter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Professor extends AbstractEntity {

    @JsonView(CourseViews.StudentAndProfessorView.class)
    @MustStartWithCapitalLetter
    private String name;

    @JsonView(CourseViews.StudentAndProfessorView.class)
    @OneToMany(mappedBy = "professor", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Course> professorCourses = new ArrayList<>();


    @Override
    public String toString() {
        return "Professor{" +
                "name='" + name + '\'' +
                '}';
    }
    public void addCourse(Course course) {
        professorCourses.add(course);
        course.setProfessor(this);
    }
}