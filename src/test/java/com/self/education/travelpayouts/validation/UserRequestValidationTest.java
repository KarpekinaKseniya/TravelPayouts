package com.self.education.travelpayouts.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import com.self.education.travelpayouts.api.UserRequest;

class UserRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = { "abc.def@mail.com", "abc@mail.com", "abc_def@mail.com", "abc.def@mail.cc",
            "abc.def@mail.org", "abc.def@mail.com" })
    void shouldSuccessValidate(final String email) {
        final UserRequest user = UserRequest.builder().name("Butterfly").email(email).build();
        final Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "  ", "\t\n\r " })
    void shouldReturnErrorWhenNameIsBlank(final String name) {
        final UserRequest user = UserRequest.builder().name(name).email("snow23@mail.com").build();
        final Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("User name cannot be blank"));
    }

    @Test
    void shouldReturnErrorWhenEmailIsNull() {
        final UserRequest user = UserRequest.builder().name("Robert Green").email(null).build();
        final Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("User email cannot be null"));
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = { "abc-d@mail.com", "\t\n\r ", "abc.def@mail-archive.com", "name", "sdsd@fdfd",
            "dsddsddssd.com", "abc-@mail.com", "abc#def@mail.com", ".abc@mail.com", "abc.def@mail.c",
            "abc.def@mail#archive.com" })
    void shouldReturnErrorWhenEmailIsWrong(final String email) {
        final UserRequest user = UserRequest.builder().name("Yellow Rabbit").email(email).build();
        final Set<ConstraintViolation<UserRequest>> violations = validator.validate(user);
        final String errorMessage = violations.iterator().next().getMessage();

        assertFalse(violations.isEmpty());
        assertThat(errorMessage, is("User email isn't valid"));
    }
}
