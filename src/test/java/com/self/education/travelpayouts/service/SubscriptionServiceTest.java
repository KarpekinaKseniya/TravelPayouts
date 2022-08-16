package com.self.education.travelpayouts.service;

import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.internal.verification.VerificationModeFactory.only;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static com.self.education.travelpayouts.domain.SubscribeStatus.SUBSCRIBED;
import static com.self.education.travelpayouts.domain.SubscribeStatus.UNSUBSCRIBED;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.NABEELA_EMAIL;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.RENTAL_CARS_TITLE;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaBuilder;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.subscriptionsEntityBuilder;

import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import com.self.education.travelpayouts.domain.SubscribeStatus;
import com.self.education.travelpayouts.domain.Subscriptions;
import com.self.education.travelpayouts.exception.ChangeSubscriptionStatusException;
import com.self.education.travelpayouts.exception.EntityNotFoundException;
import com.self.education.travelpayouts.repository.SubscriptionsRepository;

class SubscriptionServiceTest {

    @Mock
    private SubscriptionsRepository subscriptionsRepository;
    @Mock
    private UserService userService;
    @Mock
    private PartnershipProgramsService programsService;

    private SubscriptionService subscriptionService;

    private static Stream<Arguments> successData() {
        //@formatter:off
        return Stream.of(
                Arguments.of(Optional.of(subscriptionsEntityBuilder().build()), UNSUBSCRIBED),
                Arguments.of(Optional.of(subscriptionsEntityBuilder().build()), SUBSCRIBED),
                Arguments.of(empty(), SUBSCRIBED));
        //@formatter:on
    }

    @BeforeEach
    void setup() {
        openMocks(this);
        subscriptionService = new SubscriptionServiceImpl(subscriptionsRepository, userService, programsService);
    }

    @AfterEach
    void verify() {
        verifyNoMoreInteractions(subscriptionsRepository, userService, programsService);
    }

    @ParameterizedTest
    @MethodSource("successData")
    void shouldChangeSubscribeStatusSuccess(final Optional<Subscriptions> subscription, final SubscribeStatus status) {
        final Subscriptions save = subscriptionsEntityBuilder().subscribeStatus(status).build();

        given(userService.findUserByEmail(NABEELA_EMAIL)).willReturn(nabeelaBuilder().build());
        given(programsService.findPartnershipProgramByTitle(RENTAL_CARS_TITLE)).willReturn(rentalCarsEntity().build());
        given(subscriptionsRepository.findByPrimaryKeyUserAndPrimaryKeyProgram(nabeelaBuilder().build(),
                rentalCarsEntity().build())).willReturn(subscription);
        given(subscriptionsRepository.save(save)).willReturn(save);

        subscriptionService.changeSubscribeStatus(NABEELA_EMAIL, RENTAL_CARS_TITLE, status);

        then(subscriptionsRepository).should(times(1))
                .findByPrimaryKeyUserAndPrimaryKeyProgram(nabeelaBuilder().build(), rentalCarsEntity().build());
        then(userService).should(only()).findUserByEmail(NABEELA_EMAIL);
        then(programsService).should(only()).findPartnershipProgramByTitle(RENTAL_CARS_TITLE);
        then(subscriptionsRepository).should(times(1)).save(save);
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIsEmptyAndStatusIsNotSubscribed() {
        given(userService.findUserByEmail(NABEELA_EMAIL)).willReturn(nabeelaBuilder().build());
        given(programsService.findPartnershipProgramByTitle(RENTAL_CARS_TITLE)).willReturn(rentalCarsEntity().build());
        given(subscriptionsRepository.findByPrimaryKeyUserAndPrimaryKeyProgram(nabeelaBuilder().build(),
                rentalCarsEntity().build())).willReturn(empty());

        final ChangeSubscriptionStatusException actual = assertThrows(ChangeSubscriptionStatusException.class,
                () -> subscriptionService.changeSubscribeStatus(NABEELA_EMAIL, RENTAL_CARS_TITLE, UNSUBSCRIBED));
        assertThat(actual.getMessage(),
                is("Can't UNSUBSCRIBED for user email = juliano@msn.com and program title = RentalCars"));

        then(userService).should(only()).findUserByEmail(NABEELA_EMAIL);
        then(programsService).should(only()).findPartnershipProgramByTitle(RENTAL_CARS_TITLE);
        then(subscriptionsRepository).should(only())
                .findByPrimaryKeyUserAndPrimaryKeyProgram(nabeelaBuilder().build(), rentalCarsEntity().build());
    }

    @Test
    void shouldThrowExceptionWhenUserServiceReturnError() {
        final EntityNotFoundException exception = new EntityNotFoundException("User", NABEELA_EMAIL);

        given(userService.findUserByEmail(NABEELA_EMAIL)).willThrow(exception);

        final EntityNotFoundException actual = assertThrows(EntityNotFoundException.class,
                () -> subscriptionService.changeSubscribeStatus(NABEELA_EMAIL, RENTAL_CARS_TITLE, SUBSCRIBED));
        assertThat(actual, is(exception));

        then(userService).should(only()).findUserByEmail(NABEELA_EMAIL);
    }

    @Test
    void shouldThrowExceptionWhenProgramServiceReturnError() {
        final EntityNotFoundException exception = new EntityNotFoundException("Partnership Program", RENTAL_CARS_TITLE);

        given(userService.findUserByEmail(NABEELA_EMAIL)).willReturn(nabeelaBuilder().build());
        given(programsService.findPartnershipProgramByTitle(RENTAL_CARS_TITLE)).willThrow(exception);

        final EntityNotFoundException actual = assertThrows(EntityNotFoundException.class,
                () -> subscriptionService.changeSubscribeStatus(NABEELA_EMAIL, RENTAL_CARS_TITLE, SUBSCRIBED));
        assertThat(actual, is(exception));

        then(userService).should(only()).findUserByEmail(NABEELA_EMAIL);
        then(programsService).should(only()).findPartnershipProgramByTitle(RENTAL_CARS_TITLE);
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionRepositoryReturnError() {
        final RuntimeException exception = new RuntimeException("some error message");

        given(userService.findUserByEmail(NABEELA_EMAIL)).willReturn(nabeelaBuilder().build());
        given(programsService.findPartnershipProgramByTitle(RENTAL_CARS_TITLE)).willReturn(rentalCarsEntity().build());
        given(subscriptionsRepository.findByPrimaryKeyUserAndPrimaryKeyProgram(nabeelaBuilder().build(),
                rentalCarsEntity().build())).willThrow(exception);

        final RuntimeException actual = assertThrows(RuntimeException.class,
                () -> subscriptionService.changeSubscribeStatus(NABEELA_EMAIL, RENTAL_CARS_TITLE, SUBSCRIBED));
        assertThat(actual, is(exception));

        then(userService).should(only()).findUserByEmail(NABEELA_EMAIL);
        then(programsService).should(only()).findPartnershipProgramByTitle(RENTAL_CARS_TITLE);
        then(subscriptionsRepository).should(only())
                .findByPrimaryKeyUserAndPrimaryKeyProgram(nabeelaBuilder().build(), rentalCarsEntity().build());
    }
}