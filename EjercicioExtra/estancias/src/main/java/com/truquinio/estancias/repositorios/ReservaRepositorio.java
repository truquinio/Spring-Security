package com.truquinio.estancias.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truquinio.estancias.entidades.Reserva;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, String>{
    //  JpaRepository<Reserva, String> = Extiende de repositorio JPA y maneja entidad Reserva, id = String>
}
