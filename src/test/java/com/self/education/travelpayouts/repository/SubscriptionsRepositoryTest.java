package com.self.education.travelpayouts.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.self.education.travelpayouts.domain.SubscribeStatus.SUBSCRIBED;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaBuilder;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.omioEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.subscriptionsEntityBuilder;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.yisroelEntity;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.self.education.travelpayouts.domain.Subscriptions;
import com.self.education.travelpayouts.domain.SubscriptionsId;
import com.self.education.travelpayouts.integration_tests.config.HSQLConfig;

@DataJpaTest(excludeAutoConfiguration = TestDatabaseAutoConfiguration.class)
@Import(HSQLConfig.class)
@TestPropertySource(locations = "classpath:/application-test.properties")
class SubscriptionsRepositoryTest {

    @Autowired
    private SubscriptionsRepository repository;

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldFindByPrimaryKeyUserEmailAndAndPrimaryKeyProgramTitle() {

        final Optional<Subscriptions> actual =
                repository.findByPrimaryKeyUserAndPrimaryKeyProgram(nabeelaBuilder().build(),
                        rentalCarsEntity().build());

        assertTrue(actual.isPresent());
        assertThat(actual.get(), is(subscriptionsEntityBuilder().build()));
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldSaveSubscription() {
        final SubscriptionsId id =
                SubscriptionsId.builder().user(yisroelEntity()).program(omioEntity().build()).build();
        final Subscriptions subscription = Subscriptions.builder().primaryKey(id).subscribeStatus(SUBSCRIBED).build();

        assertFalse(
                repository.findByPrimaryKeyUserAndPrimaryKeyProgram(yisroelEntity(), omioEntity().build()).isPresent());
        final Subscriptions actual = repository.save(subscription);

        assertThat(actual, is(subscription));
    }

    @Test
    @Sql({ "classpath:integration/db/db_cleanup.sql", "/integration/db/db_data.sql" })
    void shouldUpdateSubscription() {
        final SubscriptionsId id =
                SubscriptionsId.builder().user(yisroelEntity()).program(rentalCarsEntity().build()).build();
        final Subscriptions subscription = Subscriptions.builder().primaryKey(id).subscribeStatus(SUBSCRIBED).build();

        assertTrue(repository.findByPrimaryKeyUserAndPrimaryKeyProgram(nabeelaBuilder().build(),
                rentalCarsEntity().build()).isPresent());
        final Subscriptions actual = repository.save(subscription);

        assertThat(actual, is(subscription));
    }
}