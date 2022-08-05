package com.self.education.travelpayouts.serialisation;

import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsResponse;

import org.junit.jupiter.api.BeforeEach;
import com.self.education.travelpayouts.api.ProgramResponse;

public class ProgramResponseSerialisationTest extends JsonTestBase<ProgramResponse> {

    @BeforeEach
    void beforeEach() {
        expected = () -> rentalCarsResponse().build();
        fileName = "expected_program_response.json";
        expectedType = ProgramResponse.class;
    }
}
