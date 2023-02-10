package com.truquinio.estancias.entidades;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Crea Constr lleno + Getters & Setters + HasCode + equals + toString
@NoArgsConstructor // Crea constructor vacío
public class Familia extends Usuario {

    // @Id
    // @GeneratedValue(generator = "uuid")
    // @GenericGenerator(name = "uuid", strategy = "uuid2")
    // private String id;

    private String nombre;
    private Integer edadMin;
    private Integer edadMax;
    private Integer numHijos;
}

/*
 * La entidad familia modela las familias que habitan en diferentes países y que
 * ofrecen alguna de las habitaciones de su hogar para acoger a algún chico
 * (por un módico precio). De cada una de estas familias se conoce el nombre,
 * la edad mínima y máxima de sus hijos, número de hijos y correo
 * electrónico. El repositorio que persiste a esta entidad (FamiliaRepositorio)
 * debe contener los métodos necesarios para guardar/actualizar los datos de las
 * familias en la base de datos, realizar consultas y eliminar o dar de baja
 * según corresponda.
 */