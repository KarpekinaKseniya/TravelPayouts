package com.self.education.travelpayouts.resource;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.self.education.travelpayouts.api.ErrorResponse;
import com.self.education.travelpayouts.api.UserResponse;
import com.self.education.travelpayouts.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/travel-payouts/v1")
public class UserResource {

    private final UserService userService;

    //@formatter:off
    @Operation(summary = "Get all users",
        description = "Endpoint for getting all users", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    //@formatter:on
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ok(userService.findAllUsers());
    }
}
