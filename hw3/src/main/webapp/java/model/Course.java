package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;


@Getter
@Setter
@ToString
public class Course  {
    private int id;
    private String title;

    private Professor professor;

}
