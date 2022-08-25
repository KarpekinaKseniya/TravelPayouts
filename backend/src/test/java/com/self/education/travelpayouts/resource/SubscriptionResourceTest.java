package com.self.education.travelpayouts.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.internal.verification.VerificationModeFactory.only;
import static org.springframework.http.HttpStatus.OK;
import static com.self.education.travelpayouts.domain.SubscribeStatus.BLOCK;
import static com.self.education.travelpayouts.domain.SubscribeStatus.UNSUBSCRIBED;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.NABEELA_EMAIL;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.OMIO_TITLE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import com.self.education.travelpayouts.service.SubscriptionService;

class SubscriptionResourceTest {

    @Mock
    private SubscriptionService subscriptionService;

    private SubscriptionResource resource;

    @BeforeEach
    void setup() {
        openMocks(this);
        resource = new SubscriptionResource(subscriptionService);
    }

    @Test
    void shouldChangeSubscriptionStatusSuccess() {
        willDoNothing().given(subscriptionService).changeSubscribeStatus(NABEELA_EMAIL, OMIO_TITLE, UNSUBSCRIBED);

        final ResponseEntity<Void> actual = resource.changeSubscriptionStatus(NABEELA_EMAIL, OMIO_TITLE, UNSUBSCRIBED);
        assertThat(actual.getStatusCode(), is(OK));

        then(subscriptionService).should(only()).changeSubscribeStatus(NABEELA_EMAIL, OMIO_TITLE, UNSUBSCRIBED);
    }

    @Test
    void shouldBlockUserSubscribeSuccess() {
        willDoNothing().given(subscriptionService).changeSubscribeStatus(NABEELA_EMAIL, OMIO_TITLE, BLOCK);

        final ResponseEntity<Void> actual = resource.blockUserSubscribe(NABEELA_EMAIL, OMIO_TITLE);
        assertThat(actual.getStatusCode(), is(OK));

        then(subscriptionService).should(only()).changeSubscribeStatus(NABEELA_EMAIL, OMIO_TITLE, BLOCK);
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionServiceReturnError() {
        final Exception exception = new RuntimeException("error message");

        willThrow(exception).given(subscriptionService).changeSubscribeStatus(NABEELA_EMAIL, OMIO_TITLE, UNSUBSCRIBED);

        final Exception actual = assertThrows(RuntimeException.class,
                () -> resource.changeSubscriptionStatus(NABEELA_EMAIL, OMIO_TITLE, UNSUBSCRIBED));
        assertThat(actual, is(exception));

        then(subscriptionService).should(only()).changeSubscribeStatus(NABEELA_EMAIL, OMIO_TITLE, UNSUBSCRIBED);
    }
}