package com.self.education.travelpayouts.repository;

import static java.lang.Boolean.TRUE;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.RENTAL_CARS_TITLE;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.goCityEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.omioEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.self.education.travelpayouts.domain.PartnershipPrograms;
import com.self.education.travelpayouts.integration_tests.config.HSQLConfig;

@DataJpaTest(excludeAutoConfiguration = TestDatabaseAutoConfiguration.class)
@Import(HSQLConfig.class)
@TestPropertySource(locations = "classpath:/application-test.properties")
class PartnershipProgramsRepositoryTest {

    @Autowired
    private PartnershipProgramsRepository repository;

    private static Stream<Arguments> searchLikeDescOrder() {
        return Stream.of(
                Arguments.of("GO", List.of(goCityEntity().build())),
                Arguments.of("go ci", List.of(goCityEntity().build())),
                Arguments.of("CiTY", List.of(goCityEntity().build())),
                Arguments.of("o ", List.of(goCityEntity().build())),
                Arguments.of("GO CITY", List.of(goCityEntity().build())),
                Arguments.of("empty", emptyList())
        );
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldFindAllPartnershipPrograms() {
        final List<PartnershipPrograms> expected =
                List.of(rentalCarsEntity().build(), omioEntity().build(), goCityEntity().build());
        final List<PartnershipPrograms> actual = repository.findAll();

        assertThat(actual, is(expected));
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldFindPartnershipProgramByTitle() {
        final Optional<PartnershipPrograms> actual = repository.findByTitle(RENTAL_CARS_TITLE);

        assertThat(actual.isPresent(), is(TRUE));
        assertThat(actual.get(), is(rentalCarsEntity().build()));
    }

    @ParameterizedTest
    @MethodSource("searchLikeDescOrder")
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldFindPartnershipProgramsByTitleOrderByDesc(final String title, final List<PartnershipPrograms> expected) {
        final List<PartnershipPrograms> actual = repository.findByTitleContainingIgnoreCaseOrderBySubscriberCountDesc(title);
        assertThat(actual, is(expected));
    }
}