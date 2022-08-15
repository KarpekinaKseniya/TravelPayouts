package com.self.education.travelpayouts.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaBuilder;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.yisroelEntity;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.self.education.travelpayouts.domain.Users;
import com.self.education.travelpayouts.integration_tests.config.HSQLConfig;

@DataJpaTest(excludeAutoConfiguration = TestDatabaseAutoConfiguration.class)
@Import(HSQLConfig.class)
@TestPropertySource(locations = "classpath:/application-test.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldFindAllUsers() {

        final List<Users> actual = repository.findAll();

        assertThat(actual.get(0), is(yisroelEntity()));
        assertThat(actual.get(1), is(nabeelaBuilder().build()));
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldCreateUser() {
        final String email = "other32email@gmail.ru";

        final Users actual = repository.save(nabeelaBuilder().email(email).id(null).build());

        assertThat(actual, is(nabeelaBuilder().id(3L).email(email).build()));
    }
}