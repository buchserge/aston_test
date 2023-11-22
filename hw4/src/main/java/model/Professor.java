package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
