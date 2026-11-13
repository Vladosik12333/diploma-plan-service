package by.vb.diplomaplanservice.entity;

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

@Entity
@Table(name = "plan_variants")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"generatedPlan", "items", "review"})
@ToString(exclude = {"generatedPlan", "items", "review"})
public class PlanVariant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "generated_plan_id", nullable = false)
	private GeneratedPlan generatedPlan;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private PlanVariantType type;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@OneToMany(mappedBy = "planVariant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PlanItem> items = new ArrayList<>();

	@OneToOne(mappedBy = "planVariant", cascade = CascadeType.ALL, orphanRemoval = true)
	private PlanReview review;

	public void addItem(PlanItem item) {
		item.setPlanVariant(this);
		items.add(item);
	}

	public void attachReview(PlanReview review) {
		review.setPlanVariant(this);
		this.review = review;
	}
}
