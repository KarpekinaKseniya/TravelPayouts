package com.self.education.travelpayouts.exception;

import static java.lang.String.format;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(final String message) {
        super(message);
    }

    public EntityNotFoundException(final String entity, final String value) {
        this(format("Can't find %s by %s", entity, value));
    }
}
