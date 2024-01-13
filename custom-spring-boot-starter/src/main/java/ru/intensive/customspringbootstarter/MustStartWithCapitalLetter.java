package ru.intensive.customspringbootstarter;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Target({TYPE, FIELD, PARAMETER, RECORD_COMPONENT})
@Constraint(validatedBy = CapitalLetterValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MustStartWithCapitalLetter {
    String message() default "Must start with capital letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}