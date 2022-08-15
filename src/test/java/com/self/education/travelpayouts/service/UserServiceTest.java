package com.self.education.travelpayouts.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaResponse;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.yisroelEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.yisroelResponse;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Users;
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
        final List<Users> users = List.of(yisroelEntity(), nabeelaEntity());
        final List<UserResponse> expected = List.of(yisroelResponse(), nabeelaResponse());

        given(userRepository.findAll()).willReturn(users);
        given(userMapper.transformEntityToResponse(yisroelEntity())).willReturn(yisroelResponse());
        given(userMapper.transformEntityToResponse(nabeelaEntity())).willReturn(nabeelaResponse());

        final List<UserResponse> actual = service.findAllUsers();
        assertThat(actual, is(expected));

        then(userRepository).should(only()).findAll();
        then(userMapper).should(times(1)).transformEntityToResponse(yisroelEntity());
        then(userMapper).should(times(1)).transformEntityToResponse(nabeelaEntity());
    }

    @Test
    void shouldThrowExceptionWhenUserMapperReturnException() {
        final List<Users> users = List.of(yisroelEntity(), nabeelaEntity());
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
}