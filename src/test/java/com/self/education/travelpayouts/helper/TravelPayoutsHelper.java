package com.self.education.travelpayouts.helper;

import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.domain.PartnershipPrograms;

public class TravelPayoutsHelper {

    private static final Long RENTAL_CARS_ID = 1L;
    private static final Long OMIO_ID = 2L;
    private static final Long GO_CITY_ID = 3L;
    private static final String RENTAL_CARS_TITLE = "RentalCars";
    private static final String OMIO_TITLE = "Omio";
    private static final String GO_CITY_TITLE = "Go City";
    private static final String RENTAL_CARS_DESCRIPTION = "Earn commissions on car rentals worldwide";
    private static final String OMIO_DESCRIPTION =
            "Earn with the Omio affiliate program and help your customers organize their European trips";
    private static final String GO_CITY_DESCRIPTION = "Earn money on multi-attraction passes for tourists";

    public static PartnershipPrograms.PartnershipProgramsBuilder rentalCarsEntity() {
        //@formatter:off
        return PartnershipPrograms.builder()
                .id(RENTAL_CARS_ID)
                .title(RENTAL_CARS_TITLE)
                .description(RENTAL_CARS_DESCRIPTION);
        //@formatter:on
    }

    public static PartnershipPrograms.PartnershipProgramsBuilder omioEntity() {
        //@formatter:off
        return PartnershipPrograms.builder()
                .id(OMIO_ID)
                .title(OMIO_TITLE)
                .description(OMIO_DESCRIPTION);
        //@formatter:on
    }

    public static PartnershipPrograms.PartnershipProgramsBuilder goCityEntity() {
        //@formatter:off
        return PartnershipPrograms.builder()
                .id(GO_CITY_ID)
                .title(GO_CITY_TITLE)
                .description(GO_CITY_DESCRIPTION);
        //@formatter:on
    }

    public static ProgramResponse.ProgramResponseBuilder rentalCarsResponse() {
        //@formatter:off
        return ProgramResponse.builder()
                .title(RENTAL_CARS_TITLE)
                .description(RENTAL_CARS_DESCRIPTION);
        //@formatter:on
    }
}
