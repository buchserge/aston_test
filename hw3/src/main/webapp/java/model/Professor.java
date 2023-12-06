package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class Professor {
    private int id;
    private String name;
    private List<Course> professorCourses;

}
