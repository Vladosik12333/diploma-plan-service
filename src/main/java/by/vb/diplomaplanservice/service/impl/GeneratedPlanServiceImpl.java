package by.vb.diplomaplanservice.service.impl;

import by.vb.diplomaplanservice.client.llm.LlmClient;
import by.vb.diplomaplanservice.dto.GeneratePlanRequest;
import by.vb.diplomaplanservice.dto.GeneratedPlanResponse;
import by.vb.diplomaplanservice.dto.GeneratedPlanSummaryResponse;
import by.vb.diplomaplanservice.entity.GeneratedPlan;
import by.vb.diplomaplanservice.entity.enums.GenerationStatus;
import by.vb.diplomaplanservice.exception.ApiException;
import by.vb.diplomaplanservice.mapper.GeneratedPlanResponseMapper;
import by.vb.diplomaplanservice.mapper.GeneratedPlanSummaryResponseMapper;
import by.vb.diplomaplanservice.repository.GeneratedPlanRepository;
import by.vb.diplomaplanservice.service.GeneratedPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GeneratedPlanServiceImpl implements GeneratedPlanService {
	private final GeneratedPlanRepository generatedPlanRepository;
	private final LlmClient llmClient;
	private final GeneratedPlanResponseMapper generatedPlanResponseMapper;
	private final GeneratedPlanSummaryResponseMapper generatedPlanSummaryResponseMapper;

	@Override
	@Transactional
	public GeneratedPlanSummaryResponse create(final Long userId, final GeneratePlanRequest request) {
		final Long tripId = request.tripId();

		if (generatedPlanRepository.existsByTripIdAndStatusNotIn(tripId, GenerationStatus.FINAL_STATUSES)) {
			throw new ApiException(HttpStatus.CONFLICT, "A plan is already being generated for this trip");
		}

		final GeneratedPlan plan = new GeneratedPlan();

		plan.setTripId(request.tripId());
		plan.setProvider(request.provider());
		plan.setUserId(userId);
		plan.setStatus(GenerationStatus.PENDING);
		plan.setModel(llmClient.model(request.provider()));

		try {
			final GeneratedPlan saved = generatedPlanRepository.save(plan);

			return generatedPlanSummaryResponseMapper.mapTo(saved);
		} catch (final DataIntegrityViolationException e) {
			log.warn("Concurrent plan generation rejected. tripId={}", tripId);
			throw new ApiException(HttpStatus.CONFLICT, "A plan is already being generated for this trip", e);
		}
	}

	@Override
	@Transactional
	public void updateStatus(final Long planId, final GenerationStatus status) {
		final GeneratedPlan plan = generatedPlanRepository.findById(planId)
				.orElseThrow(() -> new IllegalStateException("Plan not found"));

		plan.setStatus(status);
		generatedPlanRepository.save(plan);

		log.info("Plan status changed. planId={}, status={}", planId, status);
	}

	@Override
	public List<GeneratedPlanSummaryResponse> getTripPlans(final Long tripId, final Long userId) {
		final List<GeneratedPlan> plans = generatedPlanRepository.findAllByTripIdAndUserIdOrderByCreatedAtDesc(
				tripId, userId);

		log.info("Found {} plans for tripId={}, userId={}", plans.size(), tripId, userId);

		return plans.stream().map(generatedPlanSummaryResponseMapper::mapTo).toList();
	}

	@Override
	public GeneratedPlanResponse getPlan(final Long planId, final Long userId) {
		final GeneratedPlan plan = generatedPlanRepository.findByIdAndUserId(planId, userId)
				.orElseThrow(() -> {
					log.warn("Plan not found. planId={}, userId={}", planId, userId);
					return new ApiException(HttpStatus.NOT_FOUND, "Plan not found");
				});

		return generatedPlanResponseMapper.mapTo(plan);
	}

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void failInterruptedGenerations() {
		final List<GeneratedPlan> interrupted = generatedPlanRepository.findAllByStatusNotIn(GenerationStatus.FINAL_STATUSES);

		interrupted.forEach(plan -> plan.setStatus(GenerationStatus.FAILED));
		generatedPlanRepository.saveAll(interrupted);

		if (!interrupted.isEmpty()) {
			log.warn("Marked {} interrupted plan generations as FAILED", interrupted.size());
		}
	}
}
