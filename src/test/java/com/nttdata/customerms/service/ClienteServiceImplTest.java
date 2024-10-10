package com.nttdata.customerms.service;

import com.nttdata.customerms.client.IFeignCuenta;
import com.nttdata.customerms.domain.Cliente;
import com.nttdata.customerms.dto.ClienteDto;
import com.nttdata.customerms.mapper.ClienteMapper;
import com.nttdata.customerms.repository.IClienteRepository;
import com.nttdata.customerms.service.impl.ClienteServiceImpl;
import com.nttdata.customerms.util.ClienteJsonReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private IClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private IFeignCuenta feignCuenta;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private List<ClienteDto> clienteDtoList;
    private List<Cliente> clienteEntityList;

    @BeforeEach
    void setUp() throws IOException {
        clienteDtoList = ClienteJsonReader.readClientesDtoFromJson("src/test/java/com/nttdata/customerms/resources/clientesDto.json");
        clienteEntityList = ClienteJsonReader.readClientesEntityFromJson("src/test/java/com/nttdata/customerms/resources/clientesEntity.json");
    }

    @Test
    void createClient() {
        when(clienteMapper.clienteDtoToCliente(clienteDtoList.get(0))).thenReturn(clienteEntityList.get(0));
        when(clienteRepository.save(clienteEntityList.get(0))).thenReturn(clienteEntityList.get(0));
        when(clienteMapper.clienteToClienteDto(clienteEntityList.get(0))).thenReturn(clienteDtoList.get(0));
        
        ClienteDto result = clienteService.createClient(clienteDtoList.get(0));

        assertNotNull(result);
    }

    @Test
    void findAllClient() {
        when(clienteRepository.findAll()).thenReturn(clienteEntityList);
        when(clienteMapper.clienteToClienteDto(clienteEntityList.get(0))).thenReturn(clienteDtoList.get(0));
        when(clienteMapper.clienteToClienteDto(clienteEntityList.get(1))).thenReturn(clienteDtoList.get(1));

        List<ClienteDto> result = clienteService.findAllClient();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void getClientById() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(clienteEntityList.get(0)));
        when(clienteMapper.clienteToClienteDto(any(Cliente.class))).thenReturn(clienteDtoList.get(0));

        ClienteDto result = clienteService.getClientById(1L);

        assertNotNull(result);
        assertEquals(clienteDtoList.get(0).getId(), result.getId());
        verify(clienteRepository, times(1)).findById(anyLong());
    }

    @Test
    void getClientById_NotFound() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.getClientById(1L);
        });

        assertEquals("Cliente no existe", exception.getMessage());
        verify(clienteRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateClient() {
        when(clienteRepository.existsById(anyLong())).thenReturn(true);
        when(clienteMapper.clienteDtoToCliente(clienteDtoList.get(0))).thenReturn(clienteEntityList.get(0));
        when(clienteRepository.save(clienteEntityList.get(0))).thenReturn(clienteEntityList.get(0));
        when(clienteMapper.clienteToClienteDto(clienteEntityList.get(0))).thenReturn(clienteDtoList.get(0));

        ClienteDto result = clienteService.updateClient(clienteDtoList.get(0), 1L);

        assertNotNull(result);
        assertEquals(clienteDtoList.get(0).getId(), result.getId());
        verify(clienteRepository, times(1)).existsById(anyLong());
    }

    @Test
    void updateClient_NotFound() {
        when(clienteRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.updateClient(clienteDtoList.get(0), 1L);
        });

        assertEquals("Cliente no existe", exception.getMessage());
        verify(clienteRepository, times(1)).existsById(anyLong());
    }

    @Test
    void deleteClient() {
        when(clienteRepository.existsById(anyLong())).thenReturn(true);
        when(feignCuenta.obtenerCliente(anyLong())).thenReturn(false);

        clienteService.deleteClient(1L);

        verify(clienteRepository, times(1)).existsById(anyLong());
        verify(clienteRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteClient_NotFound() {
        when(clienteRepository.existsById(anyLong())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.deleteClient(1L);
        });

        assertEquals("Cliente no existe", exception.getMessage());
        verify(clienteRepository, times(1)).existsById(anyLong());
    }

    @Test
    void deleteClient_ActiveAccount() {
        when(clienteRepository.existsById(anyLong())).thenReturn(true);
        when(feignCuenta.obtenerCliente(anyLong())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.deleteClient(1L);
        });

        assertEquals("Cuenta activa", exception.getMessage());
        verify(clienteRepository, times(1)).existsById(anyLong());
    }

}