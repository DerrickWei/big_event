package com.derrick.big_event.anno;

import com.derrick.big_event.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {StateValidation.class})
public @interface State {

    // Error message on failed validation
    String message() default "state can only be draft or submitted";

    // Provide group
    Class<?>[] groups() default {};

    // Additional information
    Class<? extends Payload>[] payload() default {};
}
