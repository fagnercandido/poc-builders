package io.platformbuilders.clientes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import io.platformbuilders.clientes.entity.Cliente;
import io.platformbuilders.clientes.control.ClienteControl;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
class ApplicationMockingTests {

	private static final String URL = "/api/v1/clientes";

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private ClienteControl clienteControl;

	@Test
	void deveriaRealizarAInclusaoTest() throws JsonProcessingException, Exception {

		mock.perform(
				post(URL).contentType("application/json").content(mapper.writeValueAsString(getCliente())))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
	}
	
	@Test
	void deveriaRemoverTest() throws JsonProcessingException, Exception {

		BDDMockito.given(clienteControl.findById(Mockito.any(Long.class))).willReturn(Optional.of(getCliente()));

		Long id = 1l;

		mock.perform(
				delete(URL + "/" + id).contentType("application/json").content(""))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isNoContent());
	}
	
	
	@Test
	void deveriaRealizarAAtualizacaoPorParteTest() throws JsonProcessingException, Exception {

		BDDMockito.given(clienteControl.findById(Mockito.any(Long.class))).willReturn(Optional.of(getCliente()));

		Long id = 777L;
		Cliente cliente = getCliente();
		
		cliente.setNome("Dona Florinda");
		cliente.setId(id);

		mock.perform(
				patch(URL + "/" + id).contentType("application/json").content(mapper.writeValueAsString(cliente)))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
	}
	
	@Test
	void deveriaRealizarAAtualizaoDeTodoORegistroTest() throws JsonProcessingException, Exception {

		BDDMockito.given(clienteControl.findById(Mockito.any(Long.class))).willReturn(Optional.of(getCliente()));

		Long id = 777L;
		Cliente cliente = getCliente();
		cliente.setId(id);
		cliente.setIdade(LocalDate.of(2000, 05, 12));

		mock.perform(
				put(URL + "/" + id).contentType("application/json").content(mapper.writeValueAsString(cliente)))
				.andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
	}
	
	private Cliente getCliente() {
		return new Cliente(null, "Tripa Seca", "02124490109", LocalDate.of(1995, 07, 12));
	}

}
