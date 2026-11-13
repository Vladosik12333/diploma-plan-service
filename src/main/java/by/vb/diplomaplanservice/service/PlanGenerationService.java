package by.vb.diplomaplanservice.service;

import by.vb.diplomaplanservice.client.trip.TripServiceClient;
import by.vb.diplomaplanservice.dto.GeneratePlanRequest;
import by.vb.diplomaplanservice.dto.GeneratedPlanSummaryResponse;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanGenerationService {
	private final TripServiceClient tripServiceClient;
	private final GeneratedPlanService generatedPlanService;
	private final PlanGenerationWorker planGenerationWorker;

	public GeneratedPlanSummaryResponse start(Long userId, GeneratePlanRequest request) {
		TripResponse trip = tripServiceClient.getUserTrip(request.tripId(), userId);
		GeneratedPlanSummaryResponse plan = generatedPlanService.create(userId, request);

		log.info("Started plan generation. planId={}, tripId={}, provider={}", plan.id(),
				request.tripId(),
				request.provider());

		planGenerationWorker.generate(plan.id(), trip, request.provider());

		return plan;
	}
}
