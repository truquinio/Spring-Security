package com.truquinio.estancias.entidades;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Crea Constr lleno + Getters & Setters + HasCode + equals + toString
@NoArgsConstructor // Crea constructor vacío
public class Casa {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String calle;
    private int numero;
    private String ciudad;
    private String pais;

    @Temporal(TemporalType.DATE)
    private Date fechaDesde;

    @Temporal(TemporalType.DATE)
    private Date fechaHasta;

    private int minDias;
    private int maxDias;
    private Double precio;
    private String tipoVivienda;

}

/*
 * La entidad casa modela los datos de las casas donde las familias ofrecen
 * alguna habitación. De cada una de las casas se almacena la dirección
 * (calle, numero, código postal, ciudad y país), el periodo de
 * disponibilidad de la casa (fecha_desde, fecha_hasta), la cantidad
 * de días mínimo de estancia y la cantidad máxima de días, el precio
 * de la habitación por día y el tipo de vivienda. El repositorio que
 * persiste a esta entidad (CasaRepositorio) debe contener los métodos
 * necesarios para guardar/actualizar los datos de una vivienda, realizar
 * consultas y eliminar.
 */