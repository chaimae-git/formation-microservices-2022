package ma.omnishore.clients.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Value("${swagger.title}")
	private String title;
	@Value("${swagger.description}")
	private String description;
	@Value("${swagger.version}")
	private String version;
	@Value("${swagger.contact-name}")
	private String contactName;
	@Value("${swagger.contact-url}")
	private String contactUrl;
	@Value("${swagger.contact-email}")
	private String contactEmail;

	@Bean
	OpenAPI customOpenApi() {
		return new OpenAPI().info(apiInfo());
	}

	private Info apiInfo() {
		return new Info().title(title).description(description).version(version).contact(contact());
	}

	private Contact contact() {
		Contact contact = new Contact();
		contact.setEmail(contactEmail);
		contact.setName(contactName);
		contact.setUrl(contactUrl);
		return contact;
	}
}