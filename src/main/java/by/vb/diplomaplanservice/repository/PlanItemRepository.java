package by.vb.diplomaplanservice.repository;

import by.vb.diplomaplanservice.entity.PlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanItemRepository extends JpaRepository<PlanItem, Long> {
}
