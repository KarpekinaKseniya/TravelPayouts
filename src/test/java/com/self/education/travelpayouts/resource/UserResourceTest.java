package com.self.education.travelpayouts.resource;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.ResponseEntity.ok;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaResponse;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.service.UserService;

class UserResourceTest {

    @Mock
    private UserService userService;

    private UserResource resource;

    @BeforeEach
    void setup() {
        openMocks(this);
        resource = new UserResource(userService);
    }

    @Test
    void shouldFindAllUsersSuccess() {
        final List<UserResponse> expected = singletonList(nabeelaResponse());

        given(userService.findAllUsers()).willReturn(expected);

        final ResponseEntity<List<UserResponse>> actual = resource.findAllUsers();
        assertThat(actual, is(ok(expected)));

        then(userService).should(only()).findAllUsers();
    }

    @Test
    void shouldThrowsWhenUserServiceThrowException() {
        final Exception exception = new RuntimeException("runtime error");

        given(userService.findAllUsers()).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> resource.findAllUsers());
        assertThat(actual, is(exception));

        then(userService).should(only()).findAllUsers();
    }
}