package com.self.education.travelpayouts.integration_tests;

import static org.apache.http.HttpStatus.SC_OK;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
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
class PartnershipProgramsResourceIT {

    @Value("${local.server.port}")
    private int port;

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldGetAllProgramsSuccess() throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .when()
                .get(buildRequestUrlStr())
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(sameJSONAs(getResponse("get_all_programs_success.json")));
        //@formatter:on
    }

    @Test
    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "classpath:integration/db/db_cleanup.sql",
            "classpath:integration/db/db_data.sql" })
    void shouldGetAllProgramsByTitleSuccess() throws IOException {
        //@formatter:off
        given()
                .contentType(JSON)
                .accept(JSON)
                .when()
                .pathParam("title", "car")
                .get(buildRequestUrlStr() + "/{title}")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body(sameJSONAs(getResponse("get_all_programs_by_title_success.json")));
        //@formatter:on
    }

    private String buildRequestUrlStr() {
        return "http://localhost:" + port + "/travel-payouts/v1/partnership-programs";
    }

    private String getResponse(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/integration/responses/" + file)));
    }
}
