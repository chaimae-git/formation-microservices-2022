package ma.omnishore.clients.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import ma.omnishore.clients.domain.Client;
import ma.omnishore.clients.repository.ClientRepository;
import ma.omnishore.clients.service.impl.ClientService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ClientServiceTest {
	
	@InjectMocks
	ClientService service;

	@Mock
	ClientRepository dao;

	@Test
	void testFindAllClients() {
		List<Client> list = new ArrayList<Client>();
		Client client = new Client("BOUAGGAD", "Amine", "abouaggad@omnidata.ma", "Casablanca");

		list.add(client);

		when(dao.findAll()).thenReturn(list);

		// test
		List<Client> empList = service.getAllClients();

		assertEquals(1, empList.size());
		verify(dao, times(1)).findAll();
	}

	@Test
	void testCreateClient() {
		Client client = new Client("BOUAGGAD", "Amine", "abouaggad@omnidata.ma", "Casablanca");

		service.createClient(client);

		verify(dao, times(1)).save(client);
	}

	@Test
	void testUpdateClient() {
		Client client = new Client("BOUAGGAD", "Amine", "abouaggad@omnidata.ma", "Casablanca");
		client.setId(1L);

		when(dao.existsById(1L)).thenReturn(Boolean.TRUE);

		service.updateClient(client);

		verify(dao, times(1)).existsById(1L);
		verify(dao, times(1)).save(client);
	}

	@Test
	void testDeleteClient() {

		service.deleteClient(1L);

		verify(dao, times(1)).deleteById(1L);
	}
}