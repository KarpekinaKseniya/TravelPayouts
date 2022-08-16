package com.self.education.travelpayouts.service;

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
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.NABEELA_EMAIL;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaBuilder;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaRequest;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaResponse;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.yisroelEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.yisroelResponse;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Users;
import com.self.education.travelpayouts.exception.EntityNotFoundException;
import com.self.education.travelpayouts.mapper.UserMapper;
import com.self.education.travelpayouts.repository.UserRepository;

class UserServiceTest {

    private final static String ERROR_MESSAGE = "some error message";

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;

    private UserService service;

    @BeforeEach
    void setup() {
        openMocks(this);
        service = new UserServiceImpl(userMapper, userRepository);
    }

    @AfterEach
    void verify() {
        verifyNoMoreInteractions(userMapper, userRepository);
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
    void shouldThrowExceptionWhenUserNotFound() {
        given(userRepository.findByEmail(NABEELA_EMAIL)).willReturn(empty());

        final EntityNotFoundException actual =
                assertThrows(EntityNotFoundException.class, () -> service.findUserByEmail(NABEELA_EMAIL));
        assertThat(actual.getMessage(), is("Can't find User by juliano@msn.com"));

        then(userRepository).should(only()).findByEmail(NABEELA_EMAIL);
    }
}