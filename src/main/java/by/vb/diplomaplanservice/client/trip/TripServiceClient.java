package by.vb.diplomaplanservice.client.trip;

import by.vb.diplomaplanservice.dto.BasicResponse;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.exception.ApiException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;


@HttpExchange("/api/trip/v1/trips")
public interface TripServiceClient {
	@GetExchange("/{tripId}")
	@Retry(name = "trip-service", fallbackMethod = "getUserTripFallback")
	@CircuitBreaker(name = "trip-service")
	BasicResponse<TripResponse> getUserTripResponse(@PathVariable Long tripId, @RequestParam Long userId);

	default BasicResponse<TripResponse> getUserTripFallback(final Long tripId, final Long userId, final Throwable throwable) {
		throw switch (throwable) {
			case final HttpClientErrorException.NotFound e -> new ApiException(HttpStatus.NOT_FOUND, "Trip not found", e);
			default -> new ApiException(HttpStatus.SERVICE_UNAVAILABLE, "Trip service is unavailable", throwable);
		};
	}
}
