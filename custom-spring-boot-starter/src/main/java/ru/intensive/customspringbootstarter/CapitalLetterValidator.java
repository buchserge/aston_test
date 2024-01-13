package ru.intensive.customspringbootstarter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CapitalLetterValidator implements ConstraintValidator<MustStartWithCapitalLetter, String> {


    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (Character.isUpperCase(name.charAt(0))) return true;
        else throw new IllegalFirstLetterException();
    }
}
