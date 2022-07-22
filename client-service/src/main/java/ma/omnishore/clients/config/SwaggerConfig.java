package ma.omnishore.clients.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	private static final String OAUTH_SCHEME_NAME = "oAuth";
    private static final String TOKEN_URL_FORMAT = "%s/realms/%s/protocol/openid-connect/token";

	@Value("${keycloak.auth-server-url}")
	private String authServer;
	@Value("${keycloak.realm}")
	private String realm;
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
		return new OpenAPI().info(apiInfo()).components(new Components()
                .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
				.addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
			
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
	
	private SecurityScheme createOAuthScheme() {
		OAuthFlows flows = createOAuthFlows();

		return new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(flows);
	}

	private OAuthFlows createOAuthFlows() {
		OAuthFlow passwordFlow = createPasswordFlow();

		return new OAuthFlows().password(passwordFlow);
	}

	private OAuthFlow createPasswordFlow() {
		String tokenUrl = String.format(TOKEN_URL_FORMAT, authServer, realm);

		return new OAuthFlow().tokenUrl(tokenUrl).scopes(new Scopes());
	}
}