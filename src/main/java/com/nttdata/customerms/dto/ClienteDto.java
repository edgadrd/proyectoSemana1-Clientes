package com.nttdata.customerms.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
}
