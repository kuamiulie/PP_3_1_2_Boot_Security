package ru.kata.spring.boot_security.demo.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.METHOD, FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface UniqueNameAnnotation {

    String message() default "NAME IS ALREADY TAKEN GO FUCK";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}