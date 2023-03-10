package com.truquinio.estancias.entidades;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data // Crea Constr lleno + Getters & Setters + HasCode + equals + toString
@NoArgsConstructor // Crea constructor vacío
@EqualsAndHashCode(callSuper = false)
public class Cliente extends Usuario {

  private String nombre;
  private String calle;
  private Integer numero;
  private String codPostal;
  private String ciudad;
  private String pais;
  private String email;
}

/*
 * La entidad cliente modela información de los clientes que desean mandar a sus
 * hijos a alguna de las casas de las familias. Esta entidad es modelada por el 
 * nombre del cliente, dirección (calle, numero, código postal, ciudad y país) 
 * y su correo electrónico. El repositorio que persiste a esta entidad 
 * (ClienteRepositorio) debe contener los métodos necesarios para
 * guardar/actualizar los datos de un cliente, realizar consultas y eliminar.
 */
