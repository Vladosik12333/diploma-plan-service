package by.vb.diplomaplanservice.service.impl;

import by.vb.diplomaplanservice.client.trip.TripServiceClient;
import by.vb.diplomaplanservice.dto.GeneratePlanRequest;
import by.vb.diplomaplanservice.dto.GeneratedPlanSummaryResponse;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.service.GeneratedPlanService;
import by.vb.diplomaplanservice.service.PlanGenerationService;
import by.vb.diplomaplanservice.service.PlanGenerationWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanGenerationServiceImpl implements PlanGenerationService {
	private final TripServiceClient tripServiceClient;
	private final GeneratedPlanService generatedPlanService;
	private final PlanGenerationWorker planGenerationWorker;

	@Override
	public GeneratedPlanSummaryResponse start(final Long userId, final GeneratePlanRequest request) {
		final TripResponse trip = tripServiceClient.getUserTrip(request.tripId(), userId);
		final GeneratedPlanSummaryResponse plan = generatedPlanService.create(userId, request);

		log.info("Started plan generation. planId={}, tripId={}, provider={}", plan.id(),
				request.tripId(),
				request.provider());

		planGenerationWorker.generate(plan.id(), trip, request.provider());

		return plan;
	}
}
