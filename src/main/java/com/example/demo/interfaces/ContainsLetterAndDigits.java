package com.example.demo.interfaces;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ContainsLetterAndDigitsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContainsLetterAndDigits {
    String message() default "Must contain at least one letter and at least one digit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
