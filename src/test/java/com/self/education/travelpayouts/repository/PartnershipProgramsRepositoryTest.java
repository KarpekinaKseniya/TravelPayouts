package com.self.education.travelpayouts.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.goCityEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.omioEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsEntity;

import java.util.List;
import org.junit.jupiter.api.Test;
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

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldFindAllPartnershipPrograms() {
        final List<PartnershipPrograms> actual = repository.findAll();

        assertThat(actual.get(0), is(rentalCarsEntity().build()));
        assertThat(actual.get(1), is(omioEntity().build()));
        assertThat(actual.get(2), is(goCityEntity().build()));
    }
}