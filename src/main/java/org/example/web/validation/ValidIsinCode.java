package org.example.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = IsinCodeConstraintValidator.class)
@NotNull(message = "Value cannot be null")
@Target(FIELD)
@Retention(RUNTIME)
public @interface ValidIsinCode {

    String message() default "Value is not a valid ISIN code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
