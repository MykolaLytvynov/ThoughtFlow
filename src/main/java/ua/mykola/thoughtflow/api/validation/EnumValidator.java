package ua.mykola.thoughtflow.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private Set<String> acceptedValues;

    @Override
    public void initialize(ValidEnum annotation) {
        this.acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && acceptedValues.contains(value.toLowerCase());
    }
}
