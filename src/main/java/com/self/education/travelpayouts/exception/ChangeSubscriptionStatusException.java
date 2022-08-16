package com.self.education.travelpayouts.exception;

import static java.lang.String.format;

public class ChangeSubscriptionStatusException extends RuntimeException {

    public ChangeSubscriptionStatusException(final String message) {
        super(message);
    }

    public ChangeSubscriptionStatusException(final String email, final String programTitle, final String status) {
        this(format("Can't %s for user email = %s and program title = %s", status, email,
                programTitle));
    }
}
