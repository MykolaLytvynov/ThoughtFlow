package ua.mykola.thoughtflow.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;


@Constraint(validatedBy = {EnumValidator.class, EnumListValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
    String message() default "Invalid value for enum";
    Class<? extends Enum<?>> enumClass();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
