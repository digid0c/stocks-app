package org.example.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.regex.Pattern.compile;

public class IsinCodeConstraintValidator implements ConstraintValidator<ValidIsinCode, String> {

    @Override
    public void initialize(ValidIsinCode constraintAnnotation) {
        // intentionally left blank
    }

    @Override
    public boolean isValid(String isinCode, ConstraintValidatorContext context) {
        return isinCode != null && compile("^[A-Z]{2}[0-9A-Z]{9}[0-9]{1}$")
                .matcher(isinCode)
                .matches();
    }
}
