package com.xikra.staybooker.annotations.checkInCheckOut;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckInCheckOutValidator.class)
@Documented
public @interface CheckInCheckOut {
    String message() default "Check-out date must be after check-in date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}