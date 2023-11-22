package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends AbstractEntity {

    private String name;

    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
