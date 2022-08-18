package com.self.education.travelpayouts.resource;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.self.education.travelpayouts.api.ErrorResponse;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.api.UserRequest;
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

    //@formatter:off
    @Operation(
            summary = "Add new user",
            description = "Endpoint for added new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    //@formatter:on
    @PostMapping("/user")
    public ResponseEntity<Long> createUser(@RequestBody @Valid final UserRequest request) {
        return new ResponseEntity<>(userService.createNewUser(request), CREATED);
    }

    //@formatter:off
    @Operation(summary = "Get all user subscribed programs",
            description = "Endpoint for getting all user subscribed programs", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    //@formatter:on
    @GetMapping("/user/{id}/programs")
    public ResponseEntity<List<ProgramResponse>> findAllUserPrograms(@PathVariable final Long id) {
        return ok(userService.findAllUserProgramsById(id));
    }
}
