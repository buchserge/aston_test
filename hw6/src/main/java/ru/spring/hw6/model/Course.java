package ru.spring.hw6.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spring.hw6.util.CourseViews;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(indexes = {@Index(columnList = "testcost", name = "cost_index")})
public class Course extends AbstractEntity {

    @JsonView(CourseViews.Normal.class)
    private String title;

    @JsonView(CourseViews.Custom.class)
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;
    @JsonView(CourseViews.Normal.class)
    @JsonBackReference
    @ManyToMany(mappedBy = "studentCourses")
    private Set<Student> students = new HashSet<>();

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
        Course course = (Course) obj;
        return super.getId() != null && super.getId().equals(course.getId());
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                '}';
    }
}