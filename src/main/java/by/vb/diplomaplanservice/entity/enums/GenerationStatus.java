package by.vb.diplomaplanservice.entity.enums;

import java.util.Set;

public enum GenerationStatus {
	PENDING,
	GENERATING,
	OPTIMIZING,
	REVIEWING,
	DONE,
	FAILED;

	public static final Set<GenerationStatus> FINAL_STATUSES = Set.of(DONE, FAILED);

	public boolean isFinal() {
		return FINAL_STATUSES.contains(this);
	}
}
