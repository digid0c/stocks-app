package org.example.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

import static java.util.Locale.getISOCountries;

public class CountryCodeConstraintValidator implements ConstraintValidator<ValidCountryCode, String> {

    @Override
    public void initialize(ValidCountryCode constraintAnnotation) {
        // intentionally left blank
    }

    @Override
    public boolean isValid(String countryCode, ConstraintValidatorContext context) {
        return countryCode != null && getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3).contains(countryCode);
    }
}
