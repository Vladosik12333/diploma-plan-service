package by.vb.diplomaplanservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI diplomaPlanOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Diploma Plan Service API")
						.description("API for generating trip plans.")
						.version("v1")
						.contact(new Contact().name("Diploma Plan Service")))
				.servers(List.of(new Server().url("/api/plan")));
	}
}
