package com.egg.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.biblioteca.entidades.Imagen;

@Repository //  @Repository = Indica que es una clase repositorio
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    //  JpaRepository<Imagen, String> = Extiende de repositorio JPA y maneja entidad Imagen, id = String>
}
