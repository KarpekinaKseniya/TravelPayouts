package com.self.education.travelpayouts.service;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static com.self.education.travelpayouts.domain.SubscribeStatus.BLOCK;
import static com.self.education.travelpayouts.domain.SubscribeStatus.SUBSCRIBED;
import static com.self.education.travelpayouts.domain.SubscribeStatus.UNSUBSCRIBED;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.NABEELA_EMAIL;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.NABEELA_ID;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.goCityEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaBuilder;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaRequest;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaResponse;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsResponse;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.subscriptionsEntityBuilder;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.yisroelEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.yisroelResponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Subscriptions;
import com.self.education.travelpayouts.domain.SubscriptionsId;
import com.self.education.travelpayouts.domain.Users;
import com.self.education.travelpayouts.exception.EntityNotFoundException;
import com.self.education.travelpayouts.mapper.PartnershipProgramsMapper;
import com.self.education.travelpayouts.mapper.UserMapper;
import com.self.education.travelpayouts.repository.UserRepository;

class UserServiceTest {

    private final static String ERROR_MESSAGE = "some error message";

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PartnershipProgramsMapper programsMapper;

    private UserService service;

    @BeforeEach
    void setup() {
        openMocks(this);
        service = new UserServiceImpl(userMapper, programsMapper, userRepository);
    }

    @AfterEach
    void verify() {
        verifyNoMoreInteractions(userMapper, userRepository, programsMapper);
    }

    @Test
    void shouldFindAllUsersSuccess() {
        final List<Users> users = List.of(yisroelEntity(), nabeelaBuilder().build());
        final List<UserResponse> expected = List.of(yisroelResponse(), nabeelaResponse());

        given(userRepository.findAll()).willReturn(users);
        given(userMapper.transformEntityToResponse(yisroelEntity())).willReturn(yisroelResponse());
        given(userMapper.transformEntityToResponse(nabeelaBuilder().build())).willReturn(nabeelaResponse());

        final List<UserResponse> actual = service.findAllUsers();
        assertThat(actual, is(expected));

        then(userRepository).should(only()).findAll();
        then(userMapper).should(times(1)).transformEntityToResponse(yisroelEntity());
        then(userMapper).should(times(1)).transformEntityToResponse(nabeelaBuilder().build());
    }

    @Test
    void shouldCreateNewUserSuccess() {
        final Users newUser = nabeelaBuilder().id(null).build();

        given(userMapper.transformRequestToEntity(nabeelaRequest())).willReturn(newUser);
        given(userRepository.save(newUser)).willReturn(nabeelaBuilder().build());

        final Long actual = service.createNewUser(nabeelaRequest());
        assertThat(actual, is(nabeelaBuilder().build().getId()));

        then(userMapper).should(only()).transformRequestToEntity(nabeelaRequest());
        then(userRepository).should(only()).save(newUser);
    }

    @Test
    void shouldThrowExceptionWhenUserMapperReturnException() {
        final List<Users> users = List.of(yisroelEntity(), nabeelaBuilder().build());
        final Exception exception = new RuntimeException(ERROR_MESSAGE);

        given(userRepository.findAll()).willReturn(users);
        given(userMapper.transformEntityToResponse(yisroelEntity())).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> service.findAllUsers());
        assertThat(actual, is(exception));

        then(userRepository).should(only()).findAll();
        then(userMapper).should(only()).transformEntityToResponse(yisroelEntity());
    }

    @Test
    void shouldThrowExceptionWhenUserRepositoryReturnException() {
        final Exception exception = new RuntimeException(ERROR_MESSAGE);

        given(userRepository.findAll()).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> service.findAllUsers());
        assertThat(actual, is(exception));

        then(userRepository).should(only()).findAll();
    }

    @Test
    void shouldFindUserByEmailSuccess() {
        given(userRepository.findByEmail(NABEELA_EMAIL)).willReturn(Optional.of(nabeelaBuilder().build()));

        final Users actual = service.findUserByEmail(NABEELA_EMAIL);
        assertThat(actual, is(nabeelaBuilder().build()));

        then(userRepository).should(only()).findByEmail(NABEELA_EMAIL);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByEmail() {
        given(userRepository.findByEmail(NABEELA_EMAIL)).willReturn(empty());

        final EntityNotFoundException actual =
                assertThrows(EntityNotFoundException.class, () -> service.findUserByEmail(NABEELA_EMAIL));
        assertThat(actual.getMessage(), is("Can't find User by juliano@msn.com"));

        then(userRepository).should(only()).findByEmail(NABEELA_EMAIL);
    }

    @Test
    void shouldFindUserByIdSuccess() {
        //@formatter:off
        final Users nabeela = nabeelaBuilder().subscriptions(
                Set.of(subscriptionsEntityBuilder().subscribeStatus(SUBSCRIBED).build(),
                        Subscriptions.builder()
                                .primaryKey(SubscriptionsId.builder().user(nabeelaBuilder().build()).program(goCityEntity().build()).build())
                                .subscribeStatus(BLOCK).build(),
                        Subscriptions.builder().primaryKey(SubscriptionsId.builder().user(nabeelaBuilder().build()).program(goCityEntity().build()).build())
                                .subscribeStatus(UNSUBSCRIBED).build()
                )
        ).build();
        //@formatter:on

        given(userRepository.findUsersById(NABEELA_ID)).willReturn(Optional.of(nabeela));
        given(programsMapper.transform(rentalCarsEntity().build())).willReturn(rentalCarsResponse().build());

        final List<ProgramResponse> actual = service.findAllUserProgramsById(NABEELA_ID);
        assertThat(actual, is(singletonList(rentalCarsResponse().build())));

        then(userRepository).should(only()).findUsersById(NABEELA_ID);
        then(programsMapper).should(only()).transform(rentalCarsEntity().build());
    }

    @Test
    void shouldThrowExceptionWhenProgramMapperReturnError() {
        //@formatter:off
        final Users nabeela = nabeelaBuilder().subscriptions(
                Set.of(subscriptionsEntityBuilder().subscribeStatus(SUBSCRIBED).build())
        ).build();
        //@formatter:on
        final Exception exception = new RuntimeException(ERROR_MESSAGE);

        given(userRepository.findUsersById(NABEELA_ID)).willReturn(Optional.of(nabeela));
        given(programsMapper.transform(rentalCarsEntity().build())).willThrow(exception);

        final Exception actual =
                assertThrows(RuntimeException.class, () -> service.findAllUserProgramsById(NABEELA_ID));
        assertThat(actual, is(exception));

        then(userRepository).should(only()).findUsersById(NABEELA_ID);
        then(programsMapper).should(only()).transform(rentalCarsEntity().build());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        given(userRepository.findUsersById(NABEELA_ID)).willReturn(empty());

        final EntityNotFoundException actual =
                assertThrows(EntityNotFoundException.class, () -> service.findAllUserProgramsById(NABEELA_ID));
        assertThat(actual.getMessage(), is("Can't find User by id = " + NABEELA_ID));

        then(userRepository).should(only()).findUsersById(NABEELA_ID);
    }
}