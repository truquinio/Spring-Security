package com.truquinio.estancias.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.truquinio.estancias.entidades.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    //  JpaRepository<Cliente, String> = Extiende de repositorio JPA y maneja entidad Cliente, id = String>

    @Query("SELECT c FROM Cliente c WHERE c.email = :email")
    public Cliente buscarPorEmail(@Param("email") String email);
}