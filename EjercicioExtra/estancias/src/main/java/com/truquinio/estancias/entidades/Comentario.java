package com.truquinio.estancias.entidades;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor // Crea constructor vacío
public class Comentario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;

    @Column(columnDefinition = "TEXT")
    String descripcion;

    @OneToOne
    Casa casa;
}

/*
 * La entidad comentario permite almacenar información brindada por los clientes
 * sobre las casas en las que ya han estado. El repositorio que persiste a esta
 * entidad (ComentarioRepositorio) debe contener los métodos necesarios para
 * guardar los comentarios que realizan los clientes sobre una determinada una
 * vivienda.
 */