package by.vb.diplomaplanservice;

import by.vb.diplomaplanservice.client.trip.TripServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.service.registry.ImportHttpServices;

@SpringBootApplication
@EnableAsync
@ImportHttpServices(group = "trip-service", types = TripServiceClient.class)
public class DiplomaPlanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiplomaPlanServiceApplication.class, args);
	}

}
