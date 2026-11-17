package by.vb.diplomaplanservice.exception;

import by.vb.diplomaplanservice.entity.enums.LlmCallType;
import by.vb.diplomaplanservice.entity.enums.LlmProvider;

public class LlmException extends RuntimeException {
	private final LlmProvider provider;
	private final LlmCallType type;

	public LlmException(final LlmProvider provider, final LlmCallType type, final String message) {
		super(message);
		this.provider = provider;
		this.type = type;
	}

	public LlmException(final LlmProvider provider, final LlmCallType type, final String message,
			final Throwable cause) {
		super(message, cause);
		this.provider = provider;
		this.type = type;
	}

	public LlmProvider getProvider() {
		return provider;
	}

	public LlmCallType getType() {
		return type;
	}
}
