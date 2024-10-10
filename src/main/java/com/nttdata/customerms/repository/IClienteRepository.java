package com.nttdata.customerms.repository;


import com.nttdata.customerms.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Long> {


   // boolean existsById(Long id);
}
