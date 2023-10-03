package com.app.task.validation;

import com.app.task.annotations.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private final String PHONE_REGEX = "^((\\+84|84|0)[3|5|7|8|9])[0-9]{8}$";

    @Override
    public void initialize(Phone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return value.matches(PHONE_REGEX);
    }
}
