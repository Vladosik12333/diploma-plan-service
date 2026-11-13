package by.vb.diplomaplanservice.controller;

import by.vb.diplomaplanservice.dto.BasicResponse;
import by.vb.diplomaplanservice.dto.GeneratePlanRequest;
import by.vb.diplomaplanservice.dto.GeneratedPlanResponse;
import by.vb.diplomaplanservice.dto.GeneratedPlanSummaryResponse;
import by.vb.diplomaplanservice.service.GeneratedPlanService;
import by.vb.diplomaplanservice.service.PlanGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/plans")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Plans", description = "Trip plan generation")
public class PlanController {
	private final GeneratedPlanService generatedPlanService;
	private final PlanGenerationService planGenerationService;

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(
			summary = "Generate a plan",
			description = "Starts generating a new plan for the trip in the background. Poll the plan details endpoint until the status is DONE or FAILED."
	)
	@ApiResponse(responseCode = "202", description = "Generation started")
	@ApiResponse(responseCode = "400", description = "Invalid request")
	@ApiResponse(responseCode = "404", description = "Trip not found for this user")
	@ApiResponse(
			responseCode = "409", description = "A plan is already being generated for this trip"
	)
	public BasicResponse<GeneratedPlanSummaryResponse> generatePlan(
			@Parameter(description = "Owner of the trip", example = "1") @RequestParam @Positive
			Long userId, @Valid @RequestBody GeneratePlanRequest request
	) {
		log.info("Received plan generation request. userId={}, request={}", userId, request);
		return new BasicResponse<>(planGenerationService.start(userId, request), StringUtils.EMPTY,
				true);
	}

	@GetMapping
	@Operation(
			summary = "List trip plans",
			description = "Returns all generated plans of the trip, newest first."
	)
	@ApiResponse(responseCode = "200", description = "Plans returned")
	public BasicResponse<List<GeneratedPlanSummaryResponse>> getTripPlans(
			@Parameter(description = "Trip ID", example = "1") @RequestParam @Positive Long tripId,
			@Parameter(description = "Owner of the trip", example = "1") @RequestParam @Positive
			Long userId
	) {
		log.info("Received trip plans request. tripId={}, userId={}", tripId, userId);
		return new BasicResponse<>(generatedPlanService.getTripPlans(tripId, userId), StringUtils.EMPTY, true);
	}

	@GetMapping("/{planId}")
	@Operation(
			summary = "Get plan details",
			description = "Returns the initial and optimized plan with their reviews."
	)
	@ApiResponse(responseCode = "200", description = "Plan returned")
	@ApiResponse(responseCode = "404", description = "Plan not found for this user")
	public BasicResponse<GeneratedPlanResponse> getPlan(
			@Parameter(description = "Plan ID", example = "1") @PathVariable @Positive Long planId,
			@Parameter(description = "Owner of the plan", example = "1") @RequestParam @Positive
			Long userId
	) {
		log.info("Received plan details request. planId={}, userId={}", planId, userId);
		return new BasicResponse<>(generatedPlanService.getPlan(planId, userId), StringUtils.EMPTY, true);
	}
}
