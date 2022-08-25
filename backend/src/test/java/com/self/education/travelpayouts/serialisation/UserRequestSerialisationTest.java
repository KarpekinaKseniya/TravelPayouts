package com.self.education.travelpayouts.serialisation;

import org.junit.jupiter.api.BeforeEach;
import com.self.education.travelpayouts.api.UserRequest;
import com.self.education.travelpayouts.helper.TravelPayoutsHelper;

public class UserRequestSerialisationTest extends JsonTestBase<UserRequest> {

    @BeforeEach
    void beforeEach() {
        expected = TravelPayoutsHelper::nabeelaRequest;
        fileName = "expected_user_request.json";
        expectedType = UserRequest.class;
    }
}
