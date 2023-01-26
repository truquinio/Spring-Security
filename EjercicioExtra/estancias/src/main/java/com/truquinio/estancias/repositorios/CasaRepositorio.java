package com.truquinio.estancias.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truquinio.estancias.entidades.Casa;

@Repository
public interface CasaRepositorio extends JpaRepository<Casa, String>{
    //  JpaRepository<Casa, String> = Extiende de repositorio JPA y maneja entidad Casa, id = String>
}
