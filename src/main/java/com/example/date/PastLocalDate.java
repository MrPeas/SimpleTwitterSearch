package com.example.date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;

/**
 * Created by Janusz on 04.01.2017.
 */



@Documented
@Constraint(validatedBy = PastLocalDate.PastDateValidator.class)
@Target(ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
public @interface PastLocalDate {
    String message() default "{javax.validation.constraints.Past.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    class PastDateValidator implements ConstraintValidator<PastLocalDate,LocalDate>{
        @Override
        public void initialize(PastLocalDate pastLocalDate) {

        }

        @Override
        public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
            return localDate==null||localDate.isBefore(LocalDate.now());
        }
    }
}
