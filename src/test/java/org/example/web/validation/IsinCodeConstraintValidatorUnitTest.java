package org.example.web.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IsinCodeConstraintValidatorUnitTest {

    private IsinCodeConstraintValidator tested;

    @BeforeEach
    public void setUp() {
        tested = new IsinCodeConstraintValidator();
    }

    @Test
    public void shouldValidateCorrectIsinCode() {
        assertThat(tested.isValid("US0120719985", null)).isTrue();
    }

    @Test
    public void shouldNotValidateNullIsinCode() {
        assertThat(tested.isValid(null, null)).isFalse();
    }

    @Test
    public void shouldNotValidateEmptyIsinCode() {
        assertThat(tested.isValid("", null)).isFalse();
    }

    @Test
    public void shouldNotValidateTooShortIsinCode() {
        assertThat(tested.isValid("US12345", null)).isFalse();
    }

    @Test
    public void shouldNotValidateTooLongIsinCode() {
        assertThat(tested.isValid("US1234567890987654321", null)).isFalse();
    }

    @Test
    public void shouldNotValidateIsinCodeStartingWithNumbers() {
        assertThat(tested.isValid("120120719985", null)).isFalse();
    }

    @Test
    public void shouldNotValidateIsinCodeEndingWithLetter() {
        assertThat(tested.isValid("US012071998A", null)).isFalse();
    }

    @Test
    public void shouldNotValidateIsinCodeWithCharsNotAllowed() {
        assertThat(tested.isValid("US0_20+19985", null)).isFalse();
    }
}
