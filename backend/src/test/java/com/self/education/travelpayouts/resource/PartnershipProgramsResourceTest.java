package com.self.education.travelpayouts.resource;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.ResponseEntity.ok;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.RENTAL_CARS_TITLE;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsResponse;

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
        final List<ProgramResponse> programResponses = singletonList(rentalCarsResponse().build());
        final ResponseEntity<List<ProgramResponse>> expected = ok(programResponses);

        given(programsService.getAllPrograms()).willReturn(programResponses);

        final ResponseEntity<List<ProgramResponse>> actual = resource.findAllPrograms();
        assertThat(actual, is(expected));

        then(programsService).should(only()).getAllPrograms();
    }

    @Test
    void shouldFindProgramsByTitleAndOrderDesc() {
        final List<ProgramResponse> programs = singletonList(rentalCarsResponse().build());

        given(programsService.findProgramsByTermOrderByPopularityDesc(RENTAL_CARS_TITLE)).willReturn(programs);

        final ResponseEntity<List<ProgramResponse>> actual = resource.findProgramsByTitle(RENTAL_CARS_TITLE);
        assertThat(actual, is(ok(programs)));

        then(programsService).should(only()).findProgramsByTermOrderByPopularityDesc(RENTAL_CARS_TITLE);
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