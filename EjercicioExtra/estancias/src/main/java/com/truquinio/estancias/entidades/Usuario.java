package com.truquinio.estancias.entidades;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import com.truquinio.estancias.enumeraciones.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Crea Constr lleno + Getters & Setters + HasCode + equals + toString
@NoArgsConstructor // Crea constructor vacío
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String alias;
    private String email;
    private String clave;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
}

/*
 * La entidad usuario modela los datos de un usuario que accede al sistema para
 * registrarse como familia y ofrecer una habitación de su casa para estancias,
 * o bien, como un cliente que necesita realizar una reserva.
 * De cada usuario se debe registrar el nombre de usuario (alias), el correo
 * electrónico, el password y la fecha de alta. El repositorio que persiste a
 * esta entidad (UsuarioRepositorio) debe contener los métodos necesarios para
 * registrar el usuario en la base de datos, realizar consultas y eliminar.
 */