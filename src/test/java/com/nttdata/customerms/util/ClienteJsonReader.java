package com.nttdata.customerms.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.customerms.domain.Cliente;
import com.nttdata.customerms.dto.ClienteDto;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClienteJsonReader {

    public static List<Cliente> readClientesEntityFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<List<Cliente>>() {});
    }

    public static List<ClienteDto> readClientesDtoFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<List<ClienteDto>>() {});
    }
}
