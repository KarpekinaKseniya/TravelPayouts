package com.self.education.travelpayouts.serialisation;

import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.nabeelaResponse;

import org.junit.jupiter.api.BeforeEach;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.helper.TravelPayoutsHelper;

public class UserResponseSerialisationTest extends JsonTestBase<UserResponse> {

    @BeforeEach
    void beforeEach() {
        expected = TravelPayoutsHelper::nabeelaResponse;
        fileName = "expected_user_response.json";
        expectedType = UserResponse.class;
    }
}
