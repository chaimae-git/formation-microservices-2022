package ma.omnishore.clients.integration;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ma.omnishore.clients.api.ClientController;
import ma.omnishore.clients.config.WithMockOAuth2Conext;
import ma.omnishore.clients.domain.Client;
 
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
class ClientIntegrationTests {
 
  @Autowired
  ClientController clientController;
 
  @Test
  @WithMockOAuth2Conext(authorities = "user")
  void testCreateReadDelete() {
	  Client client = new Client("BOUAGGAD", "Amine", "abouaggad@omnidata.ma", "Casablanca");
 
    ResponseEntity<Client> clientResult = clientController.createClient(client);
 
    ResponseEntity<List<Client>> clients = clientController.findAll();
	Assertions.assertThat(clients.getBody()).isNotEmpty();
 
    clientController.deleteClient(clientResult.getBody().getId());
    Assertions.assertThat(clientController.findAll().getBody()).isEmpty();
  }
}