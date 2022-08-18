package com.self.education.travelpayouts.integration_tests;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static com.self.education.travelpayouts.domain.SubscribeStatus.SUBSCRIBED;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.RENTAL_CARS_TITLE;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.YISROEL_EMAIL;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.self.education.travelpayouts.TravelPayoutsApplication;
import com.self.education.travelpayouts.integration_tests.config.HSQLConfig;

@SpringBootTest(classes = { TravelPayoutsApplication.class }, webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = { "classpath:/application-test.properties" })
@Import(HSQLConfig.class)
@EnableConfigurationProperties
class SubscriptionResourceIT {

    private static final String WRONG_EMAIL = "wrong.email";
    private static final String WRONG_PROGRAM_TITLE = "wrong title";

    @Value("${local.server.port}")
    private int port;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldChangeSubscriptionStatusSuccess() {
        //@formatter:off
        final Map<String, String> queryParams = Map.of(
                "userEmail", YISROEL_EMAIL,
                "programTitle", RENTAL_CARS_TITLE,
                "subscribeStatus", SUBSCRIBED.name());
        //@formatter:on
        expectedChangeSubscriptionStatusSuccess(queryParams, "/");
    }

    @Test
    void shouldReturnNotFoundWhenWrongUserEmailOnChangeSubscriptionStatus() throws IOException {
        //@formatter:off
        final Map<String, String> queryParams = Map.of(
                "userEmail", WRONG_EMAIL,
                "programTitle", RENTAL_CARS_TITLE,
                "subscribeStatus", SUBSCRIBED.name());
        //@formatter:on
        expectedChangeSubscriptionStatusError(SC_NOT_FOUND, queryParams, "/", "change_status_when_user_not_found.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldReturnNotFoundWhenWrongProgramTitleOnChangeSubscriptionStatus() throws IOException {
        //@formatter:off
        final Map<String, String> queryParams = Map.of(
                "userEmail", YISROEL_EMAIL,
                "programTitle", WRONG_PROGRAM_TITLE,
                "subscribeStatus", SUBSCRIBED.name());
        //@formatter:on
        expectedChangeSubscriptionStatusError(SC_NOT_FOUND, queryParams, "/",
                "change_status_when_program_not_found.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldReturnInternalServerErrorWhenChangeSubscriptionStatus() throws IOException {
        //@formatter:off
        final Map<String, String> queryParams = Map.of(
                "userEmail", YISROEL_EMAIL,
                "programTitle", WRONG_PROGRAM_TITLE,
                "subscribeStatus", "UNKNOWN");
        //@formatter:on
        expectedChangeSubscriptionStatusError(SC_INTERNAL_SERVER_ERROR, queryParams, "/",
                "change_status_internal_server_error.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldBlockUserSubscriptionSuccess() {
        //@formatter:off
        final Map<String, String> queryParams = Map.of(
                "userEmail", YISROEL_EMAIL,
                "programTitle", RENTAL_CARS_TITLE);
        //@formatter:on
        expectedChangeSubscriptionStatusSuccess(queryParams, "/block");
    }

    @Test
    void shouldReturnNotFoundWhenWrongUserEmailOnBlockUserSubscription() throws IOException {
        //@formatter:off
        final Map<String, String> queryParams = Map.of(
                "userEmail", WRONG_EMAIL,
                "programTitle", RENTAL_CARS_TITLE);
        //@formatter:on
        expectedChangeSubscriptionStatusError(SC_NOT_FOUND, queryParams, "/block",
                "block_user_program_user_not_found.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldReturnNotFoundWhenWrongProgramTitleOnBlockUserSubscription() throws IOException {
        //@formatter:off
        final Map<String, String> queryParams = Map.of(
                "userEmail", YISROEL_EMAIL,
                "programTitle", WRONG_PROGRAM_TITLE);
        //@formatter:on
        expectedChangeSubscriptionStatusError(SC_NOT_FOUND, queryParams, "/block",
                "block_user_program_when_program_not_found.json");
    }

    private void expectedChangeSubscriptionStatusSuccess(final Map<String, String> queryParams, final String url) {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .queryParams(queryParams)
                .when()
                .put(buildRequestUrlStr() + url)
                .then()
                .assertThat()
                .statusCode(SC_OK);
        //@formatter:on
    }

    private void expectedChangeSubscriptionStatusError(final int statusCode, final Map<String, String> queryParams,
            final String url, final String responseFile) throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .queryParams(queryParams)
                .when()
                .put(buildRequestUrlStr() + url)
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body(sameJSONAs(getResponse(responseFile)));
        //@formatter:on
    }

    private String buildRequestUrlStr() {
        return "http://localhost:" + port + "/travel-payouts/v1/subscription-status";
    }

    private String getResponse(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/integration/responses/" + file)));
    }
}
