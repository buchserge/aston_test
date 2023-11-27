package ru.spring.hw6.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Professor extends AbstractEntity {

    private String name;
    @JsonBackReference
    @OneToMany(mappedBy = "professor", orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Course> professorCourses=new ArrayList<>();


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