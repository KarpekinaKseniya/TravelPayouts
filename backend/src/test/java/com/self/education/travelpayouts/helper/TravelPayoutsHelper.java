package com.self.education.travelpayouts.helper;

import static com.self.education.travelpayouts.domain.SubscribeStatus.BLOCK;

import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.api.UserRequest;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.domain.PartnershipPrograms;
import com.self.education.travelpayouts.domain.Subscriptions;
import com.self.education.travelpayouts.domain.SubscriptionsId;
import com.self.education.travelpayouts.domain.Users;

public class TravelPayoutsHelper {

    private static final Long RENTAL_CARS_ID = 1L;
    private static final Long OMIO_ID = 2L;
    private static final Long GO_CITY_ID = 3L;
    private static final String GO_CITY_TITLE = "Go City";
    private static final String RENTAL_CARS_DESCRIPTION = "Earn commissions on car rentals worldwide";
    private static final String OMIO_DESCRIPTION =
            "Earn with the Omio affiliate program and help your customers organize their European trips";
    private static final String GO_CITY_DESCRIPTION = "Earn money on multi-attraction passes for tourists";
    private static final int RENTAL_CAR_SUBSCRIBER_COUNT = 2;
    private static final int OMIO_SUBSCRIBER_COUNT = 1;
    private static final int GO_CITY_SUBSCRIBER_COUNT = 2;
    private static final String NABEELA_NAME = "Nabeela Leech";
    private static final String YISROEL_NAME = "Yisroel Mcarthur";
    public static final String RENTAL_CARS_TITLE = "RentalCars";
    public static final String NABEELA_EMAIL = "juliano@msn.com";
    public static final String OMIO_TITLE = "Omio";
    public static final String YISROEL_EMAIL = "loscar@yahoo.ca";
    public static final Long YISROEL_ID = 1L;
    public static final Long NABEELA_ID = 2L;

    public static PartnershipPrograms.PartnershipProgramsBuilder rentalCarsEntity() {
        //@formatter:off
        return PartnershipPrograms.builder()
                .id(RENTAL_CARS_ID)
                .title(RENTAL_CARS_TITLE)
                .description(RENTAL_CARS_DESCRIPTION)
                .subscriberCount(RENTAL_CAR_SUBSCRIBER_COUNT);
        //@formatter:on
    }

    public static PartnershipPrograms.PartnershipProgramsBuilder omioEntity() {
        //@formatter:off
        return PartnershipPrograms.builder()
                .id(OMIO_ID)
                .title(OMIO_TITLE)
                .description(OMIO_DESCRIPTION)
                .subscriberCount(OMIO_SUBSCRIBER_COUNT);
        //@formatter:on
    }

    public static PartnershipPrograms.PartnershipProgramsBuilder goCityEntity() {
        //@formatter:off
        return PartnershipPrograms.builder()
                .id(GO_CITY_ID)
                .title(GO_CITY_TITLE)
                .description(GO_CITY_DESCRIPTION)
                .subscriberCount(GO_CITY_SUBSCRIBER_COUNT);
        //@formatter:on
    }

    public static ProgramResponse.ProgramResponseBuilder rentalCarsResponse() {
        //@formatter:off
        return ProgramResponse.builder()
                .title(RENTAL_CARS_TITLE)
                .description(RENTAL_CARS_DESCRIPTION);
        //@formatter:on
    }

    public static Subscriptions.SubscriptionsBuilder subscriptionsEntityBuilder() {
        return Subscriptions.builder().primaryKey(
                        SubscriptionsId.builder().user(nabeelaBuilder().build()).program(rentalCarsEntity().build()).build())
                .subscribeStatus(BLOCK);
    }

    public static Users yisroelEntity() {
        //@formatter:off
        return Users.builder().id(YISROEL_ID)
                .email(YISROEL_EMAIL)
                .name(YISROEL_NAME).build();
        //@formatter:on
    }

    public static Users.UsersBuilder nabeelaBuilder() {
        //@formatter:off
        return Users.builder().id(NABEELA_ID)
                .name(NABEELA_NAME)
                .email(NABEELA_EMAIL);
        //@formatter:on
    }

    public static UserResponse yisroelResponse() {
        //@formatter:off
        return UserResponse.builder()
                .email(YISROEL_EMAIL)
                .name(YISROEL_NAME).build();
        //@formatter:on
    }

    public static UserResponse nabeelaResponse() {
        //@formatter:off
        return UserResponse.builder()
                .name(NABEELA_NAME)
                .email(NABEELA_EMAIL).build();
        //@formatter:on
    }

    public static UserRequest nabeelaRequest() {
        //@formatter:off
        return UserRequest.builder()
                .name(NABEELA_NAME)
                .email(NABEELA_EMAIL).build();
        //@formatter:on
    }
}
