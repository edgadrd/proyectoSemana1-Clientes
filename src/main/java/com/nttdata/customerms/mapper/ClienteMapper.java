package com.nttdata.customerms.mapper;

import com.nttdata.customerms.domain.Cliente;
import com.nttdata.customerms.dto.ClienteDto;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente clienteDtoToCliente (ClienteDto clienteDto);
    ClienteDto clienteToClienteDto(Cliente cliente);

}
