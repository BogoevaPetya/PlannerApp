package com.plannerapp.vallidation;

import com.plannerapp.model.annotations.StringDateInFuture;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.time.LocalDate;

public class StringDateInFutureValidator implements ConstraintValidator<StringDateInFuture, String> {

    @Override
    public void initialize(StringDateInFuture constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null && !value.isBlank()){
            LocalDate parsed = LocalDate.parse(value);
            return parsed.isAfter(LocalDate.now());
        }

        return false;
    }
}
