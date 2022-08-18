package com.self.education.travelpayouts.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaBuilder;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaRequest;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaResponse;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.self.education.travelpayouts.api.UserRequest;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.Users;

class UserMapperTest {

    private final UserMapper mapper = new UserMapperImpl();

    private static Stream<Arguments> dataEntityToResponse() {
        //@formatter:off
        return Stream.of(
                Arguments.of(nabeelaResponse(), nabeelaBuilder().build()),
                Arguments.of(null, null)
        );
        //@formatter:on
    }

    private static Stream<Arguments> dataRequestToEntity() {
        //@formatter:off
        return Stream.of(
                Arguments.of(nabeelaRequest(), nabeelaBuilder().id(null).build()),
                Arguments.of(null, null)
        );
        //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("dataEntityToResponse")
    void shouldTransformEntityToResponse(final UserResponse response, final Users user) {
        assertThat(mapper.transformEntityToResponse(user), is(response));
    }

    @ParameterizedTest
    @MethodSource("dataRequestToEntity")
    void shouldTransformRequestToEntity(final UserRequest request, final Users user) {
        assertThat(mapper.transformRequestToEntity(request), is(user));
    }
}