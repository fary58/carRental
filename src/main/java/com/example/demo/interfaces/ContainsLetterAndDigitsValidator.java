package com.example.demo.interfaces;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContainsLetterAndDigitsValidator implements ConstraintValidator <ContainsLetterAndDigits, String>{

    @Override
    public void initialize(ContainsLetterAndDigits constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if (s ==null){
            return false;
        }
        boolean containsAtLeastOneLetter = s.matches("^.*[a-zA-Z]+.*$");
        boolean containsAtLeastOneDigit = s.matches("^.*\\d.*$");


        return containsAtLeastOneLetter && containsAtLeastOneDigit;
    }


}
