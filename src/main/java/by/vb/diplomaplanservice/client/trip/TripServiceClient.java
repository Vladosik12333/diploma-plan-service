package by.vb.diplomaplanservice.client.trip;

import by.vb.diplomaplanservice.dto.BasicResponse;
import by.vb.diplomaplanservice.dto.trip.TripResponse;
import by.vb.diplomaplanservice.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/trip/v1/trips")
public interface TripServiceClient {
	@GetExchange("/{tripId}")
	BasicResponse<TripResponse> getUserTripResponse(@PathVariable Long tripId, @RequestParam Long userId);

	default TripResponse getUserTrip(Long tripId, Long userId) {
		try {
			BasicResponse<TripResponse> response = getUserTripResponse(tripId, userId);

			if (response == null || response.data() == null) {
				throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, "Trip service returned an empty response");
			}

			return response.data();
		} catch (HttpClientErrorException.NotFound e) {
			throw new ApiException(HttpStatus.NOT_FOUND, "Trip not found", e);
		} catch (RestClientException e) {
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, "Trip service is unavailable", e);
		}
	}
}
