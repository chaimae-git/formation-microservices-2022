package ma.omnishore.clients.api;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import ma.omnishore.clients.domain.Client;
import ma.omnishore.clients.service.IClientService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
@ActiveProfiles("test")
class ClientControllerTest {

	@MockBean
	private IClientService clientService;

	@Autowired
	MockMvc mockMvc;

	private final Gson gson = new Gson();

	@Test
	void testfindAll() throws Exception {
		Client client = new Client("BOUAGGAD", "Amine", "abouaggad@omnidata.ma", "Casablanca");
		List<Client> clients = Arrays.asList(client);

		Mockito.when(clientService.getAllClients()).thenReturn(clients);

		mockMvc.perform(get("/api/client")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].lastName", Matchers.is("BOUAGGAD")));
	}

	@Test
	void testGetClient() throws Exception {
		Client client1 = new Client("Test1", "Test1", "test1@test.ma", "Address 1");
		client1.setId(1L);

		Mockito.when(clientService.getClient(1L)).thenReturn(client1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/client/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		JSONAssert.assertEquals(gson.toJson(client1), result.getResponse().getContentAsString(), false);
	}

	@Test
	void testCreateClient() throws Exception {
		Client client1 = new Client("Test1", "Test1", "test1@test.ma", "Address 1");

		Mockito.when(clientService.createClient(any())).thenReturn(client1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/client").accept(MediaType.APPLICATION_JSON)
				.content(gson.toJson(client1)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();

		JSONAssert.assertEquals(gson.toJson(client1), result.getResponse().getContentAsString(), false);
	}

	@Test
	void testUpdateClient() throws Exception {
		Client client1 = new Client("Test1", "Test1", "test1@test.ma", "Address 1");
		client1.setId(1L);
		Client client2 = new Client("Test2", "Test2", "test2@test.ma", "Address 2");
		;
		client2.setId(1L);
		Mockito.when(clientService.updateClient(any())).thenReturn(client2);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/client").accept(MediaType.APPLICATION_JSON)
				.content(gson.toJson(client2)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		JSONAssert.assertEquals(gson.toJson(client2), result.getResponse().getContentAsString(), false);

	}

	@Test
	void testDeleteClient() throws Exception {

		Mockito.doNothing().when(clientService).deleteClient(1L);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/client/1")
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

}