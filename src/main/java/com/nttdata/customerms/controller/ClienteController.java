package com.nttdata.customerms.controller;


import com.nttdata.customerms.dto.ClienteDto;
import com.nttdata.customerms.service.IClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("clientes")
public class ClienteController {

    private final IClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDto>> listarClientes() {
        return new ResponseEntity<>(clienteService.findAllClient(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDto> obtenerCliente(@PathVariable ("id") Long id) {
        return new ResponseEntity<>(clienteService.getClientById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClienteDto> crearCliente( @RequestBody ClienteDto clienteDto) {
        return new ResponseEntity<>(clienteService.createClient(clienteDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@RequestBody ClienteDto clienteDto, @PathVariable ("id") Long id) {
        return new ResponseEntity<>(clienteService.updateClient(clienteDto , id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
