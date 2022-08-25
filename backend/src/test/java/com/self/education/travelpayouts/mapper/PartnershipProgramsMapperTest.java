package com.self.education.travelpayouts.mapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsResponse;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.domain.PartnershipPrograms;

class PartnershipProgramsMapperTest {

    private final PartnershipProgramsMapper mapper = new PartnershipProgramsMapperImpl();

    private static Stream<Arguments> data() {
        //@formatter:off
        return Stream.of(
                Arguments.of(rentalCarsResponse().build(), rentalCarsEntity().build()),
                Arguments.of(null, null)
        );
        //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("data")
    void shouldTransform(final ProgramResponse programResponse, final PartnershipPrograms partnershipPrograms) {
        assertThat(programResponse, is(mapper.transform(partnershipPrograms)));
    }
}