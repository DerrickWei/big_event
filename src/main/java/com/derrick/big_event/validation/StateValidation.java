package com.derrick.big_event.validation;

import com.derrick.big_event.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     * Define validation rule
     *
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.equals("草稿") || value.equals("已发布");
        }

        return false;
    }
}
