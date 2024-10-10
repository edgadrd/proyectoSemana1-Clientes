package com.nttdata.customerms.service;


import com.nttdata.customerms.dto.ClienteDto;

import java.util.List;

public interface IClienteService {
    ClienteDto createClient(ClienteDto clienteDto);
    List<ClienteDto> findAllClient();
    ClienteDto getClientById(Long id);
    ClienteDto updateClient(ClienteDto clienteDto , Long id);
    void deleteClient(Long id);
}
