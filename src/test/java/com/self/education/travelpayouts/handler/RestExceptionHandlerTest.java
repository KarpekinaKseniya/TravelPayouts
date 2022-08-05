package com.self.education.travelpayouts.handler;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.context.request.WebRequest;
import com.self.education.travelpayouts.api.ErrorResponse;

class RestExceptionHandlerTest {

    private static final String DESCRIPTION = "Some description";
    private static final String ERROR_MESSAGE = "Error message";
    //@formatter:off
    private final ErrorResponse.ErrorResponseBuilder errorResponse = ErrorResponse.builder()
            .description(DESCRIPTION)
            .message(ERROR_MESSAGE);
    //@formatter:on

    @Mock
    private WebRequest webRequest;
    @InjectMocks
    private RestExceptionHandler handler;

    @BeforeEach
    void setup() {
        openMocks(this);
        given(webRequest.getDescription(false)).willReturn(DESCRIPTION);
    }

    @AfterEach
    void verify() {
        then(webRequest).should(only()).getDescription(false);
    }

    @Test
    void shouldHandleGlobalException() {
        final Exception exception = new RuntimeException(ERROR_MESSAGE);

        final ErrorResponse actual = handler.handleGlobalException(exception, webRequest);
        assertThat(actual, is(errorResponse.statusCode(SC_INTERNAL_SERVER_ERROR).build()));
    }
}