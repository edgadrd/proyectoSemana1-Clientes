package com.nttdata.customerms.service.impl;


import com.nttdata.customerms.client.IFeignCuenta;
import com.nttdata.customerms.dto.ClienteDto;
import com.nttdata.customerms.mapper.ClienteMapper;
import com.nttdata.customerms.repository.IClienteRepository;
import com.nttdata.customerms.service.IClienteService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final IFeignCuenta feignCuenta;


    @Override
    public ClienteDto createClient(ClienteDto clienteDto) {
        return clienteMapper.clienteToClienteDto(
                this.clienteRepository.save(
                        clienteMapper.clienteDtoToCliente(clienteDto)));
    }

    @Override
    public List<ClienteDto> findAllClient() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::clienteToClienteDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDto getClientById(Long id) {

        return clienteMapper.clienteToClienteDto(this.clienteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente no existe")
        ));
    }

    @Override
    public ClienteDto updateClient(ClienteDto clienteDto , Long id)  {

        boolean exists = clienteRepository.existsById(clienteDto.getId());
        if (!exists) {
             throw new RuntimeException("Cliente no existe");
        }
        return clienteMapper.clienteToClienteDto(
                this.clienteRepository.save(
                        clienteMapper.clienteDtoToCliente(clienteDto)));

    }

    @Override
    public void deleteClient(Long id) {
        boolean exists = clienteRepository.existsById(id);
        if (!exists) {
            throw new RuntimeException("Cliente no existe");
        }

        try {
            boolean cuentaActiva = feignCuenta.obtenerCliente(id);
            if (cuentaActiva) {
                throw new RuntimeException("Cuenta activa");
            }
        }catch (FeignException e){
            throw new RuntimeException("Error al comunicarse con el servicio de cuentas: " + e.getMessage());
        }

        this.clienteRepository.deleteById(id);
    }
}
