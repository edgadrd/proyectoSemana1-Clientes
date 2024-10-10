package com.nttdata.customerms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "feignCuenta", url = "http://localhost:8081/cuenta/cuenta/")
public interface IFeignCuenta {

    @GetMapping(value = "/clienteCuenta/{id}")
    boolean obtenerCliente(@PathVariable("id") Long id);
}
