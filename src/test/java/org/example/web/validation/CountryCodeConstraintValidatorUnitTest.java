package org.example.web.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryCodeConstraintValidatorUnitTest {

    private CountryCodeConstraintValidator tested;

    @BeforeEach
    public void setUp() {
        tested = new CountryCodeConstraintValidator();
    }

    @Test
    public void shouldValidateAlpha3CountryCode() {
        assertThat(tested.isValid("FRA", null)).isTrue();
    }

    @Test
    public void shouldNotValidateNullCountryCode() {
        assertThat(tested.isValid(null, null)).isFalse();
    }

    @Test
    public void shouldNotValidateEmptyCountryCode() {
        assertThat(tested.isValid("", null)).isFalse();
    }

    @Test
    public void shouldNotValidateAlpha2CountryCode() {
        assertThat(tested.isValid("FR", null)).isFalse();
    }

    @Test
    public void shouldNotValidateFullCountryCode() {
        assertThat(tested.isValid("France", null)).isFalse();
    }
}
