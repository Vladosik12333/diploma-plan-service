package by.vb.diplomaplanservice.repository;

import by.vb.diplomaplanservice.entity.GeneratedPlan;
import by.vb.diplomaplanservice.entity.enums.GenerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GeneratedPlanRepository extends JpaRepository<GeneratedPlan, Long> {
	Optional<GeneratedPlan> findByIdAndUserId(Long id, Long userId);

	List<GeneratedPlan> findAllByTripIdAndUserIdOrderByCreatedAtDesc(Long tripId, Long userId);

	boolean existsByTripIdAndStatusNotIn(Long tripId, Collection<GenerationStatus> statuses);

	List<GeneratedPlan> findAllByStatusNotIn(Collection<GenerationStatus> statuses);
}
