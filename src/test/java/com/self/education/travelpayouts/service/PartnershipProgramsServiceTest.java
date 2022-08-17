package com.self.education.travelpayouts.service;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.RENTAL_CARS_TITLE;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsEntity;
import static com.self.education.travelpayouts.helper.TravelPayoutsHelper.rentalCarsResponse;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.domain.PartnershipPrograms;
import com.self.education.travelpayouts.exception.EntityNotFoundException;
import com.self.education.travelpayouts.mapper.PartnershipProgramsMapper;
import com.self.education.travelpayouts.repository.PartnershipProgramsRepository;

class PartnershipProgramsServiceTest {

    private static final String ERROR_MESSAGE = "error message";

    @Mock
    private PartnershipProgramsRepository programsRepository;
    @Mock
    private PartnershipProgramsMapper programsMapper;

    private PartnershipProgramsService service;

    @BeforeEach
    void setup() {
        openMocks(this);
        service = new PartnershipProgramsServiceImpl(programsRepository, programsMapper);
    }

    @AfterEach
    void verify() {
        verifyNoMoreInteractions(programsRepository, programsMapper);
    }

    @Test
    void shouldGetAllPrograms() {
        final List<ProgramResponse> expected = singletonList(rentalCarsResponse().build());
        final PartnershipPrograms entity = rentalCarsEntity().build();

        given(programsRepository.findAll()).willReturn(singletonList(entity));
        given(programsMapper.transform(entity)).willReturn(rentalCarsResponse().build());

        final List<ProgramResponse> actual = service.getAllPrograms();
        assertThat(actual, is(expected));

        then(programsRepository).should(only()).findAll();
        then(programsMapper).should(times(1)).transform(entity);
    }

    @Test
    void shouldThrowsExceptionWhenMapperReturnException() {
        final Exception exception = new RuntimeException(ERROR_MESSAGE);
        final PartnershipPrograms entity = rentalCarsEntity().build();

        given(programsRepository.findAll()).willReturn(singletonList(entity));
        given(programsMapper.transform(entity)).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> service.getAllPrograms());
        assertThat(actual, is(exception));

        then(programsRepository).should(only()).findAll();
        then(programsMapper).should(times(1)).transform(entity);
    }

    @Test
    void shouldThrowsExceptionWhenRepositoryThrowException() {
        final Exception exception = new RuntimeException(ERROR_MESSAGE);

        given(programsRepository.findAll()).willThrow(exception);

        final Exception actual = assertThrows(RuntimeException.class, () -> service.getAllPrograms());
        assertThat(actual, is(exception));

        then(programsRepository).should(only()).findAll();
    }

    @Test
    void shouldFindProgramByTitleSuccess() {
        given(programsRepository.findByTitle(RENTAL_CARS_TITLE)).willReturn(Optional.of(rentalCarsEntity().build()));

        final PartnershipPrograms actual = service.findPartnershipProgramByTitle(RENTAL_CARS_TITLE);
        assertThat(actual, is(rentalCarsEntity().build()));

        then(programsRepository).should(only()).findByTitle(RENTAL_CARS_TITLE);
    }

    @Test
    void shouldThrowExceptionWhenProgramNotFound() {
        given(programsRepository.findByTitle(RENTAL_CARS_TITLE)).willReturn(empty());

        final EntityNotFoundException actual = assertThrows(EntityNotFoundException.class,
                () -> service.findPartnershipProgramByTitle(RENTAL_CARS_TITLE));
        assertThat(actual.getMessage(), is("Can't find Partnership Program by RentalCars"));

        then(programsRepository).should(only()).findByTitle(RENTAL_CARS_TITLE);
    }

    @Test
    void shouldFindProgramByTitleContaining() {
        given(programsRepository.findByTitleContainingIgnoreCaseOrderBySubscriberCountDesc(
                RENTAL_CARS_TITLE)).willReturn(singletonList(rentalCarsEntity().build()));
        given(programsMapper.transform(rentalCarsEntity().build())).willReturn(rentalCarsResponse().build());

        final List<ProgramResponse> actual = service.findProgramsByTermOrderByPopularityDesc(RENTAL_CARS_TITLE);
        assertThat(actual, is(singletonList(rentalCarsResponse().build())));

        then(programsRepository).should(only())
                .findByTitleContainingIgnoreCaseOrderBySubscriberCountDesc(RENTAL_CARS_TITLE);
        then(programsMapper).should(only()).transform(rentalCarsEntity().build());
    }
}