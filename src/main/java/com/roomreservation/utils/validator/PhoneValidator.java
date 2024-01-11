package com.roomreservation.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    @Override
    public void initialize(ValidPhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext cxt) {
        if (phoneField == null) {
            return false;
        }
        return phoneField.matches("[0-9]+") && phoneField.length() > 8 && phoneField.length() < 14;
    }
}
