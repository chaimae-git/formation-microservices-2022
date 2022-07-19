package ma.omnishore.clients.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ma.omnishore.clients.domain.Client;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ClientRepositoryTest {

	@Autowired
	ClientRepository clientRepository;

	private Long randomId;

	@BeforeAll
	public void createClients() {

		Client client = new Client("BOUAGGAD", "Amine", "abouaggad@omnidata.ma", "Casablanca");
		randomId = clientRepository.save(client).getId();
	}

	@AfterAll
	public void deleteClients() {
		clientRepository.deleteAll();
	}

	@Test
	void testFindAllClient() {

		Iterable<Client> clients = clientRepository.findAll();
		Assertions.assertThat(clients).extracting(Client::getFirstName).containsOnly("Amine");

		clientRepository.deleteAll();
		Assertions.assertThat(clientRepository.findAll()).isEmpty();
	}

	@Test
	void testFindClient() {

		Client client = clientRepository.findById(randomId).orElse(null);
		Assertions.assertThat(client).isNotNull();
		assertEquals("Amine", client.getFirstName());
	}

	@Test
	void testCreateOrSaveClient() {

		Client client = new Client("BOUAGGAD", "Amine", "abouaggad@omnidata.ma", "Casablanca");
		client = clientRepository.save(client);
		Assertions.assertThat(client).isNotNull();
		Assertions.assertThat(client.getId()).isNotNull();
	}

	@Test
	void testDeleteClient() {

		clientRepository.deleteById(randomId);
		Client client = clientRepository.findById(randomId).orElse(null);
		Assertions.assertThat(client).isNull();
	}

}