package by.vb.diplomaplanservice.entity;

import by.vb.diplomaplanservice.entity.enums.GenerationStatus;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;
import by.vb.diplomaplanservice.entity.enums.PlanVariantType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "generated_plans")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "variants")
@ToString(exclude = "variants")
public class GeneratedPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "trip_id", nullable = false)
	private Long tripId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private GenerationStatus status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private LlmProvider provider;

	@Column(nullable = false, length = 100)
	private String model;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@OneToMany(mappedBy = "generatedPlan", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PlanVariant> variants = new ArrayList<>();

	public void addVariant(PlanVariant variant) {
		variant.setGeneratedPlan(this);
		variants.add(variant);
	}

	public Optional<PlanVariant> findVariant(PlanVariantType type) {
		return variants.stream().filter(variant -> variant.getType() == type).findFirst();
	}
}
