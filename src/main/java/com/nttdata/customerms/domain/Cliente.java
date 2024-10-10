package com.nttdata.customerms.domain;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "cliente" , schema = "banco")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="nombre" )
    private String nombre;

    @Column(name = "apellido" )
    private String apellido;

    @Column(name = "dni" , length = 8 , unique = true )
    private String dni;

    @Email
    @Column(name = "email")
    private String email;
}
