package com.self.education.travelpayouts.resource;

import static org.springframework.http.HttpStatus.OK;
import static com.self.education.travelpayouts.domain.SubscribeStatus.BLOCK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.self.education.travelpayouts.api.ErrorResponse;
import com.self.education.travelpayouts.domain.SubscribeStatus;
import com.self.education.travelpayouts.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/travel-payouts/v1/subscribe-status")
public class SubscriptionResource {

    private final SubscriptionService subscriptionService;

    //@formatter:off
    @Operation(summary = "Change subscribe status", description = "Endpoint for changed subscribe status", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    //@formatter:on
    @PutMapping
    public ResponseEntity<Void> changeSubscriptionStatus(@RequestParam("userEmail") final String userEmail,
            @RequestParam("programTitle") final String programTitle,
            @RequestParam("subscribeStatus") final SubscribeStatus subscribeStatus) {
        subscriptionService.changeSubscribeStatus(userEmail, programTitle, subscribeStatus);
        return new ResponseEntity<>(OK);
    }

    @Operation(summary = "Block User Subscribe", description = "Endpoint for blocked user subscribe on program", responses = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    //@formatter:on
    @PutMapping("/block")
    public ResponseEntity<Void> blockUserSubscribe(@RequestParam("userEmail") final String userEmail,
            @RequestParam("programTitle") final String programTitle) {
        subscriptionService.changeSubscribeStatus(userEmail, programTitle, BLOCK);
        return new ResponseEntity<>(OK);
    }
}
