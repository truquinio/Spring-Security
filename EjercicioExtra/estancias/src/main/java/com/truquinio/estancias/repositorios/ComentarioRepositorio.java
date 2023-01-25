package com.truquinio.estancias.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truquinio.estancias.entidades.Comentario;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String>{
    //  JpaRepository<Comentario, String> = Extiende de repositorio JPA y maneja entidad Comentario, id = String>
}
