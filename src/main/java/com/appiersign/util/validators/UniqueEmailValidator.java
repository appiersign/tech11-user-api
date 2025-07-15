package com.appiersign.util.validators;

import com.appiersign.services.UserService;
import com.appiersign.util.annotations.UniqueEmail;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Inject
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email == null || email.isBlank() || !userService.emailExists(email);
    }
}
