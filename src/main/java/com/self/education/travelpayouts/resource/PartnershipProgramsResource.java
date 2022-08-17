package com.self.education.travelpayouts.resource;

import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.self.education.travelpayouts.api.ErrorResponse;
import com.self.education.travelpayouts.api.ProgramResponse;
import com.self.education.travelpayouts.service.PartnershipProgramsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/travel-payouts/v1")
public class PartnershipProgramsResource {

    private final PartnershipProgramsService programsService;

    //@formatter:off
    @Operation(summary = "Get all partnership programs",
            description = "Endpoint for getting all partnership programs", responses = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    //@formatter:on
    @GetMapping("/partnership-programs")
    public ResponseEntity<List<ProgramResponse>> findAllPrograms() {
        return ok(programsService.getAllPrograms());
    }

    //@formatter:off
    @Operation(summary = "Get all partnership programs by title with autocomplete and order by popularity desc",
            description = "Endpoint for getting all partnership programs by title with autocomplete and order by popularity desc",
            responses = {
                @ApiResponse(responseCode = "200", description = "Ok"),
                @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    //@formatter:on
    @GetMapping("/partnership-programs/{title}")
    public ResponseEntity<List<ProgramResponse>> findProgramsByTitle(@PathVariable final String title) {
        return ok(programsService.findProgramsByTermOrderByPopularityDesc(title));
    }
}
