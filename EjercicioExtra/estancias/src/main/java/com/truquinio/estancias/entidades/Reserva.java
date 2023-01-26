package com.truquinio.estancias.entidades;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Crea Constr lleno + Getters & Setters + HasCode + equals + toString
@NoArgsConstructor // Crea constructor vacío
public class Reserva {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;
  private String huesped;

  @Temporal(TemporalType.DATE)
  private Date fechaDesde;

  @Temporal(TemporalType.DATE)
  private Date fechaHasta;

  @OneToOne
  private Cliente cliente;

  @OneToOne
  private Casa casa;
}

/*
 * La entidad reserva modela los datos de las reservas y estancias realizadas
 * por alguno de los clientes. Cada estancia o reserva la realiza un cliente,
 * y además, el cliente puede reservar varias habitaciones al mismo tiempo
 * (por ejemplo para varios de sus hijos), para un periodo determinado
 * (fecha_llegada, fecha_salida). El repositorio que persiste a esta entidad
 * (ReservaRepositorio) debe contener los métodos necesarios para realizar
 * una reserva, actualizar los datos (por ejemplo, fecha de la reserva),
 * realizar consultas de las reservas realizadas para una determinada vivienda
 * y eliminar reserva.
 */