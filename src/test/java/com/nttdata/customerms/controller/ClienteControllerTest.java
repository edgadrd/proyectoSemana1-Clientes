package com.nttdata.customerms.controller;
import com.nttdata.customerms.dto.ClienteDto;
import com.nttdata.customerms.service.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    public void testListarClientes() throws Exception {
        List<ClienteDto> clientes = Arrays.asList(new ClienteDto(), new ClienteDto());
        when(clienteService.findAllClient()).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(clientes.size()));

        verify(clienteService, times(1)).findAllClient();
    }

    @Test
    public void testObtenerCliente() throws Exception {
        Long id = 1L;
        ClienteDto clienteDto = new ClienteDto();
        when(clienteService.getClientById(id)).thenReturn(clienteDto);

        mockMvc.perform(get("/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteDto.getId()));

        verify(clienteService, times(1)).getClientById(id);
    }

    @Test
    public void testCrearCliente() throws Exception {
        ClienteDto clienteDto = new ClienteDto();
        when(clienteService.createClient(any(ClienteDto.class))).thenReturn(clienteDto);

        mockMvc.perform(post("/clientes")
                .contentType("application/json")
                .content("{\"name\":\"Test\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(clienteDto.getId()));

        verify(clienteService, times(1)).createClient(any(ClienteDto.class));
    }

    @Test
    public void testActualizarCliente() throws Exception {
        Long id = 1L;
        ClienteDto clienteDto = new ClienteDto();
        when(clienteService.updateClient(any(ClienteDto.class), eq(id))).thenReturn(clienteDto);

        mockMvc.perform(put("/clientes/{id}", id)
                .contentType("application/json")
                .content("{\"name\":\"Updated Test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clienteDto.getId()));

        verify(clienteService, times(1)).updateClient(any(ClienteDto.class), eq(id));
    }

    @Test
    public void testEliminarCliente() throws Exception {
        Long id = 1L;
        doNothing().when(clienteService).deleteClient(id);

        mockMvc.perform(delete("/clientes/{id}", id))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deleteClient(id);
    }
}