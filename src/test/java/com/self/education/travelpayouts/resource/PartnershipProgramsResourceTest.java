package com.self.education.travelpayouts.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.ResponseEntity.ok;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsResponse;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.service.PartnershipProgramsService;

class PartnershipProgramsResourceTest {

    private static final String ERROR_MESSAGE = "some error message";

    @Mock
    private PartnershipProgramsService programsService;
    @InjectMocks
    private PartnershipProgramsResource resource;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @AfterEach
    void verify() {
        verifyNoMoreInteractions(programsService);
    }

    @Test
    void shouldFindAllPrograms() {
        final List<ProgramResponse> programResponses = Collections.singletonList(rentalCarsResponse().build());
        final ResponseEntity<List<ProgramResponse>> expected = ok(programResponses);

        given(programsService.getAllPrograms()).willReturn(programResponses);

        final ResponseEntity<List<ProgramResponse>> actual = resource.findAllPrograms();
        assertThat(actual, is(expected));

        then(programsService).should(only()).getAllPrograms();
    }

    @Test
    void shouldThrowExceptionWhenProgramsServiceThrowsException() {
        final Exception exception = new RuntimeException(ERROR_MESSAGE);

        given(programsService.getAllPrograms()).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> resource.findAllPrograms());
        assertThat(actual, is(exception));

        then(programsService).should(only()).getAllPrograms();
    }
}