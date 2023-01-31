package com.truquinio.estancias.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.truquinio.estancias.entidades.Familia;

@Repository
public interface FamiliaRepositorio extends JpaRepository<Familia, String>{
    //  JpaRepository<Familia, String> = Extiende de repositorio JPA y maneja entidad Familia, id = String>

    @Query("SELECT f FROM Familia f WHERE f.email = :email")
    public Familia buscarPorEmail(@Param("email") String email);
}