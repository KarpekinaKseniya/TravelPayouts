package com.self.education.travelpayouts.integration_tests;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.YISROEL_ID;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
class UserResourceIT {

    private static final Long WRONG_ID = 3421L;

    @Value("${local.server.port}")
    private int port;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:integration/db/db_drop_table.sql")
    @Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "classpath:integration/db/db_setup.sql" })
    void shouldReturnInternalErrorWhenDbDoesNotExist() throws IOException {
        expectedGetAllUsers(SC_INTERNAL_SERVER_ERROR, "db_is_not_exist_error.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldGetAllUsersSuccess() throws IOException {
        expectedGetAllUsers(SC_OK, "get_users_success.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldCreateUserSuccess() throws IOException {
        expectedCreateUser(SC_CREATED, "user_request_success.json", "create_user_success.json");
    }

    @Test
    void shouldReturnBadRequestWhenValidationOnCreateUserFailure() throws IOException {
        expectedCreateUser(SC_BAD_REQUEST, "user_request_failed_validation.json", "create_user_bad_request.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldReturnConflictWhenUserEmailIsInDB() throws IOException {
        expectedCreateUser(SC_CONFLICT, "user_request_exist_email.json", "create_user_conflict.json");
    }

    @Test
    void shouldReturnInternalServerErrorWhenCreateUser() throws IOException {
        expectedCreateUser(SC_INTERNAL_SERVER_ERROR, "user_request_server_error.json",
                "create_user_internal_server_error.json");
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldFindAllUserProgramsSuccess() throws IOException {
        expectedGetUserPrograms(SC_OK, YISROEL_ID, "get_user_programs_success.json");
    }

    @Test
    void shouldReturnNotFoundWhenWrongUserId() throws IOException {
        expectedGetUserPrograms(SC_NOT_FOUND, WRONG_ID, "get_user_programs_not_found.json");
    }

    private void expectedGetAllUsers(final int statusCode, final String responseFile) throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .when()
                .get(buildRequestUrlStr() + "/users")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body(sameJSONAs(getResponse(responseFile)));
        //@formatter:on
    }

    private void expectedCreateUser(final int statusCode, final String requestFile, final String responseFile)
            throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .body(getRequest(requestFile))
                .when()
                .post(buildRequestUrlStr() + "/user")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body(sameJSONAs(getResponse(responseFile)));
        //@formatter:on
    }

    private void expectedGetUserPrograms(final int statusCode, final Long id, final String responseFile)
            throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .when()
                .pathParam("id", id)
                .get(buildRequestUrlStr() + "/user/{id}/programs")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body(sameJSONAs(getResponse(responseFile)));
        //@formatter:on
    }

    private String buildRequestUrlStr() {
        return "http://localhost:" + port + "/travel-payouts/v1";
    }

    private String getResponse(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/integration/responses/" + file)));
    }

    private String getRequest(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/integration/requests/" + file)));
    }
}
