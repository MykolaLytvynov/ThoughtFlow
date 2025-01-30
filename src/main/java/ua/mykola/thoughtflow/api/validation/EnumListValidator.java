package ua.mykola.thoughtflow.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumListValidator implements ConstraintValidator<ValidEnum, List<String>> {
    private Set<String> acceptedValues;

    @Override
    public void initialize(ValidEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) return true;
        return values.stream().allMatch(value -> acceptedValues.contains(value.toLowerCase()));
    }
}
