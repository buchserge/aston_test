package ru.spring.hw5.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.spring.hw5.util.CourseViews;
import ru.spring.hw5.util.StringSequenceIdGenerator;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class AbstractEntity {
    @JsonView(CourseViews.Normal.class)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_id_seq")
    @GenericGenerator(
            name = "acc_id_seq",
            type = StringSequenceIdGenerator.class,
            parameters = {
                    @Parameter(name = StringSequenceIdGenerator.INCREMENT_PARAM, value = "10"),
                    @Parameter(name = StringSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "U_"),
                    @Parameter(name = StringSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
    private String id;
    @JsonView(CourseViews.Custom.class)
    private String universityName;
}
